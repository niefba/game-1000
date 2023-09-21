package com.codingame.game;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class Referee extends AbstractReferee {
    // Uncomment the line below and comment the line under it to create a Solo Game
    // @Inject private SoloGameManager<Player> gameManager;
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private Provider<GameGrid> GameGridProvider;

    private GameGrid masterGrid;
    private int currentPlayer = 0;
    private Board board = null;
    private static final int WINNING_SCORE = 2000;

    @Override
    public void init() {
        // Initialize your game here.
        drawBackground();
        drawGrids();
    }

    private void drawBackground() {
        graphicEntityModule.createSprite()
                .setImage("Background.jpg")
                .setAnchor(0);
        graphicEntityModule.createSprite()
                .setImage("logo_small.png")
                .setX(280)
                .setY(915)
                .setAnchor(0.5);
        graphicEntityModule.createSprite()
                .setImage("logoCG.png")
                .setX(1920 - 280)
                .setY(915)
                .setAnchor(0.5);
    }

    private void drawGrids() {
        int bigCellSize = 240;
        int bigOrigX = (int) Math.round(1920 / 2 - 2.5 * bigCellSize);
        int bigOrigY = (int) Math.round(1080 / 2 - bigCellSize);
        masterGrid = GameGridProvider.get();
        masterGrid.draw(bigOrigX, bigOrigY, bigCellSize);
    }

    private boolean noWinner(Player player) {
        if (player.getScore() > WINNING_SCORE) {
            masterGrid.drawWinner(player);
            gameManager.addToGameSummary(GameManager.formatSuccessMessage(player.getNicknameToken() + " won!"));
            gameManager.endGame();
            return false;
        } else {
            masterGrid.drawScore(gameManager.getPlayers());
            return true;
        }
    }

    @Override
    public void gameTurn(int turn) {
        Player player = gameManager.getPlayer(currentPlayer);
        if (board == null) {
            board = new Board();
        }

        System.out.println(String.format("Next board |%s|",board.toString()));
        gameManager.addToGameSummary(
            String.format("Board |%s| for %s", board.toString(), player.getNicknameToken())
        );

        masterGrid.drawBoard(board);


        // Check the score
        if (board.getLastScore() == 0) {
            // Player lose
            gameManager.addToGameSummary(
                GameManager.formatErrorMessage(
                    String.format("%s lose %d points with board |%s|", player.getNicknameToken(), board.getTotalScore(), board.toString())
                )
            );
            currentPlayer = currentPlayer == 0 ? 1 : 0;
            board = new Board();
        }
        // Check if turn is over
        else if (board.turnIsOver()) {
            player.setScore(player.getScore() + board.getTotalScore());
            gameManager.addToGameSummary(
                String.format("%s ends his turn with %d points and a total of %d points", player.getNicknameToken(), board.getTotalScore(), player.getScore())
            );
            // Check for winner
            if (noWinner(player)) {
                currentPlayer = currentPlayer == 0 ? 1 : 0;
                board = new Board();
            }
        }

        else {
            player.sendInputLine(board.toString());
            player.execute();
            try {
                // Check validity of the player output and compute the new game state
                String output = player.getOutputs().get(0);
                if (output.equals("pass")) {
                    player.setScore(player.getScore() + board.getTotalScore());
                    gameManager.addToGameSummary(
                        String.format("%s passes with %d points and a total of %d points", player.getNicknameToken(), board.getTotalScore(), player.getScore())
                    );
                    // Check for winner
                    if (noWinner(player)) {
                        currentPlayer = currentPlayer == 0 ? 1 : 0;
                        board = new Board();
                    }
                } else if (!board.applyChoice(output)) {
                    throw new InvalidBoard("Invalid board.");
                } else {
                    gameManager.addToGameSummary(
                        String.format("%s keeps |%s|", player.getNicknameToken(), output)
                    );
                }

            } catch (TimeoutException e) {
                gameManager.addToGameSummary(GameManager.formatErrorMessage(String.format("$%d timeout!", player.getIndex())));
                player.deactivate(String.format("$%d timeout!", player.getIndex()));
            } catch (InvalidBoard e) {
                gameManager.addToGameSummary(GameManager.formatErrorMessage(player.getNicknameToken() + " lose!"));
                gameManager.addToGameSummary(GameManager.formatErrorMessage(board.toString()));
                player.deactivate(e.getMessage());
                player.setScore(-1);
                gameManager.endGame();
            }
        }


    }
}
