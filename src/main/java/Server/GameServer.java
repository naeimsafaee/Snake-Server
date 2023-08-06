package Server;

import Utility.Server;
import Utility.SocketData;

import java.util.ArrayList;

public class GameServer {


    SnakeList snakes;
    ArrayList<Food> foods;

    Server server;

    GameServer(Server server) {
        snakes = new SnakeList();
        foods = new ArrayList<>();

        this.server = server;

        createFoods(false);
    }


    Snake createSnake(int socketId) {
        Snake new_snake = new Snake(true);
        new_snake.socketId = socketId;

        snakes.add(new_snake);

        return new_snake;
    }

    Snake removeSnake(int socketId) {
        return snakes.findAndRemoveBySocketId(socketId);
    }

    void createFoods(boolean needToSend) {
        int count = Global.fixedFoodSize - foods.size();
        for (int i = 0; i < count; i++){

            Food food = new Food();

            foods.add(food);

            if(needToSend)
                server.emit("create food" , food);
        }
    }

    void snakeMove(Snake snake, SocketData socket) {
        Snake snakeInList = snakes.findSnake(snake);
        if(snakeInList == null)
            return;

        snakeInList.move(snake.x, snake.y , snake.direction);

        socket.emit("move", snake);
        socket.broadcast("move", snake);

        if (isFood(snake.x, snake.y)) {
            snakeInList.increase_size();

            Food food = findFood(snake.x, snake.y);

            socket.emit("eat" , snakeInList);
            socket.broadcast("eat" , snakeInList);

            server.emit("remove food" , food);

            foods.remove(food);

            createFoods(true);
        }

    }

    private boolean isFood(int x, int y) {
        for (int i = 0; i < foods.size(); i++)
            if (foods.get(i).x == x && foods.get(i).y == y)
                return true;
        return false;
    }

    private Food findFood(int x, int y) {
        for (int i = 0; i < foods.size(); i++)
            if (foods.get(i).x == x && foods.get(i).y == y)
                return foods.get(i);
        return null;
    }


}
