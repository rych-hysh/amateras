package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Clients.LINEMessagingClient;
import com.hryoichi.amateras.Models.AlgorithmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LINEMessagingService {

    @Autowired
    LINEMessagingClient lineMessagingClient;

    public void sendTestMessage() {
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
        lineMessagingClient.sendSimulatorTradeNotification(1, new AlgorithmResult("USD/JPY", true, true, 1,150f, true, 1));
    }
}
