package SpaceInvaders.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
    private Image bullet = new Image("file:assets/bullet.png");
    private double  width = bullet.getWidth(),
                    height = bullet.getHeight();
    private double posX, posY;
    private double speed = 10;
    private boolean belongingToPlayer = true;

    public Bullet(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(bullet, posX, posY);
    }

    public boolean isOutOfBounds(){
        return posY + bullet.getHeight() < 0 || posY > 600;
    }

    public void move(){
        if(belongingToPlayer)
            posY -= speed;
        else
            posY += speed;
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

    public void setBullet(Image bullet) {
        this.bullet = bullet;
    }

    public boolean isBelongingToPlayer() {
        return belongingToPlayer;
    }

    public void setBelongingToPlayer(boolean belongingToPlayer) {
        this.belongingToPlayer = belongingToPlayer;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
