package SpaceInvaders.Controller.Commands;

import SpaceInvaders.Model.GameField;
import SpaceInvaders.Model.Ship;

public class MoveLeftCommand implements Command {

    private final GameField gameField;

    public MoveLeftCommand(GameField gameField){
        this.gameField = gameField;
    }

    @Override
    public void execute() {
        Ship ship = gameField.getShip();
        ship.moveLeft();
    }

}
