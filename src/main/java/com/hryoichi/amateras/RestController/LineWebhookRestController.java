package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Clients.LINEMessagingClient;
import com.hryoichi.amateras.Models.LineFollowedWebhook;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.rmi.UnexpectedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RequestMapping("webhook/line")
@RestController
public class LineWebhookRestController {

    @Value("${line.channel-secret}")
    String channelSecret;

    @Autowired
    LINEMessagingClient lineMessagingClient;
    @PostMapping("/followed")
    void getUserIdWhenFollowed(@RequestHeader(name="x-line-signature") String xLineSignature, @RequestBody LineFollowedWebhook obj) throws InvalidKeyException, NoSuchAlgorithmException, UnexpectedException {

        String httpRequestBody = obj.toString(); // Request body string
        String type = obj.getEvents().get(0).get("type").toString();
        String userId;
        try{
            LinkedHashMap source = (LinkedHashMap) obj.getEvents().get(0).get("source");
            userId = (String) source.get("userId");
        }catch(Error e) {
            throw new UnexpectedException("webhook type error.");
        }
        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] source = httpRequestBody.getBytes(StandardCharsets.UTF_8);
        String signature = Base64.encodeBase64String(mac.doFinal(source));
// Compare x-line-signature request header string and the signature
        //TODO:動くか確認
        //if(!Objects.equals(xLineSignature, signature)) return;;
        if(Objects.equals(type, "follow")){
            lineMessagingClient.callApiToIssueLinkToken(userId);
        }

    }
}
