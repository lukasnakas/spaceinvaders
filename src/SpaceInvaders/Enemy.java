package SpaceInvaders;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Enemy {
    private Image enemyShip = new Image("file:assets/enemy.png");
    private double posX, posY;
    private double  width = enemyShip.getWidth(),
                    height = enemyShip.getHeight();
    private double speed = 2;
    private boolean movingLeft = true;
    private boolean isDestroyed = false;
    private boolean allowedShooting = false;
    private int borderOffset = 15;

    public Enemy (int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        if(!isDestroyed)
            gc.drawImage(enemyShip, posX, posY);
    }

    public void move(){
        if(movingLeft)
            moveLeft();
        else
            moveRight();
    }

    public void moveRight(){
        posX += speed;
    }

    public void moveLeft(){
        posX -= speed;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public boolean intersects(Bullet bullet){
        return bullet.getBoundary().intersects(this.getBoundary());
    }

    public boolean intersects(Ship ship){
        return ship.getBoundary().intersects(this.getBoundary());
    }

    public boolean intersects(Castle castle){ return castle.getBoundary().intersects(this.getBoundary()); }

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

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public int getBorderOffset() {
        return borderOffset;
    }

    public boolean isAllowedShooting() {
        return allowedShooting;
    }

    public void setAllowedShooting(boolean allowedShooting) {
        this.allowedShooting = allowedShooting;
    }

    public void setEnemyShip(Image enemyShip) {
        this.enemyShip = enemyShip;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
