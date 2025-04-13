package com.springboot.user_management.mapper.response;

import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.entity.CustomerOrder;
import com.springboot.user_management.entity.OrderDetail;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.mapper.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderResponseDtoMapper extends MapStructMapper<CustomerOrder, OrderResponseDTO> {

    @Override
    @Mapping(source = "user", target = "user", qualifiedByName = "getUserDTO")
    @Mapping(source = "orderDetails", target = "orderDetails", qualifiedByName = "getOrderDetailDTO")
    OrderResponseDTO toDTO(CustomerOrder entity);

    @Named("getUserDTO")
    default OrderResponseDTO.UserDTO getUserDTO(User user) {
        if (user == null) {
            return null;
        }
        return new OrderResponseDTO.UserDTO(user.getId(), user.getUsername(), user.getFullName());
    }

    @Named("getOrderDetailDTO")
    default List<OrderResponseDTO.OrderDetailDTO> getOrderDetailDTO(List<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            return null;
        }
        List<OrderResponseDTO.OrderDetailDTO> list = new ArrayList<>();
        for (OrderDetail detail : orderDetails) {
            OrderResponseDTO.OrderDetailDTO dto = new OrderResponseDTO.OrderDetailDTO();
            dto.setId(detail.getId());
            dto.setProductTd(detail.getProduct().getId());
            dto.setProductName(detail.getProduct().getName());
            dto.setQuantity(detail.getQuantity());
            dto.setStatus(detail.getStatus());
            list.add(dto);
        }
        return list;
    }
}
