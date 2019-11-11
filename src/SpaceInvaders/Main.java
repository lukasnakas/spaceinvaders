package SpaceInvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main extends Application {

    Image background = new Image("file:assets/background.png");

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<String> commands = new ArrayList<>();
    boolean readyToShoot = true;
    double bulletX, bulletY;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Ship ship = new Ship((int)canvas.getWidth() / 2, 550);

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(550, 50));
        enemies.add(new Enemy(300, 50));

        for (Enemy e: enemies )
            e.setGraphicsContext(gc);

        boolean timeToMoveEnemies = true;

        scene.setOnKeyPressed(keyEvent -> {
            if(commands.size() == 0)
                commands.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> commands.remove(keyEvent.getCode().toString()));

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if(commands.size() != 0) {

                    if (commands.contains("D"))
                        ship.moveRight(canvas);

                    if (commands.contains("A"))
                        ship.moveLeft();

                    if(commands.contains("SPACE") && readyToShoot) {
                        readyToShoot = false;
                        bulletX = ship.getPosX() + (ship.getWidth() / 2);
                        bulletY = ship.getPosY();
                        Bullet bullet = new Bullet(bulletX, bulletY);
                        bullets.add(bullet);
                    }
                }

                if(bullets.size() == 0)
                    readyToShoot = true;

                // background image clears canvas
                gc.drawImage( background, 0, 0 );
                ship.render(gc);

                for(Enemy e : enemies)
                    e.move(canvas);

                Iterator<Bullet> iter = bullets.iterator();

                while(iter.hasNext()){
                    Bullet bullet = iter.next();
                    if(bullet.isOutOfBounds()) {
                        bullets.remove(bullet);
                        break;
                    }
                    bullet.render(gc);
                    for(Enemy enemy : enemies){
                        if(enemy.intersects(bullet)){
                            enemies.remove(enemy);
                            iter.remove();
                            break;
                        }
                    }
                }

                if(enemies.size() == 0) {
                    System.out.println("CONGRATULATIONS!!!");
                    Platform.exit();
                }
            }

        }.start();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
