package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class StrongEnemy extends Enemy {

    private String fileName = "enemyMedium";
    private String fileFormat = ".png";
    private String filePath = "file:assets/" + fileName + fileFormat;

    public StrongEnemy(int posX, int posY, Image image) {
        super(posX, posY, image);
        difficulty = "STRONG";
        maxDamageLevel = 2;
    }

    public StrongEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image("file:assets/enemyStrong.png");
        difficulty = "STRONG";
        maxDamageLevel = 2;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        fileName = "enemyStrong_dmg";
        filePath = "file:assets/" + fileName + currentDamageLevel + fileFormat;
        enemyShip = new Image(filePath);
    }

}
