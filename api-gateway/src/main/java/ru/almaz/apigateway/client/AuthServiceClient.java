package ru.almaz.apigateway.client;


import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.almaz.apigateway.dto.UserInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceClient {
    private final WebClient.Builder webClient;

    public Mono<UserInfo> getUserInfo(String token) {
        log.info("getUserInfo");
        return webClient.build()
                .post()
                .uri("lb://auth-service/auth/user-info")
                .header(HttpHeaders.AUTHORIZATION,token)
                .retrieve()
                .bodyToMono(UserInfo.class);
    }
}
