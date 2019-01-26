package com.skilldistillery.cardgames.blackjack;

import com.skilldistillery.cardgames.common.Person;

public class Dealer extends Person {

	// TODO - dealer logic
	public Dealer() {
		this("Dealer");
	}
	
	public Dealer(String name) {
		super(name, new BlackjackHand());
	}

	public void takeTurn() {
		
	}
	
	public boolean isBlackjack() {
		if(getHand().getCards().size() == 2 && getHand().getHandValue() == 21) {
			return true;
		}
		return false;
	}
}
