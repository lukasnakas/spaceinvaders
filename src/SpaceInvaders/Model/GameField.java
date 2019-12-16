package SpaceInvaders.Model;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameField {
    private Ship ship;
    private Enemy[][] enemies;
    private ArrayList<Castle> castles;
    private GameState gameState;

    private Canvas canvas;
    private double width, height;

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private boolean readyToShoot = true;

    public GameField(GameState gameState, Canvas canvas){
        this.gameState = gameState;
        this.canvas = canvas;
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        ship = new Ship(width, height, new Image("file:assets/ship.png"));
        enemies = new Enemy[5][10];
        castles = new ArrayList<>();
        configureEnemies();
        configureCastles();
    }

    private void configureEnemies(){
        int distanceBetweenEnemiesAxisX = 70;
        int distanceBetweenEnemiesAxisY = 40;
        int enemyPosX, enemyPosY = 10;

        for(int i = 0; i < enemies.length; i++) {
            enemyPosX = 15;
            for (int j = 0; j < enemies[i].length; j++) {
                if(i < enemies.length / 3)
                    enemies[i][j] = new StrongEnemy(enemyPosX, enemyPosY, new Image("file:assets/enemyStrong.png"));
                else if(i < enemies.length / 3 * 2)
                    enemies[i][j] = new MediumEnemy(enemyPosX, enemyPosY, new Image("file:assets/enemyMedium.png"));
                else
                    enemies[i][j] = new WeakEnemy(enemyPosX, enemyPosY, new Image("file:assets/enemyWeak.png"));
                if(i == enemies.length - 1)
                    enemies[i][j].setAllowedShooting(true);
                enemyPosX += distanceBetweenEnemiesAxisX;
            }
            enemyPosY += distanceBetweenEnemiesAxisY;
        }
    }

    private void configureCastles(){
        castles.add(new Castle(100, 450));
        castles.add(new Castle(300, 450));
        castles.add(new Castle(500, 450));
        castles.add(new Castle(700, 450));
    }

    public void update(){
        handleEnemies();
        checkEnemyToCastleCollision();
        handleBullets();
        handleEnemyBullets();
        checkWinCondition();
        checkLoseCondition();
    }

    private void handleEnemies() {
        if(hasReachedBorder(enemies, canvas))
            changeDirection(enemies);

        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine)
                if (!enemy.isDestroyed())
                    enemy.move();

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
        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine)
                if (enemy.isAllowedShooting())
                    shootingEnemies.add(enemy);

        Random rand = new Random();
        int enemyIndex = rand.nextInt(shootingEnemies.size());
        return shootingEnemies.get(enemyIndex);
    }

    private void enemyShoot(Enemy[][] enemies){
        if(enemyBullets.size() == 0) {
            Enemy shootingEnemy = chooseShootingEnemy(enemies);

            double bulletPosX = (shootingEnemy.getPosX() + (shootingEnemy.getWidth()) / 2);
            double bulletPosY = shootingEnemy.getPosY() + shootingEnemy.getHeight();
            Bullet bullet = new Bullet(bulletPosX, bulletPosY, new Image("file:assets/bullet.png"));
            bullet.setBulletImage(new Image("file:assets/enemy_bullet.png"));
            bullet.setBelongingToPlayer(false);
            bullet.setSpeed(4);
            enemyBullets.add(bullet);
        }
    }

    private boolean hasReachedBorder(Enemy[][] enemies, Canvas canvas){
        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine)
                if (enemy.getPosX() <= enemy.getBorderOffset() || enemy.getPosX()
                        + enemy.getWidth() >= canvas.getWidth() - enemy.getBorderOffset())
                    return true;
        return false;
    }

    private void changeDirection(Enemy[][] enemies){
        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine) {
                enemy.setMovingLeft(!enemy.isMovingLeft());
                enemy.setPosY(enemy.getPosY() + enemy.getSpeed() * 2);
            }
    }

    private boolean areEnemiesDestroyed(Enemy[][] enemies){
        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine)
                if (!enemy.isDestroyed())
                    return false;
        return true;
    }

    private void checkWinCondition() {
        if(areEnemiesDestroyed(enemies)) {
            System.out.println("CONGRATULATIONS!!!");
            Platform.exit();
        }
    }

    private void handleEnemyBullets() {
        Iterator<Bullet> bulletsIterator = enemyBullets.iterator();

        while(bulletsIterator.hasNext()){
            Bullet bullet = bulletsIterator.next();

            if(bullet.isOutOfBounds()) {
                enemyBullets.remove(bullet);
                break;
            }
            bullet.move();

            for(Castle castle : castles)
                if(castle.intersects(bullet) && !castle.isDestroyed()) {
                    if(castle.getCurrentDamageLevel() + 1 > castle.getMaxDamageLevel())
                        castle.setDestroyed(true);
                    castle.setNextDamagedCastleImage();
                    bulletsIterator.remove();
                    break;
                }

            if(ship.intersects(bullet)) {
                bulletsIterator.remove();
                gameState.setGameOver(true);
            }
        }
    }

    private void checkEnemyToCastleCollision() {
        for(Castle castle : castles)
            for (Enemy[] enemyLine : enemies)
                for (Enemy enemy : enemyLine)
                    if (enemy.intersects(castle) && !castle.isDestroyed() && !enemy.isDestroyed()) {
                        enemy.setDestroyed(true);
                        castle.setDestroyed(true);
                    }
    }

    private void checkLoseCondition(){
        for (Enemy[] enemyLine : enemies)
            for (Enemy enemy : enemyLine)
                if ((enemy.intersects(ship) && !enemy.isDestroyed()) || enemy.getPosY() + enemy.getHeight() >= height)
                    gameState.setGameOver(true);
    }

    private void handleBullets() {
        if(bullets.size() == 0)
            readyToShoot = true;

        Iterator<Bullet> bulletsIterator = bullets.iterator();

        while(bulletsIterator.hasNext()){
            Bullet bullet = bulletsIterator.next();

            if(bullet.isOutOfBounds()) {
                bullets.remove(bullet);
                break;
            }
            bullet.move();

            for (Enemy[] enemyLine : enemies)
                for (Enemy enemy : enemyLine) {
                    if (enemy.intersects(bullet) && !enemy.isDestroyed()) {
                        enemy.takeDamage();
                        bulletsIterator.remove();
                        break;
                    }
                }

            for(Castle castle : castles)
                if(castle.intersects(bullet) && !castle.isDestroyed()) {
                    if(castle.getCurrentDamageLevel() + 1 > castle.getMaxDamageLevel())
                        castle.setDestroyed(true);
                    castle.setNextDamagedCastleImage();
                    bulletsIterator.remove();
                    break;
                }
        }
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Enemy[][] getEnemies() {
        return enemies;
    }

    public void setEnemies(Enemy[][] enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Castle> getCastles() {
        return castles;
    }

    public void setCastles(ArrayList<Castle> castles) {
        this.castles = castles;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public boolean isReadyToShoot() {
        return readyToShoot;
    }

    public void setReadyToShoot(boolean readyToShoot) {
        this.readyToShoot = readyToShoot;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
