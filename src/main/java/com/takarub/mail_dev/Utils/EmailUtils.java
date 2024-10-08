package com.takarub.mail_dev.Utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host , String token){
        return "hello " +name+ "\n\n your new account has bean created. pleas click to like below"+
                "to verify your account . \n\n" + getVericationUrl(host,token) +"\n\n any text";

    }

    public static String getVericationUrl(String host, String token) {
        return host + "/api/v1?token="+token;
    }
}
