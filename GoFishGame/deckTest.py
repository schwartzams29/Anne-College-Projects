#Anne Schwartz 
#Final Project
#11/8/13
#deck.py

from random import*
from graphics import*
from buttonclass import*
from playingCard import*

#This program creates a Deck class which contains 52 cards and methods to shuffle
    #and deal the cards.

class Deck:
    def __init__(self):
        """Create a deck of 52 cards in a standard order."""
        self.cardList=[]
        for s in ("d","c","h","s"):
            for r in range (1,7):
                self.cardList.append(PlayingCard(r,s))


    def shuffle(self):
        """Randomizes the order of the cards."""
        for i in range (24):
            card1=self.cardList[i]
            x=randrange(0,24)
            card2=self.cardList[x]

            temp=card1
            card1=card2
            card2=temp
            self.cardList[i]=card1
            self.cardList[x]=card2

    
        
    def dealCard(self):
        """Returns a single card from the top of the deck and removes the card
        from the deck."""

        cardDelt=self.cardList.pop(0)

        return(cardDelt)
        #print(cardDelt)
        
        

    def cardsLeft(self):
        """Returns the number of cards remaining in the deck."""
        sizeDeck=len(self.cardList)
        return sizeDeck
        
def main():
    n=eval(input("Please enter the number of cards: "))
    deck=Deck()
    deck.shuffle()

    for i in range (n):
        print(deck.dealCard())
        print(deck.cardsLeft())
        
   
if __name__ == "__main__":
    main()
