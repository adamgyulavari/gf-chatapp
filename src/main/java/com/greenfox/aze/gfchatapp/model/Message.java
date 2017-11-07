package com.greenfox.aze.gfchatapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    public String mid;
    public String text;
}
