package com.codingame.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
  Random random = new Random(0);
  private String name;
  private int value;
  private boolean lock;

  public Dice() {
    this.roll();
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

  public void roll() {
    this.value = ThreadLocalRandom.current().nextInt(1, 7);
    this.name = String.valueOf(this.value);
  }
}
