package SpaceInvaders.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ship {

    private Image ship = new Image("file:assets/ship.png");
    private double posX, posY;
    private double  width = ship.getWidth(),
                    height = ship.getHeight();
    private double speed = 10;
    private double fieldWidth, fieldHeight;

    public Ship (double fieldWidth, double fieldHeight){
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.posX = this.fieldWidth / 2 - width / 2; // 800 - 64 / 2
        this.posY = this.fieldHeight / 10 * 9; // 600 / 10 * 9
    }

    public void render(GraphicsContext gc){
        gc.drawImage(ship, posX, posY);
    }

    public void moveRight(){
        if (getPosX() + ship.getWidth() + 2 <= fieldWidth)
            posX += speed;
    }

    public void moveLeft(){
        if (getPosX() - 2 >= 0)
            posX -= speed;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public boolean intersects(Bullet bullet){
        return bullet.getBoundary().intersects(this.getBoundary());
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
