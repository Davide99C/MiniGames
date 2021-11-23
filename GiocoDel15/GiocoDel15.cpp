#include "header.h"

int TABELLA[4][4] = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,(int)NULL}};

void printTab(int** tabella) {
    int i,j;
    for (i = 0; i<4; i++) {
        cout << "[  ";
        for (j = 0; j<4; j++) {
            if (tabella[i][j] == 0) cout << "X  ";
            else cout << tabella[i][j] << "  ";
            
        }
        cout << " ]" << endl;
    }
}

int** initTab() {
    int i;
    int** tab = (int**)malloc(sizeof(int*)*4);
    for (i = 0; i<4; i++) {
        tab[i] = (int*)malloc(sizeof(int)*4);
        memcpy(tab[i],TABELLA[i],sizeof(int)*4);
    }
    return tab;
}

void dissortTab(int** tabella) {
    int i,j,elem,k,h,u=0;
    time_t t;
    bool trovato = false;
    // setto la tabella con tutti gli elementi a -1 
    for (i = 0; i<4; i++) {
        for (j = 0; j<4; j++) {
            if (tabella[i][j] != (int)NULL ) tabella[i][j] = -1;
        }
    }
    //printTab(tabella);
    // riempio la tabella con numeri da 1 a 15 in modo disordinato
    srand((unsigned) time(&t));
    for (i = 0; i<4; i++) {
        for (j = 0; j<4; j++) {
            if (tabella[i][j] != (int)NULL) {
                E:  elem = (rand()%15)+1;
                    for (k = 0; k<4; k++) {
                        for (h = 0; h<4; h++) {
                            if (tabella[k][h] == elem) {
                                trovato = true;
                                goto E;
                            }
                        }
                    }
                tabella[i][j] = elem; 
            }
        }
    }
}

bool isSorted(int** tabella) {
    int i,j;
    for (i = 0; i<4; i++) {
        for (j = 0; j<3; j++) {
            if (tabella[i][j] > tabella[i][j+1] && tabella[i][j+1] != (int)NULL) return false;
        }
    }
    return true;
}

void sortTab(int** tabella) {
    int i;
    for (i = 0; i<4; i++) {
        memcpy(tabella[i],TABELLA[i],sizeof(int*)*4);
    }
}

int main(int argc, char** args) {
    int i,j,elem;
    bool valido;

    //INIZIALIZZAZIONE DELLA TABELLA
    int** tabella = initTab();
    cout << "<---- INIZIALIZZAZIONE TABELLA (ordinata) ---->" << endl;
    printTab(tabella);
    //MESCOLAMENTO DEI NUMERI
    cout << "Disordino la tabella..." << endl;
    dissortTab(tabella);
    printTab(tabella);
    
    // INIZIO DEL GIOCO
    cout << "<---- INZIO GIOCO ---->" << endl;
    cout << "RICORDA: lo scambio può effettuarsi solo se la casella vuota (indicata con una X)\nè contigua (vicina in verticale o orizzontale ma non in obliquo) alla casella da muovere!" << endl;
    int pos1[2];
    int pos2[2];
    while(!isSorted(tabella)) {
        valido = false;

        //PRENDO LE COORDINATE DELLA CASELLA VUOTA (X)
        for (i = 0; i<4; i++) {
            for (j = 0; j<4; j++) {
                if (tabella[i][j] == (int)NULL) {
                    pos2[0] = i;
                    pos2[1] = j;
                }
            }
        }

        //L'UTENTE INDICA I DUE ELEMENTI DA SCAMBIARE
        S:
        cout << "Inserisci la riga: ";
        scanf("%d", &pos1[0]);
        cout << "Inserisci la colonna: ";
        scanf("%d", &pos1[1]);
        pos1[0]-=1;
        pos1[1]-=1;
        
        //VERIFICA DELLA VALIDITÀ DELLO SCAMBIO
        if ( (pos1[0] == pos2[0] && ((pos1[1]+1 == pos2[1]) || (pos1[1]-1 == pos2[1]))) ||
             (pos1[1] == pos2[1] && ((pos1[0]+1 == pos2[1]) || (pos1[0]-1 == pos2[1]))) ||
             (pos1[1] == pos2[1] && pos1[1] == pos2[1] && ((pos2[0]+1 == pos1[0]) || (pos2[0]-1 == pos1[0]))) ) {
               valido = true;  
             }
      
        //EFFETTUO LO SCAMBIO
        if (valido) {
            elem = tabella[pos1[0]][pos1[1]];
            tabella[pos1[0]][pos1[1]] = tabella[pos2[0]][pos2[1]];
            tabella[pos2[0]][pos2[1]] = elem;
            printTab(tabella);
        }
        else {
            cout << "Scambio non valido, inserisci riga e colonna adiacente alla casella vuota..." << endl;
            goto S; 
        }

    }

    cout << "COMPLIMENTI, HAI VINTO! :)" << endl;

    return 1;
}