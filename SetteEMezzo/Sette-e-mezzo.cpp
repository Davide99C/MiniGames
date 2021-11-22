// This file is part of SetteEMezzo.

//     SetteEMezzo is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     SetteEMezzo is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with SetteEMezzo.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved.

#include "header.h"

//array<double,40> MAZZO = {1.0,1.0,1.0,1.0,2.0,2.0,2.0,2.0,3.0,3.0,3.0,3.0,4.0,4.0,4.0,4.0,5.0,5.0,5.0,5.0,6.0,6.0,6.0,6.0,7.0,7.0,7.0,7.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};
double MAZZO[] = {1.0,1.0,1.0,1.0,2.0,2.0,2.0,2.0,3.0,3.0,3.0,3.0,4.0,4.0,4.0,4.0,5.0,5.0,5.0,5.0,6.0,6.0,6.0,6.0,7.0,7.0,7.0,7.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5};

void print_mazzo(int len, double* mazzo) {
    int i, j = 0;
    cout << "\nLunghezza mazzo:" << len << endl;
    cout << "Carte disponibili:" << endl; 
    for (i=0;i<len;i++) {
        if ((i+1) == len) {
            printf("%0.1f ", mazzo[i]);
            break;
        }
        else if (mazzo[i]!=mazzo[i+1] || j==3) {
            printf("%0.1f\n", mazzo[i]);
            j=0;
        } 
        else {
            printf("%0.1f ", mazzo[i]);
            j++;
        }

    }
    cout << endl;
}

double* remove(double* mazzo, double to_remove, int len) {
    int i;
    for (i=to_remove; i<len-1; i++) {
        mazzo[i] = mazzo[i+1]; 
    }
    return mazzo;
}

int main(int argc, char** args) {
    double punto_pc, punto_giocatore, punto_scoperto = 0, nuova_carta; // double punto_pc_scoperto = 0;
    int indice_carta_mazzo;
    time_t t;
    int len = 40;

    //mazzo dal quale sottrarrò le carte estratte 
    double* mazzo = (double*)malloc(sizeof(double)*len);  //array<double,40> mazzo = MAZZO;
    memcpy(mazzo,MAZZO,sizeof(double)*len);
    print_mazzo(len,mazzo);

    // PRIMA CARTA UTENTE 
    srand((unsigned) time(&t));
    indice_carta_mazzo = rand()%len; 
    punto_giocatore = mazzo[indice_carta_mazzo];  //Assegno la prima carta del mazzo all'utente
    cout << "La tua carta iniziale: " <<  punto_giocatore << endl; //%0.1f\n", punto_giocatore);

    memcpy(mazzo,remove(mazzo, indice_carta_mazzo,len),sizeof(double)*(len-1)); //rimuovo dal mazzo la carta estratta e aggiorno il mazzo di carte
    len-=1;
    //print_mazzo(len,mazzo); // DECOMMENTA SE VUOI VEDERE L'EFFETTIVA RIMOZIONE DELLE CARTE CHE ESCONO

    // PRIMA CARTA COMPUTER
    srand((unsigned) time(&t));
    indice_carta_mazzo = rand()%len; 
    punto_pc = mazzo[indice_carta_mazzo];  //Assegno la seconda carta del mazzo al computer
    cout << "Carta iniziale del banco: (nascosta)" << endl; //%0.1f\n", punto_pc);

    memcpy(mazzo,remove(mazzo, indice_carta_mazzo,len),sizeof(double)*(len-1)); //rimuovo dal mazzo la carta estratta e aggiorno il mazzo di carte
    len-=1;
    //print_mazzo(len,mazzo); // DECOMMENTA SE VUOI VEDERE L'EFFETTIVA RIMOZIONE DELLE CARTE CHE ESCONO

    // <---- INIZIO DEL GIOCO ---->

    // TURNO DELL'UTENTE
    cout << "\n<---- INIZIO DEL GIOCO ---->" << endl;
    char* richiesta = (char*)malloc(sizeof(char)*5);

    while(1) {
        cout << "Carta o stop?: ";
        scanf("%s",richiesta);

        // L'UTENTE SI FERMA AL PUNTEGGIO REALIZZATO FINO AD ORA
        if (strcmp(richiesta,"stop") == 0) {
            cout << "Attendi il turno del banco..." << endl;
            break;
        }

        // L'UTENTE RICHIEDE LA CARTA
        else if (strcmp(richiesta,"carta") == 0) {
            srand((unsigned) time(&t));
            indice_carta_mazzo = rand()%len; 
            nuova_carta = mazzo[indice_carta_mazzo];
            punto_scoperto += nuova_carta;
            punto_giocatore += nuova_carta;
            cout << "Carta uscita: " << nuova_carta << endl;
            cout << "Il tuo punteggio scoperto è: " << punto_scoperto << endl;
            cout << "Il tuo punteggio totale è: " << punto_giocatore << endl;

            memcpy(mazzo,remove(mazzo, indice_carta_mazzo,len),sizeof(double)*(len-1)); //rimuovo dal mazzo la carta estratta e aggiorno il mazzo di carte
            len-=1;

            if (punto_giocatore > SETTE_E_MEZZO) { // controllo se l'utente supera il punteggio massimo (7,5) o meno
                cout << "Hai sforato :(... Il banco vince, sfidalo di nuovo!" << endl;
                break;
            }

            if ( punto_giocatore == SETTE_E_MEZZO) { // controllo se l'utente ha fatto 7,5
                cout << "Hai fatto SETTE E MEZZO!! Attendi il turno del banco..." << endl;
                break;
            }
        }
        // controllo errore
        else {
            cerr << "Errore richiesta:\n- digitare 'carta' per ricevere una nuova carta\n- digitare 'stop' per fermarsi al punteggio fatto fino ad ora\n" << endl;
        }
        //print_mazzo(len,mazzo); // DECOMMENTA SE VUOI VEDERE L'EFFETTIVA RIMOZIONE DELLE CARTE CHE ESCONO
    }

    // TURNO DEL BANCO
    if (punto_giocatore <= SETTE_E_MEZZO) {
        cout << "  \nCarta iniziale del banco: " << punto_pc << endl;
        while(1) {
            // IL BANCO SI FERMA AL PUNTEGGIO REALIZZATO FINO AD ORA
            if (punto_pc >= 5 && punto_pc > punto_scoperto) {
                cout << "Il banco sta bene cosi" << endl;
                cout << "Il punteggio totale del banco è: " << punto_pc << endl;
                if ( punto_pc >= punto_giocatore) cout << "HAI PERSO :(. Il banco vince, sfidalo di nuovo!" << endl;
                else cout << "HAI VINTO!! :)" << endl;
                break;
            }
            // IL BANCO RICHIEDE LA CARTA
            else {
                srand((unsigned) time(&t));
                indice_carta_mazzo = rand()%len; 
                nuova_carta = mazzo[indice_carta_mazzo];
                punto_pc += nuova_carta;
                //punto_pc_scoperto += nuova_carta;
                cout << "Carta uscita: " << nuova_carta << endl;
                //cout << "Il punteggio scoperto del banco è: " << punto_pc_scoperto << endl;
                cout << "Il punteggio totale del banco è: " << punto_pc << endl;
                
                memcpy(mazzo,remove(mazzo, indice_carta_mazzo,len),sizeof(double)*(len-1)); //rimuovo dal mazzo la carta estratta e aggiorno il mazzo di carte
                len-=1;

                if (punto_pc > SETTE_E_MEZZO) { // controllo se il banco supera il punteggio massimo (7,5) o meno
                    cout << "Il banco ha sforato... HAI VINTO!! :)" << endl;
                    break;
                }
                
                if ( punto_pc == SETTE_E_MEZZO) { // controllo se il banco ha fatto 7,5
                    cout << "Il banco ha fatto SETTE E MEZZO!! Hai perso :(" << endl;
                    break;
                }

            }
            //print_mazzo(len,mazzo);   // DECOMMENTA SE VUOI VEDERE L'EFFETTIVA RIMOZIONE DELLE CARTE CHE ESCONO
        }
    }
    cout << "<---- FINE GIOCO ---->\n" << endl;
    return 1;
}