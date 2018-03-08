#Anne Schwartz
#12/18/13
#Final Project
#playGoFish.py

from goFishMedium import*
from buttonclass import*
from goFishEasy import*
from goFishHard import*

#creates the menu and calls the goFish classes

def main():
    menu=GraphWin("Go Fish Menu!",600,600)
    back=Rectangle(Point(0,0),Point(600,600))
    back.setFill("cyan")
    back.draw(menu)
    closeButton=Button(menu,Point(550,50),50,30,"Quit")
    title=Text(Point(18,10),"Go Fish")
    title.setFill("cornflower blue")
    title.setStyle("bold")
    title.setSize(30)
    title.draw(menu)
    #animated title
    for i in range(30):
        title.move(8.5,6)
        sleep(.1)


    instructions=Text(Point(300,350),"Go Fish Instructions: In this game, you will be playing\n"
                     "Go Fish against a computer opponent.  On your turn, you will be asked \n"
                     "to enter a chosen rank from the cards in your hand to ask your opponent for. \n"
                     "The objective of the game is to get as many full sets of 4 of card ranks.  \n"
                     "On your turn, if your opponent has the card asked for, the opponent will hand \n"
                     "over all the cards of that rank in it's hand.  If the opponent does not have any\n"
                     "cards of that rank, you will be instructed to 'Go Fish' in which case you must \n"
                     "draw a card from the draw pile.  Your opponent will then ask you for a rank and \n"
                     "you will have to pass the cards of that rank by clicking 'Yes!' or if you do not\n"
                     "have any cards with that rank, you will click 'Go Fish.'")
    instructions.draw(menu)
    label=Text(Point(300,500),"The different difficulties are based on the memory\n"
               "of the computer player.")
    label.draw(menu)
    

    
    easyButton=Button(menu,Point(200,450),50,30,"Easy")
    mediumButton=Button(menu,Point(300,450),50,30,"Medium")
    hardButton=Button(menu,Point(400,450),50,30,"Hard")
    pt=menu.getMouse()
    while closeButton.clicked(pt)!=True:
  
        if easyButton.clicked(pt)==True:
            win=GraphWin("Go Fish!",1000,600)
            quitButton=Button(win,Point(950,450),50,30,"Quit")
            quitButton.deactivate()
            game=GoFishEasy(win,[],[])
            game.play(win)
            #activate quit button at end of game
            quitButton.activate()
            while quitButton.clicked(pt)!=True:
                pt=win.getMouse()
            win.close()
             

        elif mediumButton.clicked(pt)==True:
            win=GraphWin("Go Fish!",1000,600)
            quitButton=Button(win,Point(950,450),50,30,"Quit")
            quitButton.deactivate()
            game=GoFishMedium(win,[],[])
            game.play(win)
            quitButton.activate()
            while quitButton.clicked(pt)!=True:
                pt=win.getMouse()
            win.close()
        elif hardButton.clicked(pt)==True:
            win=GraphWin("Go Fish!",1000,600)
            quitButton=Button(win,Point(950,450),50,30,"Quit")
            quitButton.deactivate()
            game=GoFishHard(win,[],[])
            game.play(win)
            quitButton.activate()
            while quitButton.clicked(pt)!=True:
                pt=win.getMouse()
            win.close()
        else:
            pt=menu.getMouse()
    menu.close()
        
main()
        
    
    
