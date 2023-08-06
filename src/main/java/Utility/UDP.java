package Utility;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDP extends DatagramSocket {


    public ReceiverInterface receiverInterface;

    public UDP(int port) throws IOException {
        super(port);
    }

    public void listen() {

        Thread input = new Thread(() -> {

            byte[] receive = new byte[65535];

            DatagramPacket DpReceive;

            while (true) {

                DpReceive = new DatagramPacket(receive, receive.length);

                try {
                    receive(DpReceive);

                    String received = new String(DpReceive.getData(), 0, DpReceive.getLength());

//                    System.out.println(received);

                    SocketData received_data = ParseJsonData(received);
                    received_data.address = DpReceive.getAddress();
                    received_data.port = DpReceive.getPort();
                    received_data.lastOnline = System.currentTimeMillis();

                    receiverInterface.data(received_data);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }


                receive = new byte[65535];
            }
        });

        input.start();
    }

    public void send(SocketData socket) throws IOException {

        Gson gson = new Gson();
        String payload = gson.toJson(socket);

//        System.out.println(payload);

        byte[] buf = payload.getBytes(StandardCharsets.UTF_8);

        DatagramPacket send_packet = new DatagramPacket(buf, buf.length,
                socket.address, socket.port);

        send(send_packet);
    }

    private SocketData ParseJsonData(String received) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(received);
        return gson.fromJson(object, SocketData.class);
    }

    public interface ReceiverInterface {
        void data(SocketData socket);
    }

}
