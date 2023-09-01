package com.hryoichi.amateras.Config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfileLoggerConfig {

  private static final Logger logger = LoggerFactory.getLogger(ProfileLoggerConfig.class);

  @Value("${spring.profiles.active}")
  private String activeProfile;

  @PostConstruct
  public void logActiveProfile() {
    logger.info("Active Spring profile: {}", activeProfile);
  }
}
