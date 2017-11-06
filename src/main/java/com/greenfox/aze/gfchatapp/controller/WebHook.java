package com.greenfox.aze.gfchatapp.controller;

import com.greenfox.aze.gfchatapp.model.Message;
import com.greenfox.aze.gfchatapp.model.MessageResponse;
import com.greenfox.aze.gfchatapp.model.Messaging;
import com.greenfox.aze.gfchatapp.model.Packet;
import com.greenfox.aze.gfchatapp.util.HeaderRequestInterceptor;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
        RestTemplate template = new RestTemplate();
        HashMap<String, String> urlParam = new HashMap<>();
        urlParam.put("access_token", "EAAFosDvVi5cBACmOuuA7ct2Gs1neSKJDVRjPOC5CEN9IpU6XDt4V24hZCtPWTMn5yS4cLdsmnlSoZCoQoZA7pYFKuY4p0BcyilcQ4ZBPaAZAG08opsad3vI64wCFHbzkgrrZArPCTuPx0bTPHZASBZAibzWjrlChkXEaWWyh1n9miQZDZD");

        Messaging response = new Messaging();
        response.recipient = p.entry.get(0).messaging.get(0).sender;
        response.message = new Message();
        response.message.text = "Well, you too with this: " + p.entry.get(0).messaging.get(0).message.text;

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        template.setInterceptors(interceptors);

        template.postForObject(
                "https://graph.facebook.com/v2.6/me/messages",
                response,
                MessageResponse.class,
                urlParam
        );
    }
}
