import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CharsetConversionFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 检查请求头中的字符集
        HttpHeaders headers = request.getHeaders();
        if (headers.getContentType() != null && "application/json".equals(headers.getContentType().toString())) {
            String charset = headers.getContentType().getCharSet().name();
            if ("GBK".equals(charset)) {
                // 转换请求体的字符集
                byte[] body = request.getBodyAsBytes().block();
                String decodedBody = new String(body, StandardCharsets.GBK);
                byte[] encodedBody = decodedBody.getBytes(StandardCharsets.UTF_8);

                // 创建一个新的请求对象，包含转换后的请求体
                ServerHttpRequest newRequest = request.mutate()
                        .body(Mono.just(encodedBody))
                        .build();

                // 使用新的请求对象继续处理链
                return chain.filter(exchange.mutate().request(newRequest).build());
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0; // 确定过滤器的执行顺序
    }
}
