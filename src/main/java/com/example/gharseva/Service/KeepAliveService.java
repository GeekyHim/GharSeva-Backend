package com.example.gharseva.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KeepAliveService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "0 */9 * * * *") // every 9 minutes
    public void pingServer() {
        try {
            String url = "https://gharseva-yc3k.onrender.com/ping";

            String response = restTemplate.getForObject(url, String.class);

            System.out.println("Ping success: " + response);
        } catch (Exception e) {
            System.out.println("Ping failed");
        }
    }
}