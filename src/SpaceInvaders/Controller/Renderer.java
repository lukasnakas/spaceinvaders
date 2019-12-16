package SpaceInvaders.Controller;

import SpaceInvaders.Model.Bullet;
import SpaceInvaders.Model.Castle;
import SpaceInvaders.Model.Enemy;
import SpaceInvaders.Model.GameField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
    private GraphicsContext graphicsContext;
    private GameField gameField;
    private Canvas canvas;

    private Color backgroundColor = Color.BLACK;

    public Renderer(GraphicsContext graphicsContext, GameField gameField, Canvas canvas){
        this.graphicsContext = graphicsContext;
        this.gameField = gameField;
        this.canvas = canvas;
    }

    public void render(){
        drawBackground();
        gameField.getShip().render(graphicsContext);

        for (Enemy[] enemyLine : gameField.getEnemies())
            for (Enemy enemy : enemyLine)
                enemy.render(graphicsContext);

        for(Castle castle : gameField.getCastles())
            castle.render(graphicsContext);

        for(Bullet bullet : gameField.getBullets())
            bullet.render(graphicsContext);

        for(Bullet bullet : gameField.getEnemyBullets())
            bullet.render(graphicsContext);
    }

    private void drawBackground() {
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

}
