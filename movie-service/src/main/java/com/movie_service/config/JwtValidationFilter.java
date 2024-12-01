package com.movie_service.config;

import java.io.IOException;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter implements Filter {
	
	@Value("${jwt.secret}")
    private String jwtSecret;
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");
        String userRoleHeader = httpRequest.getHeader("X-User-Role");
        String userIdHeader = httpRequest.getHeader("X-User-Id");
        

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid Authorization header");
            
            return;
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

            if (userRoleHeader == null || userIdHeader == null) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Missing required headers: X-User-Role or X-User-Id");
                
                return;
            }

            String requestURI = httpRequest.getRequestURI();
            
            if (requestURI.startsWith("/api/movies/admin") && !"ADMIN".equalsIgnoreCase(userRoleHeader)) {
            	System.out.println(userRoleHeader);
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.getWriter().write("Access denied. ADMIN role required for this resource.");
                return;
            }

            chain.doFilter(request, response); // Proceed with the request
        } catch (Exception e) {
        	System.out.println(e);
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpResponse.getWriter().write("Error validating JWT token");
        }
    }
}
