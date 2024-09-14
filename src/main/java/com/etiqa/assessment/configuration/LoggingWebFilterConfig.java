package com.etiqa.assessment.configuration;

import com.etiqa.assessment.customers.service.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.WebFilter;

import java.time.LocalDateTime;

@Configuration
public class LoggingWebFilterConfig {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Bean
    public WebFilter logFilter() {
        return (serverWebExchange, webFilterChain) -> {
            ServerHttpRequest request = serverWebExchange.getRequest();
            ServerHttpResponse response = serverWebExchange.getResponse();

            logger.info("* Request: Method: {}, URI: {}, Headers: {}, Time: {}",
                    request.getMethod(), request.getURI(), request.getHeaders(), LocalDateTime.now());

            return webFilterChain.filter(serverWebExchange)
                    .doOnSuccess(done -> logger.info("* Response: Status: {}, Headers: {}, Time: {}",
                            response.getStatusCode(), response.getHeaders(), LocalDateTime.now()))
                    .doOnError(throwable -> logger.error("Error occurred: {}", throwable.getMessage()));
        };
    }
}
