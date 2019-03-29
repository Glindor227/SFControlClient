package com.example.sfcontrolclient;

import android.util.Log;

import com.srs.common.ToAndroid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Network {
    private static  final String TAG ="Отладка";

    private static Socket socket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static String serverIP = "127.0.0.1";

    public static String getServerIP() {
        return serverIP;
    }

    public static void setServerIP(String serverIP) {
        Network.serverIP = serverIP;
    }

    public static Boolean isActive(){
        if((socket!=null)&&(socket.isConnected()))
            return true;
        return false;
    }
    static boolean start(Integer port) {
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
            Log.d(TAG, "Network.start: ping1");
            try {
//                out = new ObjectOutputStream(socket.getOutputStream());
                Log.d(TAG, "Network.start: ping2");
                String getParams = "getParams";
                out.writeObject(getParams);
                out.flush();
                Log.d(TAG, "Network.start: ping3");
//                in = new ObjectInputStream(socket.getInputStream());
                ret = (ToAndroid[]) in.readObject();
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

