package com.cookandroid.flowerdesign.utils;

public class Apis {
    public static final String url="http://13.209.225.242:8080/";
    public static Service getPersonaService(){
        return Client.getCliente(url).create(Service.class);
    }
}
