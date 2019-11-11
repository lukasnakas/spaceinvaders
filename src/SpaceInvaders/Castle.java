package SpaceInvaders;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Castle {

    private Image castle = new Image("file:assets/castle.png");
    private double posX, posY;
    private double  width = castle.getWidth(),
                    height = castle.getHeight();
    int damageLevel = 0;
    private boolean isDestroyed = false;

    public Castle (double posX, double posY){
        this.posX = posX - width / 2;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        if(!isDestroyed)
            gc.drawImage(castle, posX, posY);
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public boolean intersects(Bullet bullet){
        return bullet.getBoundary().intersects(this.getBoundary());
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCastle(Image castle) {
        this.castle = castle;
    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public void setDamageLevel(int damageLevel) {
        this.damageLevel = damageLevel;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
