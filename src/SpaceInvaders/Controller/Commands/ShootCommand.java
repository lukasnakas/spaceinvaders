package SpaceInvaders.Controller.Commands;

import SpaceInvaders.Model.Bullet;
import SpaceInvaders.Model.GameField;

import java.util.ArrayList;

public class ShootCommand implements Command {

    private final GameField gameField;

    public ShootCommand(GameField gameField){
        this.gameField = gameField;
    }

    @Override
    public void execute() {
        if(gameField.isReadyToShoot()) {
            ArrayList<Bullet> bullets = gameField.getBullets();
            gameField.setReadyToShoot(false);
            double bulletX = gameField.getShip().getPosX() + (gameField.getShip().getWidth() / 2);
            double bulletY = gameField.getShip().getPosY();
            Bullet bullet = new Bullet(bulletX, bulletY);
            bullets.add(bullet);
        }
    }

}
