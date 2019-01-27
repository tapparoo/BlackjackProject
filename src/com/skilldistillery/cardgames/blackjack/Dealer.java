package com.skilldistillery.cardgames.blackjack;

import java.util.List;

import com.skilldistillery.cardgames.common.Card;
import com.skilldistillery.cardgames.common.Deck;
import com.skilldistillery.cardgames.common.Person;

public class Dealer extends Person {

	public Dealer() {
		this("Dealer");
	}

	public Dealer(String name) {
		super(name, new BlackjackHand());
	}

	public void takeTurn(Deck deck) {
		System.out.println("\n" + getName() + "'s turn!\n");
		sleep();
		System.out.println("\n" + getName() + "'s other card was a " + getHand().getCards().get(1).toString());
		printStatus();
		while (getHand().getHandValue() < 17 || hasSoft17()) {
			sleep();
			if (hasSoft17()) {
				System.out.println("\n" + getName() + " has soft 17 and is taking a card...\n");
			} else {
				System.out.println("\n" + getName() + " is taking a card...\n");
			}
			sleep();
			getHand().addCard(deck.dealCard());
			printStatus();
		}
		if (getHand().getHandValue() <= 21) {
			System.out.println("\n" + getName() + " is standing.\n");
		}
		sleep();
	}

	void sleep() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Internal - during dealer's turn
	private void printStatus() {
		List<Card> cards = getHand().getCards();
		System.out.print("\t" + getName() + " has " + getHand().getHandValue() + ": ");
		for (Card card : cards) {
			System.out.print("\n\t\t" + card.toString());
		}
	}

	public String getUpCard() {
		return getHand().getCards().get(0).toString();
	}

	public boolean hasBlackjack() {
		if (getHand().getCards().size() == 2 && getHand().getHandValue() == 21) {
			return true;
		}
		return false;
	}

	public boolean hasSoft17() {
		BlackjackHand hand = (BlackjackHand) getHand();
		boolean hasAce = false;

		if (getHand().getHandValue() != 17) {
			return false;
		}
		for (Card card : hand.getCards()) {
			if (card.getValue() == 11) {
				hasAce = true;
			}
		}

		if (hasAce) {
			int nonAcesVal = 0;
			int acesCount = 0;
			for (Card card : hand.getCards()) {
				int val = card.getValue();
				if (val != 11) {
					nonAcesVal += val;
				} else {
					acesCount += 1;
				}
			}
			return 17 - 11 - nonAcesVal + acesCount - 1 == 0;
		}
		return false;
	}
}
