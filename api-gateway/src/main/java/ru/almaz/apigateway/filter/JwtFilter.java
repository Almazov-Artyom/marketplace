package ru.almaz.apigateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.almaz.apigateway.client.AuthServiceClient;
import ru.almaz.apigateway.dto.UserInfo;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter implements GlobalFilter {

    private final AuthServiceClient authServiceClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> str = Stream.of(exchange.getRequest().getHeaders()).map(HttpHeaders::toString).toList();
        for(String st:str){
            log.info(st);
        }
        log.info("JWT Filter");
        log.info(String.valueOf(exchange.getRequest()));
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Bearer token: {}", bearerToken);
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }
        log.info(bearerToken);

        return authServiceClient.getUserInfo(bearerToken)
                .flatMap(userInfo -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", userInfo.id().toString())
                            .header("X-User-Role", userInfo.role())
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                    return chain.filter(mutatedExchange);
                })
                .onErrorResume(WebClientResponseException.class, ex ->{
                    log.info(ex.getResponseBodyAsString());
                    exchange.getResponse().setStatusCode(ex.getStatusCode());

                    return exchange.getResponse().writeWith(
                            Mono.just(exchange.getResponse().bufferFactory()
                                    .wrap(ex.getResponseBodyAsString().getBytes())));
                });


        }



}
