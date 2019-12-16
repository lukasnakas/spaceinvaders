package SpaceInvaders.Model;

import javafx.scene.image.Image;

public class WeakEnemy extends Enemy {

    public WeakEnemy(int posX, int posY) {
        super(posX, posY);
        enemyShip = new Image("file:assets/enemyWeak.png");
        difficulty = "WEAK";
        maxDamageLevel = 0;
    }

    public void setNextDamagedEnemyImage(){
        currentDamageLevel++;
        String imageFile = "enemyWeak_dmg" + currentDamageLevel + ".png";
        enemyShip = new Image("file:assets/" + imageFile + "/");
    }

}
