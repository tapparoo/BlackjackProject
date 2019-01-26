package com.skilldistillery.cardgames.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cardgames.common.Deck;

public class BlackjackApp {
	List<Player> players = null;

	public static void main(String[] args) {
		BlackjackApp app = new BlackjackApp();
		app.run();
	}

	// TODO - incorporate betting and option to continue after each hand
	void run() {
		Scanner sc = new Scanner(System.in);
		String option = "";
		Player player = null;

		while (!option.equalsIgnoreCase("q")) {
			System.out.print("How many players? ");
			try {
				int numPlayers = Integer.parseInt(sc.next());
				if (numPlayers > 1) {
					loadMultiplePlayers(numPlayers, sc);
				} else {
					player = loadPlayer(sc);
				}
			} catch (NumberFormatException e) {
				System.out.print("Invalid selection.  Try again (y/n) ");
				if (sc.next().equalsIgnoreCase("y")) {
					continue;
				}
				break;
			}
			if (players == null) {
				runGame(player, sc);
			}else {
				runGame(players, sc);
			}
			break;
		}
		sc.close();
	}

	// TODO - incorporate dealer
	private void runGame(Player player, Scanner sc) {
		Deck deck = new Deck();
		Dealer dealer = new Dealer();
		BlackjackHand dealerHand = (BlackjackHand) dealer.getHand();
		BlackjackHand playerHand = (BlackjackHand) player.getHand();
		playerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
		playerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());

		player.takeTurn(sc, deck);
	}
	
	// TODO - incorporate multiple players and dealer
	private void runGame(List<Player> players, Scanner sc) {
		Deck deck = new Deck();
		Dealer dealer = new Dealer();
	}

	private Player loadPlayer(Scanner sc) {
		Player player = null;
		System.out.print("Enter player's name or (D)efault: ");
		String in = sc.next();
		if (in.equalsIgnoreCase("d")) {
			player = new Player();
		} else {
			player = new Player(sc.next());
		}
		return player;
	}

	private List<Player> loadMultiplePlayers(int numPlayers, Scanner sc) {
		players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Player " + (i + 1) + " name or (D)efault: ");
			String in = sc.next();
			if (in.equalsIgnoreCase("d")) {
				players.add(new Player());
			} else {
				players.add(new Player(sc.next()));
			}
		}
		return players;
	}
}
