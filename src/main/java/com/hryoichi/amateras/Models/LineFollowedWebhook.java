package com.hryoichi.amateras.Models;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
@Getter
public class LineFollowedWebhook {
    String destination;
    ArrayList<LinkedHashMap> events;
}
