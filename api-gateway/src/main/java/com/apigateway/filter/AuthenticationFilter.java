package com.apigateway.filter;

import com.apigateway.exception.NotFoundException;
import com.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class AuthenticationFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    @Autowired
    private RouterValidator validator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(NameConfig.class);
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            if (path.startsWith("/api/product/image/")|| path.startsWith("/api/product/getById/")) {
                return chain.filter(exchange);
            }

            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new NotFoundException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                    List<String> roles = jwtUtil.extractRoles(authHeader);
                    if (path.startsWith("/admin") && (roles == null || !roles.contains("ADMIN"))) {
                        throw new NotFoundException("Forbidden: ADMIN role required");
                    }

                } catch (Exception e) {
                    throw new NotFoundException("Unauthorized access to application");
                }
            }

            return chain.filter(exchange);
        });
    }
}
