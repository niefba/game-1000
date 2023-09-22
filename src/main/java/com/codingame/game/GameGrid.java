package com.codingame.game;

import java.util.ArrayList;
import java.util.List;

import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;

public class GameGrid {
    @Inject private GraphicEntityModule graphicEntityModule;

    private String[] images = { "blank", "dice_1.png", "dice_2.png", "dice_3.png", "dice_4.png", "dice_5.png", "dice_6.png" };

    private Group entity;
    private List<Sprite> placeholders = new ArrayList<>(6);
    private Text[] scoreText = new Text[2];
    private Text versusText;

    public void draw(int origX, int origY, int cellSize) {
        this.entity = graphicEntityModule.createGroup();
        this.scoreText[0] = graphicEntityModule.createText();
        this.scoreText[1] = graphicEntityModule.createText();
        this.versusText = graphicEntityModule.createText();

        for(int i=0;i<6;i++) {
            Sprite placeholder = graphicEntityModule.createSprite()
                .setX(convert(origX, cellSize, i))
                .setY(convert(origY, cellSize, 1))

                .setBaseWidth((int) (0.8 * cellSize))
                .setBaseHeight((int) (0.8 * cellSize))
                .setAnchor(0.5);
            this.entity.add(placeholder);
            this.placeholders.add(i, placeholder);
        }

        versusText.setText("vs")
                    .setVisible(false)
                    .setX(1920/2)
                    .setY(200)
                    .setZIndex(20)
                    .setFontFamily("Arial")
                    .setFontSize(100)
                    .setFillColor(0xf2bb13)
                    .setAnchor(0.5);
        this.entity.add(versusText);

        scoreText[0].setText("")
                    .setX(660)
                    .setY(200)
                    .setZIndex(20)
                    .setFontFamily("Arial")
                    .setFontSize(100)
                    .setFillColor(0xffffff)
                    .setAnchor(0.5);
        this.entity.add(scoreText[0]);
        scoreText[1].setText("")
                    .setX(1920 - 660)
                    .setY(200)
                    .setZIndex(20)
                    .setFontFamily("Arial")
                    .setFontSize(100)
                    .setFillColor(0xffffff)
                    .setAnchor(0.5);
        this.entity.add(scoreText[1]);
    }

    public void drawScore(List<Player> players, int currentPlayer) {
        int otherPlayer = currentPlayer == 0 ? 1 : 0;
        this.versusText.setVisible(true);

        this.scoreText[0].setText(String.format("%d", players.get(0).getScore()));
        this.scoreText[1].setText(String.format("%d", players.get(1).getScore()));
        graphicEntityModule.commitEntityState(0, this.scoreText[0], this.scoreText[1]);

        this.scoreText[currentPlayer].setFillColor(0xffffff, Curve.EASE_IN);
        this.scoreText[otherPlayer].setFillColor(0x5a5a5a, Curve.EASE_IN);
        graphicEntityModule.commitEntityState(0.5, this.scoreText[0], this.scoreText[1]);
    }

    public void drawWinner(Player player) {
        this.scoreText[0].setVisible(false);
        this.scoreText[1].setVisible(false);
        graphicEntityModule.commitEntityState(0, this.scoreText[0], this.scoreText[1]);

        this.versusText.setText(String.format("%s won!", player.getNicknameToken()));
        this.versusText.setScale(0);
        graphicEntityModule.commitEntityState(0.2, this.versusText);
        this.versusText.setScale(1, Curve.ELASTIC);
        graphicEntityModule.commitEntityState(1, this.versusText);
    }

    public void drawBoard(Board board, List<Player> players, int currentPlayer) {
        List<Dice> dices = board.getDices();

        for(int i = 0; i < placeholders.size(); i++) {
            // Update dices value
            placeholders.get(i).setImage(images[dices.get(i).getValue()]);

            // Animate arrival for rolled dices
            if (!dices.get(i).isLock()) {
                placeholders.get(i).setScale(0);
                graphicEntityModule.commitEntityState(0.2, placeholders.get(i));
                placeholders.get(i).setRotation(3, Curve.EASE_IN);
                graphicEntityModule.commitEntityState(0.4, placeholders.get(i));
                placeholders.get(i).setRotation(0, Curve.EASE_OUT);
                placeholders.get(i).setScale(1, Curve.ELASTIC);
                graphicEntityModule.commitEntityState(1, placeholders.get(i));
            }
        }

        drawScore(players, currentPlayer);
    }

    private int convert(int orig, int cellSize, double unit) {
        return (int) (orig + unit * cellSize);
    }
}