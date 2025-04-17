package com.springboot.user_management.service.impl;

import com.springboot.user_management.config.SecurityUtils;
import com.springboot.user_management.constant.FailureMessage;
import com.springboot.user_management.constant.SuccessMessage;
import com.springboot.user_management.constant.ValidationMessage;
import com.springboot.user_management.dto.request.user.UserInfoRequestDTO;
import com.springboot.user_management.dto.request.user.UserPasswordRequestDTO;
import com.springboot.user_management.dto.response.paging.Metadata;
import com.springboot.user_management.dto.response.paging.UserResponsePagingDTO;
import com.springboot.user_management.dto.response.user.UserResponseDTO;
import com.springboot.user_management.entity.Product;
import com.springboot.user_management.entity.Role;
import com.springboot.user_management.entity.User;
import com.springboot.user_management.enums.RoleEnum;
import com.springboot.user_management.mapper.response.UserResponseDtoMapper;
import com.springboot.user_management.repository.ProductRepository;
import com.springboot.user_management.repository.RoleRepository;
import com.springboot.user_management.repository.UserRepository;
import com.springboot.user_management.service.UserService;
import com.springboot.user_management.utils.BaseResponse;
import com.springboot.user_management.utils.ResponseFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserResponseDtoMapper userResponseDtoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> getUserInfo() {
        try {
            String username = SecurityUtils.getUsername();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));

            if (!user.getStatus()) {
                throw new BadRequestException(FailureMessage.DISABLED_USER);
            }

            UserResponseDTO responseDTO = userResponseDtoMapper.toDTO(user);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> changeUserPassword(UserPasswordRequestDTO dto) {
        try {
            String username = SecurityUtils.getUsername();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));

            if (!user.getStatus()) {
                throw new BadRequestException(FailureMessage.DISABLED_USER);
            }

            dto.trimFields();
            if (!passwordEncoder.matches(dto.getOllPassword(), user.getPassword())) {
                throw new BadRequestException(ValidationMessage.INCORRECT_PASSWORD);
            }

            if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
                throw new BadRequestException(ValidationMessage.DUPLICATE_PASSWORD);
            }

            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
            UserResponseDTO responseDTO = userResponseDtoMapper.toDTO(user);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, "Password changed.");
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserInfo(UserInfoRequestDTO dto) {
        try {
            String username = SecurityUtils.getUsername();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));

            if (!user.getStatus()) {
                throw new BadRequestException(FailureMessage.DISABLED_USER);
            }

            dto.trimFields();
            if (userRepository.existsByUsernameAndIdNot(dto.getUsername(), user.getId())) {
                throw new BadRequestException("Username already exists!");
            }

            if (userRepository.existsByEmailAndIdNot(dto.getEmail(), user.getId())) {
                throw new BadRequestException("Email already exists!");
            }

            user.setUsername(dto.getUsername());
            user.setFullName(dto.getFullName());
            user.setEmail(dto.getEmail());
            user.setAddress(dto.getAddress());
            userRepository.save(user);
            UserResponseDTO responseDTO = userResponseDtoMapper.toDTO(user);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, "User info updated.");
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponsePagingDTO>> getAllUserByConditions(String startDate, String endDate, String username, String fullName, Boolean status, String role, Integer page, Integer size) {
        try {
            Set<String> roles = SecurityUtils.getRoles();
            if (roles.contains(RoleEnum.ADMIN.getType())) {
                if (!RoleEnum.getRoleNames().contains(role)) {
                    throw new BadRequestException(FailureMessage.ROLE_NOT_ALLOWED);
                }
            } else {
                if (!role.equals(RoleEnum.MEMBER.name()) || !role.equals(RoleEnum.SHIPPER.name())) {
                    throw new BadRequestException(FailureMessage.ROLE_NOT_ALLOWED);
                }
            }

            startDate = LocalDate.parse(startDate).atStartOfDay()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            endDate = LocalDate.parse(endDate).atTime(23, 59, 59)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Pageable pageable = PageRequest.of(page - 1, size);
            Page<User> userPage = userRepository.findAllByConditions(startDate, endDate, username, fullName, status, role, pageable);
            List<UserResponseDTO> responseDTOList = userPage.getContent().stream()
                    .map(user -> {
                        UserResponseDTO dto = userResponseDtoMapper.toDTO(user);
                        dto.setRoles(user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()));
                        return dto;
                    })
                    .collect(Collectors.toList());
            UserResponsePagingDTO responsePagingDTO = new UserResponsePagingDTO(Metadata.build(userPage), responseDTOList);
            return ResponseFactory.success(HttpStatus.OK, responsePagingDTO, SuccessMessage.SUCCESS);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserStatus(Integer id, Boolean status) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));

            Set<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            if (userRoles.contains(RoleEnum.ADMIN.getType())) {
                throw new BadRequestException("Cannot change status this user!");
            }

            if (status) {
                if (user.getStatus()) {
                    throw new BadRequestException("This user already activated!");
                }

                List<Product> productList = Optional.ofNullable(
                        productRepository.findAllByCreatedBy(user.getUsername()))
                        .orElse(Collections.emptyList());
                if (!productList.isEmpty()) {
                    productList.forEach(product -> product.setStatus(true));
                }
                productRepository.saveAll(productList);
            } else {
                if (!user.getStatus()) {
                    throw new BadRequestException("This user already inactivated!");
                }

                List<Product> productList = Optional.ofNullable(
                                productRepository.findAllByCreatedBy(user.getUsername()))
                        .orElse(Collections.emptyList());
                if (!productList.isEmpty()) {
                    productList.forEach(product -> product.setStatus(false));
                }
                productRepository.saveAll(productList);
            }
            user.setStatus(status);
            UserResponseDTO responseDTO = userResponseDtoMapper.toDTO(user);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.STATUS_CHANGED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserRole(Integer id, String role) {
        try {
            if (RoleEnum.getRoleNames().contains(role)) {
                throw new BadRequestException(FailureMessage.ROLE_NOT_ALLOWED);
            }

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException(FailureMessage.USER_NOT_FOUND));

            Set<String> userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            if (userRoles.contains(RoleEnum.getTypeFromName(role))) {
                throw new BadRequestException("This user role already added!");
            }

            if (role.equals(RoleEnum.ADMIN.name())) {
                long countAdmin = userRepository.countByRoles_NameAndStatusIsTrue(RoleEnum.ADMIN.getType());
                if (countAdmin >= 3) {
                    throw new BadRequestException("The number of allowed admins is full!");
                }
                if (userRoles.contains(RoleEnum.SHIPPER.getType())) {
                    throw new BadRequestException("Shipper cannot become admin!");
                }
            } else if (role.equals(RoleEnum.OWNER.name())) {
                if (userRoles.contains(RoleEnum.SHIPPER.getType())) {
                    throw new BadRequestException("Shipper cannot become owner!");
                }
            } else if (role.equals(RoleEnum.SHIPPER.name())) {
                if (userRoles.contains(RoleEnum.ADMIN.getType())
                        || userRoles.contains(RoleEnum.OWNER.getType())) {
                    throw new BadRequestException("Admin or owner cannot become shipper!");
                }
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(
                        RoleEnum.ADMIN.getType(),
                        RoleEnum.OWNER.getType(),
                        RoleEnum.SHIPPER.getType()
                ));
                for (String name : list) {
                    if (userRoles.contains(name)) {
                        Role roleToRemove = roleRepository.findRoleByName(name);
                        if (roleToRemove != null) {
                            user.getRoles().remove(roleToRemove);
                        }
                    }
                }
            }

            Role newRole = roleRepository.findRoleByName(RoleEnum.getTypeFromName(role));
            if (newRole != null) {
                user.getRoles().add(newRole);
            }
            userRepository.save(user);
            UserResponseDTO responseDTO = userResponseDtoMapper.toDTO(user);
            return ResponseFactory.success(HttpStatus.OK, responseDTO, SuccessMessage.STATUS_CHANGED);
        } catch (Exception e) {
            return ResponseFactory.error(HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
}
