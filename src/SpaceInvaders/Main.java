package SpaceInvaders;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {
    Image background = new Image("file:assets/background.png");

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Bullet> enemyBullets = new ArrayList<>();
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
        ArrayList<Castle> castles = new ArrayList<>();
        Enemy[][] enemies = new Enemy[5][11];

        int distanceBetweenEnemiesAxisX = 70;
        int distanceBetweenEnemiesAxisY = 40;
        int enemyPosX = 15, enemyPosY = 10;

        for(int i = 0; i < enemies.length; i++) {
            enemyPosX = 15;
            for (int j = 0; j < enemies[i].length; j++) {
                enemies[i][j] = new Enemy(enemyPosX, enemyPosY);
                if(i == enemies.length - 1)
                    enemies[i][j].setAllowedShooting(true);
                enemyPosX += distanceBetweenEnemiesAxisX;
            }
            enemyPosY += distanceBetweenEnemiesAxisY;
        }

        castles.add(new Castle(100, 450));
        castles.add(new Castle(300, 450));
        castles.add(new Castle(500, 450));
        castles.add(new Castle(700, 450));

        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().toString() != "SPACE")
                commands.clear();
            if(!commands.contains(keyEvent.getCode().toString()))
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
                render(gc, ship, enemies, castles, bullets, enemyBullets);
                handleCommands(ship, canvas, gc);
                handleEnemies(enemies, canvas);
                checkEnemyToCastleCollision(enemies, castles);
                handleBullets(enemies, castles);
                handleEnemyBullets(ship, castles);
                checkWinCondition(enemies);
                checkLoseCondition(enemies, ship, canvas);
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
            for(int j = 0; j < enemies[i].length; j++)
                if(!enemies[i][j].isDestroyed())
                    enemies[i][j].move(canvas);

        updateAvailableEnemyShooters(enemies);
        enemyShoot(enemies);
    }

    private void updateAvailableEnemyShooters(Enemy[][] enemies){
        for(int i = enemies.length - 1; i >= 0; i--)
            for(int j = 0; j < enemies[i].length; j++){
                Enemy enemy = enemies[i][j];
                if (!enemy.isDestroyed() && !enemy.isAllowedShooting() && !isEnemyShooterInColumn(enemies, j)) {
                    enemy.setAllowedShooting(true);
                    break;
                }
            }
    }

    private boolean isEnemyShooterInColumn(Enemy[][] enemies, int columnIndex){
        for(int i = enemies.length - 1; i >= 0; i--)
            if(enemies[i][columnIndex].isAllowedShooting())
                return true;
        return false;
    }

    private Enemy chooseShootingEnemy(Enemy[][] enemies){
        ArrayList<Enemy> shootingEnemies = new ArrayList<>();
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                if(enemies[i][j].isAllowedShooting())
                    shootingEnemies.add(enemies[i][j]);

        Random rand = new Random();
        int enemyIndex = rand.nextInt(shootingEnemies.size());
        return shootingEnemies.get(enemyIndex);
    }

    private void enemyShoot(Enemy[][] enemies){
        if(enemyBullets.size() == 0) {
            Enemy shootingEnemy = chooseShootingEnemy(enemies);

            double bulletPosX = (shootingEnemy.getPosX() + (shootingEnemy.getWidth()) / 2);
            double bulletPosY = shootingEnemy.getPosY() + shootingEnemy.getHeight();
            Bullet bullet = new Bullet(bulletPosX, bulletPosY);
            bullet.setBullet(new Image("file:assets/enemy_bullet.png"));
            bullet.setBelongingToPlayer(false);
            bullet.setSpeed(4);
            enemyBullets.add(bullet);
        }
    }

    private boolean hasReachedBorder(Enemy[][] enemies, Canvas canvas){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                if (enemies[i][j].getPosX() <= enemies[i][j].getBorderOffset() || enemies[i][j].getPosX() + enemies[i][j].getWidth() >= canvas.getWidth() - enemies[i][j].getBorderOffset())
                    return true;
        return false;
    }

    private void changeMovingWay(Enemy[][] enemies){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++) {
                enemies[i][j].setMovingLeft(!enemies[i][j].isMovingLeft());
                enemies[i][j].setPosY(enemies[i][j].getPosY() + enemies[i][j].getSpeed() * 2);
            }
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

    private void handleEnemyBullets(Ship ship, ArrayList<Castle> castles) {
        Iterator<Bullet> iter = enemyBullets.iterator();

        while(iter.hasNext()){
            Bullet bullet = iter.next();

            if(bullet.isOutOfBounds()) {
                enemyBullets.remove(bullet);
                break;
            }
            bullet.move();

            for(Castle castle : castles)
                if(castle.intersects(bullet) && !castle.isDestroyed()) {
                    int currentDamageLevel = castle.getDamageLevel();
                    if(currentDamageLevel + 1 > 3)
                        castle.setDestroyed(true);
                    String imageFile = "castle_dmg" + (currentDamageLevel + 1) + ".png";
                    castle.setDamageLevel(currentDamageLevel + 1);
                    castle.setCastle(new Image("file:assets/" + imageFile + "/"));
                    iter.remove();
                    break;
                }

            if(ship.intersects(bullet)) {
                iter.remove();
                gameOver();
            }
        }
    }

    private void checkEnemyToCastleCollision(Enemy[][] enemies, ArrayList<Castle> castles) {
        for(Castle castle : castles)
            for(int i = 0; i < enemies.length; i++)
                for(int j = 0; j < enemies[i].length; j++)
                    if(enemies[i][j].intersects(castle) && !castle.isDestroyed() && !enemies[i][j].isDestroyed()){
                        enemies[i][j].setDestroyed(true);
                        castle.setDestroyed(true);
                    }
    }

    private void checkLoseCondition(Enemy[][] enemies, Ship ship, Canvas canvas){
        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++) {
                Enemy enemy = enemies[i][j];
                if((enemy.intersects(ship) && !enemy.isDestroyed()) || enemy.getPosY() + enemy.getHeight() >= canvas.getHeight())
                    gameOver();
            }
    }

    private void handleBullets(Enemy[][] enemies, ArrayList<Castle> castles) {
        if(bullets.size() == 0)
            readyToShoot = true;

        Iterator<Bullet> iter = bullets.iterator();

        while(iter.hasNext()){
            Bullet bullet = iter.next();

            if(bullet.isOutOfBounds()) {
                bullets.remove(bullet);
                break;
            }
            bullet.move();

            for(int i = 0; i < enemies.length; i++)
                for(int j = 0; j < enemies[i].length; j++) {
                    Enemy enemy = enemies[i][j];
                    if (enemy.intersects(bullet) && !enemy.isDestroyed()) {
                        enemy.setDestroyed(true);
                        if (enemy.isAllowedShooting())
                            enemy.setAllowedShooting(false);
                        iter.remove();
                        break;
                    }
                }

            for(Castle castle : castles)
                if(castle.intersects(bullet) && !castle.isDestroyed()) {
                    int currentDamageLevel = castle.getDamageLevel();
                    if(currentDamageLevel + 1 > 3)
                        castle.setDestroyed(true);
                    String imageFile = "castle_dmg" + (currentDamageLevel + 1) + ".png";
                    castle.setDamageLevel(currentDamageLevel + 1);
                    castle.setCastle(new Image("file:assets/" + imageFile + "/"));
                    iter.remove();
                    break;
                }
        }
    }

    public void render(GraphicsContext gc, Ship ship, Enemy[][] enemies, ArrayList<Castle> castles, ArrayList<Bullet> bullets, ArrayList<Bullet> enemyBullets){
        gc.drawImage( background, 0, 0 );
        ship.render(gc);

        for(int i = 0; i < enemies.length; i++)
            for(int j = 0; j < enemies[i].length; j++)
                enemies[i][j].render(gc);

        for(Castle castle : castles)
            castle.render(gc);

        for(Bullet bullet : bullets)
            bullet.render(gc);

        for(Bullet bullet : enemyBullets)
            bullet.render(gc);
    }

    private void gameOver(){
        System.out.println("Game over. You lost!");
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
