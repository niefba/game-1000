package com.codingame.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
  Random random = new Random(0);
  private String name;
  private int value;
  private boolean lock;
  private int turn;
  private static final String[] DICE_LABELS = {"1st", "2nd"};

  public Dice() {
    this.roll(0);
    this.lock = false;
  }

  public String getName() {
    return this.name;
  }

  public int getValue() {
    return this.value;
  }

  public void lock() {
    this.lock = true;
  }

  public boolean isLock() {
    return this.lock;
  }

  public boolean isUnlock() {
    return !this.lock;
  }

  public String getLabel() {
    return DICE_LABELS[this.turn];
  }

  public void roll(int turn) {
    this.value = ThreadLocalRandom.current().nextInt(1, 7);
    this.name = String.valueOf(this.value);
    this.turn = turn;
  }
}
