package com.etiqa.assessment.configuration;

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

    private static final Logger logger = LoggerFactory.getLogger(LoggingWebFilterConfig.class);

    @Bean
    public WebFilter logFilter() {
        return (serverWebExchange, webFilterChain) -> {
            ServerHttpRequest request = serverWebExchange.getRequest();
            ServerHttpResponse response = serverWebExchange.getResponse();

            logger.info("Request: Method: {}, URI: {}, Headers: {}, Time: {}, Body:{}",
                    request.getMethod(), request.getURI(), request.getHeaders(), LocalDateTime.now(), request.getBody());

            return webFilterChain.filter(serverWebExchange)
                    .doOnSuccess(done -> logger.info("Response: Status: {}, Headers: {}, Time: {}, Body: {}",
                            response.getStatusCode(), response.getHeaders(), LocalDateTime.now(), response))
                    .doOnError(throwable -> logger.error("Error occurred: {}", throwable.getMessage()));
        };
    }

    //** To write the Request Body and Response Body
//    @Bean
//    public WebFilter logFilter() {
//        return (ServerWebExchange exchange, WebFilterChain chain) -> {
//            ServerHttpRequest decoratedRequest = decorateRequest(exchange.getRequest());
//            ServerHttpResponse decoratedResponse = decorateResponse(exchange.getResponse());
//            return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build());
//        };
//    }
//    private ServerHttpRequest decorateRequest(ServerHttpRequest request) {
//        return new ServerHttpRequestDecorator(request) {
//            @Override
//            public Flux<DataBuffer> getBody() {
//                return super.getBody()
//                        .flatMap(dataBuffer -> {
//                            byte[] bodyBytes = new byte[dataBuffer.readableByteCount()];
//                            dataBuffer.read(bodyBytes);
//                            DataBufferUtils.release(dataBuffer); // Release memory
//                            String bodyString = new String(bodyBytes, StandardCharsets.UTF_8);
//                            logger.info("Request Body: {}", bodyString);
//
//                            // Cache the body so it can be read again by the handler
//                            DataBuffer cachedBuffer = new DefaultDataBufferFactory().wrap(bodyBytes);
//                            return Mono.just(cachedBuffer);
//                        });
//            }
//        };
//    }
//    private ServerHttpResponse decorateResponse(ServerHttpResponse response) {
//        return new ServerHttpResponseDecorator(response) {
//            @Override
//            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                if (body instanceof Flux) {
//                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
//                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
//                        // Collect all buffers
//                        int totalBytes = dataBuffers.stream().mapToInt(DataBuffer::readableByteCount).sum();
//                        byte[] responseBytes = new byte[totalBytes];
//                        int position = 0;
//                        // Combine all the DataBuffers into a single byte array
//                        for (DataBuffer dataBuffer : dataBuffers) {
//                            int readableBytes = dataBuffer.readableByteCount();
//                            dataBuffer.read(responseBytes, position, readableBytes);
//                            position += readableBytes;
//                            DataBufferUtils.release(dataBuffer); // Release memory
//                        }
//                        String responseBody = new String(responseBytes, StandardCharsets.UTF_8);
//                        logger.info("Response Body: {}", responseBody);
//                        return response.bufferFactory().wrap(responseBytes);
//                    }));
//                }
//                return super.writeWith(body);
//            }
//        };
//    }
}
