package com.skilldistillery.cardgames.common;

public abstract class Person {
	private String name;
	private Hand hand;

	public Person(String name) {
		this.name = name;
	}

	public Person(String name, Hand hand) {
		this.name = name;
		this.hand = hand;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

}
