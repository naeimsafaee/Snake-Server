package Utility;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Server implements UDP.ReceiverInterface {

    UDP udp;

    public SocketList sockets;

    protected ArrayList<Event> events;

    static Server instance;

    public Server(int port) throws IOException {
        instance = this;

        sockets = new SocketList();
        events = new ArrayList<>();

        udp = new UDP(port);
        udp.receiverInterface = this;
        udp.listen();

        System.out.println("Server listening on port: " + port);

        on("connection", new SocketDataInterface() {
            @Override
            public void data(SocketData socket) {

                socket.socketId = (new Random()).nextInt();

                sockets.add(socket);

                System.out.println("One client connected with id : " + socket.socketId);

                socket.emit("connection", "you are connected to the server successfully");
            }
        });

        on("disconnect", new SocketDataInterface() {
            @Override
            public void data(SocketData socket) {
                System.out.println("One socket disconnected with id: " + socket.socketId);

                sockets.removeWithSocketId(socket.socketId);
            }
        });

        on("heartbeat", new SocketDataInterface() {
            @Override
            public void data(SocketData socket) {
                sockets.setOnline(socket);
            }
        });

        runHeartBeat();
    }

    public void emit(String event, String payload) {
        for (SocketData socket : sockets) {
            socket.emit(event, payload);
        }
    }

    public void emit(String event, Object object) {
        for (SocketData socket : sockets) {
            socket.emit(event, object);
        }
    }

    public void emit(String event) {
        emit(event, "");
    }

    private void runHeartBeat() {
        new HeartBeat(this).start();
    }


    void emit(SocketData data) {
        try {
            udp.send(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void emit(SocketData data, SocketData... excludeSockets) {
        //send this to all clients except excludeSockets
        for (int i = 0; i < Server.getInstance().sockets.size(); i++)
            for (int j = 0; j < excludeSockets.length; j++) {
                if (Server.getInstance().sockets.get(i).socketId != excludeSockets[j].socketId){
                    emit(new SocketData(data.socketId, data.event, data.payload, data.type ,
                            Server.getInstance().sockets.get(i).address , Server.getInstance().sockets.get(i).port));
                }
            }

    }


    public void on(String event, SocketInterface socketInterface) {
        on(event, socketInterface, Tools.getGeneric(socketInterface));
    }

    void on(String event, SocketInterface socketInterface, Type type) {
        events.add(new Event(event, socketInterface, type));
    }


    @Override
    public void data(SocketData data) {
        //do not convert to foreach , we are using it in another thread
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            if (event.name.equals(data.event)) {

                if (event.socketInterface instanceof SocketObjectInterface<?>) {
                    ((SocketObjectInterface<Object>) event.socketInterface).data(Tools.ParseJsonData(data.payload, event.type));
                } else if (event.socketInterface instanceof SocketObjectAndDataInterface<?>) {
                    ((SocketObjectAndDataInterface<Object>) event.socketInterface).data(Tools.ParseJsonData(data.payload, event.type), data);
                } else {
                    ((SocketDataInterface) event.socketInterface).data(data);
                }

            }
        }
    }

    public static Server getInstance() {
        return instance;
    }

}
