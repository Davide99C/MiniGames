// This file is part of Bingo.

//     Bingo is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     Bingo is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with Bingo.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved.

package Client;

import java.util.Scanner;
import javax.swing.JTextArea;

public class Downloader implements Runnable {
	
	private Scanner scanner;
	private TicketCell[][] caselle;
	private JTextArea log;
	private boolean running;

	public Downloader(TicketCell[][] caselle, Scanner scanner, JTextArea log) {
		// TODO Auto-generated constructor stub
		this.caselle = caselle;
		this.scanner = scanner;
		this.log = log;
		running = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!running) {
			running = true;
			while(running) {
				String testo;
				String num = scanner.nextLine();
				if (num.equals("+")) {
					testo = log.getText()+"FINE PARTITA!"+"\n";
					running = false;
					Frame.setbuttons(false,true);
				}
				else {
					testo = log.getText()+"Estratto:"+num+"\n";
					for (int i = 0 ; i<3; i++) {
						for (int j=0 ; j<5; j++) {
							if (caselle[i][j].getValue() == Integer.parseInt(num) ) {
								caselle[i][j].setSelected(true);
							}
						}
					}
				}
				log.setText(testo);
			}
		}
		
	}

}
