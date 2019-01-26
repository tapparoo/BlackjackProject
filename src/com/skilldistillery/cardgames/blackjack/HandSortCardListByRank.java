package com.skilldistillery.cardgames.blackjack;

import java.util.Comparator;

import com.skilldistillery.cardgames.common.Card;

public class HandSortCardListByRank implements Comparator<Card> {
	public int compare(Card c1, Card c2) {
		return c1.getValue() - c2.getValue();
	}

}
