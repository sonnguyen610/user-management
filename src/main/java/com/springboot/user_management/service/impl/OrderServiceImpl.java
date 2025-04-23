package com.springboot.user_management.service.impl;

import com.springboot.user_management.config.SecurityUtils;
import com.springboot.user_management.constant.Constants;
import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.OrderRequestDTO;
import com.springboot.user_management.dto.request.OrderSearchDTO;
import com.springboot.user_management.dto.response.OrderResponseDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.OrderResponsePagingDTO;
import com.springboot.user_management.dto.response.paging.ProductResponsePagingDTO;
import com.springboot.user_management.entity.*;
import com.springboot.user_management.enums.OrderDetailStatus;
import com.springboot.user_management.enums.OrderStatus;
import com.springboot.user_management.enums.PaymentMethod;
import com.springboot.user_management.mapper.response.OrderResponseDtoMapper;
import com.springboot.user_management.repository.*;
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

    @Autowired
    private CartRepository cartRepository;

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
            String username = SecurityUtils.getUsername();

            User user = userRepository.findUserByUsername(username);

            List<ProductDTO> productList = getProductListByOrderRequest(dto, user.getId());

            dto.trimFields();
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setUser(user);
            customerOrder.setStatus(OrderStatus.PENDING);
            customerOrder.setShippingAddress(dto.getShippingAddress());
            customerOrder.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));

            Integer totalPrice = 0;
            List<OrderDetail> orderDetailList = new ArrayList<>();
            for (ProductDTO productDTO : productList) {
                Product product = productRepository.findProductById(productDTO.id());
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setQuantity(productDTO.quantity());
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

    private List<ProductDTO> getProductListByOrderRequest(OrderRequestDTO dto, Integer id) throws BadRequestException {
        List<ProductDTO> productList = new ArrayList<>();

        if (Constants.ORDER_TYPE.CART.equals(dto.getOrderType())) {
            if (dto.getProductIds() == null || dto.getProductIds().isEmpty()) {
                throw new BadRequestException(ValidationMessage.PRODUCT_NOT_SELECTED);
            }

            List<Cart> cartListByUser = cartRepository.findAllByUserId(id);
            List<Integer> productIdsByUser = cartListByUser.stream().map(Cart::getProductId).collect(Collectors.toList());
            if (dto.getProductIds().stream().anyMatch(e -> !productIdsByUser.contains(e))) {
                throw new BadRequestException(FailureMessage.PRODUCT_NOT_IN_CART);
            }

            List<Cart> cartListByOrder = cartListByUser.stream()
                    .filter(cart -> dto.getProductIds().contains(cart.getProductId()))
                    .collect(Collectors.toList());
            for (Cart cart : cartListByOrder) {
                ProductDTO productDTO = new ProductDTO(cart.getProductId(), cart.getQuantity());
                productList.add(productDTO);
            }
        } else if (Constants.ORDER_TYPE.DIRECT.equals(dto.getOrderType())) {
            if (dto.getProduct() == null) {
                throw new BadRequestException(ValidationMessage.PRODUCT_NOT_SELECTED);
            }

            Product product = productRepository.findById(dto.getProduct().getId())
                    .orElseThrow(() -> new BadRequestException(FailureMessage.PRODUCT_NOT_FOUND));
            if (!product.getStatus()) {
                throw new BadRequestException(FailureMessage.DISABLED_PRODUCT);
            }
            if (dto.getProduct().getQuantity() == null || dto.getProduct().getQuantity() < 1) {
                throw new BadRequestException(ValidationMessage.PRODUCT_NOT_SELECTED);
            }
            if (dto.getProduct().getQuantity() > product.getQuantity()) {
                throw new BadRequestException(ValidationMessage.OUT_OF_STOCK);
            }

            ProductDTO productDTO = new ProductDTO(dto.getProduct().getId(), dto.getProduct().getQuantity());
            productList.add(productDTO);
        } else {
            throw new BadRequestException(ValidationMessage.ORDER_TYPE_NOT_VALID);
        }

        return productList;
    }

    private record ProductDTO(Integer id, Integer quantity) {}

//    @Override
//    public void cancelOrder(Integer id, OrderUpdateRequestDTO dto) throws BadRequestException {
//        String username = SecurityUtils.getUsername();
//        Set<String> roles = SecurityUtils.getRoles();
//
//        CustomerOrder order = customerOrderRepository.findById(id)
//                .orElseThrow(() -> new BadRequestException("Order info not found!"));
//
//        if (order.getStatus().equals(OrderStatus.PENDING)
//                || order.getStatus().equals(OrderStatus.CONFIRMED)
//                || order.getStatus().equals(OrderStatus.SHIPPED)) {
//            order.setStatus(OrderStatus.CANCELLED);
//
//            OrderHistory orderHistory = new OrderHistory();
//            orderHistory.setOrderId(order.getId());
//            orderHistory.setStatus(String.valueOf(OrderStatus.CANCELLED));
//            orderHistory.setNote(dto.getCancelReason().trim());
//            orderHistory.setUpdatedBy(username);
//
//            List<OrderDetail> orderDetails = order.getOrderDetails();
//            List<ShippingHistory> shippingHistories = new ArrayList<>();
//            if (orderDetails != null && !orderDetails.isEmpty()) {
//                for (OrderDetail detail : orderDetails) {
//                    Integer shipperId = shippingHistoryRepository.findShipperIdByOrderId(order.getId());
//                    if (shipperId != null) {
//                        ShippingHistory shippingHistory = new ShippingHistory();
//                        shippingHistory.setOrderId(order.getId());
//                        shippingHistory.setProductId(detail.getProduct().getId());
//                        shippingHistory.setShipperId(shipperId);
//                        shippingHistory.setStatus(String.valueOf(OrderStatus.CANCELLED));
//                        shippingHistory.setNote(dto.getCancelReason().trim());
//                        shippingHistory.setUpdatedBy(username);
//                        shippingHistories.add(shippingHistory);
//                    }
//                }
//            }
//            shippingHistoryRepository.saveAll(shippingHistories);
//            orderHistoryRepository.save(orderHistory);
//            customerOrderRepository.save(order);
//        } else if (order.getStatus().equals(OrderStatus.DELIVERED)) {
//            throw new BadRequestException("This order already delivered!");
//        } else if (order.getStatus().equals(OrderStatus.COMPLETED)
//                || order.getStatus().equals(OrderStatus.PARTIALLY_COMPLETED)) {
//            throw new BadRequestException("This order already completed!");
//        } else {
//            throw new BadRequestException("This order already cancelled!");
//        }
//    }
}
