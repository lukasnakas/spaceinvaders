package SpaceInvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    GameState gameState = new GameState(new Score(), new Level());
    ArrayList<String> commands = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameField gameField = new GameField(gameState, canvas.getWidth(), canvas.getHeight());
        Renderer renderer = new Renderer(gc, gameField, canvas);

        scene.setOnKeyPressed(keyEvent -> {
            if(!keyEvent.getCode().toString().equals("SPACE"))
                commands.clear();
            if(!commands.contains(keyEvent.getCode().toString()))
                commands.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> commands.remove(keyEvent.getCode().toString()));

        new AnimationTimer()
        {
            @Override
            public void handle(long currentNanoTime)
            {
                renderer.render();
                handleCommands(gameField, canvas, gameField.getBullets());
                gameField.handleEnemies(canvas);
                gameField.checkEnemyToCastleCollision();
                gameField.handleBullets();
                gameField.handleEnemyBullets();
                gameField.checkWinCondition();
                gameField.checkLoseCondition();

                if(gameState.isGameOver())
                    gameOver();
            }

        }.start();

        primaryStage.show();
    }

    private void handleCommands(GameField gameField, Canvas canvas, ArrayList<Bullet> bullets) {
        if(commands.size() != 0) {

            if (commands.contains("D"))
                gameField.getShip().moveRight(canvas);

            if (commands.contains("A"))
                gameField.getShip().moveLeft();

            if(commands.contains("SPACE") && gameField.isReadyToShoot()) {
                gameField.setReadyToShoot(false);
                double bulletX = gameField.getShip().getPosX() + (gameField.getShip().getWidth() / 2);
                double bulletY = gameField.getShip().getPosY();
                Bullet bullet = new Bullet(bulletX, bulletY);
                bullets.add(bullet);
            }
        }
    }

    private void gameOver(){
        System.out.println("Game over. You lost!");
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
