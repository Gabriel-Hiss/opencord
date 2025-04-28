package net.opencord.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.opencord.annotation.Jwt;
import net.opencord.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod method) {
            if (method.getMethod().isAnnotationPresent(Jwt.class) || method.getBeanType().isAnnotationPresent(Jwt.class)) {
                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Token ausente ou formato inválido");
                }

                String token = authHeader.substring(7);
                try {
                    if (!jwtTokenUtil.validateToken(token)) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token inválido");
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Erro na validação do token: " + e.getMessage());
                }
            }
        }
        return true;
    }
}