package SpaceInvaders;

import java.util.ArrayList;

public class Gamefield {
    private Ship ship;
    private Enemy[][] enemies;
    private ArrayList<Castle> castles;

    public Gamefield(int width, int height){
        ship = new Ship(width / 2, height / 10 * 9);
        enemies = new WeakEnemy[5][10];
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
                enemies[i][j] = new WeakEnemy(enemyPosX, enemyPosY);
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
}
