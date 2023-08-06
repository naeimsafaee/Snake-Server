package Tools;

import Server.Global;

import java.awt.*;
import java.util.Random;

public class Rand extends Random {

    public int rand(int min, int max) {
        return this.nextInt(max - min) + min;
    }

    public int rand() {
        return this.nextInt();
    }

    public String randColor() {

        Color _color = Global.colors[rand(0 , Global.colors.length)];
//        Color _color = new Color(this.nextFloat(), this.nextFloat(), this.nextFloat());

        return Integer.toString(_color.getRGB());
    }

}
