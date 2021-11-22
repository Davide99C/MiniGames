// This file is part of TestaOCroce.

//     TestaOCroce is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     TestaOCroce is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with TestaOCroce.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved.

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

#define TESTA "testa"
#define CROCE "croce"

//IMMAGINI DELLA MONETA
void MonetaTesta() {
    printf("\n\tTESTA\t\n");
    printf("     _________\n   /           \\\n  /   /|   __   \\\n |   / |  /      |\n |     | |---    |\n |     | |---    |\n  \\   _|_ \\__   /\n   \\ _________ /\n");
    
    return;
}
void MonetaCroce() {
    printf("\n\tCROCE\t\n");
    printf("     _________\n   /           \\\n  /   \\   /     \\\n |     \\O/       |\n |      ||       |\n |      /\\       |\n  \\    /  \\     /\n   \\ _________ /\n");
    
    return;
}

//MODALITÀ GIOCATORE SINGOLO (1 VS PC)
void Gioca1() {
    char* scelta = (char*)malloc(sizeof(char)*5);
    int val;
    L:
    printf("Scrivi la facciata della moneta (testa o croce): ");
    scanf("%s",scelta);
    if (strcmp(scelta,TESTA) == 0) { 
        MonetaTesta();
        val=1;
    }
    else if (strcmp(scelta,CROCE) == 0) {
     MonetaCroce();
     val=0;
    }
    else {
        printf("\nFacciata della moneta non esistente!\n");
        return;
    }
    printf("\nOK, hai scelto %s", scelta);
    printf("\nInserisci 1 per lanciare la moneta, altro per terminare o cambiare la tua scelta: ");
    int lancio;
    scanf("%d", &lancio);
    if (lancio != 1) {
        char* dubbio;
        R:
        printf("\nVuoi cambiare facciata della moneta? (rispondi si o no): ");
        scanf("%s",dubbio);
        if (strcmp(dubbio,"si") == 0) goto L;
        else if (strcmp(dubbio,"no") == 0) {
            printf("\n");
            return;
        }
        else {
            printf("\nRisposta non valida, riprova...");
            goto R;
        }
    }
    
    E:
    printf("\nHai lanciato la moneta...");
    
    srand( time(NULL) );
    int ris = rand()%2;

    if (ris == 1) { 
        MonetaTesta();
        printf("\nE' uscita testa!");
    }
    else if (ris == 0) {
        MonetaCroce();
        printf("\nE' uscita croce!");
    }
    else {
        printf("\nErrore... rilancio la moneta");
        goto E;
    }
    if (val == ris) printf("\nHAI VINTO! :)\n");
    else printf("\nHAI PERSO! :(\n");
    
    return;
    
}

//MODALITÀ 2 GIOCATORI (GIOCATORE1 VS GIOCATORE2)
void Gioca2() {

    char* scelta = (char*)malloc(sizeof(char)*5);
    int val; 
    int giocatore;
    char* giocatore1 = (char*)malloc(sizeof(char*));
    char* giocatore2 = (char*)malloc(sizeof(char*));
    char* primo = (char*)malloc(sizeof(char*));
    char* secondo = (char*)malloc(sizeof(char*));

    printf("Inserisci il nome del giocatore1: ");
    scanf("%s",giocatore1);
    printf("Inserisci il nome del giocatore2: ");
    scanf("%s",giocatore2);

    srand( time(NULL) );
    giocatore = (rand()%2)+1;
    if (giocatore == 1) {
        strcpy(primo,giocatore1);
        strcpy(secondo,giocatore2);
    }
    else if (giocatore == 2) {
        strcpy(primo,giocatore2);
        strcpy(secondo,giocatore1);
    }

    printf("\nSceglie per primo/a %s\n", primo);

    L:  
    printf("Scrivi la facciata della moneta (testa o croce): ");
    scanf("%s",scelta);
    if (strcmp(scelta,TESTA) == 0) val=1;
    else if (strcmp(scelta,CROCE) == 0) val=0;
    else {
        printf("\nFacciata della moneta non esistente!\n");
        goto L;
    }

    printf("OK, %s ha scelto %s",primo,scelta);
   
    printf("\nQuindi %s avrà %s", secondo, strcmp(scelta,TESTA) == 0 ? CROCE : TESTA);

    printf("\nInserisci 1 per lanciare la moneta, altro per terminare: ");
    int lancio;
    scanf("%d", &lancio);
    if (lancio != 1) return;

    E:
    printf("Hai lanciato la moneta...");
    srand( time(NULL) );
    int ris = rand()%2;
    if (ris == 1) {
        MonetaTesta();
        printf("\nE' uscita testa!");
    }
    else if (ris == 0) {
        MonetaCroce();
        printf("\nE' uscita croce!");
    }
    else {
        printf("\nErrore... rilancio la moneta");
        goto E;
    }
    if (val == ris) printf("\n%s HA VINTO! :)\n", primo);
    else printf("\n%s HA VINTO! :) \n", secondo);
    
    return;
}


//SCEGLI LA MODALITÀ DI GIOCO
int main(int argc, void** args) {
    printf("\nScegli la modalità di gioco:\n 1: giocatore vs PC \n 2: giocatore1 vs giocatore2\n 3: termina programma\n");
    int res;
    scanf("%d",&res);
    if (res==1) Gioca1();
    else if (res==2) Gioca2();
    else return 0;
}