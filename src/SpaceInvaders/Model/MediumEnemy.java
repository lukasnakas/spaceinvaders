package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class MediumEnemy extends Enemy {

    public MediumEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image("file:assets/enemyMedium.png");
        difficulty = "MEDIUM";
        maxDamageLevel = 1;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        String imageFile = "enemyMedium_dmg" + currentDamageLevel + ".png";
        enemyShip = new Image("file:assets/" + imageFile + "/");
    }

}
