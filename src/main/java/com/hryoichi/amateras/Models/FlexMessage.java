package com.hryoichi.amateras.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class FlexMessage {
    final String type = "flex";
    String altText;
    Object contents;
}
