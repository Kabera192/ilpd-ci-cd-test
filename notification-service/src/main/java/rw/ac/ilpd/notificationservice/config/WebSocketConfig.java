package rw.ac.ilpd.notificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        // prefix used by clients to subscribe to a particular topic/queue and
        // listen for messages
        config.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("notification_user")
                .setClientPasscode("notification_pass")
                .setSystemLogin("notification_user")
                .setSystemPasscode("notification_pass");

        // prefix for all requests to the notification controller
        config.setApplicationDestinationPrefixes("/notifications");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        // WebSocket endpoint for clients to connect to the websocket
        registry.addEndpoint("/notifications")
                // In prod this should define the address of the client (frontend)
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Fallback to SockJS for browser compatibility
    }
}