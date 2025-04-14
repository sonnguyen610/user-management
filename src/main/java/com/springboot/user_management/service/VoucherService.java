package com.springboot.user_management.service;

import com.springboot.user_management.entity.Voucher;
import com.springboot.user_management.utils.BaseResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;

public interface VoucherService {
    ResponseEntity<BaseResponse<Voucher>> createVoucher(Voucher voucher);

    ResponseEntity<BaseResponse<Voucher>> getVoucher(Integer id);

    int applyVoucher(String code, Integer totalPrice) throws BadRequestException;
}
