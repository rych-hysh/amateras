package com.hryoichi.amateras.Clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Models.FlexMessage;
import com.hryoichi.amateras.Models.LineMessage;
import com.hryoichi.amateras.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    SimulatorsRepository simulatorsRepository;
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



    public void sendSimulatorTradeNotification(int simulatorId, AlgorithmResult algorithmResult){
        String simulatorName = simulatorsRepository.findById(simulatorId).orElseThrow().getSimulatorName();
        String askOrBid = algorithmResult.isAsk()? "Ask" : "Bid";
        String isSettlement = algorithmResult.isSettle()? "Settlement" : "New order";
        String flexMessageAsJson = "{\n" +
            "  \"type\": \"bubble\",\n" +
            "  \"body\": {\n" +
            "    \"type\": \"box\",\n" +
            "    \"layout\": \"vertical\",\n" +
            "    \"contents\": [\n" +
            "      {\n" +
            "        \"type\": \"text\",\n" +
            "        \"text\": \"Amateras Trading Notification\",\n" +
            "        \"weight\": \"bold\",\n" +
            "        \"color\": \"#1DB446\",\n" +
            "        \"size\": \"sm\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"text\",\n" +
            "        \"text\": \"" + simulatorName + "\",\n" +
            "        \"weight\": \"bold\",\n" +
            "        \"size\": \"xxl\",\n" +
            "        \"margin\": \"md\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"text\",\n" +
            "        \"text\": \"" + simulatorName + " made a transaction.\",\n" +
            "        \"size\": \"xs\",\n" +
            "        \"color\": \"#aaaaaa\",\n" +
            "        \"wrap\": true\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"separator\",\n" +
            "        \"margin\": \"xxl\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"box\",\n" +
            "        \"layout\": \"vertical\",\n" +
            "        \"margin\": \"xxl\",\n" +
            "        \"spacing\": \"sm\",\n" +
            "        \"contents\": [\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\",\n" +
            "                \"flex\": 0,\n" +
            "                \"text\": \"Pair\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"" + algorithmResult.getPair() + "\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"Ask or Bid\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\",\n" +
            "                \"flex\": 0\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"" + askOrBid + "\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"type\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\",\n" +
            "                \"flex\": 0\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"" + isSettlement + "\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"Rate\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\",\n" +
            "                \"flex\": 0\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"" + algorithmResult.getAtRate() + "\" ,\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"Lots\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"" + algorithmResult.getLots() + " lots\",\n" +
            "                \"align\": \"end\",\n" +
            "                \"color\": \"#555555\",\n" +
            "                \"size\": \"sm\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"separator\",\n" +
            "            \"margin\": \"xxl\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"Funds\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"$7.31\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"box\",\n" +
            "            \"layout\": \"horizontal\",\n" +
            "            \"contents\": [\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"Profits / Loss\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#555555\"\n" +
            "              },\n" +
            "              {\n" +
            "                \"type\": \"text\",\n" +
            "                \"text\": \"$8.0\",\n" +
            "                \"size\": \"sm\",\n" +
            "                \"color\": \"#111111\",\n" +
            "                \"align\": \"end\"\n" +
            "              }\n" +
            "            ]\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"button\",\n" +
            "            \"action\": {\n" +
            "              \"type\": \"uri\",\n" +
            "              \"label\": \"See detail\",\n" +
            "              \"uri\": \"http://linecorp.com/\"\n" +
            "            },\n" +
            "            \"style\": \"primary\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"separator\",\n" +
            "        \"margin\": \"xxl\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"box\",\n" +
            "        \"layout\": \"horizontal\",\n" +
            "        \"margin\": \"md\",\n" +
            "        \"contents\": [\n" +
            "          {\n" +
            "            \"type\": \"text\",\n" +
            "            \"text\": \"PAYMENT ID\",\n" +
            "            \"size\": \"xs\",\n" +
            "            \"color\": \"#aaaaaa\",\n" +
            "            \"flex\": 0\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"text\",\n" +
            "            \"text\": \"#743289384279\",\n" +
            "            \"color\": \"#aaaaaa\",\n" +
            "            \"size\": \"xs\",\n" +
            "            \"align\": \"end\"\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"styles\": {\n" +
            "    \"footer\": {\n" +
            "      \"separator\": true\n" +
            "    }\n" +
            "  }\n" +
            "}";
        sendFlexMessage(flexMessageAsJson, simulatorName + " made a new transaction.", "Ufe530665f25924f84bf6fd10d91cb8c3");
    }

    public void callApiToIssueLinkToken(String userId) {
        String ApiUrl = "https://api.line.me/v2/bot/user/" + userId + "/linkToken";
        RequestEntity<Object> request = RequestEntity
                .post(ApiUrl)
                .header("Authorization", "Bearer " + accessToken)
                .header("X-Line-Retry-Key", String.valueOf(generateRetryKey()))
                .accept(MediaType.APPLICATION_JSON)
                .body(null);
        ResponseEntity<LinkedHashMap> response = null;
        try{
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(request, LinkedHashMap.class);
        }catch(Exception e){
            System.out.println(e);
            //TODO: Error handling
        }
        if(Objects.isNull(response) || !response.getStatusCode().is2xxSuccessful())return;
        String linkToken = (String) response.getBody().get("linkToken");
        redirectUserToLinkURL(linkToken, userId);
    }
    private void redirectUserToLinkURL(String linkToken, String userId){
        String ApiUrl = "https://api.line.me/v2/bot/message/push";
        LinkedHashMap body = new LinkedHashMap<>();
        body.put("to", userId);
        LinkedHashMap msg = new LinkedHashMap();
        LinkedHashMap template = new LinkedHashMap();
        LinkedHashMap actions = new LinkedHashMap<>();
        msg.put("type", "template");
        msg.put("altText", "Account Link");
        template.put("type", "buttons");
        template.put("text", "Account Link");
        actions.put("type", "uri");
        actions.put("label", "Account Link");
        actions.put("uri", "http://localhost:8080/link?linkToken=" + linkToken);
        template.put("actions", new ArrayList<>(Arrays.asList(actions)));
        msg.put("template", template);
        body.put("messages", new ArrayList<>(Arrays.asList(msg)));
        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = "";
        try{
            jsonBody = mapper.writeValueAsString(body);
        }catch (Exception e){
            return;
        }

        RequestEntity<Object> request = RequestEntity
                .post(ApiUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .body(jsonBody);
        ResponseEntity<LinkedHashMap> response = null;
        try{
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(request, LinkedHashMap.class);
        }catch(Exception e){
            System.out.println(e);
            //TODO: Error handling
        }
    }
}
