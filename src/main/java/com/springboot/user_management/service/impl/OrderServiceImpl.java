package com.springboot.user_management.service.impl;

import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.request.OrderSearchDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.OrderResponsePagingDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.CustomerOrder;
import com.springboot.user_management.entity.OrderDetail;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.enums.OrderDetailStatus;
import com.springboot.user_management.enums.OrderStatus;
import com.springboot.user_management.enums.PaymentMethod;
import com.springboot.user_management.mapper.response.OrderResponseDtoMapper;
import com.springboot.user_management.repository.CustomerOrderRepository;
import com.springboot.user_management.repository.OrderDetailRepository;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.service.OrderService;
import com.springboot.user_management.service.VoucherService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderResponseDtoMapper orderResponseDtoMapper;

    @Autowired
    private VoucherService voucherService;

    @Override
    public ResponseEntity<BaseResponse<OrderResponseDTO>> viewOrderById(Integer id) {
        try {
            if (!customerOrderRepository.existsById(id)) {
                throw new BadRequestException(FailureMessage.DATA_NOT_FOUND);
            }

            CustomerOrder customerOrder = customerOrderRepository.getReferenceById(id);
            OrderResponseDTO requestDTO = orderResponseDtoMapper.toDTO(customerOrder);
            return ResponseFactory.success(HttpStatus.OK, requestDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<OrderResponseDTO>> createOrder(OrderRequestDTO dto) {
        try {
            dto.trimFields();

            User user = userRepository.getReferenceById(1);

            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setUser(user);
            customerOrder.setStatus(OrderStatus.PENDING);
            customerOrder.setShippingAddress(dto.getShippingAddress());
            customerOrder.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));

            Integer totalPrice = 0;
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (OrderRequestDTO.ProductDTO productDTO : dto.getProducts()) {
                Product product = productRepository.findByIdAndStatusIsTrue(productDTO.getId())
                        .orElseThrow(() -> new BadRequestException(FailureMessage.DATA_NOT_FOUND));
                if (productDTO.getQuantity() <= 0) {
                    throw new BadRequestException(ValidationMessage.NOT_SELECT_PRODUCT);
                }
                if (productDTO.getQuantity() > product.getQuantity()) {
                    throw new BadRequestException(ValidationMessage.OUT_OF_STOCK);
                }

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(productDTO.getQuantity());
                orderDetail.setCustomerOrder(customerOrder);
                orderDetail.setProduct(product);
                orderDetail.setStatus(OrderDetailStatus.PENDING);
                orderDetail.setPrice(product.getPrice());
                orderDetail.setTotal(orderDetail.getQuantity() *orderDetail.getPrice());
                orderDetailList.add(orderDetail);
                totalPrice += orderDetail.getTotal();
            }

            if (dto.getVoucher() != null && !dto.getVoucher().isEmpty()) {
                int discount = voucherService.applyVoucher(dto.getVoucher(), totalPrice);
                totalPrice = totalPrice - discount;
            }
            customerOrder.setTotalPrice(totalPrice);
            customerOrder.setOrderDetails(orderDetailList);

            orderDetailRepository.saveAll(orderDetailList);
            customerOrderRepository.save(customerOrder);
            OrderResponseDTO requestDTO = orderResponseDtoMapper.toDTO(customerOrder);
            return ResponseFactory.success(HttpStatus.OK, requestDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<OrderResponsePagingDTO>> getAllOrderByConditions(OrderSearchDTO dto, Integer page, Integer size) {
        try {
            dto.trimFields();

            if (dto.getStartDate() == null || dto.getEndDate() == null) {
                throw new BadRequestException("Vui long chon ngay bat dau va ngay ket thuc!");
            }

            if (dto.getMinAmount() == null && dto.getMaxAmount() != null) {
                dto.setMinAmount(0);
            }

            if (dto.getMinAmount() != null && dto.getMaxAmount() == null) {
                dto.setMaxAmount(Integer.MAX_VALUE);
            }

            if (dto.getMinAmount() != null && dto.getMaxAmount() != null
                    && dto.getMinAmount() > dto.getMaxAmount()) {
                throw new BadRequestException("minAmount khong duoc lon hon maxAmount!");
            }

            String startDate = LocalDate.parse(dto.getStartDate()).atStartOfDay()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String endDate = LocalDate.parse(dto.getEndDate()).atTime(23, 59, 59)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Pageable pageable = PageRequest.of(page - 1, size);
            Page<CustomerOrder> customerOrderPage = customerOrderRepository.findAllByConditions(dto.getOrderStatus(), startDate, endDate, dto.getMinAmount(), dto.getMaxAmount(), dto.getCustomerName(), dto.getPaymentMethod(), pageable);
            List<OrderResponseDTO> responseDTOList = customerOrderPage.getContent().stream().map(orderResponseDtoMapper::toDTO).collect(Collectors.toList());
            OrderResponsePagingDTO responsePagingDTO = new OrderResponsePagingDTO(Metadata.build(customerOrderPage), responseDTOList);
            return ResponseFactory.success(HttpStatus.OK, responsePagingDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
}
