package com.fh.shop.api.member.util;

public class KeyUtil {


    public  static  String  buildActiveMemberKey(String id){
        return "active:member:"+id;
    }

    public static String buildMemberKey(Long id){
        return "member:"+id;
    }

    public static String buildImageKey(String imageKey){
        return "image:code:" + imageKey;
    }

    public static String buildCdKey(String cdKey){
        return "member:" + cdKey;
    }

    public static String buildCartKey(Long id){
        return "cart:" + id;
    }

    public static String buildTokenKey(String token){
        return "token:" + token;
    }
}
