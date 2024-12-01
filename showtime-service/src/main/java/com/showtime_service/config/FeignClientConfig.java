package com.showtime_service.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {

			@Override
			public void apply(RequestTemplate requestTemplate) {
				Map<String, String> headers = JwtValidationFilter.HEADER_HOLDER.get();

				if (headers != null) {
					headers.forEach(requestTemplate::header);;
				}
			}
		};
	}
}