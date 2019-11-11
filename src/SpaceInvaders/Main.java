package SpaceInvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


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
        Enemy[][] enemies = new Enemy[5][11];

        int distanceBetweenEnemiesAxisX = 70;
        int distanceBetweenEnemiesAxisY = 40;
        int enemyPosX = 15, enemyPosY = 20;

        for(int i = 0; i < enemies.length; i++) {
            enemyPosX = 15;
            for (int j = 0; j < enemies[i].length; j++) {
                enemies[i][j] = new Enemy(enemyPosX, enemyPosY);
                enemyPosX += distanceBetweenEnemiesAxisX;
            }
            enemyPosY += distanceBetweenEnemiesAxisY;
        }

//        enemies.add(new Enemy(550, 50));
//        enemies.add(new Enemy(300, 50));

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString() != "SPACE")
                commands.clear();
            commands.add(keyEvent.getCode().toString());
        });

        scene.setOnKeyReleased(keyEvent -> {
            if(commands.contains(keyEvent.getCode().toString()))
                commands.remove(keyEvent.getCode().toString());
        });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                render(gc, ship, enemies, bullets);
                handleCommands(ship, canvas, gc);

                if(bullets.size() == 0)
                    readyToShoot = true;

                handleEnemies(enemies, canvas);
                handleBullets(enemies);
                checkWinCondition(enemies);
            }

        }.start();

        primaryStage.show();
    }

    private void handleCommands(Ship ship, Canvas canvas, GraphicsContext gc) {
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
    }

    private void handleEnemies(Enemy[][] enemies, Canvas canvas) {
        if(hasReachedBorder(enemies, canvas))
            changeMovingWay(enemies);

        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++) {
                if(!enemies[i][j].isDestroyed())
                enemies[i][j].move(canvas);
            }
    }

    private boolean hasReachedBorder(Enemy[][] enemies, Canvas canvas){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                if (enemies[i][j].getPosX() == 15 || enemies[i][j].getPosX() + enemies[i][j].getWidth() == 765)
                    return true;
        return false;
    }

    private void changeMovingWay(Enemy[][] enemies){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                enemies[i][j].setMovingLeft(!enemies[i][j].isMovingLeft());
    }

    private boolean areEnemiesDestroyed(Enemy[][] enemies){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                if(!enemies[i][j].isDestroyed())
                    return false;
        return true;
    }

    private void checkWinCondition(Enemy[][] enemies) {
        if(areEnemiesDestroyed(enemies)) {
            System.out.println("CONGRATULATIONS!!!");
            Platform.exit();
        }
    }

    private void handleBullets(Enemy[][] enemies) {
        Iterator<Bullet> iter = bullets.iterator();

        while(iter.hasNext()){
            Bullet bullet = iter.next();

            if(bullet.isOutOfBounds()) {
                bullets.remove(bullet);
                break;
            }
            bullet.move();

            for(int i = 0; i < enemies.length; i++)
                for(int j = 0; j < enemies[i].length; j++)
                    if(enemies[i][j].intersects(bullet) && !enemies[i][j].isDestroyed()){
                        enemies[i][j].setDestroyed(true);
                        iter.remove();
                        break;
                    }
        }
    }

    public void render(GraphicsContext gc, Ship ship, Enemy[][] enemies, ArrayList<Bullet> bullets){
        gc.drawImage( background, 0, 0 );
        ship.render(gc);

        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                enemies[i][j].render(gc);

        for(Bullet bullet : bullets)
            bullet.render(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
