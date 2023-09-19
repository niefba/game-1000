package com.codingame.game;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Board {
  Random random = new Random(0);
  private List<String> dices = new ArrayList<>();;
  private int totalScore;
  private int turn;

  public Board(int size) {
      for(int i=0; i < size;i++) {
        this.dices.add(String.valueOf(ThreadLocalRandom.current().nextInt(1, 7)));
      }
      this.totalScore = 0;
      this.turn = 1;
  }

  @Override
  public String toString() {
      return this.dices.stream().map(Object::toString).collect(Collectors.joining(" "));
  }

  public Integer getScore() {
    return calculateScore(this.dices);
  }

  public Integer getTotalScore() {
    return this.totalScore + calculateScore(this.dices);
  }

  private Integer calculateScore(List<String> dices) {
    int score = 0;
    int count1 = Collections.frequency(dices, "1");
    int count2 = Collections.frequency(dices, "2");
    int count3 = Collections.frequency(dices, "3");
    int count4 = Collections.frequency(dices, "4");
    int count5 = Collections.frequency(dices, "5");
    int count6 = Collections.frequency(dices, "6");

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
    if (!line.equals("none")) {
      List<String> dices = Arrays.asList(line.split(" "));

      for (String dice : dices) {
        if (this.dices.contains(dice)) {
          this.dices.remove(dice);
        } else {
          return false;
        }
      }

      // Update total score
      this.totalScore += calculateScore(dices);
    }

    // Roll the remaining dices
    for(int i=0; i < this.dices.size(); i++) {
      this.dices.set(i, String.valueOf(ThreadLocalRandom.current().nextInt(1, 7)));
    }
    return true;
  }
}
