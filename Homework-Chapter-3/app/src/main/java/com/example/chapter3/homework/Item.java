package com.example.chapter3.homework;


import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Item {
    private UUID uuid;
    private String name;
    private String description;
    public Item(){

    }
    public Item(String b, String c){

        uuid= randomUUID();
        name=b;
        description=c;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
