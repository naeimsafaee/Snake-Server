package Utility;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

public class Client{

    public int id;
    private final int port;
    private final InetAddress address;


    public Client(int id, InetAddress address, int port) {
        this.id = id;
        this.address = address;
        this.port = port;
    }

    public Client(InetAddress address, int port) {
        id = (new Random()).nextInt();
        this.address = address;
        this.port = port;
    }


}
