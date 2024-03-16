package org.homework.fujitsuhomework2024.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UnixTimestampConverter {
    public Timestamp unixToTimestamp(String s){
        long unixTimestamp = Long.parseLong(s);
        long milliseconds = unixTimestamp * 1000L;
        return new java.sql.Timestamp(milliseconds);
    }
}
