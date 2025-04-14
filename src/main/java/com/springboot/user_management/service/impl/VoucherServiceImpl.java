package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.Constants;
import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.entity.Voucher;
import com.springboot.user_management.repository.VoucherRepository;
import com.springboot.user_management.service.VoucherService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public ResponseEntity<BaseResponse<Voucher>> createVoucher(Voucher voucher) {
        try {
            voucher.setCode(generateVoucherCode());
            voucher.setExpiryDate(LocalDate.now().plusDays(30));
            voucherRepository.save(voucher);
            return ResponseFactory.success(HttpStatus.OK, voucher, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<Voucher>> getVoucher(Integer id) {
        try {
            Voucher voucher = voucherRepository.findByIdAndStatusIsTrue(id).orElseThrow(() -> new BadRequestException(FailureMessage.DATA_NOT_FOUND));
            return ResponseFactory.success(HttpStatus.OK, voucher, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public int applyVoucher(String code, Integer totalPrice) throws BadRequestException {
        Voucher voucher = voucherRepository.findByCodeAndStatusIsTrue(code)
                .orElseThrow(() -> new BadRequestException("Voucher not exists or inactivated!"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.parse(formatter.format(new Date()));
        if (voucher.getExpiryDate().isBefore(currentDate)) {
            throw new BadRequestException("Voucher has expired!");
        }

        if (totalPrice < voucher.getMinAmount()) {
            throw new BadRequestException("Total order amount is less than the minimum voucher amount!");
        }

        int discount = (totalPrice * voucher.getPercent()) / 100;
        if (discount > voucher.getMaxAmount()) {
            discount = voucher.getMaxAmount();
        }

        return discount;
    }

    private String generateVoucherCode() {
        String chars = Constants.VOUCHER_CHARACTERS;
        Random random = new Random();
        return random.ints(Constants.VOUCHER_MAX_LENGTH, 0, chars.length())
                .mapToObj(i -> String.valueOf(chars.charAt(i)))
                .reduce("", String::concat);
    }
}
