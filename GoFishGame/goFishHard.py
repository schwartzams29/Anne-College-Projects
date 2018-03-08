#Anne Schwartz
#Final Project
#12/18/13
#goFishHard.py

from random import*
from graphics import*
from buttonclass import*
from playingCard import*
from deck import*
from time import *


#This program creates a simulation of a game of Go Fish by creating a
    #GoFish class.  The computer has a memory of 6 requests.

class GoFishHard:
    
    def __init__(self,win,cHand=[],pHand=[]):
        """Constructor that initializes instance variables.
        It also gives the playingDeck an initial shuffle."""
        self.pHand=[]
        self.cHand=[]
        self.requestRank=0
        self.cards=Deck()
        self.cards.shuffle()
        self.cHandDrawn=[]
        self.pHandDrawn=[]
        self.memory=[]
        self.mem0=[0]
        self.mem1=[0]
        self.mem2=[0]
        self.mem3=[0]
        self.mem4=[0]
        self.mem5=[0]
        self.requestButton=Button(win,Point(640,430),50,30,"Request")
        self.drawButton=Button(win,Point(550,300),50,30,"Draw")
        self.drawButton.deactivate()
        self.scoreC=0
        self.scoreP=0
        self.playerRequest=Entry(Point(570,430),7)
        self.playerRequest.draw(win)
        self.ranksInHand=[]
        self.options=[]
        self.goFishButton=Button(win,Point(550,200),50,30,"Go Fish")
        self.goFishButton.deactivate()
        self.passCardsButton=Button(win,Point(450,200),50,30,"Yes!")
        self.passCardsButton.deactivate()
        self.compScore=Text(Point(910,150),"0")
        self.compScore.draw(win)
        self.playerScore=Text(Point(890,450),"0")
        self.playerScore.draw(win)
        self.validEntries=[str(1),str(2),str(3),str(4),str(5),str(6),str(7),str(8),str(9),str(10),str(11),str(12),str(13)]
        self.checkEnd=False
        self.drawDeck = Image(Point(470,300), "playingcards/b2fv.gif")
        self.drawDeck.draw(win)

        
        
        
    
        
    def initDeal(self, gwin, xposC, yposC):
        """Deals out initial 8 cards to the player and 8 to the computer
        and displays player's hand on a graphical win face up and the computer's
        cards face down.
        xposC and yposC give initial position for computer cards
        xposP and yposP are analogous"""

        cRankShown = 0
        self.cRankList=[]
        #deal 7 cards face down for computer player
        for i in range (7):
            dealtCardC=self.cards.dealCard()
            self.cHand.append(dealtCardC)
        
            cRank=dealtCardC.getRank()
            cSuit=dealtCardC.getSuit()

            self.cRankList.append(cRank)
            
        self.pRankList=[]
        #deal 7 initial cards for player
        for i in range (7):
            dealtCardP=self.cards.dealCard()
            self.pHand.append(dealtCardP)
  
            
            pRank=dealtCardP.getRank()
            pSuit=dealtCardP.getSuit()
            self.pRankList.append(pRank)
            
        return self.cHand, self.pHand,xposC
    
 
    def checkForPointsC(self,win):
        """Checks to see if the computer has 4 of the same rank"""
        compFreq={}
        rankList=[]
        
        for card in self.cHand:
            rank=card.getRank()
            rankList.append(rank)
        for rank in rankList:
            compFreq[rank]=compFreq.get(rank,0)+1
        freqListC=list(compFreq.items())
        freqListC.sort()

        
        numC=len(freqListC)
        checkC=False
        for i in range (numC):
            numberOfRank=freqListC[i][1]
            #remove cards of that rank from cHand if 4 of that rank
            if numberOfRank==4:
                rank=freqListC[i][0]

                checkC=True
       
        if checkC==True:
            self.scoreC=self.scoreC+1
            for card in self.cHand:
                if card.getRank()==rank:
                    indexC=self.cHand.index(card)
            self.cHand.pop(indexC)
            self.cHand.pop(indexC-1)
            self.cHand.pop(indexC-2)
            self.cHand.pop(indexC-3)
                    
            self.orderC(win,50,50)
            self.compScore.undraw()
            self.compScore=Text(Point(910,150),self.scoreC)
            self.compScore.draw(win)
        checkC=False
        self.checkEnd=self.checkForEnd(win)
        return self.checkEnd
 
                            
    def checkForPointsP(self,win):
        """Checks to see if the player has 4 of the same rank"""

        playerFreq={}
        rankList=[]
        
        for card in self.pHand:
            rank=card.getRank()
            rankList.append(rank)
        for rank in rankList:
            playerFreq[rank]=playerFreq.get(rank,0)+1

        freqListP=list(playerFreq.items())
        freqListP.sort()

        numP=len(freqListP)
        checkP=False
        for i in range (numP):
            numberOfRank=freqListP[i][1]
            #remove cards of that rank from cHand if 4 of that rank
            if numberOfRank==4:
                rank=freqListP[i][0]
 
                checkP=True
        if checkP==True:
            self.scoreP=self.scoreP+1
            for card in self.pHand:
                if card.getRank()==rank:
                    indexP=self.pHand.index(card)
            
            self.pHand.pop(indexP)
            self.pHand.pop(indexP-1)
            self.pHand.pop(indexP-2)
            self.pHand.pop(indexP-3)
                   
            self.orderP(win,50,550)

            self.playerScore.undraw()
            self.playerScore=Text(Point(890,450),self.scoreP)
            self.playerScore.draw(win)
        if len(self.cHand)==0:
            self.goFishC(win)


        checkP=False
        self.checkEnd=self.checkForEnd(win)
        return self.checkEnd
            
   
    def byFreq(self,pair):
        return pair.getRank()
    
    def orderC(self,gwin,xposC, yposC):
        """Orders the computer's cards by the rank"""
        if len(self.cHand)==0:
            self.goFishC(gwin)
    
        
        num=len(self.cHand)
        for old in self.cHandDrawn:
            old.undraw()
        self.checkEnd=self.checkForEnd(gwin)
        for i in range (num):
            if self.checkEnd!=True:
                self.cHand.sort(key=self.byFreq)
                    
                cCardName = "playingcards/"+str(self.cHand[i].getSuit())+str(self.cHand[i].getRank())+".gif"
                cCard=Image(Point(xposC,yposC),cCardName)
                cCardDraw = Image(Point(xposC, yposC), "playingcards/b2fv.gif")
                self.cHandDrawn.append(cCardDraw)
            
                cCardDraw.draw(gwin)
                xposC=xposC+40
                self.checkEnd = self.checkForEnd
            else:
                return self.checkEnd
 

        
        
            
    
    def orderP(self,gwin,xposP, yposP):
        """Orders the player's cards by the rank"""
        if len(self.pHand)==0:
            self.goFishP(gwin)
        
        num=len(self.pHand)
        for old in self.pHandDrawn:
            old.undraw()
        self.checkEnd=self.checkForEnd(gwin)
        for i in range (num):
            if self.checkForEnd(gwin)==False:
                self.pHand.sort(key=self.byFreq)
                
                pCardName = "playingcards/"+str(self.pHand[i].getSuit())+str(self.pHand[i].getRank())+".gif"
                pCard = Image(Point(xposP, yposP),pCardName)
                self.pHandDrawn.append(pCard)
                pCard.draw(gwin)
                xposP = xposP + 40
            else:
                return self.checkEnd
        
    

    def requestRankConversion(self,request):
        """Converts player's entry to rank number if entered letter for face card."""
 
        if request=="A" or request=="a":
            request=str(1)
        elif request=="J" or request=="j":
            request=str(11)
        elif request=="Q"or request=="q":
            request=str(12)
        elif request=="K" or request=="k":
            request=str(13)
        else:
            request=request

        return request
        
    def pTurn(self,win):
        """Takes the player's request and finds occurances of that rank in the
         computer's hand.  It then removes the card from the computer's hand and
         passes it the the player's hand."""
        
        self.requestButton.activate()
        pt=win.getMouse()
        while self.requestButton.clicked(pt)!=True:
            pt=win.getMouse()
        
        pRankList=[]
       
        for card in self.pHand:
            rank=card.getRank()
            pRankList.append(rank)
            
        playerRequest=self.playerRequest.getText()
        self.requestRank=self.requestRankConversion(playerRequest)

        #check for valid entry
        if playerRequest=="":
            warning=Text(Point(300,300),"Please enter a rank and then click anywhere\n to continue!")
            warning.draw(win)
            win.getMouse()
 
            warning.undraw()
            self.pTurn(win)
        elif self.requestRank not in self.validEntries:
            warn=Text(Point(300,300),"Invalid entry. Click anywhere to continue.")
            warn.draw(win)
            win.getMouse()
 
            warn.undraw()
            self.pTurn(win)
            
 
        #only allow player to request rank if they have a card with that suit
        elif int(self.requestRank) not in pRankList and self.requestRank in self.validEntries:
            warn=Text(Point(300,300),"Please only enter a rank if you have a card\n "
                      "in your hand with that rank! Click anywhere to continue.")
            warn.draw(win)
            win.getMouse()
 
            warn.undraw()
            self.pTurn(win)
        
            
        else:
            indexC=[]
            self.memoryHard()
            cardstoPass=[]

            #if rank in hand
            for card in self.cHand:
                
                if card.getRank()==int(self.requestRank):
                    #pass card to player
                    self.pHand.append(card)
                    cardstoPass.append(card)

                    indexC.append(self.cHand.index(card))
            num=len(indexC)
            xPos=50
            yPos=150
         
            if num>0:
                index1=indexC[0]
                #remove the cards with the rank from the computer
                for i in range (num):
                    self.cHand.pop(index1)
                cardImageList=[]
                #draw cards that computer is passing to player before adding to hand
                for card in cardstoPass:
                    cardName = "playingcards/"+str(card.getSuit())+str(card.getRank())+".gif"
                    cardImage = Image(Point(xPos, yPos),cardName)
                    cardImageList.append(cardImage)
                    cardImage.draw(win)
                    xPos = xPos + 40
                sleep(.7)
                #undraw display of cards and add to player's hand
                for card in cardImageList:
                    card.undraw()

                self.orderC(win,50,50)
                self.orderP(win,50,550)
                self.checkForPointsP(win)
                if self.checkEnd!=True:
                    self.pTurn(win)
                    return self.checkEnd
                else:
                    return self.checkEnd
                
                
                
            else:
                self.drawButton.activate()
                inst=Text(Point(470,300),"GoFish!")
                inst.setSize(20)
                inst.setStyle("bold")
                inst.setTextColor("dark blue")
                inst.draw(win)
                pt=win.getMouse()
                while self.drawButton.clicked(pt)!=True:
                    pt=win.getMouse()
                inst.undraw()
                self.goFishP(win)
            
            self.requestButton.deactivate()
            self.checkForPointsP(win)
            return self.checkEnd

    def memoryHard(self):
        """Remembers the previous 6 requests of the player."""
        temp2=self.mem1
        temp3=self.mem2
        temp4=self.mem3
        temp5=self.mem4
        self.mem1=self.mem0
        self.mem2=temp2
        self.mem3=temp3
        self.mem4=temp4
        self.mem5=temp5
        self.mem0=int(self.requestRank)
        self.memory=[self.mem0,self.mem1,self.mem2,self.mem3,self.mem4,self.mem5]
        print(self.memory)
        return self.memory
    
    def ranksFirstTime(self):
        """Creates list of ranks in hand and a list of the cards in the computer's
        hand which are in the memory, for the first request of the computer
        for each time it's the computer's turn."""

        self.ranksInHand=[]
        self.options=[]
        for card in self.cHand:
            rank=card.getRank()
            if rank not in self.ranksInHand:
               self.ranksInHand.append(rank)
            if rank in self.memory and rank not in self.options:
                self.options.append(rank)
        return self.ranksInHand, self.options
        
        

    def cTurn(self, win):
        """Uses the memory to simulate the computer's turn."""
         
        self.passCardsButton.activate()
        self.goFishButton.activate()
        lengthOpt=len(self.options)
        lengthRanks=len(self.ranksInHand)
        #if computer has card with rank that matches a rank in the memory
        if lengthOpt>0:
            requestIndex=randrange(0,lengthOpt)
            request=self.options[requestIndex]
            #replace request in memory with zero so won't continue to ask for it
            while request in self.memory:
                indexMemoryRequest=self.memory.index(request)
                if indexMemoryRequest==0:
                    self.mem0="0"
                elif indexMemoryRequest==1:
                    self.mem1="0"
                elif indexMemoryRequest==2:
                    self.mem2="0"
                elif indexMemoryRequest==3:
                    self.mem3="0"
                elif indexMemoryRequest==4:
                    self.mem4="0"
                elif indexMemoryRequest==5:
                    self.mem5="0"
                self.memory=[self.mem0,self.mem1,self.mem2,self.mem3,self.mem4,self.mem5]
           
            #remove card from options so computer won't ask for the same card in one turn
            self.options.remove(request)
            #only allow computer to request same suit in one turn if no others to choose
            if len(self.ranksInHand)!=1:
                self.ranksInHand.remove(request)

        elif lengthRanks>0:
            requestIndex=randrange(0,lengthRanks)
            request=self.ranksInHand[requestIndex]
            #if other cards to choose from
            if len(self.ranksInHand)!=1:
                #remove card so computer won't ask for the same card in one turn
                self.ranksInHand.remove(request)
        #convert to name so user friendly
        if request==1:
            requestName="Ace"
        elif request==11:
            requestName="Jack"
        elif request==12:
            requestName="Queen"
        elif request==13:
            requestName="King"
        else:
            requestName=request
      
        text=Text(Point(300,200),"Do you have any "+str(requestName)+"s")
        text.draw(win)
        pt=win.getMouse()

        while self.passCardsButton.clicked(pt)!=True and self.goFishButton.clicked(pt)!=True:
            pt=win.getMouse()
        if self.passCardsButton.clicked(pt)==True:
            indexP=[]
            cardstoPass=[]

            for card in self.pHand:
                if card.getRank()==int(request):
                    #pass card to player
                    self.cHand.append(card)
                    cardstoPass.append(card)

                    indexP.append(self.pHand.index(card))
            num=len(indexP)
            xPos=50
            yPos=450
            text.undraw()
        
            if num!=0:
                index1=indexP[0]
                #remove the cards with the rank from the computer
                for i in range (num):
                    
                    self.pHand.pop(index1)
                cardImageList=[]
                #draw cards that computer is passing to player before adding to hand
                for card in cardstoPass:
                    cardName = "playingcards/"+str(card.getSuit())+str(card.getRank())+".gif"
                    cardImage = Image(Point(xPos, yPos),cardName)
                    cardImageList.append(cardImage)
                    cardImage.draw(win)
                    xPos = xPos + 40
                sleep(.7)
                #undraw display of cards and add to player's hand
                for card in cardImageList:
                    card.undraw()

                self.checkEnd=self.orderC(win,50,50)
                self.checkEnd=self.orderP(win,50,550)
                self.checkEnd=self.checkForPointsC(win)
                if self.checkForEnd(win)!=True:
                    self.cTurn(win)
                    return self.checkEnd
                else:
                    return self.checkEnd
            else:
                warning=Text(Point(300,300),"You do not have any cards with that rank.\n"
                             "The computer gets to draw a card from the deck.")
                warning.draw(win)
                sleep(4)
                self.goFishC(win)
                warning.undraw()
                
  
        elif self.goFishButton.clicked(pt)==True:
            self.goFishC(win)
            text.undraw()
        self.checkEnd=self.checkForPointsC(win)        
        self.passCardsButton.deactivate()
        self.goFishButton.deactivate()
        self.checkEnd=self.checkForEnd(win)
        return self.checkEnd
        

            


    def goFishP(self,win):
        """Adds a card from the deck to the player's hand."""
 
        draw=self.cards.dealCard()
        self.pHand.append(draw)
        cardName = "playingcards/"+str(draw.getSuit())+str(draw.getRank())+".gif"
        card = Image(Point(350,300),cardName)
        card.draw(win)
        
        sleep(2)
        card.undraw()
        
        self.orderP(win,50,550)
        self.drawButton.deactivate()
        self.checkEnd=self.checkForEnd(win)
        return self.checkEnd
        
    def goFishC(self,win):
        """Adds a card from the deck to the computer's hand."""

        draw=self.cards.dealCard()
        self.cHand.append(draw)
        self.orderC(win,50,50)
        self.checkEnd=self.checkForEnd(win)
        return self.checkEnd

    def checkForEnd(self,gwin):
        """Checks to see if the game has ended.  The game will end when
        the draw deck, computer's hand or the player's hand is empty."""
        #if draw deck empty, game ends
        if self.cards.cardsLeft()==0:
            end=Text(Point(500,300),"Game Over!")
            end.setStyle("bold")
            end.setSize(25)
            end.draw(gwin)
            self.goFishButton.deactivate()
            self.requestButton.deactivate()
            self.drawButton.deactivate()
            self.passCardsButton.deactivate()
            self.drawDeck.undraw()
            #check who won
            if self.scoreP>self.scoreC:
                winner=Text(Point(500,360),"Congratualtions!!  You win!")
                winner.setTextColor("Dark Blue")
                winner.setSize(25)
                winner.draw(gwin)
                return True
            elif self.scoreC>self.scoreP:
                winner=Text(Point(500,360),"The computer won.  Better luck next time!")
                winner.setTextColor("HotPink")
                winner.setSize(25)
                winner.draw(gwin)
                return True
            elif self.scoreC==self.scoreP:
                winner=Text(Point(500,360),"It's a tie!!")
                winner.setSize(25)
                winner.draw(gwin)
                return True
            
        #if a player's hand is empty, draw card from deck and continue
        elif len(self.pHand)==0:
            self.goFishP(gwin)
            return False
        elif len(self.cHand)==0:
            self.goFishC(gwin)
            return False
        else:
            return False


    def play(self,win):
        """Method to play a game of goFish."""
        compScore=Text(Point(850,150),"Computer's Score: ")
        compScore.draw(win)
        playerScore=Text(Point(850,450),"Your Score: ")
        playerScore.draw(win)
 
        label1=Text(Point(350,430),"Enter the rank of the card you want to ask your opponent for \n"
                    "(ex. 2 if you would like cards that have a rank of 2, k if you would like kings):")
        label1.draw(win)
     

        self.initDeal(win,50,50)
        self.orderC(win,50,50)
        self.orderP(win,50,550)
        check=False
        while check!=True:
    
            check=self.pTurn(win)
            
            #create initial ranks and memory lists
            if check!=True:
                self.ranksFirstTime()
                check=self.cTurn(win)
  

