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
		app.go();
	}

	// TODO - incorporate betting
	void go() {
		Scanner sc = new Scanner(System.in);

		while (true) {
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
			String option = "";
			if (players == null) {
				option = runGame(player, sc);
			} else {
				option = runGame(players, sc);
			}
			if (option.equalsIgnoreCase("y")) {
				continue;
			} else {
				break;
			}
		}
		sc.close();
	}

	private String runGame(Player player, Scanner sc) {
		Deck deck = new Deck();
		Dealer dealer = new Dealer();
		int bet = 0;
		boolean winner = false;
		String out = "";

		while (true) {
			while (true) {
				winner = false;
				System.out.println(player.getName() + " has $" + player.getCash());
				System.out.print("Bet amount (currently " + bet + "): ");
				if (!out.equalsIgnoreCase("A")) {
					try {
						bet = Integer.parseInt(sc.next());
						if (bet < 0) {
							System.out.println("Enter a positive number.");
							bet = 0;
							continue;
						}
					} catch (NumberFormatException e) {
						System.out.println("Invalid bet amount.  Try again? (y/n) ");
						if (sc.next().equalsIgnoreCase("Y")) {
							continue;
						} else {
							return "n";
						}
					}
				}
				break;
			}

			startNewHand(player, dealer, deck);

			if (dealer.hasBlackjack()) {
				if (!player.hasBlackjack()) {
					printResults(player, dealer);
					break;
				}
			} else if (player.hasBlackjack()) {
				winner = true;
				printResults(player, dealer);
				break;
			} else {
				player.takeTurn(sc, deck, dealer.getUpCard());
				if (player.getHand().getHandValue() > 21) {
					printResults(player, dealer);
				} else {
					dealer.takeTurn(deck);
					if (dealer.getHand().getHandValue() > 21
							|| player.getHand().getHandValue() > dealer.getHand().getHandValue()) {
						winner = true;
					}
					printResults(player, dealer);
				}
			}
			if (winner) {
				player.setCash(player.getCash() + bet);
			} else {
				player.setCash(player.getCash() - bet);
			}

			System.out.print("\n" + player.getName() + " has " + player.getCash()
					+ ".\n(A)gain with same bet\n(C)hange bet\n(N)ew Game\n(Q)uit\n>> ");
			out = sc.next().toUpperCase();
			switch (out) {
			case "Q":
				return "Q";
			case "N":
				return "Y";
			default:
				continue;
			}
		}
		return "N";
	}

	// TODO - incorporate multiple players and dealer
	private String runGame(List<Player> players, Scanner sc) {
		Deck deck = new Deck();
		Dealer dealer = new Dealer();

		return "";
	}

	public void startNewHand(Player player, Dealer dealer, Deck deck) {
		BlackjackHand playerHand = (BlackjackHand) player.getHand();
		BlackjackHand dealerHand = (BlackjackHand) dealer.getHand();
		playerHand.clearHand();
		dealerHand.clearHand();

		if (deck.checkDeckSize() < 15) {
			System.out.println("\nTime to shuffle...");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deck = new Deck();
		}

		playerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());

		playerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
	}

	private Player loadPlayer(Scanner sc) {
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

		out += "\n" + dealer.getName() + " had " + dealerHandVal + ".\n" + player.getName() + " had " + playerHandVal
				+ ".";

		if (playerHandVal > 21) {
			out += "\n**" + player.getName() + " busted.\n" + dealer.getName() + " wins. :( ";
		} else if (dealerHandVal > 21) {
			out += "\n**" + dealer.getName() + " busted. " + player.getName() + " wins!";
		} else if (playerHandVal > dealerHandVal) {
			out += "\n*** " + player.getName() + " wins!! ***";
		} else if (dealerHandVal > playerHandVal) {
			out += "\n**" + dealer.getName() + " wins. :(";
		} else {
			out += "\nThis round was a tie.";
		}
		System.out.println(out);
	}
}
