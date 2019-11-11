package SpaceInvaders;

import com.sun.prism.Graphics;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
    private Image bullet = new Image("file:assets/bullet.png");
    private double width = bullet.getWidth(),
                height = bullet.getHeight();
    private double posX, posY;
    private double speed = 10;

    public Bullet(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(bullet, posX, posY);
    }

    public boolean isOutOfBounds(){
        if(posY + bullet.getHeight() < 0)
            return true;
        else
            return false;
    }

    public void move(){
        posY -= speed;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return this.speed;
    }
}
