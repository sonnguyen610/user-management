package com.springboot.user_management.repository.custom.impl;

import com.springboot.user_management.dto.request.ProductSearchDTO;
import com.springboot.user_management.dto.response.ProductResponseDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.mapper.response.ProductResponseDtoMapper;
import com.springboot.user_management.repository.custom.ProductCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final EntityManager entityManager;

    @Autowired
    private ProductResponseDtoMapper productResponseDtoMapper;

    @Override
    public ProductResponsePagingDTO findAllProductByConditions(ProductSearchDTO dto, Integer page, Integer size) throws BadRequestException {
        StringBuilder sql = new StringBuilder("select p.* from product p " +
                "join brand b on b.id = p.brand_id " +
                "join category c on c.id = p.category_id " +
                "where p.status = true and b.status = true and c.status = true ");
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            sql.append("and p.name like :name ");
        }
        if (dto.getBrand() != null && !dto.getBrand().isEmpty()) {
            sql.append("and b.brand_id in (:brandIds) ");
        }
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            sql.append("and b.category_id in (:categoryIds) ");
        }
        if (dto.getMinPrice() != null && dto.getMaxPrice() !=null) {
            boolean checkPrice = dto.getMinPrice() <= dto.getMaxPrice();
            if (!checkPrice) {
                throw new BadRequestException("Min price cannot greater than max price");
            } else {
                sql.append("and p.price between :minPrice and :maxPrice ");
            }
        }
        switch (dto.getSortBy().trim()) {
            case "saleCount":
                sql.append("order by p.sale_count desc");
                break;
            case "price":
                sql.append(String.format("order by p.price %s",
                        dto.getSortType() == null ? "desc" : dto.getSortType().trim()));
            default:
                sql.append("order by p.created_at desc");
                break;
        }

        Query query = entityManager.createNativeQuery(sql.toString(), Product.class);
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            query.setParameter("name", "%" + dto.getName().trim() + "%");
        }
        if (dto.getBrand() != null && !dto.getBrand().isEmpty()) {
            query.setParameter("brandIds", dto.getBrand());
        }
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            query.setParameter("categoryIds", dto.getCategory());
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        long total =((Number) query.getSingleResult()).longValue();
        int totalPage = (int) Math.ceil((double) total /size);

        List<Product> productList = query.getResultList();
        List<ProductResponseDTO> dtoList = productResponseDtoMapper.toListDTO(productList);

        Metadata metadata = new Metadata(page, size, totalPage, total);
        ProductResponsePagingDTO response = new ProductResponsePagingDTO(metadata, dtoList);

        return response;
    }
}
