package SpaceInvaders.Model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Castle {

    private Image castleImage = new Image("file:assets/castle.png");
    private double posX, posY;
    private double  width = castleImage.getWidth(),
                    height = castleImage.getHeight();
    private int currentDamageLevel = 0;
    private int maxDamageLevel = 3;
    private boolean isDestroyed = false;

    public Castle (double posX, double posY){
        this.posX = posX - width / 2;
        this.posY = posY;
    }

    public void render(GraphicsContext gc){
        if(!isDestroyed)
            gc.drawImage(castleImage, posX, posY);
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(posX, posY, width, height);
    }

    public boolean intersects(Bullet bullet){
        return bullet.getBoundary().intersects(this.getBoundary());
    }

    public void setNextDamagedCastleImage(){
        currentDamageLevel++;
        String imageFile = "castle_dmg" + currentDamageLevel + ".png";
        castleImage = new Image("file:assets/" + imageFile + "/");
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

    public void setCastleImage(Image castleImage) {
        this.castleImage = castleImage;
    }

    public int getCurrentDamageLevel() {
        return currentDamageLevel;
    }

    public void setCurrentDamageLevel(int currentDamageLevel) {
        this.currentDamageLevel = currentDamageLevel;
    }

    public int getMaxDamageLevel() {
        return maxDamageLevel;
    }

    public void setMaxDamageLevel(int maxDamageLevel) {
        this.maxDamageLevel = maxDamageLevel;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
