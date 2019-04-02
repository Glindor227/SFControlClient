package com.example.sfcontrolclient;

import android.util.Log;

public class ParamName{
    public static String getNameByIt(Integer type){
        switch (type) {
            case 1:
                return "Занимает памяти";
            case 2:
                return "Количество потоков";
            case 3:
                return "Количество дескрипторов";
            case 4:
                return "Загружено КД";
            case 5:
                return "Доступно ЦПУ КД";
        }
        return "Неизвестно";
    }
    public static String getValueStringById(Integer type, Integer value){
        switch (type) {
            case 1:
                return value + " KB";
            case 2:
            case 3:
                return String.valueOf(value);
            case 4:
            case 5:
                return value==1?"Да":"Нет";
        }
        return "???";
    }

    public static String getStringByIt(String nameKD, Integer type, Integer value,Integer error){
        String fullStr =  "ЦПУ("+nameKD+") - "+ ParamName.getNameByIt(type)+": ";
        if(value==0)
            fullStr = fullStr + "Нет";
        else{
            if(value==1)
                fullStr = fullStr + "Да";
            else
                fullStr = fullStr + value;
        }
        if(type==1)
            fullStr = fullStr + " KB";
        if(error==2)
            fullStr = fullStr + " Внимание";
        if(error==3)
            fullStr = fullStr + " ОПАСНОСТЬ!";
        Log.d(MainActivity.TAG, fullStr);

        return fullStr;
    }

}
