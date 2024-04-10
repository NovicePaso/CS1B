// Timothy Kwock
// CS1B - Assignment 8 - The 24-Point Card Game

package application;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;

public class TwentyFourPointCardGame extends Application
{
   public static final String NEW_LINE = "\n";
   final int NUM_CARDS_PER_HAND = 4;
   final int NUM_DECKS = 1;
   final int NUM_PLAYERS = 1;
   Image[] humanImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] humanViews = new ImageView[NUM_CARDS_PER_HAND];
   Image[] computerImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] computerViews = new ImageView[NUM_CARDS_PER_HAND];
   Image[] playedImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] playedViews = new ImageView[NUM_CARDS_PER_HAND];
   Label[] playLabelText = new Label[NUM_PLAYERS];
   
   Image[] handImages = new Image[NUM_CARDS_PER_HAND];
   ImageView[] handViews = new ImageView[NUM_CARDS_PER_HAND];
   
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
      Scene scene = new Scene(pane, 400, 200);
      primaryStage.setTitle("Card Table");
      primaryStage.setScene(scene);
      
      // create images
      HBox shufflePane = new HBox(15);
      HBox verifyPane = new HBox(15);
      HBox cardPane = new HBox(15);
      cardPane.setAlignment(Pos.CENTER);
      
      // create shufflePane and cardPane.  Add functionality to Shuffle button
      int[] dealtCardVals = new int[NUM_CARDS_PER_HAND];
      Button shuffleBtn = new Button("Shuffle");
      shuffleBtn.setOnAction(e -> {
         cardPane.getChildren().clear();
         Deck houseDeck = new Deck(NUM_DECKS);
         houseDeck.shuffle();
         for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
         {
            Card dealtCard = houseDeck.dealCard();
            handImages[i] = GUICard.getImage(dealtCard);
            dealtCardVals[i] = GUICard.valueAsInt(dealtCard) + 1;
            System.out.println(dealtCardVals[i]);
            handViews[i] = new ImageView(handImages[i]);
            cardPane.getChildren().add(handViews[i]);
         }
      });
      Label verifyLabel = new Label();
      verifyLabel.setWrapText(true);
      verifyLabel.setMaxWidth(200);
      shufflePane.getChildren().addAll(verifyLabel, shuffleBtn);
      shufflePane.setAlignment(Pos.CENTER_RIGHT);
      
      
      // create verifyPane
      Label expReqLabel = new Label("Enter an expression:");
      TextField expBox = new TextField();
      Button verifyBtn = new Button("Verify");
      
      // add functionality to verifyBtn
      verifyBtn.setOnAction(e -> {
         verifyLabel.setText("");
         String expBoxText = expBox.getText();
         if(verifyCardVals(dealtCardVals, expBoxText))
         {
            if(verifyExpression(expBoxText))
            {
               verifyLabel.setText("Correct");
            }
            else
            {
               verifyLabel.setText("Incorrect Result");
            }
         }
         else
         {
            verifyLabel.setText("The numbers in the expression don't match the "
               + "numbers in the set");
         }
      });
      verifyPane.getChildren().addAll(expReqLabel, expBox, verifyBtn);
      verifyPane.setAlignment(Pos.CENTER);
      
      pane.setTop(shufflePane);
      pane.setCenter(cardPane);  
      pane.setBottom(verifyPane); 

      // show everything to the user
      primaryStage.show();  
   }
   
   // Verify card values of dealt cards vs expression operands
   static boolean verifyCardVals(int[] dealtCardVals, String expression)
   {
      Stack<Integer> operandStack = new Stack<>();
      Stack<Character> operatorStack = new Stack<>();
      String expCopy = new String(expression);
      expCopy = insertBlanks(expCopy);
      String[] tokens = expCopy.split(" ");
      
      for(String token: tokens)
      {
         if (token.length() == 0)
            continue;
         else if(token.charAt(0) == '+' || token.charAt(0) == '-')
         {
         }
         else if(token.charAt(0) == '*' || token.charAt(0) == '/')
         {
            operatorStack.push(token.charAt(0));
         }
         else if(token.trim().charAt(0) == '(') 
         {
            operatorStack.push('(');
         }
         else if(token.trim().charAt(0) == ')')
         {
            operatorStack.pop();
         }
         else
         {
            operandStack.push(Integer.valueOf(token));
         }
      }
      
      for(int i = 0; i < operandStack.size(); i++)
      {
         System.out.println(operandStack.get(i));
      }
      
      for(int i = 0; i < dealtCardVals.length; i++)
      {
         if(operandStack.contains(dealtCardVals[i]))
         {
            operandStack.remove(operandStack.indexOf(dealtCardVals[i]));
         }
         else
         {
            return false;
         }
      }
      return true; 
   }
   
   static boolean verifyExpression(String expression)
   {
      return (evaluateExpression(expression) == 24);
   }
   
   private static int evaluateExpression(String expression)
   {
      // Create operandStack to store operands
      Stack<Integer> operandStack = new Stack<>();
      // Create operatorStack to store operators
      Stack<Character> operatorStack = new Stack<>();

      // Insert blanks around (, ), +, -, /, and *
      expression = insertBlanks(expression);

      // Extract operands and operators
      String[] tokens = expression.split(" ");

      // Phase 1: Scan tokens
      for (String token : tokens)
      {
         if (token.length() == 0) // Blank space
            continue; // Back to the while loop to extract the next token
         else if (token.charAt(0) == '+' || token.charAt(0) == '-')
         {
            // Process all +, -, *, / in the top of the operator stack
            while (!operatorStack.isEmpty() && (operatorStack.peek() == '+' || 
               operatorStack.peek() == '-'
               || operatorStack.peek() == '*' || operatorStack.peek() == '/'))
            {
               processAnOperator(operandStack, operatorStack);
            }
            // Push the + or - operator into the operator stack
            operatorStack.push(token.charAt(0));
         } 
         else if (token.charAt(0) == '*' || token.charAt(0) == '/')
         {
            // Process all *, / in the top of the operator stack
            while (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || 
               operatorStack.peek() == '/'))
            {
               processAnOperator(operandStack, operatorStack);
            }

            // Push the * or / operator into the operator stack
            operatorStack.push(token.charAt(0));
         } 
         else if (token.trim().charAt(0) == '(')
         {
            operatorStack.push('('); // Push '(' to stack
         }
         else if (token.trim().charAt(0) == ')')
         {
            // Process all the operators in the stack until seeing '('
            while (operatorStack.peek() != '(')
            {
               processAnOperator(operandStack, operatorStack);
            }
            operatorStack.pop(); // Pop the '(' symbol from the stack
         } 
         else
         { 
            // Push an operand to the stack
            operandStack.push(Integer.valueOf(token));
         }
      }

      // Phase 2: process all the remaining operators in the stack
      while (!operatorStack.isEmpty())
      {
         processAnOperator(operandStack, operatorStack);
      }

      // Return the result
      return operandStack.pop();
   }

   private static void processAnOperator(Stack<Integer> operandStack, 
      Stack<Character> operatorStack)
   {
      char op = operatorStack.pop();
      int op1 = operandStack.pop();
      int op2 = operandStack.pop();
      if (op == '+')
         operandStack.push(op2 + op1);
      else if (op == '-')
         operandStack.push(op2 - op1);
      else if (op == '*')
         operandStack.push(op2 * op1);
      else if (op == '/')
         operandStack.push(op2 / op1);
   }

   private static String insertBlanks(String s)
   {
      String result = "";

      for (int i = 0; i < s.length(); i++)
      {
         if (s.charAt(i) == '(' || s.charAt(i) == ')' || s.charAt(i) == '+' || 
               s.charAt(i) == '-' || s.charAt(i) == '*' || s.charAt(i) == '/')
            result += " " + s.charAt(i) + " ";
         else
            result += s.charAt(i);
      }
      return result;
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
      private boolean cardError;
      
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
      private static String cardlValsConvertAssist = "A23456789TJQKX";
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
         return 0; 
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