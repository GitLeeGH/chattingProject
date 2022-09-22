package com.example.chattproject.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class SpringConfig implements WebSocketMessageBrokerConfigurer {

    // 웹 소켓 연결을 위한 엔드포인트 설정 및 stomp sub/pub 엔드 포인트 설정

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //stomp 접속 주소 url => /ws-stomp
        registry.addEndpoint("/ws-stomp")   // 연결될 엔드 포인드
                .withSockJS();                     // SocketJS 를 연결 한다는 설정
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 메세지를 구독하는 요청 url => 즉 메세지를 받을 때
        registry.enableSimpleBroker("/sub");

        // 메세지를 발행하는 요청 url => 즉 메세지를 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");

    }
}
