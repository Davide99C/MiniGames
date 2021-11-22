// This file is part of CampoMinato.

//     CampoMinato is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     CampoMinato is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with CampoMinato.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved

package Client;

import javax.swing.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Receiver implements Runnable {
    private final Scanner input;
    private static Logger log = Logger.getLogger("Receiver");
    private boolean transmitting = true;
    private boolean finished = false;
    private final GUI frame;

    Receiver(Scanner input, GUI grid) {
        this.input = input;
        this.frame = grid;
    }

    @Override
    public void run() {
        log.log(Level.INFO, "Starting receiving");
        while (input.hasNext() && transmitting) {
            String s = input.nextLine();
            log.log(Level.INFO, "Received: " + s);

            if (s.equals("interrompi")) {
                log.log(Level.WARNING, "Interrupted by the client");
                transmitting = false;
                frame.setTransmitting(false);
            } else if (s.equals("done")) {
                log.log(Level.WARNING, "Ended by server");
                transmitting = false;
                frame.setTransmitting(false);
                play();
            } else {
                String[] v = s.split(":");
                if (v[2].equals("-1")) {
                    frame.getCell(Integer.parseInt(v[0]), Integer.parseInt(v[1])).setMine(true);
                }
                frame.getCell(Integer.parseInt(v[0]), Integer.parseInt(v[1])).setAdjacentMines(Integer.parseInt(v[2]));
            }
        }

    }

    private void play() {
        JOptionPane.showMessageDialog(null, "Inizio partita");
        while (!finished) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int r = frame.checkGrid();
            if (r == 100) {
                JOptionPane.showMessageDialog(null, "Hai vinto!");
                finished = true;
            } else if (r == -1) {
                JOptionPane.showMessageDialog(null, "Hai perso :c");
                finished = true;
                ActionHandler.reveal(frame);


            }

        }
        frame.toggleBoard(false);
    }
}