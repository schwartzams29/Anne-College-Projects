#Anne Schwartz 
#Final Project
#12/18/13
#playingCard.py


#This program creates the PlayingCard class which has a suit and rank.

from random import*
from graphics import*
from buttonclass import*

class PlayingCard:
    """A PlayingCard contains a suit and rank."""

    def __init__(self, rank, suit):
        """Rank is an int in the range 1-13 indicating the ranks of Ace-King,
        and suit is a single character "d," "c," "h," or "s" inidcating the suit
         (diamonds, clubs, hearts, or spades).  Create the corresponding card."""

        self.rank=rank
        self.suit=suit
           

    def getRank(self):
        """Returns the rank of the card."""
        return self.rank
    
    def getSuit(self):
        """Returns the suit of the card."""
        return self.suit

    def BJValue(self):
        """Returns the Blackjack value of a card.  Ace counts as 1, face cards
        counts as 10."""
        cardNum=self.getRank()
        if 1<=cardNum<=10:
            value=cardNum
        else:
            value=10

        return (value)

    def __str__(self):
        """Returns a string that names the card.  For example, "Ace of Spades"."""
     
        rankList = ["Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"]
        suitDict = {"d":"Diamonds", "c":"Clubs", "h":"Hearts", "s":"Spades"}
    
                         
        rank = rankList[(self.rank)-1]
        suit = suitDict[self.suit]
        CardType = str(rank)+" of "+str(suit)
        return CardType

            
def main():
    n=eval(input("Please enter the number of cards: "))
    for i in range (n):
        rank=randrange(1,14)    
        suits=["d","c","h","s"]
        suit=suits[randrange(0,4)]
        card=PlayingCard(rank,suit)
        print(card)
        

    
if __name__ == "__main__":
    main()

