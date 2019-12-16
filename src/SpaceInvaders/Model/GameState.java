package SpaceInvaders.Model;

public class GameState {

    private static GameState gameState = null;
    private boolean isGameOver = false;

    private GameState(){

    }

    public static GameState getInstance(){
        if(gameState == null)
            gameState = new GameState();
        return gameState;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

}
