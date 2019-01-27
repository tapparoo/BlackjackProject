package com.skilldistillery.cardgames.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cardgames.common.Deck;

public class BlackjackApp {
	List<Player> players = null;
	Player player = null;

	public static void main(String[] args) {
		BlackjackApp app = new BlackjackApp();
		app.run();
	}

	// TODO - incorporate betting and option to continue after each hand
	void run() {
		Scanner sc = new Scanner(System.in);

		String option = "";

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
			} else {
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
		if (dealerHand.getHandValue() == 21 || playerHand.getHandValue() == 21) {
			printResults(player, dealer);
		}
		
		player.takeTurn(sc, deck, dealer.getUpCard());

		if (player.getHand().getHandValue() > 21) {
			printResults(player, dealer);
		} else {
			dealer.takeTurn(deck);
			printResults(player, dealer);
		}
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
			player = new Player(in);
		}
		return player;
	}

	private List<Player> loadMultiplePlayers(int numPlayers, Scanner sc) {
		players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Player " + (i + 1) + " name or (D)efault: ");
			String in = sc.next();
			if (in.equalsIgnoreCase("d")) {
				players.add(new Player("Player" + (i + 1)));
			} else {
				players.add(new Player(sc.next()));
			}
		}
		return players;
	}

	public void printResults(Player player, Dealer dealer) {
		String out = "";
		int playerHandVal = player.getHand().getHandValue();
		int dealerHandVal = dealer.getHand().getHandValue();

		out += "\n\n" + dealer.getName() + " had " + dealerHandVal + ".\n" + player.getName() + " had " + playerHandVal
				+ ".";

		if (playerHandVal > 21) {
			out += "\n" + player.getName() + " busted with " + playerHandVal + ". " + dealer.getName() + " wins. :( ";
		} else if (dealerHandVal > 21) {
			out += "\n" + dealer.getName() + " busted with " + dealerHandVal + ". " + player.getName() + " wins!";
		} else if (playerHandVal > dealerHandVal) {
			out += "\n*** " + player.getName() + " wins!! ***";
		} else if (dealerHandVal > playerHandVal) {
			out += "\n" + dealer.getName() + " wins. :(";
		} else {
			out += "\nThis round was a tie.";
		}
		System.out.println(out);
	}
}
