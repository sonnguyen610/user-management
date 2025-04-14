package com.springboot.user_management.controller.impl;

import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.controller.VoucherController;
import com.springboot.user_management.entity.Voucher;
import com.springboot.user_management.exception.ValidationException;
import com.springboot.user_management.service.VoucherService;
import com.springboot.user_management.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VoucherControllerImpl implements VoucherController {
    @Autowired
    private VoucherService voucherService;

    @Override
    public ResponseEntity<BaseResponse<Voucher>> createVoucher(Voucher voucher, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors, ValidationMessage.VALIDATION_FAILED);
        }

        return voucherService.createVoucher(voucher);
    }

    @Override
    public ResponseEntity<BaseResponse<Voucher>> getVoucher(Integer id) {
        return voucherService.getVoucher(id);
    }
}
