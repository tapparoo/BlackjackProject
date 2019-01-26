package com.skilldistillery.cardgames.blackjack;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cardgames.common.Card;
import com.skilldistillery.cardgames.common.Deck;
import com.skilldistillery.cardgames.common.Person;

public class Player extends Person {
	// TODO - implement betting / double / split

	private double cash;
	
	public Player() {
		this("Player");
	}

	public Player(String name) {
		this(name, 100.0);
	}

	public Player(String name, Double startingCash) {
		super(name, new BlackjackHand());
		this.cash = startingCash;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double money) {
		this.cash = money;
	}

	public void takeTurn(Scanner sc, Deck deck) {
		String choice = "";

		while (!choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("Q")) {

			if (isBlackjack()) {
				System.out.println(getName() + " has Blackjack!!");
				break;
			}

			int val = getHand().getHandValue();
			printStatus();

			if (val < 21) {
				System.out.println("\n\nTotal: " + val);
				printOptions(val);
				choice = sc.next();

				try {
					doOption(choice, deck);
					val = getHand().getHandValue();
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					if (sc.next().equalsIgnoreCase("Y"))
						continue;
					else
						break;

				}
			} else if (val > 21) {
				System.out.println("\n" + getName() + " busted with " + val);
				break;
			} else
				break;
		}
	}

	public void doOption(String choice, Deck deck) throws IllegalArgumentException {
		Card card;
		BlackjackHand hand = (BlackjackHand) getHand();

		// TODO - Split/double options
		switch (choice) {
		case "H":
		case "h":
			card = deck.dealCard();
			hand.addCard(card);
			break;
		case "S":
		case "s":
		case "Q":
		case "q":
			break;
		default:
			throw new IllegalArgumentException("Invalid selection.  Tr(y) again or (q)uit? ");
		}
	}

	// TODO - Split/double options
	public void printOptions(int val) {
		if (val < 21) {
			System.out.print("\n(H)it\n" + "(S)tand\n" + ">> ");
		}
	}

	public void printStatus() {
		List<Card> cards = getHand().getCards();
		System.out.print(getName() + " has: ");
		for (Card card : cards) {
			System.out.print("\n\t" + card.toString());
		}
	}

	public boolean isBlackjack() {
		if (getHand().getCards().size() == 2 && getHand().getHandValue() == 21) {
			return true;
		}
		return false;
	}
}
