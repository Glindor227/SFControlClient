package com.srs.common;

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
}
