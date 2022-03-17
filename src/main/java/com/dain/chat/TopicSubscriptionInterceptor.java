/*
package com.dain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Log4j2
@Component
@RequiredArgsConstructor
public class TopicSubscriptionInterceptor extends ChannelInterceptorAdapter {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageChannel clientOutboundChannel;



    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){

            MessageHeaders headers = message.getHeaders();
            MultiValueMap<String, String> map= headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
            String chatRoomId = map.getFirst("roomId");

            StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
            headerAccessor.setSessionId(accessor.getSessionId());
            headerAccessor.setSubscriptionId(accessor.getSubscriptionId());
            if (!validateSubscription(chatRoomId)) {
                headerAccessor.setMessage("FULL");
                clientOutboundChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));
                return null;
            }else{
                headerAccessor.setMessage("OK");
            }
            clientOutboundChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));
        }
        return message;
    }

    private boolean validateSubscription(String chatRoomId) {

        ChatRoom chatRoom = chatRoomRepository.findRoomById(chatRoomId);
        if(chatRoom != null && chatRoom.checkCount()){
            return true;
        }
        return false;
    }
}*/
