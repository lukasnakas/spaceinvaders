package SpaceInvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Renderer {
    Image background = new Image("file:assets/background.png");
    private GraphicsContext graphicsContext;
    private GameField gameField;

    public Renderer(GraphicsContext graphicsContext, GameField gameField){
        this.graphicsContext = graphicsContext;
        this.gameField = gameField;
    }

    public void render(){
        graphicsContext.drawImage(background, 0, 0);
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

}
