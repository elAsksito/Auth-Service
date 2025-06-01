package com.ask.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.model.entity.LoginEvent;
import com.ask.auth.model.entity.User;
import com.ask.auth.repository.LoginEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginEventService {

    private final LoginEventRepository loginEventRepository;

    @Async
    @Transactional
    public CompletableFuture<LoginEvent> logSuccessfulLoginEvent(
            User user,
            String ipAddress,
            String userAgent,
            String fingerprint) {

        GeoInfo geo = fetchGeoInfo(ipAddress);

        LoginEvent loginEvent = LoginEvent.builder()
                .user(user)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceFingerprint(fingerprint)
                .geoCountry(geo.country())
                .geoCity(geo.city())
                .success(true)
                .failureReason(null)
                .build();

        LoginEvent savedEvent = loginEventRepository.save(loginEvent);
        log.info("Login exitoso guardado para el usuario: {}", user.getEmail());
        return CompletableFuture.completedFuture(savedEvent);
    }

    @Async
    @Transactional
    public CompletableFuture<LoginEvent> logFailedLoginEvent(
            User user,
            String ipAddress,
            String userAgent,
            String fingerprint,
            String failureReason) {

        GeoInfo geo = fetchGeoInfo(ipAddress);

        LoginEvent loginEvent = LoginEvent.builder()
                .user(user)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceFingerprint(fingerprint)
                .geoCountry(geo.country())
                .geoCity(geo.city())
                .success(false)
                .failureReason(failureReason)
                .build();

        LoginEvent savedEvent = loginEventRepository.save(loginEvent);
        log.info("Login fallido guardado para el usuario: {}. Motivo: {}", user.getEmail(), failureReason);
        return CompletableFuture.completedFuture(savedEvent);
    }

    private GeoInfo fetchGeoInfo(String ipAddress) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ipAddress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String json = reader.lines().collect(Collectors.joining());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(json);

                String country = node.path("country").asText(null);
                String city = node.path("city").asText(null);
                return new GeoInfo(country, city);
            }
        } catch (Exception e) {
            log.warn("Falló la búsqueda geográfica para la IP: {}", ipAddress, e);
            return new GeoInfo(null, null);
        }
    }

    public record GeoInfo(String country, String city) {}
}