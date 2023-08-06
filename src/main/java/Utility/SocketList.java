package Utility;

import java.util.ArrayList;

public class SocketList extends ArrayList<SocketData> {



    public void emit(String event, String payload) {
        for (SocketData socket : this) {
            socket.emit(event, payload);
        }
    }

    public void setOnline(SocketData socket) {
        for (int i = 0; i < size(); i++)
            if (get(i).socketId == socket.socketId) {
                get(i).lastOnline = System.currentTimeMillis();
                return;
            }
    }

    public void removeOfflineSockets() {

        long now = System.currentTimeMillis();

        for (int i = size() - 1; i >= 0; i--) {
            if (now - get(i).lastOnline > 3000) {
                SocketData removed = get(i);
                removed.event = "disconnect";
                Server.getInstance().data(removed);
            }
        }
    }

    public SocketData removeWithSocketId(int socketId) {
        for (int i = size() - 1; i >= 0; i--) {
            if(get(i).socketId == socketId)
                return super.remove(i);
        }
        return null;
    }

}
