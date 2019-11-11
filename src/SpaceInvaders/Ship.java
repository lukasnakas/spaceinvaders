package SpaceInvaders;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Window;

public class Ship {
    private Image ship = new Image("file:assets/ship.png");
    private double posX, posY;
    private double  width = ship.getWidth(),
                    height = ship.getHeight();

    public Ship (double posX, double posY){
        this.posX = posX - width / 2;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(ship, posX, posY);
    }

    public void moveRight(Canvas canvas){
        if (getPosX() + ship.getWidth() + 2 <= canvas.getWidth())
            posX += 5;
    }

    public void moveLeft(){
        if (getPosX() - 2 >= 0)
            posX -= 5;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getWidth(){
        return this.width;
    }

    public double getHeight(){
        return this.height;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
