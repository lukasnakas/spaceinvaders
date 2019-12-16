package SpaceInvaders.Service;

import SpaceInvaders.Controller.Commands.Command;
import SpaceInvaders.Controller.Commands.MoveLeftCommand;
import SpaceInvaders.Controller.Commands.MoveRightCommand;
import SpaceInvaders.Controller.Commands.ShootCommand;
import SpaceInvaders.Model.GameField;
import SpaceInvaders.Model.GameState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Map;

public class CommandHandler {
    private Map<KeyCode, Command> commands;
    private GameState gameState;
    private GameField gameField;

    public CommandHandler(GameField gameField, GameState gameState, Map<KeyCode, Command> commands){
        this.commands = commands;
        this.gameState = gameState;
        this.gameField = gameField;
    }

    public void handle(KeyEvent key) {
        if (gameState.isGameOver()) {
            return;
        }

        var command = commands.get(key.getCode());
        if (command != null) {
            command.execute();
        }
    }

    public void createCommands(){
        Command moveLeft = new MoveLeftCommand(gameField);
        commands.put(KeyCode.A, moveLeft);

        Command moveRight = new MoveRightCommand(gameField);
        commands.put(KeyCode.D, moveRight);

        Command shoot = new ShootCommand(gameField);
        commands.put(KeyCode.SPACE, shoot);
    }

    public void handleCommands(KeyEvent key){
        Command command = commands.get(key.getCode());

        if(command != null)
            command.execute();
    }

}
