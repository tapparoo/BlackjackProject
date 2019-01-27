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

	// TODO - deal with soft 17
	public void takeTurn(Deck deck) {
		System.out.println("\n" + getName() + "'s turn!\n");
		sleep();
		System.out.println("\n" + getName() + "'s other card was a " + getHand().getCards().get(1).toString());
		printStatus();
		while (getHand().getHandValue() < 17) {
			sleep();
			System.out.println("\n" + getName() + " is taking a card...\n");
			sleep();
			getHand().addCard(deck.dealCard());
			printStatus();
		}
		if(getHand().getHandValue() <= 21) {
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

	// External - during player(s) turn
	public void printStatus(String dealerUpCard) {
		List<Card> cards = getHand().getCards();
		System.out.print("\t" + getName() + " has ");
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

	// TODO - figure out logic. This will only work for 1st two cards
	public boolean hasSoft17() {
		if (getHand().getHandValue() == 17) {
			if (getHand().getCards().get(0).getValue() == 11 || getHand().getCards().get(1).getValue() == 11) {
				return true;
			}
		}
		return false;
	}
}
