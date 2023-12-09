package com.witcher.ttrpgapi.pojo;


import jakarta.validation.constraints.NotNull;

public class Message {
    @NotNull
    private String content;
    private String sendTo;

}
