package com.greenfox.aze.gfchatapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebHook {
    @GetMapping({"/", ""})
    public String receiveEvent() {
        return "ok";
    }
}
