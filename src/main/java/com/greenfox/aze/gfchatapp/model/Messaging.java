package com.greenfox.aze.gfchatapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messaging {
    public User sender;
    public User recipient;
    public Message message;
}
