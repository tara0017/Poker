/**
 *  5-15-16
 * @author Afek
 */
import java.util.Scanner;
	 
public class TexasHoldEmGame {
	static TexasHoldEmPlayer a = new TexasHoldEmPlayer("Player 1");
	static TexasHoldEmPlayer b = new TexasHoldEmPlayer("Player 2");
	static Card[] deck = new Card[52];
	static Card[] shuffled;
	private static Card[] finalCommuniytCards = new Card[5];
    
	
	/**
	 * Sets up the game of Texas Hold'em poker by shuffling a deck of 52 cards, assigning the first 2 cards to player a's hand and
	 * the next 2 cards to player b's hand.  Next, it assigns the following five cards to the table (i.e. community cards).  It then 
	 * prints the cards played along with each player's updated probability of winning.  Finally it displays who won the hand, and with
	 * which card values 
	 */
	public static void gameSetUp(){
		Scanner scanner = new Scanner(System.in);
	    String key = "deal";
	    String word;
	    
		Card.createDeck(deck);
		Card.makeAcesHigh(deck);

		shuffled = Card.shuffleCards(deck);


		System.out.println("*****************************************************************************************************************************************************************");
		System.out.println("  |     | |--- |    |--- |---| |-| |-| |---    --|-- |---|    --|-- |--- \\ /   /\\   |---     |   | |---| |    |--\\  | |--- |-| |-|    |---| |---| | / |--- |---|");
		System.out.println("  |     | |--  |    |    |   | | |-| | |--       |   |   |      |   |--   |   /__\\  |___     |---| |   | |    |   |   |--- | |-| |    |___| |   | |/\\ |--  |___|");
		System.out.println("  |_|-|_| |___ |___ |___ |___| |     | |___      |   |___|      |   |___ / \\ /    \\  ___|    |   | |___| |___ |__/    |___ |     |    |     |___| |  \\|___ |  \\");
		System.out.print("*****************************************************************************************************************************************************************");
		
		
		//dealing 2 cards to each player
		a.hand[0] = shuffled[0];
		a.hand[1] = shuffled[1];
		b.hand[0] = shuffled[2];
		b.hand[1] = shuffled[3];
		//assigning the next five cards to the table as community cards
		Card[] communityCards = {shuffled[4], shuffled[5], shuffled[6], shuffled[7], shuffled[8]};
		finalCommuniytCards = communityCards;

//CREATING THE HANDS		
		//Create each player's hand 
		for(int i = 0; i < a.hand.length; i++){
			a.handWithCommunityCards[i] = a.hand[i];
		}
		for(int i = 0; i < b.hand.length; i++){
			b.handWithCommunityCards[i] = b.hand[i];
		}
		//Add the community cards to each player's hand
		for(int i = 0; i < 5; i++){
			a.handWithCommunityCards[i+2] = communityCards[i];
			b.handWithCommunityCards[i+2] = communityCards[i];
		}
		
		//print each player's hand
		String tabs = "\t\t\t\t\t\t\t\t\t\t\t\t";
		if(a.hand[0].cSuit.compareTo("Diamonds") == 0){
			tabs = "\t\t\t\t\t\t\t\t\t\t\t";		//adjusts spacing in case of Diamonds suit
		}
		System.out.println("\n" + a.name + "'s hand is: \t\t\t\t\t\t\t\t\t\t\t" + b.name + "'s hand is:");
		System.out.println(a.hand[0].cValue + "\t" + a.hand[0].cUniSuit + tabs + b.hand[0].cValue + "\t" + b.hand[0].cUniSuit);
		tabs = "\t\t\t\t\t\t\t\t\t\t\t\t";
		if(a.hand[1].cSuit.compareTo("Diamonds") == 0){
			tabs = "\t\t\t\t\t\t\t\t\t\t\t";		//adjusts spacing in case of Diamonds suit
		}
		System.out.println(a.hand[1].cValue + "\t" + a.hand[1].cUniSuit + tabs + b.hand[1].cValue + "\t" + b.hand[1].cUniSuit);
		
		//check for % chance of winning before flop
		System.out.println("\nPre-flop probability of winning is:");
		updateWinProbability(5);		
		
		//pause until player types "deal"
		System.out.print("Type \"deal\" to continue\t");
		do{
		    word = scanner.next();
		    word = word.replaceAll("[\\(\\),.;\\-'!]", "");
		    word = word.toLowerCase();
		  }while( (word.compareTo(key) != 0) && scanner.hasNext());

		//print flop		
		System.out.println("\t\t\t\t\t\t\t     Community cards:");
		for(int i = 0; i < 3; i++){
			System.out.println("\t\t\t\t\t\t\t     " + shuffled[i+4].cValue + "\t\t" + shuffled[i+4].cUniSuit);			
		}
	
		//check for % chance of winning post flop
		System.out.println("\nPost-flop probability of winning is:");
		updateWinProbability(2);
	
		//pause until player types "deal"
		System.out.print("Type \"deal\" to continue\t");
		do{
		    word = scanner.next();
		    word = word.replaceAll("[\\(\\),.;\\-'!]", "");
		    word = word.toLowerCase();
		}while( (word.compareTo(key) != 0) && scanner.hasNext());

		//print "the turn" card
		System.out.println("\t\t\t\t\t\t\t     " + shuffled[7].cValue + "\t\t" + shuffled[7].cUniSuit);
	
		//check for % chance of winning after turn
		System.out.println("\nWith one card left to play the probability of winning is:");
		updateWinProbability(1);	
	
		//pause until player types "deal"
		System.out.print("Type \"deal\" to continue\t");
		do{
		    word = scanner.next();
		    word = word.replaceAll("[\\(\\),.;\\-'!]", "");
		    word = word.toLowerCase();
		}while( (word.compareTo(key) != 0) && scanner.hasNext());		

		//print "the river" card
		System.out.println("\t\t\t\t\t\t\t     " + shuffled[8].cValue + "\t\t" + shuffled[8].cUniSuit);		
	
		//check for final % chance of winning
		System.out.println("\nAll the cards have been played. The probability of winning is:");
		updateWinProbability(0);

		//print finalized resultArray
		System.out.println();
		System.out.println("\tPlayer 1's result array: [ " + a.resultArray[0] + " ]" + "\t" + a.resultArray[1] + "\t" + a.resultArray[2] + "\t"+ a.resultArray[3] + "\t"+ a.resultArray[4] + "\t" + a.resultArray[5]);
		System.out.println("\tPlayer 2's result array: [ " + b.resultArray[0] + " ]" + "\t" + b.resultArray[1] + "\t" + b.resultArray[2] + "\t"+ b.resultArray[3] + "\t"+ b.resultArray[4] + "\t" + b.resultArray[5]);

		//print wining result
		System.out.println();
		handPrintout();
		

	}
	
	/**
	 * Method to compare the two players' resultArray and determine who wins the hand
	 * 
	 * @param y	Player y
	 * @param z	Player z
	 * 
	 * @return	the winning player (y, z, or null=draw)
	 */
	public static TexasHoldEmPlayer whoWonHand(TexasHoldEmPlayer y, TexasHoldEmPlayer z){
		//reset each player's resultArray in case it was previously tested
		for(int i = 0; i<y.resultArray.length; i++){
			y.resultArray[i] = 0;
			z.resultArray[i] = 0;
		}
		
		//evaluate each player's hand
		y.checkForAFlush(y.handWithCommunityCards);
		if(y.resultArray[0] == 0){		//no flush (or straight flush) found
			y.checkForAStraight(y.handWithCommunityCards);
			if(y.resultArray[0] == 0){	//no straight found
				y.checkFor(y.handWithCommunityCards);
			}
		}
		z.checkForAFlush(z.handWithCommunityCards);
		if(z.resultArray[0] == 0){		//no flush (or straight flush) found
			z.checkForAStraight(z.handWithCommunityCards);
			if(z.resultArray[0] == 0){	//no straight found
				z.checkFor(z.handWithCommunityCards);
			}
		}
		
		
		TexasHoldEmPlayer winner = null;
		int levelY = y.resultArray[0];
		int levelZ = z.resultArray[0];
		if(levelY > levelZ){
			winner = y;
		}else
			if(levelZ > levelY){
				winner = z;
			}else
				if(levelY == levelZ){	//both are at the same level (must compare individual cards)
					if(y.resultArray[1] != z.resultArray[1]){	//level is the same but lead card is not
						if(y.resultArray[1] > z.resultArray[1]){
							winner = y;
						}else{
							winner = z;
						}
					}else
						if(y.resultArray[2] != z.resultArray[2]){	//level is the same but 2nd card is not
							if(y.resultArray[2] > z.resultArray[2]){
								winner = y;
							}else{
								winner = z;
							}
						}else
							if(y.resultArray[3] != z.resultArray[3]){	//level is the same but 3rd card is not
								if(y.resultArray[3] > z.resultArray[3]){
									winner = y;
								}else{
									winner = z;
								}
							}else
								if(y.resultArray[4] != z.resultArray[4]){	//level is the same but 4th card is not
									if(y.resultArray[4] > z.resultArray[4]){
										winner = y;
									}else{
										winner = z;
									}
								}else
									if(y.resultArray[5] != z.resultArray[5]){	//level is the same but last card is not
										if(y.resultArray[5] > z.resultArray[5]){
											winner = y;
										}else{
											winner = z;
										}
									}else{		//level is the same and so are all of the cards
										//draw winner = null
										//winner is defaulted to null from declaration
									}
				}
		return winner;
	}

	
	/**
	 * Method used to calculate each player's probability of winning the hand
	 * 
	 * @param numCardsLeft	The number of cards still left to play
	 */
	public static void updateWinProbability(int numCardsLeft){
		int numOfAWins = 0;		//tracks the number of times A wins
		int numOfBWins = 0;		//tracks the number of times B wins
		int numTies = 0;		//tracks the number of ties
		int probA = 0;			//the % chance A wins
		int probB = 0;			//the % chance B wins
		int probTie = 0;		//the % chance hand is a draw
		if(numCardsLeft == 0){
			a.handWithCommunityCards[0] = shuffled[0];
			a.handWithCommunityCards[1] = shuffled[1];
			b.handWithCommunityCards[0] = shuffled[2];
			b.handWithCommunityCards[1] = shuffled[3];
			for(int i = 2; i < 7; i++){
				a.handWithCommunityCards[i] = shuffled[i + 2];
				b.handWithCommunityCards[i] = shuffled[i + 2];
			}
			TexasHoldEmPlayer w = whoWonHand(a, b);
			if(w == null){	//tie
				numTies++;
			}else{
				if(w == a){	//Player a wins
					numOfAWins++;
				}else{
					if(w == b){	//Player b wins
						numOfBWins++;
					}
				}
			}
		}
		if(numCardsLeft == 1){	//one card left to add to community cards
			for(int i = 8; i<52; i++){	
				a.handWithCommunityCards[0] = a.hand[0];	//used because handWithCommunityCards gets
				a.handWithCommunityCards[1] = a.hand[1];	//resorted based on suit and value
				b.handWithCommunityCards[0] = b.hand[0];
				b.handWithCommunityCards[1] = b.hand[1];
				for(int k = 0; k < finalCommuniytCards.length; k++){	//assign original cards back to hand
					a.handWithCommunityCards[k+2] = finalCommuniytCards[k];	
					b.handWithCommunityCards[k+2] = finalCommuniytCards[k];
				}									
				a.handWithCommunityCards[6] = shuffled[i];	//cycle through each of the remaining cards in deck
				b.handWithCommunityCards[6] = shuffled[i];	//and assign them to each player's handWithCommunityCards
				TexasHoldEmPlayer w = whoWonHand(a, b);
				if(w == null){	//tie
					numTies++;
				}else{
					if(w == a){	//Player a wins
						numOfAWins++;
					}else{
						if(w == b){	//Player b wins
							numOfBWins++;
						}
					}
				}				
			}
		}
		if(numCardsLeft == 2){	//Two cards left to add to community cards
			for(int i = 7; i < 51; i++){
				for(int j = i+1; j<52; j++){
					a.handWithCommunityCards[0] = a.hand[0];	//used because handWithCommunityCards gets
					a.handWithCommunityCards[1] = a.hand[1];	//resorted based on suit and value
					b.handWithCommunityCards[0] = b.hand[0];
					b.handWithCommunityCards[1] = b.hand[1];
					for(int k = 0; k < finalCommuniytCards.length; k++){	//assign original cards back to hand
						a.handWithCommunityCards[k+2] = finalCommuniytCards[k];	
						b.handWithCommunityCards[k+2] = finalCommuniytCards[k];
					}									
					a.handWithCommunityCards[5] = shuffled[i];	//cycle through each of the remaining cards in deck
					a.handWithCommunityCards[6] = shuffled[j];	//and assign them to each player's last 2 
					b.handWithCommunityCards[5] = shuffled[i];	//handWithCommunityCards spots
					b.handWithCommunityCards[6] = shuffled[j];
					TexasHoldEmPlayer w = whoWonHand(a, b);
					if(w == null){	//tie
						numTies++;
					}else{
						if(w == a){	//Player a wins
							numOfAWins++;
						}else{
							if(w == b){	//Player b wins
								numOfBWins++;
							}
						}
					}				
				}
			}
		}
		if(numCardsLeft == 5){	//Five cards left to add to community cards
			for(int e = 4; e < 48; e++){
				for(int f = e + 1; f < 49; f++){
					for(int g = f + 1; g < 50; g++){
						for(int h = g + 1; h < 51; h++){
							for(int i = h + 1; i < 52; i++){
								a.handWithCommunityCards[0] = a.hand[0];	//used because handWithCommunityCards gets
								a.handWithCommunityCards[1] = a.hand[1];	//resorted based on suit and value
								b.handWithCommunityCards[0] = b.hand[0];
								b.handWithCommunityCards[1] = b.hand[1];

								//cycle through each of the remaining cards in deck and assign them as the community cards
								a.handWithCommunityCards[2] = shuffled[e];	
								a.handWithCommunityCards[3] = shuffled[f];	
								a.handWithCommunityCards[4] = shuffled[g];	
								a.handWithCommunityCards[5] = shuffled[h];	 
								a.handWithCommunityCards[6] = shuffled[i];	
								
								b.handWithCommunityCards[2] = shuffled[e];	
								b.handWithCommunityCards[3] = shuffled[f];	
								b.handWithCommunityCards[4] = shuffled[g];	
								b.handWithCommunityCards[5] = shuffled[h];	 
								b.handWithCommunityCards[6] = shuffled[i];	
								
								TexasHoldEmPlayer w = whoWonHand(a, b);
								if(w == null){	//tie
									numTies++;
								}else{
									if(w == a){	//Player a wins
										numOfAWins++;
									}else{
										if(w == b){	//Player b wins
											numOfBWins++;
										}
									}
								}												
							}	
						}	
					}	
				}
			}
		}
		probA = Math.round((float) (100 * numOfAWins)/(numOfAWins + numOfBWins + numTies));	//calculate probabilities
		probB = Math.round((float) (100 * numOfBWins)/(numOfAWins + numOfBWins + numTies));
		probTie = Math.round((float) (100 * numTies)/(numOfAWins + numOfBWins + numTies));
		
		String tabsA = "\t\t";		//adjustment for spacing
		if(numOfAWins >= 1000000){
			tabsA = "\t";
		}
		System.out.print("(" + numOfAWins + ")" + " The odds Player 1 wins is: " + probA + "%" + tabsA);
		String tabsB = "\t\t\t";	//adjustment for spacing
		if(numTies > 171230){		//10% of (48 choose 5)
			tabsB = "\t\t";
		}
		System.out.print("(" + numTies + ")" + " The odds hand is a draw is: " + probTie + "%" + tabsB);
		System.out.println("(" + numOfBWins + ")" + " The odds Player 2 wins is: " + probB + "%");


	}

	
	/**
	 * Prints out the result (i.e. high-card, pair, 2 pair, etc.) of the hand after all the cards have been played
	 * 
	 */
	public static void handPrintout(){
		
		TexasHoldEmPlayer winningPlayer = whoWonHand(a,b);
		String AsHand = null;
		String BsHand = null;
		
		String convertedFaceA1;			//converts numerical value to a string for face cards
		switch(a.resultArray[1]){
		case 11:
			convertedFaceA1 = "Jack";
			break;
		case 12:
			convertedFaceA1 = "Queen";
			break;
		case 13:
			convertedFaceA1 = "King";
			break;
		case 14:
			convertedFaceA1 = "Ace";
			break;
		default:
			convertedFaceA1 = String.valueOf(a.resultArray[1]);
			break;
		}
		String convertedFaceA3;			//converts numerical value to a string for face cards
		switch(a.resultArray[3]){
		case 11:
			convertedFaceA3 = "Jack";
			break;
		case 12:
			convertedFaceA3 = "Queen";
			break;
		case 13:
			convertedFaceA3 = "King";
			break;
		case 14:
			convertedFaceA3 = "Ace";
			break;
		default:
			convertedFaceA3 = String.valueOf(a.resultArray[3]);
			break;
		}
		String convertedFaceA4;			//converts numerical value to a string for face cards
		switch(a.resultArray[4]){
		case 11:
			convertedFaceA4 = "Jack";
			break;
		case 12:
			convertedFaceA4 = "Queen";
			break;
		case 13:
			convertedFaceA4 = "King";
			break;
		case 14:
			convertedFaceA4 = "Ace";
			break;
		default:
			convertedFaceA4 = String.valueOf(a.resultArray[4]);
			break;
		}

		String convertedFaceB1;			//converts numerical value to a string for face cards
		switch(b.resultArray[1]){
		case 11:
			convertedFaceB1 = "Jack";
			break;
		case 12:
			convertedFaceB1 = "Queen";
			break;
		case 13:
			convertedFaceB1 = "King";
			break;
		case 14:
			convertedFaceB1 = "Ace";
			break;
		default:
			convertedFaceB1 = String.valueOf(b.resultArray[1]);
			break;
		}
		String convertedFaceB3;			//converts numerical value to a string for face cards
		switch(b.resultArray[3]){
		case 11:
			convertedFaceB3 = "Jack";
			break;
		case 12:
			convertedFaceB3 = "Queen";
			break;
		case 13:
			convertedFaceB3 = "King";
			break;
		case 14:
			convertedFaceB3 = "Ace";
			break;
		default:
			convertedFaceB3 = String.valueOf(b.resultArray[3]);
			break;
		}
		String convertedFaceB4;			//converts numerical value to a string for face cards
		switch(b.resultArray[4]){
		case 11:
			convertedFaceB4 = "Jack";
			break;
		case 12:
			convertedFaceB4 = "Queen";
			break;
		case 13:
			convertedFaceB4 = "King";
			break;
		case 14:
			convertedFaceB4 = "Ace";
			break;
		default:
			convertedFaceB4 = String.valueOf(b.resultArray[4]);
			break;
		}


		switch(a.resultArray[0]){
		case 1:
			AsHand = convertedFaceA1 + "-high card.";
			break;
		case 2:
			AsHand =  "a pair of " + convertedFaceA1 + "s.";
			break;
		case 3:
			AsHand =  "two pairs, " + convertedFaceA1 + "s and " + convertedFaceA3 + "s.";
			break;
		case 4:
			AsHand =  "three " + convertedFaceA1 + "s.";
			break;
		case 5:
			AsHand =  "a " + convertedFaceA1 + "-high straight.";
			break;
		case 6:
			AsHand =  "a flush.";
			break;
		case 7:
			AsHand =  "a full house, " + convertedFaceA1 + "s over " + convertedFaceA4 + "s.";
			break;
		case 8:
			AsHand =  "four " + convertedFaceA1 + "s.";
			break;
		case 9:
			AsHand =  "a " + convertedFaceA1 + "-high straight flush.";
			break;
		}
		
		
		switch(b.resultArray[0]){
		case 1:
			BsHand = convertedFaceB1 + "-high card.";
			break;
		case 2:
			BsHand =  "a pair of " + convertedFaceB1 + "s.";
			break;
		case 3:
			BsHand =  "two pairs, " + convertedFaceB1 + "s and " + convertedFaceB3 + "s.";
			break;
		case 4:
			BsHand =  "three " + convertedFaceB1 + "s.";
			break;
		case 5:
			BsHand =  "a " + convertedFaceB1 + "-high straight.";
			break;
		case 6:
			BsHand =  "a flush.";
			break;
		case 7:
			BsHand =  "a full house, " + convertedFaceB1 + "s over " + convertedFaceB4 + "s.";
			break;
		case 8:
			BsHand =  "four " + convertedFaceB1 + "s.";
			break;
		case 9:
			BsHand =  "a " + convertedFaceB1 + "-high straight flush.";
			break;
		}
		
				
		if(winningPlayer == null){
			System.out.println("The hand was a draw.");
		}
		if(winningPlayer == a){
			System.out.println(a.name + " wins the hand with " + AsHand + " " + b.name + " only had " + BsHand);
			System.out.println("\uFF04\uFF04\uFF04\uFF04\uFF04\uFF04\uFF04");
		}
		if(winningPlayer == b){
			System.out.println(b.name + " wins the hand with " + BsHand + " " + a.name + " only had " + AsHand);
			System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\uFF04\uFF04\uFF04\uFF04\uFF04\uFF04\uFF04");
		}		
		
	}
}