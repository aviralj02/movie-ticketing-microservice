package com.booking_service.config;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter implements Filter {

	public static final ThreadLocal<Map<String, String>> HEADER_HOLDER = new ThreadLocal<>();

	@Value("${jwt.secret}")
	private String jwtSecret;

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		Map<String, String> headersLocal = new HashMap<>();

		String authorizationHeader = httpRequest.getHeader("Authorization");
		String userRoleHeader = httpRequest.getHeader("X-User-Role");
		String userIdHeader = httpRequest.getHeader("X-User-Id");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().write("Missing or invalid Authorization header");

			return;
		}

		String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

		// For Feign Client
		if (token != null) {
			headersLocal.put("Authorization", "Bearer " + token);
		}
		if (userRoleHeader != null) {
			headersLocal.put("X-User-Role", userRoleHeader);
		}
		if (userIdHeader != null) {
			headersLocal.put("X-User-Id", userIdHeader);
		}

		if (!headersLocal.isEmpty()) {
			HEADER_HOLDER.set(headersLocal);
		}

		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

			if (userRoleHeader == null || userIdHeader == null) {
				httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
				httpResponse.getWriter().write("Missing required headers: X-User-Role or X-User-Id");

				return;
			}

			chain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println(e);
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			httpResponse.getWriter().write("Error validating JWT token");
		} finally {
			HEADER_HOLDER.remove();
		}
	}
}
