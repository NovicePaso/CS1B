// Timothy Kwock
// Winter 2024 CS1B Assignment 2 - Card Deck

import java.util.Scanner;

public class CardDeck
{
   final static String NEW_LINE = "\n";

   public static class CardIdentity
   {
      public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };
      public static char[] Value = { 'A', 'K', 'Q', 'J', 'T', '9', '8', '7',
              '6', '5', '4', '3', '2' };
      // Private member(s)
      private char value;
      private Suit suit;

      // no-arg constructor
      public CardIdentity()
      {
         // default values
         this.value = 'A';
         this.suit = Suit.SPADES;
      }

      public boolean set(char value, Suit suit)
      {
         if(isValid(value, suit))
         {
            this.value = value;
            this.suit = suit;
            return true;
         }
         else
         {
            return false;
         }
      }

      // accessors
      public Suit getSuit()
      {
         return suit;
      }

      public char getValue()
      {
         return value;
      }

      // helper method checks legality of card value and suit         
      private static boolean isValid(char value, Suit suit)
      {
         if(((value <= '9' && value >= '2') || value == 'A' || value == 'K' ||
                 value == 'Q' || value == 'J' || value == 'T') && (suit == Suit.CLUBS
                 || suit == Suit.DIAMONDS || suit == Suit.HEARTS || suit ==
                 Suit.SPADES))
         {
            return true;
         }
         else
         {
            return false;
         }
      }
   }

   public static class Card extends CardIdentity
   {
      // private member(s)
      boolean cardError;

      // constructors
      public Card()
      {
         super();
         cardError = false;
      }

      public Card(char value, Suit suit)
      {
         super();
         set(value, suit);
      }

      @Override
      public boolean set(char value, Suit suit)
      {
         if(super.isValid(value, suit))
         {
            super.set(value, suit);
            cardError = false;
            return true;
         }
         else
         {
            cardError = true;
            return false;
         }
      }

      @Override
      public String toString()
      {
         if(cardError)
         {
            return "** Invalid **";
         }
         else
         {
            return (super.getValue() + " of " + super.getSuit());
         }
      }

      // accessor
      public boolean getCardError()
      {
         return cardError;
      }

      public boolean equals(Card card)
      {
         if((getValue() == card.getValue()) && (getSuit() == card.getSuit()))
         {
            return true;
         }
         else
         {
            return false;
         }
      }
   }

   public static class Hand extends Card
   {
      // constants
      static final int MAX_CARDS = 30;

      // private members
      private Card[] myCards;
      private int numCards;

      // no-arg constructor
      public Hand()
      {
         myCards = new Card[MAX_CARDS];
         numCards = 0;
      }

      // removes all cards from hand
      public void resetHand()
      {
         for(int i = 0; i < numCards; i++)
         {
            myCards[i] = null;
         }
         numCards = 0;
      }

      public boolean takeCard(Card card)
      {
         // does not take a card if over or equal to MAX_CARDS in hand
         if(numCards >= MAX_CARDS)
         {
            return false;
         }
         else
         {
            // takes a card but won't add if there is a cardError
            if(card.cardError == false)
            {
               Card newCard = new Card(card.getValue(), card.getSuit());
               myCards[numCards] = newCard;
               numCards++;
            }
            return true;
         }
      }

      // plays the last available Card element in myCards[]
      public Card playCard()
      {
         Card tempCard = myCards[numCards - 1];
         myCards[numCards - 1] = null;
         numCards--;
         return tempCard;
      }

      @Override
      public String toString()
      {
         String returnString = "Hand: " + NEW_LINE + "{" + NEW_LINE;
         for(int i = 0; i < numCards; i++)
         {
            returnString = returnString.concat(myCards[i].toString()
                    + NEW_LINE);
         }
         returnString = returnString.concat("}");
         return returnString;
      }

      public int getNumCards()
      {
         return numCards;
      }

      public Card inspectCard(int k)
      {
         if(k <= numCards - 1 && k >= 0)
         {
            return myCards[k];
         }
         else
         {
            // returns a new card object with cardError if k is outOfBounds
            Card badCard = new Card();
            badCard.set('B', Suit.SPADES); // dummy value triggering cardError
            return badCard;
         }
      }
   }

   public static class Deck extends Card
   {
      // private constants
      private static final int MAX_PACKS = 6;
      private static final int NUM_CARDS_PER_PACK = 52;
      private static final int MAX_CARDS_PER_DECK = MAX_PACKS *
              NUM_CARDS_PER_PACK;

      // private members
      private static Card[] masterPack;
      private Card[] cards;
      private int topCard;
      private int numPacks;

      // constructors
      public Deck()
      {
         numPacks = 1;
         topCard = NUM_CARDS_PER_PACK * numPacks;
         cards = new Card[NUM_CARDS_PER_PACK];
         allocateMasterPack();
         initializePack(numPacks);
      }

      public Deck(int numPacks)
      {
         topCard = NUM_CARDS_PER_PACK * numPacks;
         cards = new Card[topCard];
         allocateMasterPack();
         if (!initializePack(numPacks))
         {
            numPacks = 1;
            initializePack(numPacks);
         }
      }

      // public methods
      public boolean initializePack(int numPacks)
      {
         if (numPacks <= MAX_PACKS && numPacks > 0)
         {
            for(int i = 0; i < topCard; i++)
            {
               cards[i] = new Card(masterPack[i % NUM_CARDS_PER_PACK].getValue()
                       , masterPack[i % NUM_CARDS_PER_PACK].getSuit());
            }
            return true;
         }
         else
         {
            return false;
         }
      }

      public void shuffle()
      {
         // tempCard to facilitate switching 2 cards
         Card tempCard = new Card();
         for(int i = 0; i < cards.length; i++)
         {
            // generates 2 random numbers to be used as indices
            int tempInt1 = (int)Math.floor(Math.random() * cards.length);
            int tempInt2 = (int)Math.floor(Math.random() * cards.length);

            // Switches 2 random cards
            tempCard.set(cards[tempInt1].getValue(), cards[tempInt1].getSuit());
            cards[tempInt1].set(cards[tempInt2].getValue(),
                    cards[tempInt2].getSuit());
            cards[tempInt2].set(tempCard.getValue(), tempCard.getSuit());
         }
      }

      public Card dealCard()
      {
         Card dealtCard = new Card();
         dealtCard.set(cards[topCard - 1].getValue(),
                 cards[topCard - 1].getSuit());
         cards[topCard - 1] = null;
         topCard -= 1;
         return dealtCard;
      }

      public int getTopCard()
      {
         return topCard;
      }

      public Card inspectCard(int k) throws IndexOutOfBoundsException
      {
         try
         {
            Card inspectedCard = new Card();
            inspectedCard.set(cards[k].getValue(), cards[k].getSuit());
            return inspectedCard;
         }
         catch(IndexOutOfBoundsException ex)
         {
            Card badCard = new Card();
            badCard.set('B', Suit.SPADES); // dummy value triggering cardError
            return badCard;
         }
      }

      // private methods
      private static void allocateMasterPack()
      {
         if (masterPack == null)
         {
            masterPack = new Card[NUM_CARDS_PER_PACK];

            for(int i = 0; i < NUM_CARDS_PER_PACK; i++)
            {
               masterPack[i] = new Card(Value[i % Value.length],
                       CardIdentity.Suit.values()[i / Value.length]);
            }
         }
      }
   }

   public static void main(String[] args)
   {
      // ----------------------- Phase 1 Main --------------------------------

      // 2 packs
      Deck phase1Deck = new Deck(2);
      for(int i = 0; i < phase1Deck.cards.length; i++)
      {
         System.out.println(phase1Deck.dealCard().toString());
      }
      System.out.print("\n");
      phase1Deck = new Deck(2);
      phase1Deck.shuffle();
      for(int i = 0; i < phase1Deck.cards.length; i++)
      {
         System.out.println(phase1Deck.dealCard().toString());
      }
      System.out.print("\n");

      // 1 pack
      phase1Deck = new Deck();
      for(int i = 0; i < phase1Deck.cards.length; i++)
      {
         System.out.println(phase1Deck.dealCard().toString());
      }
      System.out.print("\n");
      phase1Deck = new Deck();
      phase1Deck.shuffle();
      for(int i = 0; i < phase1Deck.cards.length; i++)
      {
         System.out.println(phase1Deck.dealCard().toString());
      }

      // ---------------------- Phase 2 Main ---------------------------------

      Scanner input = new Scanner(System.in);

      // user inputs number of players
      System.out.print("Enter number of players (1 - 10): ");
      int numPlayers = input.nextInt();
      while(numPlayers < 1 || numPlayers > 10)
      {
         System.out.println("Please enter a valid number of players (1 - 10)");
         numPlayers = input.nextInt();
      }
      input.close();

      // instantiates new Hand objects for each player
      Hand[] playerHands = new Hand[numPlayers];
      for(int i = 0; i < numPlayers; i++)
      {
         playerHands[i] = new Hand();
      }

      // instantiate a single deck and deal cards to each player
      Deck phase2Deck = new Deck();
      while(phase2Deck.getTopCard() != 0)
      {
         for(int i = 0; i < numPlayers; i++)
         {
            if(phase2Deck.getTopCard() != 0)
            {
               playerHands[i].takeCard(phase2Deck.dealCard());
            }
            else
            {
               break;
            }
         }
      }

      // displays all players' hands
      System.out.println("Hands dealt from an UNSHUFFLED deck:");
      for(int i = 0; i < numPlayers; i++)
      {
         System.out.println("Player " + (i + 1) + " has " +
                 playerHands[i].getNumCards() + " cards in hand");
         System.out.println(playerHands[i].toString() + NEW_LINE);
      }

      // reset objects to initial state
      for(int i = 0; i < numPlayers; i++)
      {
         playerHands[i].resetHand();
      }
      phase2Deck = new Deck();

      // shuffles deck and deals cards to each player
      phase2Deck.shuffle();
      while(phase2Deck.getTopCard() != 0)
      {
         for(int i = 0; i < numPlayers; i++)
         {
            if(phase2Deck.getTopCard() != 0)
            {
               playerHands[i].takeCard(phase2Deck.dealCard());
            }
            else
            {
               break;
            }
         }
      }

      // displays all players' hands
      System.out.println("Hands dealt from a SHUFFLED deck:");
      for(int i = 0; i < numPlayers; i++)
      {
         System.out.println("Player " + (i + 1) + " has " +
                 playerHands[i].getNumCards() + " cards in hand");
         System.out.println(playerHands[i].toString() + NEW_LINE);
      }
   }
}
      
      /* -------------------- Output Phase 1 ---------------------------------
       
2 of SPADES
3 of SPADES
4 of SPADES
5 of SPADES
6 of SPADES
7 of SPADES
8 of SPADES
9 of SPADES
T of SPADES
J of SPADES
Q of SPADES
K of SPADES
A of SPADES
2 of HEARTS
3 of HEARTS
4 of HEARTS
5 of HEARTS
6 of HEARTS
7 of HEARTS
8 of HEARTS
9 of HEARTS
T of HEARTS
J of HEARTS
Q of HEARTS
K of HEARTS
A of HEARTS
2 of DIAMONDS
3 of DIAMONDS
4 of DIAMONDS
5 of DIAMONDS
6 of DIAMONDS
7 of DIAMONDS
8 of DIAMONDS
9 of DIAMONDS
T of DIAMONDS
J of DIAMONDS
Q of DIAMONDS
K of DIAMONDS
A of DIAMONDS
2 of CLUBS
3 of CLUBS
4 of CLUBS
5 of CLUBS
6 of CLUBS
7 of CLUBS
8 of CLUBS
9 of CLUBS
T of CLUBS
J of CLUBS
Q of CLUBS
K of CLUBS
A of CLUBS
2 of SPADES
3 of SPADES
4 of SPADES
5 of SPADES
6 of SPADES
7 of SPADES
8 of SPADES
9 of SPADES
T of SPADES
J of SPADES
Q of SPADES
K of SPADES
A of SPADES
2 of HEARTS
3 of HEARTS
4 of HEARTS
5 of HEARTS
6 of HEARTS
7 of HEARTS
8 of HEARTS
9 of HEARTS
T of HEARTS
J of HEARTS
Q of HEARTS
K of HEARTS
A of HEARTS
2 of DIAMONDS
3 of DIAMONDS
4 of DIAMONDS
5 of DIAMONDS
6 of DIAMONDS
7 of DIAMONDS
8 of DIAMONDS
9 of DIAMONDS
T of DIAMONDS
J of DIAMONDS
Q of DIAMONDS
K of DIAMONDS
A of DIAMONDS
2 of CLUBS
3 of CLUBS
4 of CLUBS
5 of CLUBS
6 of CLUBS
7 of CLUBS
8 of CLUBS
9 of CLUBS
T of CLUBS
J of CLUBS
Q of CLUBS
K of CLUBS
A of CLUBS

6 of SPADES
4 of SPADES
3 of CLUBS
J of DIAMONDS
J of CLUBS
6 of CLUBS
4 of SPADES
Q of CLUBS
A of HEARTS
Q of SPADES
7 of SPADES
K of HEARTS
4 of CLUBS
9 of DIAMONDS
3 of HEARTS
4 of HEARTS
J of SPADES
J of HEARTS
8 of DIAMONDS
9 of HEARTS
K of SPADES
T of HEARTS
K of SPADES
9 of DIAMONDS
K of CLUBS
A of HEARTS
5 of SPADES
J of SPADES
4 of DIAMONDS
5 of DIAMONDS
8 of HEARTS
7 of DIAMONDS
9 of HEARTS
T of SPADES
T of DIAMONDS
A of DIAMONDS
A of CLUBS
2 of HEARTS
J of DIAMONDS
8 of CLUBS
T of SPADES
3 of SPADES
A of DIAMONDS
4 of DIAMONDS
8 of SPADES
2 of CLUBS
Q of HEARTS
7 of HEARTS
5 of SPADES
7 of DIAMONDS
9 of SPADES
5 of CLUBS
9 of CLUBS
8 of DIAMONDS
Q of DIAMONDS
6 of DIAMONDS
Q of HEARTS
Q of SPADES
7 of CLUBS
J of CLUBS
8 of CLUBS
Q of DIAMONDS
5 of CLUBS
2 of HEARTS
2 of SPADES
5 of HEARTS
T of HEARTS
7 of HEARTS
6 of HEARTS
7 of CLUBS
2 of SPADES
3 of CLUBS
8 of HEARTS
3 of HEARTS
Q of CLUBS
K of DIAMONDS
6 of HEARTS
8 of SPADES
2 of DIAMONDS
3 of DIAMONDS
K of DIAMONDS
J of HEARTS
3 of DIAMONDS
5 of DIAMONDS
6 of SPADES
A of CLUBS
T of DIAMONDS
3 of SPADES
T of CLUBS
2 of DIAMONDS
9 of SPADES
K of HEARTS
6 of DIAMONDS
A of SPADES
4 of HEARTS
T of CLUBS
A of SPADES
K of CLUBS
9 of CLUBS
5 of HEARTS
4 of CLUBS
6 of CLUBS
7 of SPADES
2 of CLUBS

2 of SPADES
3 of SPADES
4 of SPADES
5 of SPADES
6 of SPADES
7 of SPADES
8 of SPADES
9 of SPADES
T of SPADES
J of SPADES
Q of SPADES
K of SPADES
A of SPADES
2 of HEARTS
3 of HEARTS
4 of HEARTS
5 of HEARTS
6 of HEARTS
7 of HEARTS
8 of HEARTS
9 of HEARTS
T of HEARTS
J of HEARTS
Q of HEARTS
K of HEARTS
A of HEARTS
2 of DIAMONDS
3 of DIAMONDS
4 of DIAMONDS
5 of DIAMONDS
6 of DIAMONDS
7 of DIAMONDS
8 of DIAMONDS
9 of DIAMONDS
T of DIAMONDS
J of DIAMONDS
Q of DIAMONDS
K of DIAMONDS
A of DIAMONDS
2 of CLUBS
3 of CLUBS
4 of CLUBS
5 of CLUBS
6 of CLUBS
7 of CLUBS
8 of CLUBS
9 of CLUBS
T of CLUBS
J of CLUBS
Q of CLUBS
K of CLUBS
A of CLUBS

3 of HEARTS
T of DIAMONDS
2 of DIAMONDS
A of HEARTS
2 of HEARTS
6 of CLUBS
7 of HEARTS
2 of SPADES
T of SPADES
5 of SPADES
Q of SPADES
K of DIAMONDS
9 of SPADES
7 of CLUBS
K of HEARTS
A of SPADES
5 of HEARTS
J of SPADES
4 of DIAMONDS
9 of HEARTS
8 of HEARTS
T of CLUBS
A of DIAMONDS
4 of HEARTS
J of CLUBS
7 of SPADES
9 of CLUBS
3 of DIAMONDS
4 of SPADES
5 of DIAMONDS
2 of CLUBS
5 of CLUBS
8 of SPADES
9 of DIAMONDS
Q of DIAMONDS
J of DIAMONDS
K of SPADES
8 of DIAMONDS
8 of CLUBS
A of CLUBS
7 of DIAMONDS
J of HEARTS
Q of HEARTS
T of HEARTS
6 of SPADES
3 of SPADES
4 of CLUBS
K of CLUBS
3 of CLUBS
Q of CLUBS
6 of HEARTS
6 of DIAMONDS

-----------------------------------------------------------------------------

--------------------------------- Output Phase 2 ----------------------------
       
Enter number of players (1 - 10): 7
Hands dealt from an UNSHUFFLED deck:
Player 1 has 8 cards in hand
Hand: 
{
2 of SPADES
9 of SPADES
3 of HEARTS
T of HEARTS
4 of DIAMONDS
J of DIAMONDS
5 of CLUBS
Q of CLUBS
}

Player 2 has 8 cards in hand
Hand: 
{
3 of SPADES
T of SPADES
4 of HEARTS
J of HEARTS
5 of DIAMONDS
Q of DIAMONDS
6 of CLUBS
K of CLUBS
}

Player 3 has 8 cards in hand
Hand: 
{
4 of SPADES
J of SPADES
5 of HEARTS
Q of HEARTS
6 of DIAMONDS
K of DIAMONDS
7 of CLUBS
A of CLUBS
}

Player 4 has 7 cards in hand
Hand: 
{
5 of SPADES
Q of SPADES
6 of HEARTS
K of HEARTS
7 of DIAMONDS
A of DIAMONDS
8 of CLUBS
}

Player 5 has 7 cards in hand
Hand: 
{
6 of SPADES
K of SPADES
7 of HEARTS
A of HEARTS
8 of DIAMONDS
2 of CLUBS
9 of CLUBS
}

Player 6 has 7 cards in hand
Hand: 
{
7 of SPADES
A of SPADES
8 of HEARTS
2 of DIAMONDS
9 of DIAMONDS
3 of CLUBS
T of CLUBS
}

Player 7 has 7 cards in hand
Hand: 
{
8 of SPADES
2 of HEARTS
9 of HEARTS
3 of DIAMONDS
T of DIAMONDS
4 of CLUBS
J of CLUBS
}

Hands dealt from a SHUFFLED deck:
Player 1 has 8 cards in hand
Hand: 
{
2 of SPADES
8 of CLUBS
5 of DIAMONDS
T of HEARTS
8 of HEARTS
7 of CLUBS
2 of HEARTS
Q of CLUBS
}

Player 2 has 8 cards in hand
Hand: 
{
3 of DIAMONDS
T of SPADES
4 of SPADES
6 of CLUBS
8 of DIAMONDS
A of SPADES
9 of HEARTS
J of DIAMONDS
}

Player 3 has 8 cards in hand
Hand: 
{
K of DIAMONDS
J of CLUBS
5 of HEARTS
Q of HEARTS
Q of SPADES
9 of DIAMONDS
4 of HEARTS
4 of CLUBS
}

Player 4 has 7 cards in hand
Hand: 
{
J of SPADES
4 of DIAMONDS
K of SPADES
9 of CLUBS
7 of DIAMONDS
K of CLUBS
5 of SPADES
}

Player 5 has 7 cards in hand
Hand: 
{
8 of SPADES
6 of DIAMONDS
A of CLUBS
9 of SPADES
Q of DIAMONDS
7 of HEARTS
5 of CLUBS
}

Player 6 has 7 cards in hand
Hand: 
{
7 of SPADES
3 of HEARTS
6 of HEARTS
K of HEARTS
3 of SPADES
T of DIAMONDS
T of CLUBS
}

Player 7 has 7 cards in hand
Hand: 
{
6 of SPADES
2 of DIAMONDS
A of DIAMONDS
A of HEARTS
2 of CLUBS
J of HEARTS
3 of CLUBS
}

----------------------------------------------------------------------------*/