package com.example.sfcontrolclient;

import android.util.Log;

import com.srs.common.Authentication;
import com.srs.common.AuthenticationGood;
import com.srs.common.ToAndroid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Network {
    private static  final String TAG ="Отладка";

    private static Boolean authGood = false;

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
/*    private static String serverIP = "127.0.0.1";
    private static Integer serverPort = 0;
    private static String login = "";
    private static String pass = "";


    public static String getServerIP() {
        return serverIP;
    }

    public static void setServerConfig(String serverIP,Integer port) {
        Network.serverIP = serverIP;
        Network.serverPort = port;
        Network.login = login;
        Network.pass = pass;


    }
*/
    public static Boolean isActive(){
        if((socket!=null)&&(socket.isConnected()))
            return true;
        return false;
    }
    static boolean start(String serverIP,Integer port,String login,String pass) {
        System.out.println("Запускаем Network");
        try {
            Log.d(TAG, "Network.start: begin0");
            socket = new Socket();
            Log.d(TAG, "Network.start: begin1 ip = "+serverIP);
            socket.connect(new InetSocketAddress(serverIP, port));
            Log.d(TAG, "Network.start: begin2");
            out = new ObjectOutputStream(socket.getOutputStream());
            Log.d(TAG, "Network.start: begin3");
            in = new ObjectInputStream(socket.getInputStream());
            Log.d(TAG, "Network.start: begin4");

            if(!socket.isConnected()){
                Log.d(TAG, "Network.start: begin4+");
                Thread.sleep(100);
            }
            Log.d(TAG, "Network.start: begin5");
            out.writeObject( new Authentication(login,pass));
            out.flush();
            Log.d(TAG, "Network.start: begin6");
            //                in = new ObjectInputStream(socket.getInputStream());
            Object retObj =  in.readObject();
            Log.d(TAG, "Network.start: begin7");
            if(retObj instanceof AuthenticationGood){
                authGood=true;
                Log.d(TAG, "Network.start: Авторизация удачна: ");
            }
            if(retObj instanceof String){
                String strFail = (String)retObj;
                Log.d(TAG, "Network.start: Авторизация неудачна: "+strFail);
                return false;

            }


        } catch (Exception e) {
            Log.d(TAG, "Network.start: Exception! "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ToAndroid[]  ping(){
        ToAndroid[] ret = null;
        if(socket.isConnected()) {
            Log.d(TAG, "Network.ping1");
            try {

                if(authGood){
                    Log.d(TAG, "Network.ping2");
                    String getParams = "getParams";
                    out.writeObject(getParams);
                    out.flush();
                    Log.d(TAG, "Network.ping3");
                    Object obj = in.readObject();
                    if(obj instanceof String){
                        Log.d(TAG, "Network.ERROR "+ obj);
                        return null;
                    }
                    ret = (ToAndroid[]) obj;

                }



            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        else{
            Log.d(TAG, "Network.start: isConnected fail");
        }
        return ret;
    }

    static void stop() {
        System.out.println("Останавливаем Network");

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

