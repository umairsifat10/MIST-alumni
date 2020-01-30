package com.example.imtiaz.mist;

import java.util.HashMap;
import java.util.Map;

public class user {
    public String name, email;
    public user(String name,String email) {
        this.name=name;
        this.email=email;
    }

    public user() {

    }

    public void setName(String name) {
        this.name=name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("email", email);
        return result;
    }

}
