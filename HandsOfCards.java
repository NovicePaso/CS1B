
public class HandsOfCards
{
   final static String NEW_LINE = "\n";
   
   public static class CardIdentity
   {  
      public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };
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
   
   public static void main(String[] args)
   {
// --------------------- Phase 1 Main -----------------------------
      
      // create 3 new card objects
      Card card1a = new Card('J', CardIdentity.Suit.HEARTS); // valid entry
      Card card2a = new Card(); // valid default entry
      Card card3a = new Card('0', CardIdentity.Suit.SPADES); // invalid entry
      
      // display cards
      System.out.println("Cards: " + NEW_LINE + "{");
      System.out.println(card1a.toString());
      System.out.println(card2a.toString());
      System.out.println(card3a.toString());
      System.out.println("}" + NEW_LINE);
      
      // set one card from valid to invalid, and another visa versa
      card1a.set('1', CardIdentity.Suit.DIAMONDS); // valid to invalid
      card3a.set('Q', CardIdentity.Suit.HEARTS); // invalid to valid
      
      // display updated cards
      System.out.println("Cards: " + NEW_LINE + "{");
      System.out.println(card1a.toString());
      System.out.println(card2a.toString());
      System.out.println(card3a.toString());
      System.out.println("}" + NEW_LINE);
      
//  --------------------- Phase 2 Main ----------------------------
      
      // create between 2 and 5 explicit card objects and 1 hand object
      Card card1b = new Card('K', CardIdentity.Suit.CLUBS);
      Card card2b = new Card('8', CardIdentity.Suit.DIAMONDS);
      Card card3b = new Card('3', CardIdentity.Suit.HEARTS);
      Card card4b = new Card('A', CardIdentity.Suit.SPADES);
      Card[] cardArray = {card1b, card2b, card3b, card4b};
      Hand myHand = new Hand();
      
      // take random card of the 4 created until MAX_CARDS
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         myHand.takeCard(cardArray[(int)(Math.random() * 4)]);
      }
      
      // display numCards and cards in hand
      System.out.println("Number of cards in hand: " + myHand.getNumCards() 
      + NEW_LINE);
      System.out.println(myHand.toString() + NEW_LINE);
      
      // inspect a legal int card in hand
      System.out.println("Inspected Card: " + myHand.inspectCard(4) + NEW_LINE);
         
      // play and display each played card in hand until empty
      System.out.println("You've Played:");
      while(myHand.getNumCards() > 0)
      {
         System.out.println(myHand.playCard().toString());
      }
      
      // verify no cards in hand
      System.out.println(NEW_LINE + "Number of cards in hand: " + 
      myHand.getNumCards() + NEW_LINE);
      System.out.println(myHand.toString() + NEW_LINE);
      
      // inspect an illegal int card in hand
      System.out.println("Inspected Card: " + myHand.inspectCard(4));
   }
}


/* ---------------------- Phase 1 Output ----------------------------------

Cards: 
{
J of HEARTS
A of SPADES
** Invalid **
}

Cards: 
{
** Invalid **
A of SPADES
Q of HEARTS
}

 ------------------------ Phase 2 Output ----------------------------------

Number of cards in hand: 30

Hand: 
{
A of SPADES
K of CLUBS
A of SPADES
A of SPADES
A of SPADES
3 of HEARTS
8 of DIAMONDS
3 of HEARTS
K of CLUBS
A of SPADES
K of CLUBS
A of SPADES
K of CLUBS
K of CLUBS
3 of HEARTS
K of CLUBS
3 of HEARTS
3 of HEARTS
A of SPADES
K of CLUBS
8 of DIAMONDS
K of CLUBS
K of CLUBS
K of CLUBS
8 of DIAMONDS
A of SPADES
A of SPADES
A of SPADES
K of CLUBS
3 of HEARTS
}

Inspected Card: A of SPADES

You've Played:
3 of HEARTS
K of CLUBS
A of SPADES
A of SPADES
A of SPADES
8 of DIAMONDS
K of CLUBS
K of CLUBS
K of CLUBS
8 of DIAMONDS
K of CLUBS
A of SPADES
3 of HEARTS
3 of HEARTS
K of CLUBS
3 of HEARTS
K of CLUBS
K of CLUBS
A of SPADES
K of CLUBS
A of SPADES
K of CLUBS
3 of HEARTS
8 of DIAMONDS
3 of HEARTS
A of SPADES
A of SPADES
A of SPADES
K of CLUBS
A of SPADES

Number of cards in hand: 0

Hand: 
{
}

Inspected Card: ** Invalid **


-------------------------------------------------------------------------- */