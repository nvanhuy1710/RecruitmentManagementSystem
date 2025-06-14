package com.app.homeworkoutapplication.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;
import tech.jhipster.config.JHipsterProperties;

import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketConfiguration.class);

    public static final String IP_ADDRESS = "IP_ADDRESS";

    private final JHipsterProperties jHipsterProperties;

    private final WSHandshakeHandler wsHandshakeHandler;

    public WebSocketConfiguration(
            JHipsterProperties jHipsterProperties,
            WSHandshakeHandler wsHandshakeHandler
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.wsHandshakeHandler = wsHandshakeHandler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/", "/queue/");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowedOrigins = Optional
                .ofNullable(jHipsterProperties.getCors().getAllowedOrigins())
                .map(origins -> origins.toArray(new String[0]))
                .orElse(new String[0]);

        registry
                .addEndpoint("/websocket/tracker")
                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOriginPatterns(allowedOrigins)
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());

        registry
                .addEndpoint("/websocket/recruit")
                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOriginPatterns("http://localhost:3000")
                .withSockJS()
                .setInterceptors(httpSessionHandshakeInterceptor());
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(100 * 1024 * 1024);
        registration.setSendTimeLimit(30 * 10000);
        registration.setSendBufferSizeLimit(50 * 1024 * 1024);
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        return new HandshakeInterceptor() {
            @SuppressWarnings("NullableProblems")
            @Override
            public boolean beforeHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes
            ) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
                    
                    // Thêm CORS headers
                    response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
                    response.getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
                    response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                    response.getHeaders().add("Access-Control-Allow-Credentials", "true");
                }
                return true;
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void afterHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Exception exception
            ) {}
        };
    }

    private WSHandshakeHandler defaultHandshakeHandler() {
        return wsHandshakeHandler;
    }

    @MessageExceptionHandler
    public void handleExceptions(Throwable t) {
        log.error("Error handling message: " + t.getMessage());
    }
}

