package SpaceInvaders;

import SpaceInvaders.Controller.Commands.Command;
import SpaceInvaders.Controller.Renderer;
import SpaceInvaders.Model.GameField;
import SpaceInvaders.Model.GameState;
import SpaceInvaders.Model.Level;
import SpaceInvaders.Model.Score;
import SpaceInvaders.Service.CommandHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GameApplication extends Application {
    private GameState gameState;
    private GameField gameField;
    private Renderer renderer;
    private CommandHandler commandHandler;
    private GraphicsContext graphicsContext;
    private Map<KeyCode, Command> commands = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        graphicsContext = canvas.getGraphicsContext2D();

        gameState = new GameState(new Score(), new Level());
        gameField = new GameField(gameState, canvas);

        renderer = new Renderer(graphicsContext, gameField, canvas);

        commandHandler = new CommandHandler(gameField, gameState, commands);
        commandHandler.createCommands();

        scene.setOnKeyPressed(keyEvent -> commandHandler.handleCommands(keyEvent));

        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                renderer.render();
                gameField.update();

                if(gameState.isGameOver())
                    gameOver();
            }

        }.start();

        primaryStage.show();
    }

    private void gameOver(){
        System.out.println("Game over. You lost!");
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
