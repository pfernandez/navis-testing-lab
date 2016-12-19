# Testing Lab Files

This repository is the Java version of the lab files. Git tags are used 
to mark various points in the development history.

## Instructions

You are to test-drive the development of a console-based game of 2-player blackjack where the computer
plays the dealer. You may *not* run the game (or even write a `main()`) until it has a complete, runnable specification. 

You must write runnable tests for every algorithm you write (anything with logic). You do not have
to write tests for library classes/functions (e.g. System.in can be assumed to read from the process's 
standard input), but *you must test any code that uses them*.

### Part 1 -- Basics of specification testing 

- Specifications must name and test all behaviors
- Generate enumerations (or equivalent) for Rank and Suit.
    - Define the value of Rank as the min scoring value of that rank, e.g. Ace = 1
    - What are the behaviors of these? What tests might you write?
- Generate a Card abstraction that holds rank/suit
    - Make sure Card is an immutable value class with `getRank()` and `getSuit()`.
    - Cards must have a functional equals(), hashCode(), and toString() (with spec)
- Generate a Deck abstraction
    - Methods: 
        - Constructor generates a deck in rank/suit order
        - `int size()` indicates how many cards remain
        - `Card dealCard()` removes the "top card" and returns it. Throws OutOfCardsError if no cards remain.
        - `void shuffle()` mutates the deck in place to randomize the location of cards
            - The shuffle algorithm requires some advanced thinking: how do you easily 
              prove your logic is correct without basing the tests on what you are specifically doing?
            - HINT: the tests for this require you take control of the randomness.
- Make a `Hand` (container of cards)
    - `String visibleHand(boolean hideBottom)` - Returns a string that represents current hand. The option indicates that the first card should be obscured.
    - `Set<Card> getCards()` - Returns cards as a set
    - `void addCard(Card)` 
    - `int size()` - returns the number of cards in the hand
- Create a `Blackjack` class, and add a single method: 
    - `int score(Set<Card> cards)` that can compute the score of a hand
    - Score should return the maximum score possible from the hand without (if possible) busting. In
      other words, an Ace should be 11 if that makes the score 21 or less, otherwise 1.
  
### Part 2 -- I/O Layer: integration/manual testing

Use your result from part 1 to continue:

- Create an I/O layer for obtaining console input and showing output: ConsoleIO
    - ConsoleIO interface
        - `String readLine()`
        - `void printf(String format, Object... args)`
    - Implement the interface (`SystemConsole`)
    - Write manual integration tests for `SystemConsole`
    
### Part 3 -- Injection and Mocking

- Create a `Dependencies` class
    - Add injection support for ConsoleIO, which should, by default, inject a singleton SystemConsole instance.
- Create a `String Prompt.prompt(String question, String legalResponsePattern, String defaultReturnValue)` function:
    - Empty (whitespace only) response (or IO exceptions) must return the default. 
      This prevents infinite loops if the console is closed.
    - legalResponsePattern is a REGEX
    - Re-asks question until legal response (if resp is non-empty)
    - Trims the user input (removes whitespace from endpoints)
    - Must be UNIT testable (*not* integration tested). Use injection and mocking.
- Improve your `Deck` and `Deck.shuffle()` tests, if you have not yet used injection there.

### Part 4 -- Round out algorithms

- Make a Player interface 
    - `Action nextAction(Hand otherPlayerHand)` - Returns enumerated value `Hit`, `Stay`, or `Busted` (based on known cards and those of the other player)
    - `Hand getHand();` - Returns the player's hand
- Implement and spec test a `HumanPlayer` that interacts with the console. 
    - Use injection and mocking to ensure easy testing
    - Do not do any form of integration testing here. 
- Implement and spec test a `BotPlayer` 
    - Hits if not won score is under 17 (or something simple and similar).
    - Do not do any form of integration testing here (e.g. you should *not* 
    actually do things like compute a score during testing).

### Part 5 -- Dealing with more complex logic.

- Create a `BlackjackGame` class with:
    - `play()`: the main logic of game
    - Cleanly code algorithms, and make each routine cleanly testable while
    staying at the appropriate abstraction level.

Discuss with classmates the various ways you go about splitting this up 
and making it all testable. Algorithms that have multiple steps and decisions can
make for complicated tests, which in turn indicate that you should redesign
your algorithm or otherwise take control of things that you're not intending to
run during testing.

### Part 5 -- Implement main() AND TEST it before running it:

- Main logic is:
    - Create necessary resources for the game
    - Inject a game
    - Play the game
- Write behavior tests for main()!

Now run your fully functioning game for the first time!

