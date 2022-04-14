package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject){

        try{
            boolean wasPrivate = false;

            Field f = target.getClass().getDeclaredField(fieldName);
            if(!f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            else{
                f.setAccessible(false);
                wasPrivate = false;

            }
            f.set(target, toInject);
            if(!wasPrivate){
                f.setAccessible(true);
            }else{
                f.setAccessible(false);
            }

        }
        catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        catch(IllegalAccessException e){
            e.printStackTrace();
        }

    }
}
