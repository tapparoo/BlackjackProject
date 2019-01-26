package com.skilldistillery.cardgames.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	List<Card> cards;
	
	public Deck(){
		cards = new ArrayList<>();
		for(Suit suit : Suit.values()) {
			for(Rank rank : Rank.values()) {
				cards.add(new Card(suit, rank));
			}
		}
		shuffle();
	}
	
	public int checkDeckSize() {
		return cards.size();
	}
	
	public Card dealCard() {
		Card card = cards.remove(0);
		return card;
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
}