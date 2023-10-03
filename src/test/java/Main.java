import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Main {
    public static void main(String[] args) {
        /* Multiplayer Game */
        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();

        // Adds as many player as you need to test your game
        gameRunner.addAgent(Player1.class);
        gameRunner.addAgent(Player2.class);

        // Another way to add a player
        // gameRunner.addAgent("python3 /home/user/player.py");

        gameRunner.start();
    }
}
