package com.chumore.reservation.util;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class InputValidator {

    public static String validateNotEmpty(String value, String errorMsg,List<String> errorMsgs){
        if(value==null|| value.trim().isEmpty()){
            errorMsgs.add(errorMsg);
            return null;
        }

        return value.trim();
    }

    public static String validateString(String value,String regex,String errorMsg,List<String> errorMsgs){
        if(value==null || value.trim().isEmpty()){
            errorMsgs.add(errorMsg);
            return null;
        }else if(!value.trim().matches(regex)){
            errorMsgs.add(errorMsg);
        }

        return value.trim();
    }

    public static Integer validateInteger(String value,String errorMsg,List<String> errorMsgs){
        try {
            if(value==null || value.trim().isEmpty()){
                errorMsgs.add(errorMsg);
                return null;
            }else{
                return Integer.valueOf(value.trim());
            }
        }catch(NumberFormatException e){
            errorMsgs.add(errorMsg);
            return null;
        }
    }

    public static Date validateDate(String value, String errorMsg, List<String> errorMsgs){
        try{
            return Date.valueOf(value.trim());
        }catch(IllegalArgumentException e){
            errorMsgs.add(errorMsg);
            return new Date(System.currentTimeMillis());
        }
    }

    public static Time validateTime(String value,String errorMsg, List<String> errorMsgs){
        try{
            return Time.valueOf(value.trim());
        }catch(IllegalArgumentException e){
            errorMsgs.add(errorMsg);
            return new Time(System.currentTimeMillis());
        }

    }


}
