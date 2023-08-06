package Utility;

public class HeartBeat extends Thread{

    Server server;
    HeartBeat(Server server){
        this.server = server;
    }

    @Override
    public void run() {
        super.run();

        while (true){

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            server.emit("heartbeat");

            server.sockets.removeOfflineSockets();
        }

    }
}
