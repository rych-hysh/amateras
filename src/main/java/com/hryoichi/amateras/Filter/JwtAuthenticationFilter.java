package com.hryoichi.amateras.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.lang.JoseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
  String region;
  String userPoolId;
  String Issuer;
  String Audience;
  public JwtAuthenticationFilter(String region, String userPoolId, String issuer, String audience){
     this.region = region;
     this.userPoolId = userPoolId;
     this.Issuer = issuer;
     this.Audience = audience;
  }
  public Map<String, PublicKey> getPublicKey() {
    String jwksUrl = "https://cognito-idp." + region + ".amazonaws.com/" + userPoolId + "/.well-known/jwks.json";
    Map<String, PublicKey> keys = new HashMap<>();
    try (InputStream in = new URL(jwksUrl).openStream()){
      Map<String, List<Map<String, String>>> keyList = new ObjectMapper().readValue(in, Map.class);
      for(Map<String, String> keyMap : keyList.get("keys")){
        String kid = keyMap.get("kid");
        RSAPublicKeySpec spec = new RSAPublicKeySpec(
            new BigInteger(1, Base64.getUrlDecoder().decode(keyMap.get("n"))),
            new BigInteger(1, Base64.getUrlDecoder().decode(keyMap.get("e")))
        );
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
        keys.put(kid, publicKey);
      }
    } catch (Exception e){
      throw new RuntimeException("Failed to get public keys from JWKS", e);
    }
    return keys;
  }

  public JwtClaims verifyToken(String token, Map<String, PublicKey> keys) {
    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
        .setSkipAllValidators()
        .setDisableRequireSignature()
        .setSkipSignatureVerification()
        .build();
    try {
      String kid = jwtConsumer.process(token).getJoseObjects().get(0).getKeyIdHeaderValue();
      PublicKey publicKey = keys.get(kid);
      if(publicKey == null){
        throw new RuntimeException("Failed to find public key for kid: " + kid);
      }
      jwtConsumer = new JwtConsumerBuilder()
          .setRequireExpirationTime()
          .setVerificationKey(publicKey)
          .build();
      return jwtConsumer.processToClaims(token);
    } catch (Exception e){
      throw new RuntimeException("Failed to verify token", e);
    }
  }
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     String header = request.getHeader("Authorization");

    System.out.println("filter passing");
    if(header == null || !header.startsWith("Bearer ")){
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = header.substring(7);

      System.out.println(token);
      JwtConsumer firstPassJwtConsumer = new JwtConsumerBuilder()
           .setSkipAllValidators()
          .setDisableRequireSignature()
          .setSkipSignatureVerification()
          .build();
      JwtContext jwtContext = firstPassJwtConsumer.process(token);
      String keyId = jwtContext.getJoseObjects().get(0).getKeyIdHeaderValue();

      PublicKey publicKey = getPublicKey().get(keyId);
      if(publicKey == null) {
        throw new ServletException("Invalid token");
      }

      JsonWebSignature jws = new JsonWebSignature();
      jws.setCompactSerialization(token);
      jws.setKey(publicKey);

      boolean signatureVerified = jws.verifySignature();
      if(!signatureVerified){
        throw new ServletException("Invalid token");
      }

      JwtConsumer jwtConsumer = new JwtConsumerBuilder()
          .setRequireExpirationTime()
          .setAllowedClockSkewInSeconds(300)
          .setRequireSubject()
          .setExpectedIssuer(Issuer)
          .setExpectedAudience(Audience)
          .setVerificationKey(publicKey)
          .build();
      JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
      String username = jwtClaims.getSubject();

      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, "", null));

      filterChain.doFilter(request, response);
    } catch (JoseException | MalformedClaimException ex){
      throw new ServletException("Failed to parse token");
    } catch (InvalidJwtException e) {
      throw new RuntimeException(e);
    } catch (Exception e){
      System.out.println(e.toString());
    }

  }
}
