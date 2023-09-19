package com.codingame.game;

public class InvalidBoard extends Exception {
  private static final long serialVersionUID = -8185589153224401564L;

  public InvalidBoard(String message) {
      super(message);
  }
}
