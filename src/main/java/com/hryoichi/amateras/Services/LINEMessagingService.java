package com.hryoichi.amateras.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.amateras.Models.FlexMessage;
import com.hryoichi.amateras.Models.LineMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class LINEMessagingService {

    @Value("${line.post-url}")
    String messagePostUrl;

    @Value("${line.channel-access-token}")
    String accessToken;

    public void sendTestMessge() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonFlex = "{" +
                "  \"type\": \"bubble\"," +
                "  \"body\": {" +
                "    \"type\": \"box\"," +
                "    \"layout\": \"vertical\"," +
                "    \"contents\": [" +
                "      {" +
                "        \"type\": \"image\"," +
                "        \"url\": \"https://scdn.line-apps.com/n/channel_devcenter/img/flexsnapshot/clip/clip3.jpg\"," +
                "        \"size\": \"full\"," +
                "        \"aspectMode\": \"cover\"," +
                "        \"aspectRatio\": \"1:1\"," +
                "        \"gravity\": \"center\"" +
                "      }," +
                "      {" +
                "        \"type\": \"box\"," +
                "        \"layout\": \"vertical\"," +
                "        \"contents\": []," +
                "        \"position\": \"absolute\"," +
                "        \"background\": {" +
                "          \"type\": \"linearGradient\"," +
                "          \"angle\": \"0deg\"," +
                "          \"endColor\": \"#00000000\"," +
                "          \"startColor\": \"#00000099\"" +
                "        }," +
                "        \"width\": \"100%\"," +
                "        \"height\": \"40%\"," +
                "        \"offsetBottom\": \"0px\"," +
                "        \"offsetStart\": \"0px\"," +
                "        \"offsetEnd\": \"0px\"" +
                "      }," +
                "      {" +
                "        \"type\": \"box\"," +
                "        \"layout\": \"horizontal\"," +
                "        \"contents\": [" +
                "          {" +
                "            \"type\": \"box\"," +
                "            \"layout\": \"vertical\"," +
                "            \"contents\": [" +
                "              {" +
                "                \"type\": \"box\"," +
                "                \"layout\": \"horizontal\"," +
                "                \"contents\": [" +
                "                  {" +
                "                    \"type\": \"text\"," +
                "                    \"text\": \"Test Message\"," +
                "                    \"size\": \"xl\"," +
                "                    \"color\": \"#ffffff\"" +
                "                  }" +
                "                ]" +
                "              }," +
                "              {" +
                "                \"type\": \"box\"," +
                "                \"layout\": \"horizontal\"," +
                "                \"contents\": [" +
                "                  {" +
                "                    \"type\": \"box\"," +
                "                    \"layout\": \"baseline\"," +
                "                    \"contents\": [" +
                "                      {" +
                "                        \"type\": \"text\"," +
                "                        \"text\": \"This message is sent for test.\"," +
                "                        \"color\": \"#ffffff\"," +
                "                        \"size\": \"md\"," +
                "                        \"flex\": 0," +
                "                        \"align\": \"end\"" +
                "                      }" +
                "                    ]," +
                "                    \"flex\": 0," +
                "                    \"spacing\": \"lg\"" +
                "                  }" +
                "                ]" +
                "              }" +
                "            ]," +
                "            \"spacing\": \"xs\"" +
                "          }" +
                "        ]," +
                "        \"position\": \"absolute\"," +
                "        \"offsetBottom\": \"0px\"," +
                "        \"offsetStart\": \"0px\"," +
                "        \"offsetEnd\": \"0px\"," +
                "        \"paddingAll\": \"20px\"" +
                "      }" +
                "    ]," +
                "    \"paddingAll\": \"0px\"" +
                "  }" +
                "}";
        FlexMessage flexMessage = new FlexMessage();
        flexMessage.setAltText("laugh");
        flexMessage.setContents(mapper.readValue(jsonFlex, Object.class));
        LineMessage lineMessage = new LineMessage();

        lineMessage.setTo("Ufe530665f25924f84bf6fd10d91cb8c3");
        lineMessage.setMessages(new ArrayList<>(List.of(flexMessage)));

        String json = mapper.writeValueAsString(lineMessage);
        final var randomRetryKey = generateRetryKey();
        RequestEntity<Object> req = RequestEntity
                .post(messagePostUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .header("X-Line-Retry-Key", String.valueOf(randomRetryKey))
                .accept(MediaType.APPLICATION_JSON)
                .body(json);
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(req, LinkedHashMap.class);
            LinkedHashMap<String, LinkedHashMap> res = response.getBody();
        }catch(Exception error){
            final var s = error.toString();
        }
    }

    UUID generateRetryKey(){
        return UUID.randomUUID();
    }
}
