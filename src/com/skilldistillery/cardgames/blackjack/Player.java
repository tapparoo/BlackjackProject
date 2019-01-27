package com.skilldistillery.cardgames.blackjack;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cardgames.common.Card;
import com.skilldistillery.cardgames.common.Deck;
import com.skilldistillery.cardgames.common.Person;

public class Player extends Person {
	// TODO - implement betting / double / split

	private double cash;
	private double currentBet;
	private boolean doublingDown;

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

	public void takeTurn(Scanner sc, Deck deck, String dealerUpCard) {
		String choice = "";

		while (!choice.equalsIgnoreCase("S") && !choice.equalsIgnoreCase("Q")) {

			if (hasBlackjack()) {
				System.out.println(getName() + " has Blackjack!!");
				break;
			}

			int val = getHand().getHandValue();

			printStatus(dealerUpCard);
			System.out.println("\n\tTotal: " + val);
			if (!isDoublingDown()) {
				if (val == 21) {
					System.out.println("\n" + getName() + " has 21. Standing.\n");
				}

				if (val < 21) {
					printOptions(val);
					choice = sc.next();

					try {
						doOption(choice, deck);
						val = getHand().getHandValue();
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
						continue;
					}
				} else {
					break;
				}
			} else {
				setDoublingDown(false);
				break;
			}
		}
	}

	public void doOption(String choice, Deck deck) throws IllegalArgumentException {
		Card card;
		BlackjackHand hand = (BlackjackHand) getHand();

		// TODO - Split/double options
		switch (choice) {
		case "D":
		case "d":
			if (cash < currentBet * 2) {
				throw new IllegalArgumentException("You don't have that much.");
			}
			setCurrentBet(currentBet * 2);
			setDoublingDown(true);
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
			throw new IllegalArgumentException("Invalid selection.");
		}
	}

	// TODO - Split/double options
	public void printOptions(int val) {
		if (val < 21) {
			System.out.print("\n(H)it" + "\n(D)ouble" + "\n(S)tand\n" + ">> ");
		} else {
			System.out.println(getName() + " has 21. " + getName() + " stands.\n");
		}
	}

	public void printStatus(String dealerUpCard) {
		List<Card> cards = getHand().getCards();
		System.out.println("\n\tDealer showing:\n\t\t" + dealerUpCard);
		System.out.print("\t" + getName() + " has ");
		for (Card card : cards) {
			System.out.print("\n\t\t" + card.toString());
		}
	}

	public boolean hasBlackjack() {
		if (getHand().getCards().size() == 2 && getHand().getHandValue() == 21) {
			return true;
		}
		return false;
	}

	public double getCurrentBet() {
		return currentBet;
	}

	public void setCurrentBet(double currentBet) {
		this.currentBet = currentBet;
	}

	public boolean isDoublingDown() {
		return doublingDown;
	}

	public void setDoublingDown(boolean doubling) {
		this.doublingDown = doubling;
	}

}
