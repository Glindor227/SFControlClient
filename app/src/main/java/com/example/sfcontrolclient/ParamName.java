package com.example.sfcontrolclient;

import android.util.Log;

public class ParamName{
    private static String getNameByIt(Integer type){
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
    public static String getStringByIt(String nameKD, Integer type, Integer value){
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
        Log.d(MainActivity.TAG, fullStr);

        return fullStr;
    }

}
