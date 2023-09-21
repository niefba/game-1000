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

    private String[] images = { "dice_0.png", "dice_1.png", "dice_2.png", "dice_3.png", "dice_4.png", "dice_5.png", "dice_6.png" };

    private Group entity;
    private List<Sprite> placeholders = new ArrayList<>(6);
    private Text scoreText;

    public void draw(int origX, int origY, int cellSize) {
        this.entity = graphicEntityModule.createGroup();
        this.scoreText = graphicEntityModule.createText();

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

        scoreText.setText("")
                    .setX(1920/2)
                    .setY(200)
                    .setZIndex(20)
                    .setFontFamily("Arial")
                    .setFontSize(100)
                    .setFillColor(0xf2bb13)
                    .setAnchor(0.5);
        this.entity.add(scoreText);
    }

    public void drawScore(List<Player> players) {
        this.scoreText.setText(String.format("%05d vs %05d", players.get(0).getScore(), players.get(1).getScore()));
    }

    public void drawBoard(Board board) {
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
    }

    private int convert(int orig, int cellSize, double unit) {
        return (int) (orig + unit * cellSize);
    }

    public void hide() {
        this.entity.setAlpha(0);
        this.entity.setVisible(false);
    }

    public void activate() {
        this.entity.setAlpha(1, Curve.NONE);
        graphicEntityModule.commitEntityState(1, entity);
    }

    public void deactivate() {
            this.entity.setAlpha(0, Curve.NONE);
            //graphicEntityModule.commitEntityState(1, entity);
            graphicEntityModule.commitEntityState(0.1, entity);
    }
}