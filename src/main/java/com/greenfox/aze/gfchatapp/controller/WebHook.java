package com.greenfox.aze.gfchatapp.controller;

import com.greenfox.aze.gfchatapp.model.Message;
import com.greenfox.aze.gfchatapp.model.MessageResponse;
import com.greenfox.aze.gfchatapp.model.Messaging;
import com.greenfox.aze.gfchatapp.model.Packet;
import com.greenfox.aze.gfchatapp.util.HeaderRequestInterceptor;
import com.greenfox.aze.gfchatapp.util.LoggingRequestInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/webhook")
public class WebHook {

    private static final String verify = "abc123";

    @GetMapping({"/", ""})
    public String verify(@RequestParam("hub.challenge") String challenge) {
        return challenge;
    }

    @PostMapping({"/", ""})
    public String receive(@RequestBody Packet p) {
        try {
            System.out.println(p.object);
            System.out.println("Message received from: " + p.entry.get(0).messaging.get(0).sender.id);
            sendResponse(p);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "received";
    }

    public void sendResponse(Packet p) {
        RestTemplate template = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        String token = "EAAFosDvVi5cBACmOuuA7ct2Gs1neSKJDVRjPOC5CEN9IpU6XDt4V24hZCtPWTMn5yS4cLdsmnlSoZCoQoZA7pYFKuY4p0BcyilcQ4ZBPaAZAG08opsad3vI64wCFHbzkgrrZArPCTuPx0bTPHZASBZAibzWjrlChkXEaWWyh1n9miQZDZD";

        Messaging response = new Messaging();
        response.recipient = p.entry.get(0).messaging.get(0).sender;
        response.message = new Message();
        response.message.text = "Well, you too with this: " + p.entry.get(0).messaging.get(0).message.text;
        System.out.println(response.message.text);

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        template.setInterceptors(interceptors);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Messaging> entity = new HttpEntity<>(response, headers);
        LoggingRequestInterceptor.log.debug("mukodik a logger");
        try {
            template.exchange(
                    "https://graph.facebook.com/v2.6/me/messages?access_token="+token,
                    HttpMethod.POST,
                    entity,
                    MessageResponse.class
            );
        } catch (ResourceAccessException exception) {
            exception.printStackTrace();
        }
    }
}
