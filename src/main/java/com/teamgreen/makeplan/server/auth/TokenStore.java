package com.teamgreen.makeplan.server.auth;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();

    public void save(String email, String token) {
        tokenMap.put(email, token);
    }

    public String find(String email) {
        return tokenMap.get(email);
    }

    public void delete(String email) {
        tokenMap.remove(email);
    }

    public boolean exists(String email, String token) {
        return token.equals(tokenMap.get(email));
    }

    public String getEmailByToken(String token) {
        return tokenMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(token))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

}