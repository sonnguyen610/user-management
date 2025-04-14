package com.springboot.user_management.dto.response.paging;

import com.springboot.user_management.dto.response.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponsePagingDTO extends ResponseData {
    Metadata metadata;
    List<OrderResponseDTO> response;
}
