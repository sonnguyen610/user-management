package com.springboot.user_management.repository.custom;

import com.springboot.user_management.dto.request.ProductSearchDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import org.apache.coyote.BadRequestException;

public interface ProductCustomRepository {

    ProductResponsePagingDTO findAllProductByConditions(ProductSearchDTO dto, Integer page, Integer size) throws BadRequestException;
}
