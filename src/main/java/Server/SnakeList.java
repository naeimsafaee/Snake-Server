package Server;

import java.util.ArrayList;

public class SnakeList extends ArrayList<Snake> {

    Snake findAndRemoveBySocketId(int socketId) {
        for (int i = 0; i < size(); i++) {
            if (get(i).socketId == socketId) {
                Snake snake = get(i);
                remove(i);
                return snake;
            }
        }
        return null;
    }


    Snake findSnake(Snake snake) {
        for (int i = 0; i < size(); i++) {
            if (get(i).id == snake.id)
                return get(i);
        }
        return null;
    }

}
