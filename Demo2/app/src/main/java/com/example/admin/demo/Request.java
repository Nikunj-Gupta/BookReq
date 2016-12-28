package com.example.admin.demo;

/**
 * Created by Nikunj on 14-05-2016.
 */
public class Request {

    private String message, time, status;

    public Request(String mes, String t, String stat)
    {
        message = mes;
        time = t;
        status = stat;
    }

    public String getMessage(){
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
