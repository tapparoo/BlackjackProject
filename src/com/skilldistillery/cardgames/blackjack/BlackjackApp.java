package com.skilldistillery.cardgames.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.cardgames.common.Deck;

public class BlackjackApp {
	List<Player> players = null;

	public static void main(String[] args) {
		BlackjackApp app = new BlackjackApp();
		app.go();
	}

	void go() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("How many players? (1-5)");
			try {
				int numPlayers = Integer.parseInt(sc.next());
				if (numPlayers < 1 || numPlayers > 5) {
					System.out.println("Number of players must be between 1-5.\n");
					continue;
				}
				loadPlayers(numPlayers, sc);
			} catch (NumberFormatException e) {
				System.out.print("Invalid selection.  Try again (y/n) ");
				if (sc.next().equalsIgnoreCase("y")) {
					continue;
				}
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			}

			String option = runGame(sc);
			if (option.equalsIgnoreCase("y")) {
				continue;
			} else {
				break;
			}
		}
		sc.close();
	}

	private String runGame(Scanner sc) {
		Dealer dealer = new Dealer();
		Deck deck = new Deck();
		boolean[] playerBustOrBJ = new boolean[players.size()];
		boolean skipDealerTurn = true;

		while (true) {
			startNewHand(dealer, deck);
			if (dealer.hasBlackjack()) {
				System.out.println("Dealer has blackjack. :(");
			} else {
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);

					doPlayerTurn(p, deck, sc, dealer);

					if (p.getHand().getHandValue() > 21 || p.hasBlackjack()) {
						playerBustOrBJ[i] = true;
					} else {
						playerBustOrBJ[i] = false;
					}
				}
				// skip dealer's turn if all players bust and/or have blackjack
				for (boolean b : playerBustOrBJ) {
					if (b == false) {
						skipDealerTurn = false;
					}
				}
				if (!skipDealerTurn) {
					dealer.takeTurn(deck);
				}
				printDealerResults(dealer);

				// adjust cash
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);

					if (!isTie(p, dealer)) {
						if (isWinner(players.get(i), dealer)) {
							if (p.hasBlackjack()) {
								p.setCash(p.getCash() + (3 * p.getCurrentBet()) / 2);
							} else {
								p.setCash(p.getCash() + p.getCurrentBet());
							}
						} else {
							p.setCash(p.getCash() - p.getCurrentBet());
						}
					} else {
						if (p.hasBlackjack() && !dealer.hasBlackjack()) {
							p.setCash(p.getCash() + (p.getCurrentBet() * 3) / 2);
						}
					}
				}
			}
			for (Player player : players) {
				printPlayerResults(player, dealer, player.getCurrentBet());
			}

			System.out.print("\n(C)ontinue\n(N)ew Game\n(Q)uit\n>> ");

			switch (sc.next().toUpperCase()) {
			case "Q":
				return "Q";
			case "N":
				return "Y";
			default:
				continue;
			}
		}
	}

	private boolean isWinner(Player player, Dealer dealer) {
		if (player.getHand().getHandValue() <= 21 && (dealer.getHand().getHandValue() > 21
				|| player.getHand().getHandValue() > dealer.getHand().getHandValue())) {
			return true;
		}
		return false;
	}

	private boolean isTie(Player player, Dealer dealer) {
		if (player.getHand().getHandValue() <= 21
				&& player.getHand().getHandValue() == dealer.getHand().getHandValue()) {
			return true;
		}
		return false;
	}

	private void doPlayerTurn(Player player, Deck deck, Scanner sc, Dealer dealer) {
		double bet = player.getCurrentBet();

		while (true) {
			System.out.println("\n" + player.getName() + " has $" + player.getCash());
			System.out.print("Bet amount: ");
			try {
				bet = Double.parseDouble(sc.next());
				if (bet < 0) {
					System.out.println("Enter a positive number.");
					bet = 0;
					continue;
				} else if (bet > player.getCash()) {
					System.out.println("You don't have that much.");
					bet = 0;
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid bet amount.  Try again.");
				continue;
			}
			break;
		}

		player.setCurrentBet(bet);
		if (!dealer.hasBlackjack()) {
			if (!player.hasBlackjack()) {
				player.takeTurn(sc, deck, dealer.getUpCard());
			} else {
				System.out.println("\n" + player.getName() + " has Blackjack!");
			}
		}
	}

	public void startNewHand(Dealer dealer, Deck deck) {
		if (deck.checkDeckSize() < 20) {
			System.out.println("\nTime to shuffle...");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deck = new Deck();
		}
		for (Player player : players) {
			player.getHand().clearHand();
		}
		dealer.getHand().clearHand();

		// Every player gets 1 card, then the dealer, then again
		for (int i = 0; i < 2; i++) {
			for (Player player : players) {
				player.getHand().addCard(deck.dealCard());
			}
			dealer.getHand().addCard(deck.dealCard());
		}
	}

	private List<Player> loadPlayers(int numPlayers, Scanner sc) {
		players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Player " + (i + 1) + "'s name or (D)efault: ");
			String in = sc.next();
			if (in.equalsIgnoreCase("d")) {
				players.add(new Player("Player" + (i + 1)));
			} else {
				players.add(new Player(in));
			}
		}
		return players;
	}

	public void printDealerResults(Dealer dealer) {
		int dealerHandVal = dealer.getHand().getHandValue();
		String out = "\n" + separator() + "\n\tDealer had " + dealerHandVal + ". ";

		if (dealerHandVal > 21) {
			out += "Dealer busts!\n";
		} else if (dealerHandVal == 21 && dealer.getHand().getCards().size() == 2) {
			out += "\nBlackjack.  :(\n";
		}
		out += "\n" + separator();
		System.out.println(out);
	}

	public void printPlayerResults(Player player, Dealer dealer, double bet) {
		int playerHandVal = player.getHand().getHandValue();
		int dealerHandVal = dealer.getHand().getHandValue();
		String out = player.getName() + " has " + playerHandVal + ". ";

		if (player.hasBlackjack() && !dealer.hasBlackjack()) {
			out += player.getName() + " has Blackjack. and won $" + bet + ". New cash total: $" + player.getCash();
		} else if (playerHandVal > 21) {
			out += player.getName() + " busted and lost $" + bet + ". New cash total: $" + player.getCash();
		} else if (dealerHandVal > 21 || playerHandVal > dealerHandVal) {
			out += player.getName() + " wins $" + bet + ". New cash total: $" + player.getCash();
		} else if (dealerHandVal > playerHandVal) {
			out += player.getName() + " lost $" + bet + ". New cash total: $" + player.getCash();
		} else {
			out += "Tie. " + player.getName() + "'s current cash total: $" + player.getCash();
		}
		out += "\n" + separator();

		System.out.println(out);
	}
	
	public String separator() {
		return "------------------------------------------------------------";
	}
}
