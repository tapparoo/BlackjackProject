package com.skilldistillery.cardgames.blackjack;

import java.util.Collections;
import java.util.List;

import com.skilldistillery.cardgames.common.Card;
import com.skilldistillery.cardgames.common.Hand;

public class BlackjackHand extends Hand {

	public BlackjackHand() {
	}

	@Override
	public int getHandValue() {
		List<Card> hand = getCards();

		int value = 0;

		// To ensure aces are calculated last for 11 or 1 value determination
		HandSortCardListByRank comp = new HandSortCardListByRank();
		Collections.sort(hand, comp);

		for (Card card : hand) {
			if (card.getValue() == 11 && value + card.getValue() > 21) {
				value += 1;
			} else {
				value += card.getValue();
			}
		}
		return value;
	}
}
