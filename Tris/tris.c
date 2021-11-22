// This file is part of Tris.

//     Tris is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     Tris is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with Tris.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved.

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

char** tabella;

char** initTabella() {
    char** tabella = (char**)malloc(3*sizeof(char*));
    for (int i=0; i<3; i++) {
        tabella[i] = (char*)malloc(3*sizeof(char));
    }
    for (int j=0;j<3;j++) {
        for (int h=0;h<3;h++) {
            tabella[j][h] = '?';
        }
    }
    return tabella;
}

void printTabella(char** tabella) {
    for (int j=0;j<3;j++) {
        printf("|");
        for (int h=0;h<3;h++) {
            printf("%c|",tabella[j][h]);
        }
        printf("\n");
    }
}

int controlloRes(char giocatore, char computer) {
    int res = 0;

    for (int i=0; i<3; i++) {  //controllo vincita sulle righe
        if (tabella[i][0]+tabella[i][1]- 2*tabella[i][2] == 0) {
            if (tabella[i][0] == giocatore) res = 1;
            else if  (tabella[i][0] == computer) res = 2;
        }
    }
    for (int j=0; j<3; j++) { //cotrollo vincita sulle colonne
        if (tabella[0][j]+tabella[1][j]- 2*tabella[2][j] == 0) {
            if (tabella[0][j] == giocatore) res = 1;
            else if  (tabella[0][j] == computer) res = 2;
        }
    }
    if (tabella[0][0]+tabella[1][1]-2*tabella[2][2]==0) {
        if (tabella[1][1] == giocatore) res = 1;
        if (tabella[1][1] == computer) res = 2;
    }
    if (tabella[0][2]+tabella[1][1]-2*tabella[2][0]==0) {
        if (tabella[1][1] == giocatore) res = 1;
        if (tabella[1][1] == computer) res = 2;
    }

    if (res == 1) return 1;
    else if (res == 2) return 2;
    else {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (tabella[i][j] == '?') return 3;
            }
        }   
    }
    return 0;
    
}

void giocaTris(char giocatore, char computer) {
    bool corretto = false;
    int fine;
    printf("Inizi tu...\n");

    for (int i=0; i<5; i++) {
        int riga,colonna;

        // mossa dell'utente:
        printf("Fai la tua mossa!\n");
        L1: corretto = false;
        while(!corretto) {
            printf("Inserisci la riga (1,2,3): ");
            scanf("%d",&riga);
            if (riga < 1 || riga > 3) printf("Riga inserita non valida\n");
            else corretto = true;
        }

        corretto = false;
        while(!corretto) {
            printf("Inserisci la colonna (1,2,3): ");
            scanf("%d",&colonna);
            if (colonna < 1 || colonna > 3) printf("Colonna inserita non valida\n");
            else corretto = true;
        }

        if (tabella[riga-1][colonna-1] == '?') tabella[riga-1][colonna-1] = giocatore;
        else {
            printf("Casella gia occupata, riprova\n");
            goto L1;
        }

        printTabella(tabella);
        fine = controlloRes(giocatore,computer);
        if (fine == 1) {
            printf("Hai vinto!\n");
            break;
        }
        else if (fine == 0) {
            printf("Pareggio!\n");
            break;
        }
        
        
        //mossa del computer:
        if (i<4) {
            L2: riga = rand()%3;
            colonna = rand()%3;
            if (tabella[riga][colonna] == '?') tabella[riga][colonna] = computer;
            else goto L2;

            printf("Mossa del computer...\n");
            printTabella(tabella);
            fine = controlloRes(giocatore,computer);
            
            if (fine == 2) {
                printf("Hai perso :(\n");
                break;
            }
        }
    }
    
}

int main( int argc, char** argv) {

    tabella = initTabella();
    printTabella(tabella);

    char simbolo;
    char avversario;
    printf("Scegli un simbolo tra O e X:\n");
    scanf("%c", &simbolo);
    
    if (simbolo == 'X') avversario = 'O';
    else if (simbolo == 'O') avversario = 'X';
    else {
        printf("Simbolo non valido!\n");
        return 0;
    }
    printf("Hai scelto il simbolo %c quindi il tuo avversario giocherà con %c\n", simbolo, avversario);

    giocaTris(simbolo,avversario);
    //controlloRes(simbolo, avversario);

    return 1;
}