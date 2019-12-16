package SpaceInvaders;

public class GameState {

    private Score score;
    private Level level;
    private boolean isGameOver = false;

    public GameState(Score score, Level level){
        this.level = level;
        this.score = score;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void update(){
        score.add();
        if(level.getAmountOfEnemiesAlive() <= 0)
            level.loadNext();
    }
}
