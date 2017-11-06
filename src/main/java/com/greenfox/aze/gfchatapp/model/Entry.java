package com.greenfox.aze.gfchatapp.model;

import java.util.List;

public class Entry {
    public String id;
    public long timestamp;
    public List<Messaging> messaging;

    public class Messaging {
        public User sender;
        public User recipient;
    }

    public class User {
        public String id;
    }
}
