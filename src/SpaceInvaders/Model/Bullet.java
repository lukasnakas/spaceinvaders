package SpaceInvaders.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bullet {
    private Image bulletImage;
    private double width, height;
    private double posX, posY;
    private double speed = 10;
    private boolean belongingToPlayer = true;

    public Bullet(double posX, double posY, Image image){
        this.posX = posX;
        this.posY = posY;
        bulletImage = image;
        width = bulletImage.getWidth();
        height = bulletImage.getHeight();
    }

    public Bullet(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(bulletImage, posX, posY);
    }

    public boolean isOutOfBounds(){
        return posY + bulletImage.getHeight() < 0 || posY > 600;
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

    public void setBulletImage(Image bulletImage) {
        this.bulletImage = bulletImage;
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

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
