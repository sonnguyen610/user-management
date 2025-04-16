package com.springboot.user_management.config;

import com.springboot.user_management.constant.FailureMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getUsername() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new BadRequestException(FailureMessage.UNAUTHENTICATED_USER);
            }

            return authentication.getName();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static Set<String> getRoles() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                throw new BadRequestException(FailureMessage.UNAUTHENTICATED_USER);
            }


            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptySet();
        }
    }
}
