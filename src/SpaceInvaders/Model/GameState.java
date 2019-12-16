package SpaceInvaders.Model;

public class GameState {

    private boolean isGameOver = false;

    public GameState(){
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

}
