package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class MediumEnemy extends Enemy {

    private String fileName = "enemyMedium";
    private String fileFormat = ".png";
    private String filePath = "file:assets/" + fileName + fileFormat;

    public MediumEnemy(int posX, int posY, Image image) {
        super(posX, posY, image);
        difficulty = "MEDIUM";
        maxDamageLevel = 1;
    }

    public MediumEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image("file:assets/enemyMedium.png");
        difficulty = "MEDIUM";
        maxDamageLevel = 1;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        fileName = "enemyMedium_dmg";
        filePath = "file:assets/" + fileName + currentDamageLevel + fileFormat;
        enemyShip = new Image(filePath);
    }

}
