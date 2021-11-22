# This file is part of GiocoDellaPalla.

#     GiocoDellaPalla is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     (at your option) any later version.

#     GiocoDellaPalla is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.

#     You should have received a copy of the GNU General Public License
#     along with GiocoDellaPalla.  If not, see <http://www.gnu.org/licenses/>.

#     Copyright 2021, Davide Chiarabini, All rights reserved

#Importo I moduli
import os, sys, pygame, random
from pygame.locals import *
from pygame import *


import sys
####################################
#Inzizio Programma
os.environ['SDL_VIDEO_CENTERED'] = "1"
pygame.init()
pygame.display.set_caption("PallaPy")
icon = pygame.image.load("icona.png")
icon = pygame.display.set_icon(icon)
screen = pygame.display.set_mode((640,480))#,pygame.FULLSCREEN)
pygame.mouse.set_visible(0)
ballpic = image.load('palla.png')
ballx = 9	
bally = 6
ballxmove = 3
ballymove = 3
vita=5

####################################
#Sfondo
background = pygame.Surface(screen.get_size())
background = background.convert()
background.fill((0,0,0))
###################################    
#Sistema conteggio punti
        
class Score(pygame.sprite.Sprite):
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.vita = time.get_ticks()/1000
        self.font = pygame.font.Font(None, 25)
        
    def update(self):
        self.text = "Punteggio: %d" % (self.vita)
        self.image = self.font.render(self.text, 1, (255, 255, 0))
        self.rect = self.image.get_rect()
        self.rect.center = (320,20)


        
###################################################################        
#Gioco
def game():
    ballx = 9	
    bally = 6
    ballxmove = 3
    ballymove = 3
    vita=5
    global score
    score = Score()
    scoreSprite = pygame.sprite.Group(score)
    clock = pygame.time.Clock()
    keepGoing = True
    counter = 0
    #Main Loop
    while keepGoing:
        clock.tick(30)
        screen.fill(0)	
        screen.blit(ballpic, (ballx, bally))	
        scoreSprite.update()
        scoreSprite.draw(screen)
        pygame.display.flip()
        ballx = ballx + ballxmove	
        bally = bally + ballymove
        if score.vita ==-10:
            sys.exit()
        if ballx > 600:
            ballxmove = -3
            score.vita += -10 
        if ballx < 0:
            ballxmove = 3
            score.vita += -10 
        if bally > 440:
            ballymove = -3
            score.vita += -10 
        if bally < 0:
            ballymove = 3
            score.vita += -10 
        if screen.get_at((mouse.get_pos())) == (255, 255, 255, 255):
            done=True

        for e in event.get():
            if e.type == KEYUP:
                if e.key == K_RIGHT:
                    ballxmove = 3
                    score.vita += +10 
                if e.key == K_LEFT :
                    ballxmove = -3
                    score.vita += +10 
                if e.key == K_ESCAPE :
                    sys.exit()
                if e.key == K_UP  :
                    score.vita += +10  
                    ballymove = -3
                if e.key == K_DOWN:
                    ballymove  = 3
                    score.vita += +10  

if __name__ == "__main__":
    game()
