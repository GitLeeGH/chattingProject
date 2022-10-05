package com.example.chattproject.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatMessageVO {

    private int msgSeq;
    private int roomId;
    private String sendId;
    private String message;
    private Timestamp regdate;


}
