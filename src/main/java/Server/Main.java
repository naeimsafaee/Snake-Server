package Server;


import Utility.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            Server server = new Server(1234);

            GameServer gameServer = new GameServer(server);

            server.on("connection", new SocketDataInterface() {
                @Override
                public void data(SocketData socket) {

                    //send all previous snakes to client
                    for (int i = 0; i < gameServer.snakes.size(); i++)
                        if (gameServer.snakes.get(i).socketId != socket.socketId)
                            socket.emit("previous snakes", gameServer.snakes.get(i));

                    //send all food to client
                    for (int i = 0; i < gameServer.foods.size(); i++)
                        socket.emit("create food", gameServer.foods.get(i));

                }
            });


            server.on("create snake", new SocketDataInterface() {
                @Override
                public void data(SocketData socket) {
                    System.out.println("One client request to create snake with id: " + socket.socketId);

                    Snake client_snake = gameServer.createSnake(socket.socketId);

                    //send snake to client
                    socket.emit("create snake", client_snake);

                    //send snake to other client
                    socket.broadcast("create snake", client_snake);
                }
            });

            server.on("move", new SocketObjectAndDataInterface<Snake>() {
                @Override
                public void data(Snake snake, SocketData socket) {
                    gameServer.snakeMove(snake, socket);
                }
            });

            server.on("disconnect", new SocketDataInterface() {
                @Override
                public void data(SocketData socket) {
                    Snake removedSnake = gameServer.removeSnake(socket.socketId);

                    if (removedSnake != null)
                        server.emit("remove snake", removedSnake);
                }
            });

            server.on("snake died", new SocketObjectInterface<Snake>() {
                @Override
                public void data(Snake removedSnake) {
                    server.emit("remove snake", removedSnake);
                }

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
