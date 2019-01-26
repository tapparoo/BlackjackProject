package com.skilldistillery.cardgames.blackjack;

import java.util.Scanner;

import com.skilldistillery.cardgames.common.Deck;

public class BlackjackApp {

	public static void main(String[] args) {
		BlackjackApp app = new BlackjackApp();
		app.run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		Deck deck = new Deck();
		Player player = new Player("Adam");
		BlackJackHand hand = (BlackJackHand)player.getHand();
		
		hand.addCard(deck.dealCard());
		hand.addCard(deck.dealCard());
		player.takeTurn(sc, deck);
		
	}
}
