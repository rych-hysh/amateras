package com.hryoichi.amateras.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
public class LineMessage {
    String to;
    List<Object> messages;
}
