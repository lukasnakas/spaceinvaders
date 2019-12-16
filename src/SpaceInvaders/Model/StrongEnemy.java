package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class StrongEnemy extends Enemy {

    public StrongEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image("file:assets/enemyStrong.png");
        difficulty = "STRONG";
        maxDamageLevel = 2;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        String imageFile = "enemyStrong_dmg" + currentDamageLevel + ".png";
        enemyShip = new Image("file:assets/" + imageFile + "/");
    }

}
