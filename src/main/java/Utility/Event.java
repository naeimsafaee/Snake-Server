package Utility;

import java.lang.reflect.Type;

public class Event {

    String name;
    SocketInterface socketInterface;

    Type type;

    Event(String name , SocketInterface socketInterface , Type type){
        this.name = name;
        this.socketInterface = socketInterface;
        this.type = type;
    }

}
