package com.hryoichi.amateras.Clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.amateras.Models.FlexMessage;
import com.hryoichi.amateras.Models.LineMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class LINEMessagingClient {
    @Value("${line.post-url}")
    String messagePostUrl;

    @Value("${line.channel-access-token}")
    String accessToken;

    public void sendFlexMessage(String flexMessageAsJson, String altText, String toUuid){
        ObjectMapper mapper = new ObjectMapper();
        FlexMessage flexMessage = new FlexMessage();
        flexMessage.setAltText(altText);
        try{
            flexMessage.setContents(mapper.readValue(flexMessageAsJson, Object.class));
        }catch(Exception e){
            System.out.println(e);
            // TODO: Error handling
            return;
        }
        LineMessage lineMessage = new LineMessage();
        lineMessage.setTo(toUuid);
        lineMessage.setMessages(new ArrayList<>(List.of(flexMessage)));
        String sendJson;
        try{
            sendJson = mapper.writeValueAsString(lineMessage);
        }catch(Exception e){
            System.out.println(e);
            // TODO: Error handling
            return;
        }
        final UUID randomRetryKey = generateRetryKey();
        RequestEntity<Object> request = RequestEntity
                .post(messagePostUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .header("X-Line-Retry-Key", String.valueOf(randomRetryKey))
                .accept(MediaType.APPLICATION_JSON)
                .body(sendJson);
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(request, LinkedHashMap.class);
        }catch(Exception e){
            System.out.println(e);
            //TODO: Error handling
        }
    }

    UUID generateRetryKey(){
        return UUID.randomUUID();
    }
}
