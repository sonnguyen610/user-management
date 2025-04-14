package com.springboot.user_management.controller;

import com.springboot.user_management.entity.Voucher;
import com.springboot.user_management.utils.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/vouchers")
public interface VoucherController {

    @PostMapping
    ResponseEntity<BaseResponse<Voucher>> createVoucher(@Valid @RequestBody Voucher voucher, BindingResult bindingResult);

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<Voucher>> getVoucher(@PathVariable Integer id);
}
