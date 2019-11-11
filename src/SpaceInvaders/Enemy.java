package SpaceInvaders;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy {
    private GraphicsContext gc;
    private Image enemyShip = new Image("file:assets/enemy.png");
    private double posX, posY;
    private double  width = enemyShip.getWidth(),
                    height = enemyShip.getHeight();
    private double speed = 2;
    private boolean movingLeft = true;

    public Enemy (int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void setGraphicsContext(GraphicsContext gc){
        this.gc = gc;
    }

    public void render(){
        gc.drawImage(enemyShip, posX, posY);
    }

    public void move(Canvas canvas){
        if(movingLeft)
            moveLeft();
        else
            moveRight(canvas);
    }

    public void moveRight(Canvas canvas){

        if(posX + enemyShip.getWidth() + speed < canvas.getWidth() - 10)
            posX += speed;
        else
            movingLeft = true;
        render();
    }

    public void moveLeft(){
        if(posX - speed > 10)
            posX -= speed;
        else
            movingLeft = false;
        render();
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