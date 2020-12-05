  
/**
 * 5-15-16
 * 
 * @author Afek
 *
 */

public class TexasHoldEmPlayer {

	Card[] hand = new Card[2];
	Card[] handWithCommunityCards = new Card[7];
	int numberOfC = 0;
	int numberOfD = 0;
	int numberOfH = 0;
	int numberOfS = 0;
	String name;
	int[] resultArray = new int[6];		//first entry indicates level (high card = 1, pair = 2, two pair = 3, etc) followed by the 5 cards

	/**
	 * Constructor method
	 * 
	 * @param n	The player's name
	 */
	public TexasHoldEmPlayer(String n){
		this.name = n;
	}

	/**
	 * Check to see if player has a flush.  If it does, then it also checks for a possible straight flush.
	 * 
	 * @param d The player's hand to check
	 * 
	 * @return	The resultArray where resultArray[0] = 6 in the case of a flush, resultArray[0] = 9 in the case of a straight flush, otherwise resultArray[0] = 0 
	 */
	public int[] checkForAFlush(Card[] d){
		determineSuitDistribution(d);

		//first entry in resultArray should be set to 6 if there is a flush, followed by the 5 card values in descending order
		String suit = null;
		if(numberOfC >=5){
			suit = "Clubs";
		}else 
			if(numberOfD >=5){
				suit = "Diamonds";
			}else
				if(numberOfH >=5){
					suit = "Hearts";
				}else
					if(numberOfS >=5){
						suit = "Spades";
					}
		//if there is a flush assign the 5 highest values to the resultArray
		if(suit != null){
			resultArray[0] = 6;
			int lastCardValueAdded = 15; 	//used to track last value of card added to array so we do not just add max value 5 times (15 is a generic value greater than ace)
			for(int i = 1; i < 6; i++){
				int nextHighestCardInSuit = 0; 
				for(int j = 0; j < d.length; j++){
					if((d[j].cSuit.compareTo(suit) == 0) && (d[j].cValue > nextHighestCardInSuit) && (d[j].cValue < lastCardValueAdded)){
						nextHighestCardInSuit = d[j].cValue;
					}
				}
				lastCardValueAdded = nextHighestCardInSuit;
				resultArray[i] = nextHighestCardInSuit;
			}
		}

		////////CHECK FOR A POSSIBLE STRAIGHT FLUSH////////
		if(suit != null){			
			//make a hand made up of only the suited cards
			int lengthOfSuitedHand = 0;  //number of cards in the suit
			if(suit.compareTo("Clubs") == 0){
				lengthOfSuitedHand = numberOfC;
			}else 
				if(suit.compareTo("Diamonds") == 0){
					lengthOfSuitedHand = numberOfD;
				}else 
					if(suit.compareTo("Hearts") == 0){
						lengthOfSuitedHand = numberOfH;
					}else 
						if(suit.compareTo("Spades") == 0){
							lengthOfSuitedHand = numberOfS;
						}

			Card[] suitedHand = new Card[lengthOfSuitedHand];
			int suitHandIndex = 0;
			for(int i = 0; i < d.length; i++){
				if(d[i].cSuit.compareTo(suit) == 0){
					suitedHand[suitHandIndex] = d[i];
					suitHandIndex++;
				}
			}

			//check if suited hand has a straight of length 5 or more
			if(this.checkForAStraight(suitedHand)[0] == 5){
				resultArray = this.checkForAStraight(suitedHand);
				resultArray[0] = 9;
			}

		}
		return resultArray;
	}

	
	/**
	 * Check to see if player has a straight.
	 * 
	 * @param d The player's hand to check.
	 * 
	 * @return The resultArray where resultArray[0] = 5 in the case of a straight, otherwise resultArray[0] = 0 
	 */
	public int[] checkForAStraight(Card[] d){
		//first entry in resultArray should be set to 5 if there is a straight followed by the 5 card values in descending order
		int straightHighestValue = 0;
		int dupIndex = 1;
		int lengthOfStraight = 1;
		int longestStraight = 1;
		int indexWhereStraightStarts = 0;
		
		//create a duplicate Card array w/o repeated values
		Card[] dSorted = Card.sortByValue(d);			//same as d only sorted by values
		Card[] duplicate = new Card[dSorted.length];	//same as dSorted but w/o repeated values
		duplicate[0] = dSorted[0];		
		for(int i = 1; i < dSorted.length; i++){
			if(dSorted[i].cValue != dSorted[i - 1].cValue){
				duplicate[dupIndex] = dSorted[i];
				dupIndex++;
			}
		}

		//check if there are 5 consecutive cValue numbers in duplicate card array
		if(7-dupIndex <= 2){ //are there at least 5 cards left in the array
			for(int i = 0; i < dupIndex-1; i++){
				if(duplicate[i].cValue == duplicate[i+1].cValue + 1){	//we have consecutive numbers
					lengthOfStraight++;
					if(lengthOfStraight > longestStraight){
						longestStraight = lengthOfStraight;
					}
				}else{													//break in consecutive numbers
					lengthOfStraight = 1;
					if(i <= 2){
						indexWhereStraightStarts = (i + 1);						
					}
				}

			}
		}

		if(longestStraight >= 5){ 		//Are there at least 5 consecutive number
			straightHighestValue = duplicate[indexWhereStraightStarts].cValue;
			resultArray[0] = 5;
			for(int i = 0; i<5; i++){
				resultArray[i+1] = straightHighestValue - i;				
			}
		}else 							//check for a possible straight where ace is low (i.e. ace value = 1)
			if(longestStraight == 4 && duplicate[0].cValue == 14){ 		//straight of length 4 and the ace are present
				if(duplicate[indexWhereStraightStarts].cValue == 5){
					resultArray[0] = 5;				//straight is at level 5
					for(int i = 0; i<5; i++){		//assign the values 5, 4, 3, 2, 1 into array following level 5 entry 
						resultArray[i+1] = 5 - i;				
					}
				}
			}
		return resultArray;
	}


	/**
	 * Determines how many Clubs, Diamonds, Hearts and Spades a hand has.  This is used to determine if a flush is present.
	 * 
	 * @param d	The player's hand to check.
	 */
	public void determineSuitDistribution(Card[] d){
		numberOfC = 0;
		numberOfD = 0;
		numberOfH = 0;
		numberOfS = 0;
		for(int i = 0; i < d.length; i++){		//determine distribution of suits
			if(d[i] != null){		//In case hand is not yet complete
				if(d[i].cSuit == "Clubs"){
					numberOfC++;
				}else
					if(d[i].cSuit == "Diamonds"){
						numberOfD++;
					}else
						if(d[i].cSuit == "Hearts"){
							numberOfH++;
						}else
							if(d[i].cSuit == "Spades"){
								numberOfS++;
							}	
			}
		}

	}

	
	/**
	 * Method to see if player has a high card, pair, 2 pair, 3-of-a-kind, full house, or 4-of-a-kind. resultArray[0] = 1 in case of a high card, resultArray[0] = 2 
	 * in case of a pair, 3 in case of 2-pair, 4 in case of 3-of-a-kind, 7 in case of a full house, and 8 in case of 4-of-a-kind.  Uses a histogram array representing 
	 * occurrence of each card value and bases result on the number of modes and value of the mode.
	 * 
	 * @param d	The player's hand to check.
	 * 
	 * @return	The resultArray with the correct resultArray[0] value, followed by the five card values making up the hand 
	 */
	public int[] checkFor(Card[] d){

		int[] histogram = new int[13];
		for (int p = 0; p<7; p++){ //counts the instances of each card
			histogram[(d[p].cValue-2)]++;
		}
		int mode = 0;
		int indexOfTheMode= 0;
		for(int i = 0; i< histogram.length; i++){ // finds the maximum value in the histogram array
			if(histogram[i]>mode){
				mode = histogram[i];
				indexOfTheMode = i;
			}
		}
		int numberOfModes= 0;
		for(int i = 0; i<histogram.length; i++){
			if(histogram[i] == mode){
				numberOfModes++;
			}
		}

		if(numberOfModes == 7){ // high card
			resultArray[0] = 1;
			Card[] sorted = Card.sortByValue(d);
			for(int i = 0; i<5; i++){
				resultArray[i+1] = sorted[i].cValue;
			}
		}else
			if(numberOfModes == 3){ // three pairs
				resultArray[0] = 3;
				int j = 1;
				for(int n = histogram.length-1; n >= 0; n--){
					if(histogram[n] == mode){
						resultArray[j] = n + 2;
						j++;
						resultArray[j] = n + 2;
						j++;
						if(j == 5){
							break;
						}
					}
				}
				for(int t = histogram.length-1; t>=0; t--){
					if(histogram[t] == 1){ 
						resultArray[5] = t + 2;
						break;
					}
				}

			}
			else{
				if(numberOfModes == 2){ // two trips or two pairs
					if(mode == 3){ // two trips, so full house
						resultArray[0] = 7;
						int j = 1;
						for(int i = histogram.length-1; i>=0; i--){
							if(histogram[i] == mode){
								resultArray[j] = i + 2;
								j++;
								resultArray[j] = i + 2;
								j++;
								if(j == 6){
									break;
								}
								resultArray[j] = i + 2;
								j++;
							}
						}
					}else{
						if(mode == 2){ // two pairs
							int j = 1;
							resultArray[0] = 3;
							for(int i = histogram.length-1; i>=0; i--){
								if(histogram[i] == mode){
									resultArray[j] = i + 2;
									j++;
									resultArray[j] = i + 2;
									j++;
								}
							}
							for(int i = histogram.length-1; i>=0; i--){
								if(histogram[i] == 1){ 
									resultArray[5] = i + 2;
									break;
								}
							}
						}
					}
				}else{
					if(numberOfModes == 1){ // pair, three of a kind, or four of a kind, or full house
						if(mode == 4){ // four of a kind
							resultArray[0] = 8;
							int j = 1;
							for(int i = histogram.length-1; i>=0; i--){
								if(histogram[i] == mode){
									resultArray[j] = i + 2;
									j++;
									resultArray[j] = i + 2;
									j++;
									resultArray[j] = i + 2;
									j++;
									resultArray[j] = i + 2;
									j++;
									break;
								}    
								for(int s = histogram.length-1; s >= 0; s--){
									if(histogram[s] > 0 && histogram[s] < 4){ 
										resultArray[5] = s + 2;
										break;
									}
								}
							}
						}else
							if(mode == 3){ // three of a kind and full house
								int j = 1;
								for(int i = histogram.length-1; i>=0; i--){
									if(histogram[i] == mode){
										resultArray[j] = i + 2;
										j++;
										resultArray[j] = i + 2;
										j++;
										resultArray[j] = i + 2;
										j++; 
										break;
									}
								}
								for(int i = histogram.length-1; i>=0; i--){
									if(histogram[i] == 2){  // full house
										resultArray[4] = i + 2;
										resultArray[5] = i + 2;
										resultArray[0] = 7;
										break;
									}
									else{ // three of a kind
										int k = 4;
										for(int m = histogram.length-1; m>=0; m--){
											if(histogram[m] > 0 && histogram[m] < 3){ 
												resultArray[k] = m + 2;
												k++;
												if(k == 6){
													resultArray[0] = 4;
													break;
												}
											}
										}
									}
								}

							}else 
								if(mode == 2){ // pair
									resultArray[1] = indexOfTheMode + 2;
									resultArray[2] = indexOfTheMode + 2;
									int k = 3;
									for(int r = histogram.length-1; r>=0; r--){
										if(histogram[r] == 1){ 
											resultArray[k] = r + 2;
											k++;
											if(k == 6){
												resultArray[0] = 2;
												break;
											}
										}
									}
								}
					}
				}
			}

		return resultArray;
	}

}