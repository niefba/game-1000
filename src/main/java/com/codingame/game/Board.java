package com.codingame.game;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class Board {
  private List<Dice> dices = new ArrayList<>();
  private int totalScore;
  private int turn;
  private static final int DICE_NB = 6;

  public Board() {
      for(int i=0; i < DICE_NB;i++) {
        this.dices.add(new Dice());
      }
      this.totalScore = 0;
      this.turn = 1;
  }

  @Override
  public String toString() {
      return dices.stream().filter(dice -> dice.isUnlock()).map(Dice::getName).collect(Collectors.joining(" "));
  }

  public Integer getLastScore() {
    List<Dice> unlockDices = this.dices.stream().filter(dice -> dice.isUnlock()).collect(Collectors.toList());
    return calculateScore(unlockDices);
  }

  public List<Dice> getDices() {
    return this.dices;
  }

  public Integer getTotalScore() {
    return this.totalScore + getLastScore();
  }

  private Integer calculateScore(List<Dice> dices) {
    List<String> diceNames = dices.stream().map(Dice::getName).collect(Collectors.toList());

    int score = 0;
    int count1 = Collections.frequency(diceNames, "1");
    int count2 = Collections.frequency(diceNames, "2");
    int count3 = Collections.frequency(diceNames, "3");
    int count4 = Collections.frequency(diceNames, "4");
    int count5 = Collections.frequency(diceNames, "5");
    int count6 = Collections.frequency(diceNames, "6");

    if (count1 == 1 && count1 == count2 && count1 == count3 && count1 == count4 && count1 == count5 && count1 == count6) {
      score += 1000;
    }
    else {
      switch (count1) {
        case 3:
          score += 1000;
          break;
        case 4:
          score += 2000;
          break;
        case 5:
          score += 4000;
          break;
        case 6:
          score += 8000;
          break;
        default:
          score += 100 * count1;
          break;
      }

      switch (count5) {
        case 3:
          score += 500;
          break;
        case 4:
          score += 1000;
          break;
        case 5:
          score += 2000;
          break;
        case 6:
          score += 4000;
          break;
        default:
          score += 50 * count5;
          break;
      }

      while (count2 >= 3) {
        score += 200;
        count2 --;
      }

      while (count3 >= 3) {
        score += 300;
        count3 --;
      }

      while (count4 >= 3) {
        score += 400;
        count4 --;
      }

      while (count6 >= 3) {
        score += 600;
        count6 --;
      }
    }


    return score;
  }

  public Boolean turnIsOver() {
    return this.turn == 3;
  }

  public Boolean applyChoice(String line) {
    this.turn++;

    List<String> diceNames = Arrays.asList(line.split(" "));
    List<Dice> selectedDices = new ArrayList<>();

    // Check that dices are available
    for (String diceName : diceNames) {
      for(Dice dice : this.dices) {
        if(dice.getName().equals(diceName) && dice.isUnlock()) {
          System.out.println(String.format("diceName %s",diceName));
          dice.lock();
          selectedDices.add(dice);
          break;
        }
      }
    }

    // Dice not found
    if (selectedDices.size() != diceNames.size()) {
      return false;
    }

    // Update total score
    this.totalScore += calculateScore(selectedDices);

    // Roll remaining dices
    for(Dice dice : this.dices) {
      if(dice.isUnlock()) {
        dice.roll();
      }
    }

    return true;
  }
}
