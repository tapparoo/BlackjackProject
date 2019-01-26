package com.skilldistillery.cardgames.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Hand implements Comparator<Card>{
	private List<Card> hand;

	public Hand() {
		this.hand = new ArrayList<>();
	}
	
	public abstract int getHandValue();
	
	public void addCard(Card card) {
		hand.add(card);
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	public List<Card> getCards(){
		return hand;
	}
	
	@Override
	public int compare(Card c1, Card c2) {
		return c1.getValue() - c2.getValue();
	}
	
	@Override
	public String toString() {
		String out = "Hand total: " + getHandValue();
		
		// imp later
//		for(Card card : hand) {
//			out += card.rank.getValue() + " " + card.suit.getName();
//		}
		return out;
	}
	
	
}
