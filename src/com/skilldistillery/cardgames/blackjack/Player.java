package com.skilldistillery.cardgames.blackjack;

import com.skilldistillery.cardgames.common.Person;

public class Player extends Person {
	// TODO - multiple players, multiple hands each (split)
	
	private double cash;
	
	public Player(String name) {
		this(name, 100.0);
	}
	
	public Player(String name, Double startingCash) {
		super(name, new BlackJackHand());
		this.cash = startingCash;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double money) {
		this.cash = money;
	}
}
