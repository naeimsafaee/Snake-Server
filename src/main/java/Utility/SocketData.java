package Utility;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.util.Random;

public class SocketData {

    public int socketId;
    public String event;
    public String payload;
    public String type;

    public transient int port;

    public transient InetAddress address;

    public transient long lastOnline;

    SocketData(int socketId, String event, String payload, String type ,
               InetAddress address , int port) {
        this.socketId = socketId;
        this.event = event;
        this.payload = payload;
        this.type = type;
        this.address = address;
        this.port = port;
        this.lastOnline = System.currentTimeMillis();
    }

    SocketData(String event, String payload, String type ,
               InetAddress address , int port) {
        this.socketId = new Random().nextInt();
        this.event = event;
        this.payload = payload;
        this.type = type;
        this.address = address;
        this.port = port;
        this.lastOnline = System.currentTimeMillis();
    }

    public void emit(String event, String payload) {
        Server.getInstance().emit(new SocketData(
                socketId, event, payload, "server_to_client" , address , port));
    }

    //ok
    public void emit(String event, Object object) {
        Gson gson = new Gson();

        Server.getInstance().emit(new SocketData(
                socketId, event, gson.toJson(object), "server_to_client" ,address , port));
    }

    public void broadcast(String event, Object object){

        Gson gson = new Gson();

        this.event = event;
        this.payload = gson.toJson(object);
        this.type = "server_to_client";

        Server.getInstance().emit(this , this);
    }


    public void on(String event, SocketInterface socketInterface) {
        Server.getInstance().on(event, socketInterface);
    }


}
