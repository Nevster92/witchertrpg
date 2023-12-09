package com.witcher.ttrpgapi;

import com.witcher.ttrpgapi.repository.CharacterJdbcRepository;
import com.witcher.ttrpgapi.service.WsService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@EnableWebSocketMessageBroker
@Configuration
@CrossOrigin()
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WsConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WsConfig.class);
    @Autowired
    private JwtDecoder jwtDecoder;

    private WsService wsService;
    @Autowired
    public void WsService(WsService wsService) {
        this.wsService = wsService;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("X-Authorization");
                    logger.debug("X-Authorization: {}", authorization);
                    String accessToken = authorization.get(0).split(" ")[1];
                    Jwt jwt = jwtDecoder.decode(accessToken);
                    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                    Authentication authentication = converter.convert(jwt);
                    accessor.setUser(authentication);
                }
                if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                    Principal principal = accessor.getUser();
                    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) principal;
                    Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();
                    long id = jwt.getClaim("id");
                    if(!wsService.wsRoomAcces(accessor.getDestination(), id ))
                    throw new IllegalArgumentException("No permission for this topic");
                    }
                return message;
            }
        });
    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
       registry.addEndpoint("/stomp-endpoint").setAllowedOrigins("*").withSockJS();
      registry
              .addEndpoint("/stomp-endpoint")
              .setAllowedOrigins("http://localhost:5173")
              .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }



}
