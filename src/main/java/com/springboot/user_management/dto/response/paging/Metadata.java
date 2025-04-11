package com.springboot.user_management.dto.response.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Metadata {
    private int page;
    private int size;
    private int totalPage;
    private long total;

    public static <I> Metadata build(Page<I> page) {
        return Metadata.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .total(page.getTotalElements())
                .build();
    }

    public static Metadata buildEmpty() {
        return Metadata.builder()
                .page(0)
                .size(0)
                .totalPage(0)
                .total(0)
                .build();
    }
}
