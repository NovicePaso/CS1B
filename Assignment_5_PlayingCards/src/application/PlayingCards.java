// Timothy Kwock
// CS1B - Assignment 5 Playing Cards

package application;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;

public class PlayingCards extends Application
{
   public static final String NEW_LINE = "\n";
   final int NUM_CARDS_PER_HAND = 7;
   final int NUM_PLAYERS = 2;
   Image[] humanImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] humanViews = new ImageView[NUM_CARDS_PER_HAND];
   Image[] computerImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] computerViews = new ImageView[NUM_CARDS_PER_HAND];
   Image[] playedImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] playedViews = new ImageView[NUM_CARDS_PER_HAND];
   Label[] playLabelText = new Label[NUM_PLAYERS];
   
   static final int NUM_CARD_IMAGES = 57;  // 52 + 4 jokers + 1 back-card image
   static Pane pane = new FlowPane();
   static Image[] image = new Image[NUM_CARD_IMAGES];
   static ImageView[] views = new ImageView[NUM_CARD_IMAGES];

   // for assisting with conversions:
   static String cardlValsConvertAssist = "23456789TJQKAX";
   static String suitValsConvertAssist = "CDHS";
   
   private void moveCard(Image cTemp, Image hTemp, Pane pPane)
   {
      pPane.getChildren().clear();
      playLabelText[0] = new Label("Computer");
      playLabelText[1] = new Label("You");
      playedImages[0] = cTemp;
      playedImages[1] = hTemp;
      
      for(int i = 0; i < playedImages.length; i++)
      {
         playedViews[i] = new ImageView(playedImages[i]);
         pPane.getChildren().add(playedViews[i]);
      }
      
      for(int i = 0; i < NUM_PLAYERS; i++)
      {
         pPane.getChildren().add(playLabelText[i]);
      }
   }
   
   public static void main(String[] args) 
   {
      launch(args);
   }

   public void start(Stage primaryStage)
   {      
      // Create the scene and place it in the stage
      BorderPane pane = new BorderPane(); 
      Scene scene = new Scene(pane, 800, 600);
      primaryStage.setTitle("Card Table");
      primaryStage.setScene(scene);
      
      // create images
      HBox computerPane = new HBox(15);
      HBox humanPane = new HBox(15);
      FlowPane playedPane = new FlowPane(100, 15);
      playedPane.setPadding(new Insets(100, 200, 100, 200));
      playedPane.setAlignment(Pos.CENTER);
      playedPane.setStyle("-fx-border-color: blue");
      computerPane.setPadding(new Insets(15, 100, 15, 100));
      humanPane.setPadding(new Insets(15, 30, 15, 30));
      
      
      // add cards to human and computer panes
      for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         computerImages[i] = GUICard.getImage(generateRandomCard());
         computerViews[i] = new ImageView(GUICard.getBackCardImage());
         computerPane.getChildren().add(computerViews[i]);  
         
         // adds cards as buttons to human panes
         Button humanBtn = new Button();
         humanImages[i] = GUICard.getImage(generateRandomCard());
         humanViews[i] = new ImageView(humanImages[i]);  
         humanBtn.setGraphic(humanViews[i]);
         humanPane.getChildren().add(humanBtn);
         
         // plays card by human when clicked
         final Image hTemp = humanImages[i];
         humanBtn.setOnAction(e -> {
            final Image cTemp = computerImages[(int)(Math.random() * 
               NUM_CARDS_PER_HAND)];
            moveCard(cTemp, hTemp, playedPane);
         });
      }
      
      // initialize starting cards in playedPane as Ace of Spades
      moveCard(GUICard.getImage(new Card('A', CardIdentity.Suit.SPADES)), 
         GUICard.getImage(new Card('A', Card.Suit.SPADES)), playedPane);
      pane.setTop(computerPane);
      pane.setCenter(playedPane);  
      pane.setBottom(humanPane); 

      // show everything to the user
      primaryStage.show();  
   }
   
   static Card generateRandomCard()
   {
      int randomValue, randomSuit;
      randomValue = (int)(Math.random() * 14);
      randomSuit = (int)(Math.random() * 4);
      return new Card(turnIntIntoCardValueChar(randomValue), 
         GUICard.turnIntIntoSuit(randomSuit));
   }
   
   static void loadCardImages()
   {
      String imageFileName;
      int intSuit, intVal;

      for (intSuit = 0; intSuit < 4; intSuit++)
         for (intVal = 0; intVal < 14; intVal++ )
         {
            imageFileName = "file:images/" + turnIntIntoCardValueChar(intVal) + 
               turnIntIntoCardSuitChar(intSuit) + ".gif";
            image[image.length - 1] = new Image(imageFileName);
            views[image.length - 1] = new ImageView(image[image.length - 1]);
            pane.getChildren().add(views[image.length - 1]);
         }

      imageFileName = "file:images/BK.gif";
      image[image.length - 1] = new Image(imageFileName);
      views[image.length - 1] = new ImageView(image[image.length - 1]);
      pane.getChildren().add(views[image.length - 1]);
   }  
   
   // turns 0 - 13 into 'A', '2', '3', ... 'Q', 'K', 'X'
   static char turnIntIntoCardValueChar(int k)
   {
   
      if ( k < 0 || k > 13)
         return '?'; 
      return cardlValsConvertAssist.charAt(k);
   }
   
   // turns 0 - 3 into 'C', 'D', 'H', 'S'
   static char turnIntIntoCardSuitChar(int k)
   {
      if ( k < 0 || k > 3)
         return '?'; 
      return suitValsConvertAssist.charAt(k);
   }


   static class CardIdentity
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
   
   static class Card extends CardIdentity
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
   
   static class Hand extends Card
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
   
   static class Deck extends Card
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

   class GUICard
   {
      // 14 = A thru K (+ joker)
      private static Image[][] imageCards = new Image[14][4];   
      private static ImageView[][] imageCardViews = new ImageView[14][4];
      private static Image imageBack;
      private static ImageView imageCardBack;
      private static boolean imagesLoaded = false; 
      private static String cardlValsConvertAssist = "23456789TJQKAX";
      private static String suitValsConvertAssist  = "CDHS";
      private static Card.Suit suitConvertAssist[] =
      {
         Card.Suit.CLUBS,
         Card.Suit.DIAMONDS,
         Card.Suit.HEARTS,
         Card.Suit.SPADES
      };
      
      // generates image array from files
      static void loadCardImages()
      {
         String imageFileName;
         int intSuit, intVal;
         
         if(imagesLoaded == false)
         {
            for (intSuit = 0; intSuit < 4; intSuit++)
               for (intVal = 0; intVal < 14; intVal++)
               {
                  imageFileName = "file:images/" + 
                     turnIntIntoCardValueChar(intVal) + 
                     turnIntIntoCardSuitChar(intSuit) + ".gif";
                  imageBack = new Image(imageFileName);
                  imageCards[intVal][intSuit] = imageBack;
                  imageCardBack = new ImageView(imageCards[intVal][intSuit]);
                  imageCardViews[intVal][intSuit] = imageCardBack;
               }
            imagesLoaded = true;
         }
      }
      
       // turns 0 - 13 into 'A', '2', '3', ... 'Q', 'K', 'X'
       static char turnIntIntoCardValueChar(int k)
       {
       
          if ( k < 0 || k > 13)
             return '?'; 
          return cardlValsConvertAssist.charAt(k);
       }
       
       // turns 0 - 3 into 'C', 'D', 'H', 'S'
       static char turnIntIntoCardSuitChar(int k)
       {
          if ( k < 0 || k > 3)
             return '?'; 
          return suitValsConvertAssist.charAt(k);
       }
      
      static Card.Suit turnIntIntoSuit(int k)
      {
         return suitConvertAssist[k];
      }
      
      static int valueAsInt(Card card)
      {
         return cardlValsConvertAssist.indexOf(card.getValue());
      }
      
      static int suitAsInt(Card card)
      {
         for (int i = 0; i < suitConvertAssist.length; i++)
         {
            if(suitConvertAssist[i] == (card.getSuit()))
            {
               return i;
            }
         }
         return 0; // dummy value
      }
      
      static public Image getImage(Card card)
      {
         loadCardImages();
         return imageCards[valueAsInt(card)][suitAsInt(card)];
      }
      
      static public Image getBackCardImage()
      {
         return new Image("file:images/BK.gif");
      }
   }
}