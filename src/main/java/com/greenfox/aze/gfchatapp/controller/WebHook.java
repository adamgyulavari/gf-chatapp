package com.greenfox.aze.gfchatapp.controller;

import com.greenfox.aze.gfchatapp.model.Message;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebHook {

    private static final String verify = "abc123";

    @GetMapping({"/", ""})
    public String verify(@RequestParam("hub.challenge") String challenge) {
        return challenge;
    }

    @PostMapping({"/", ""})
    public String reveive(Message message) {
        try {
            System.out.println("Message received from: " + message.entry.get(0).messaging.get(0).sender.id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "received";
    }
}
