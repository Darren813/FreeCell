package cs3500.hw02.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.MultiFreecellModel;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.card.ICard;
import cs3500.hw02.model.AFreecellModelTest;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the SimpleFreecellControllerTest class.
 */
public abstract class AFreecellControllerTest {

  private FreecellModel<ICard> model;
  private StringBuilder log;

  @Before
  public void initData() {
    this.model = makeModel();
    this.log = new StringBuilder();
  }

  public abstract FreecellModel<ICard> makeModel();

  /**
   * Represents a SimpleFreecellModel class for testing that is used in AFreecellmodelTest.
   */
  public static class SimpleFreecellModelTests extends AFreecellModelTest {

    @Override
    public FreecellModel<ICard> makeModel() {
      return new SimpleFreecellModel();
    }
  }

  /**
   * Represents a MultiFreecellModel class for testing that is used in AFreecellmodelTest.
   */
  public static class MultiFreecellModelTests extends AFreecellModelTest {

    @Override
    public FreecellModel<ICard> makeModel() {
      return new MultiFreecellModel();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullModel() {
    new SimpleFreecellController(null, new StringReader(""), new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullReadable() {
    new SimpleFreecellController(this.model, null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNullAppendable() {
    new SimpleFreecellController(this.model, new StringReader(""), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGameNullDeck() {
    new SimpleFreecellController(this.model, new StringReader("testing this out."), this.log)
        .playGame(null, 8, 4, false);
  }

  @Test
  public void playGameTooLittleCascades() {
    new SimpleFreecellController(this.model, new StringReader("testing this out."), this.log)
        .playGame(this.model.getDeck(), 3, 4, false);
    assertEquals("Could not start game.", this.log.toString());
  }

  @Test
  public void playGameTooManyCascades() {
    new SimpleFreecellController(this.model, new StringReader("testing this out."), this.log)
        .playGame(this.model.getDeck(), 53, 4, false);
    assertEquals("Could not start game.", this.log.toString());
  }

  @Test
  public void playGameTooLittleOpen() {
    new SimpleFreecellController(this.model, new StringReader("testing this out."), this.log)
        .playGame(this.model.getDeck(), 8, 0, false);
    assertEquals("Could not start game.", this.log.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void playGameRanOutOfInputs() {
    new SimpleFreecellController(this.model, new StringReader("8"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
  }

  // checks gameOver because game shouldn't be over if it returns when you write "q"
  @Test
  public void playGameRanOutOfInputsReturns() {
    try {
      new SimpleFreecellController(this.model, new StringReader("8"), this.log)
          .playGame(this.model.getDeck(), 8, 4, false);
    } catch (IllegalStateException e) {
      assertFalse(this.model.isGameOver());
    }
  }

  @Test
  public void playGameQAsSourcePileInput() {
    new SimpleFreecellController(this.model, new StringReader("q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playGameQAsCardIndexInput() {
    new SimpleFreecellController(this.model, new StringReader("c1 q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playGameQAsDestPileInput() {
    new SimpleFreecellController(this.model, new StringReader("c1 8 q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playGameAskForSourcePileAgain() {
    new SimpleFreecellController(this.model, new StringReader("8 8 8 q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Enter a valid source pile.\n"
        + "Enter a valid source pile.\n"
        + "Enter a valid source pile.\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playGameAskForIndexAgain() {
    new SimpleFreecellController(this.model, new StringReader("c1 f1 f1 q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Enter a valid number\n"
        + "Enter a valid number\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playGameAskForDestPileAgain() {
    new SimpleFreecellController(this.model, new StringReader("c1 8 8 q"), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Enter a valid destination pile.\n"
        + "Game quit prematurely.", this.log.toString());
  }

  @Test
  public void playWholeGameWithSomeInvalidMove() {
    StringBuilder allMoves = new StringBuilder();
    allMoves
        .append(" c10 10 f10")
        .append(" c1 7 f1").append(" c2 7 f2").append(" c3 7 f3").append(" c4 7 f4")
        .append(" c5 6 f1").append(" c6 6 f2").append(" c7 6 f3").append(" c8 6 f4")
        .append(" c1 6 f1").append(" c2 6 f2").append(" c3 6 f3").append(" c4 6 f4")
        .append(" c5 5 f1").append(" c6 5 f2").append(" c7 5 f3").append(" c8 5 f4")
        .append(" c1 5 f1").append(" c2 5 f2").append(" c3 5 f3").append(" c4 5 f4")
        .append(" c5 4 f1").append(" c6 4 f2").append(" c7 4 f3").append(" c8 4 f4")
        .append(" c1 4 f1").append(" c2 4 f2").append(" c3 4 f3").append(" c4 4 f4")
        .append(" c5 3 f1").append(" c6 3 f2").append(" c7 3 f3").append(" c8 3 f4")
        .append(" c1 3 f1").append(" c2 3 f2").append(" c3 3 f3").append(" c4 3 f4")
        .append(" c5 2 f1").append(" c6 2 f2").append(" c7 2 f3").append(" c8 2 f4")
        .append(" c1 2 f1").append(" c2 2 f2").append(" c3 2 f3").append(" c4 2 f4")
        .append(" c5 1 f1").append(" c6 1 f2").append(" c7 1 f3").append(" c8 1 f4")
        .append(" c1 1 f1").append(" c2 1 f2").append(" c3 1 f3").append(" c4 1 f4")
        .append(" c5 0 f1").append(" c6 0 f2").append(" c7 0 f3").append(" c8 0 f4")
        .append(" c1 0 f1").append(" c2 0 f2").append(" c3 0 f3").append(" c4 0 f4");
    new SimpleFreecellController(this.model, new StringReader(allMoves.toString()), this.log)
        .playGame(this.model.getDeck(), 8, 4, false);
    assertTrue(this.model.isGameOver());
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "Invalid move. Try again.\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠, A♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥, A♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠\n"
        + "F2: A♥\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦, A♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠\n"
        + "F2: A♥\n"
        + "F3: A♦\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣, A♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠\n"
        + "F2: A♥\n"
        + "F3: A♦\n"
        + "F4: A♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠, 2♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠, 2♠\n"
        + "F2: A♥\n"
        + "F3: A♦\n"
        + "F4: A♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥, 2♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠, 2♠\n"
        + "F2: A♥, 2♥\n"
        + "F3: A♦\n"
        + "F4: A♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦, 2♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠, 2♠\n"
        + "F2: A♥, 2♥\n"
        + "F3: A♦, 2♦\n"
        + "F4: A♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣, 2♣\n"
        + "F1: A♠, 2♠\n"
        + "F2: A♥, 2♥\n"
        + "F3: A♦, 2♦\n"
        + "F4: A♣, 2♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠, 3♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠\n"
        + "F2: A♥, 2♥\n"
        + "F3: A♦, 2♦\n"
        + "F4: A♣, 2♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥, 3♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠\n"
        + "F2: A♥, 2♥, 3♥\n"
        + "F3: A♦, 2♦\n"
        + "F4: A♣, 2♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦, 3♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠\n"
        + "F2: A♥, 2♥, 3♥\n"
        + "F3: A♦, 2♦, 3♦\n"
        + "F4: A♣, 2♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣, 3♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠\n"
        + "F2: A♥, 2♥, 3♥\n"
        + "F3: A♦, 2♦, 3♦\n"
        + "F4: A♣, 2♣, 3♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠, 4♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠\n"
        + "F2: A♥, 2♥, 3♥\n"
        + "F3: A♦, 2♦, 3♦\n"
        + "F4: A♣, 2♣, 3♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥, 4♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥\n"
        + "F3: A♦, 2♦, 3♦\n"
        + "F4: A♣, 2♣, 3♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦, 4♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦\n"
        + "F4: A♣, 2♣, 3♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣, 4♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠, 5♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥, 5♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦, 5♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣, 5♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠, 6♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥, 6♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦, 6♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣, 6♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠, 7♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥, 7♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦, 7♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣, 7♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠, 8♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥, 8♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦, 8♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣, 8♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠, 9♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥, 9♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦, 9♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣, 9♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠, 10♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥, 10♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦, 10♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣, 10♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠, J♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥, J♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦, J♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣, J♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5: Q♠\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6: Q♥\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7: Q♦\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8: Q♣\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: K♠\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8:\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1:\n"
        + "C2: K♥\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8:\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: K♦\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8:\n"
        + "F1: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3:\n"
        + "C4: K♣\n"
        + "C5:\n"
        + "C6:\n"
        + "C7:\n"
        + "C8:\n"
        + "Game over.", this.log.toString());
  }
}
