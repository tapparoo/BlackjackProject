package com.skilldistillery.cardgames.blackjack;

import java.util.List;

import com.skilldistillery.cardgames.common.Card;
import com.skilldistillery.cardgames.common.Hand;

public class BlackJackHand extends Hand {

	public BlackJackHand() {
	}
	
	@Override
	public int getHandValue() {
		List<Card> hand = getCards();
		
		int value = 0;
		
		for(Card card : hand) {
			value += card.getValue();
		}
		
		return value;
	}

}
