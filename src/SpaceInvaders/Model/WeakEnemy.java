package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class WeakEnemy extends Enemy {

    private String fileName = "enemyWeak";
    private String fileFormat = ".png";
    private String filePath = "file:assets/" + fileName + fileFormat;

    public WeakEnemy(int posX, int posY, Image image) {
        super(posX, posY, image);
        difficulty = "WEAK";
        maxDamageLevel = 0;
    }

    public WeakEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image(filePath);
        difficulty = "WEAK";
        maxDamageLevel = 0;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        fileName = "enemyWeak_dmg";
        filePath = "file:assets/" + fileName + currentDamageLevel + fileFormat;
        enemyShip = new Image(filePath);
    }

}
