package Server;

import java.util.Random;

public class Food {

    public int id;
    public int x;
    public int y;

    public String color;

    public Food() {
        x = (int) (Math.random() * Global.cols - 1);
        y = (int) (Math.random() * Global.rows - 1);

        id = (new Random()).nextInt();

        color = Integer.toString(Global.foodColor.getRGB());

    }

    private void getValAleaNotInSnake() {

        /*for (Snake snake : snakes) {
            ArrayList<Snake> snake_body = snake.brain.snake_body;

            for (int i = 0; i <= snake_body.size() - 1; i++) {
                if (p.getY() == snake_body.get(i).getX() &&
                        p.getX() == snake_body.get(i).getY()) {

                    ranX = (int) (Math.random() * Global.cols - 1);
                    ranY = (int) (Math.random() * Global.rows - 1);
                    p = new Food(ranX, ranY);
                    i = 0;
                }
            }
        }*/


//        return p;
    }

}
