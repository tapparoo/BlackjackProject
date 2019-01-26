package com.skilldistillery.cardgames.blackjack;

import com.skilldistillery.cardgames.common.Person;

public class Dealer extends Person {

	public Dealer(String name) {
		super(name, new BlackJackHand());
	}

	public void takeTurn() {
		
	}
}
