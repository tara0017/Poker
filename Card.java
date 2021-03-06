/**
 * General class for card games
 * 5-15-16
 * @author Afek
 * 
 */

public class Card {	
	int cFace;		// A=1 through K=13
	int cValue;			
	String cSuit;
	String cUniSuit;
	static String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
	static String[] unicodeSuits = {"\u2663", "\u2666", "\u2764", "\u2660"};
	static Card[] player1;
	static Card[] player2;
	static Card[] player3;
	static Card[] player4;
	

	/**
	 * Card constructor method
	 * 
	 * @param cardFace	The card's face (1= ace through 13 = king)
	 * @param cardValue	The card's value (2= two through 13 = king, with ace being able to switch between 1 and 14)
	 * @param cardSuit	The card's suit	(Clubs, Diamonds, Hearts, or Spades)
	 * @param uniSuit	The card suit's symbol followed by the suit as a string
	 */
	public Card(int cardFace, int cardValue, String cardSuit, String uniSuit){
		cFace = cardFace;
		cValue = cardValue;
		cSuit = cardSuit; //+ uniSuit;
		cUniSuit = uniSuit +cardSuit;
	}
		
	
	/**
	 * Creates a deck consisting of 52 cards (no jokers).  Aces are automatically assigned a value of 1 (low); however that can be changed with makeAcesHigh() method
	 * 
	 * @param d	The array of cards which will form the deck
	 */
	public static void createDeck(Card[] d){
		//create a deck 
		for(int i = 0; i < 4; i++){
			for(int j=0; j<13; j++){
				d[13*i+j] = new Card(j+1, j+1, Card.suits[i], Card.unicodeSuits[i]);
			}
		}
	}

	
	/**
	 * Randomly shuffles an array of cards
	 * 
	 * @param d	The array to be shuffled
	 * @return	The shuffled/randomized array of cards	
	 */
	public static Card[] shuffleCards(Card[] d){
		Card[] shuffled = new Card[52];			//shuffled array which will be returned
		double[] random = new double[52];	//in between array to randomize cards
		
		//assign random values to "in between" array
		for(int i = 0; i<random.length; i++){
			random[i] = Math.random();
		}
		
		//check to see which index in random array is smallest
		int index = 0;						//assume smallest value occurs at index 0
		int j = 0;							//variable to track index of shuffled array
		
		for(int k=0; k<d.length; k++){		//cycle through to assign randomized cards into shuffled array
						
			for(int i = 0; i<random.length; i++){	//check if the other values in random array are smaller
				if(random[i] < random[index]){
					index = i;
				}
			}
			shuffled[j] = d[index];
			random[index] = 2.0;		//assign a number larger than 1 so it is now the largest value
			j++;			
		}	
		return shuffled;
	}

	
	/**
	 * Prints an array of cards with card number (starting at 0), face, value and suit
	 * 
	 * @param d
	 */
	public static void printDeck(Card[] d){
		
		for(int i =0; i<d.length; i++){
			if(d[i] == null){
			}else{
				System.out.println("Card #" + (i) + "	Face: " + d[i].cFace + "	Value : " + d[i].cValue + "	Suit: " + d[i].cSuit);										
			}
		}
		
	}
	
	
	/**
	 * Distributes ALL cards from an array to a specific number of players (able to accommodate 1, 2, or 4 players).  This does not 
	 * allow for only dealing a certain number of cards (may need to add this for a different game)
	 * 
	 * @param d	The array of cards to be dealt to the players
	 * @param numberOfPlayers	The number of players to which we will deal the cards (allows for 1, 2, or 4)
	 */
	public static void dealDeck(Card[] d, int numberOfPlayers){		//method for dealing cards out up to 4 players
		//check to see if 52 % numberOfPlayers == 0
		//this method can accommodate 1,2, or 4 players

		int numberOfCardsPerPlayer = d.length/numberOfPlayers;

		player1 = new Card[numberOfCardsPerPlayer];
		player2 = new Card[numberOfCardsPerPlayer];
		player3 = new Card[numberOfCardsPerPlayer];
		player4 = new Card[numberOfCardsPerPlayer];
		
		
		//Dealing out cards from deck to each player
		System.arraycopy(d, 0, player1, 0, numberOfCardsPerPlayer);		
		if(numberOfPlayers >= 2)	
			System.arraycopy(d, 0 + numberOfCardsPerPlayer, player2, 0, numberOfCardsPerPlayer);		
		if(numberOfPlayers >= 3)	
			System.arraycopy(d, 0 + 2*numberOfCardsPerPlayer, player3, 0, numberOfCardsPerPlayer);		
		if(numberOfPlayers >= 4)
			System.arraycopy(d, 0 + 3*numberOfCardsPerPlayer, player4, 0, numberOfCardsPerPlayer);		
			
	}

	
	/**
	 * Changes value of aces from 1 (low) to 14 (high)
	 *  
	 * @param d	The array of cards where aces will be made high
	 */
	public static void makeAcesHigh(Card[] d){
		for(int i = 0; i < d.length; i++){
			if(d[i].cValue == 1){
				d[i].cValue = 14;
			}
		}	
	}
	
	
	/**
	 * Sorts an array of cards based on the card's value From high to low
	 * 
	 * @param d	The array of cards to be sorted
	 * @return	The sorted array of cards
	 */
	public static Card[] sortByValue(Card[] d){
		boolean swapHappenedFlag = false;
		Card temp;
	
		do{
			swapHappenedFlag = false;
			for(int i = 0; i < (d.length-1); i++){
				if(d[i].cValue < d[i+1].cValue){
					temp = d[i+1];
					d[i+1] = d[i];
					d[i] = temp;
					swapHappenedFlag = true;
				}
			}
		}while(swapHappenedFlag);
		return d;
	}
	
	
	/**
	 * Sorts an array of cards based on the card's suits in the following order: Clubs, Diamonds, Hearts, Spades
	 * 
	 * @param d	The array of cards to be sorted
	 * @return	The sorted array of cards
	 */
	public static Card[] sortBySuit(Card[] d){
		Card[] sortedCards = new Card[d.length];
		int sortedCardIndex=0;
		int nextCardToAssignIndex = 0;
		int numOfClubs = 0;
		int numOfDiamonds = 0;
		int numOfHearts = 0;
		int numOfSpades = 0;
		for(int i = 0; i < d.length; i++){		//determine distribution of suits
			if(d[i].cSuit == "Clubs"){
				numOfClubs++;
			}else
				if(d[i].cSuit == "Diamonds"){
					numOfDiamonds++;
				}else
					if(d[i].cSuit == "Hearts"){
						numOfHearts++;
					}else
						if(d[i].cSuit == "Spades"){
							numOfSpades++;
						}
		}
		
		int currentClubValue =15;			//start with a card value greater than max value
		for(int i=0; i<numOfClubs; i++){		//repeat this for as many clubs as are in the hand
			currentClubValue =15;			//reset club value to a value greater than max 
			for(int j = 0; j<d.length; j++){	//cycle through all cards in card array d to compare cValue
				if(d[j] != null && d[j].cSuit == "Clubs" && d[j].cValue < currentClubValue){
					nextCardToAssignIndex = j;
					currentClubValue = d[j].cValue;
				}
			}
			sortedCards[sortedCardIndex] = d[nextCardToAssignIndex];
			d[nextCardToAssignIndex] = null;			//remove that card from original hand
			sortedCardIndex++;
		}
		int currentDiamondValue =15;		//start with a card value greater than max value
 		for(int i=0; i<numOfDiamonds; i++){		//repeat this for as many diamonds as are in the hand
			currentDiamondValue =15;		//reset diamond value to a value greater than max 
			for(int j = 0; j<d.length; j++){	//cycle through all cards in card array d to compare cValue
				if(d[j] != null && d[j].cSuit == "Diamonds" && d[j].cValue < currentDiamondValue){
					nextCardToAssignIndex = j;
					currentDiamondValue = d[j].cValue;
				}
			}
			sortedCards[sortedCardIndex] = d[nextCardToAssignIndex];
			d[nextCardToAssignIndex] = null;			//remove that card from original hand
			sortedCardIndex++;
		}
 		
		int currentHeartsValue =15;			//start with a card value greater than max value
		for(int i=0; i<numOfHearts; i++){		//repeat this for as many hearts as are in the hand
			currentHeartsValue =15;			//reset hearts value to a value greater than max 
			for(int j = 0; j<d.length; j++){	//cycle through all cards in card array d to compare cValue
				if(d[j] != null && d[j].cSuit == "Hearts" && d[j].cValue < currentHeartsValue){
					nextCardToAssignIndex = j;
					currentHeartsValue = d[j].cValue;
				}
			}
			sortedCards[sortedCardIndex] = d[nextCardToAssignIndex];
			d[nextCardToAssignIndex] = null;			//remove that card from original hand
			sortedCardIndex++;
		}
		int currentSpadesValue =15;			//start with a card value greater than max value
		for(int i=0; i<numOfSpades; i++){		//repeat this for as many hearts as are in the hand
			currentSpadesValue =15;			//reset spades value to a value greater than max 
			for(int j = 0; j<d.length; j++){	//cycle through all cards in card array d to compare cValue
				if(d[j] != null && d[j].cSuit == "Spades" && d[j].cValue < currentSpadesValue){
					nextCardToAssignIndex = j;
					currentSpadesValue = d[j].cValue;
				}
			}
			sortedCards[sortedCardIndex] = d[nextCardToAssignIndex];
			d[nextCardToAssignIndex] = null;			//remove that card from original hand
			sortedCardIndex++;
		}
		return sortedCards;
	}

	
	/**
	 * Prints an array of cards with the card number (starting at 0), card value, and card suit
	 * 
	 * @param d	The array of cards to be printed
	 */
	public static void printValueAndSuit(Card[] d){
		
		for(int i =0; i<d.length; i++){
			if(d[i] == null){
			}else{
				System.out.println("Card #	" + (i) + "	" + d[i].cValue + "	" + d[i].cUniSuit);							
			}
		}	
	}
	
}