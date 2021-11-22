// This file is part of GiocoDelLotto.

//     GiocoDelLotto is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     GiocoDelLotto is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with GiocoDelLotto.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved

package Client;

import java.awt.Color;
import java.util.*;

import javax.swing.JOptionPane;

public class Downloader implements Runnable {
	
	private ColoredButton[] casella;
	private Scanner scanner;
	private boolean running;

	public Downloader(ColoredButton[] casella, Scanner scanner) {
		// TODO Auto-generated constructor stub
		this.casella=casella;
		this.scanner = scanner;
		running=false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (!running) {
			running=true;
			while(running) {
				String risposta = scanner.nextLine();
				String[] info=risposta.split(";");
				String pos = info[0];
				String num = info[1];
				if (pos.equals("*") && num.equals("*")) {
					running = false;
					Frame.setButtons(false, true);
					int count = 0;
					for (int i=0;i<casella.length;i++) {
						if (casella[i].getBackground()==Color.GREEN) count++;
					}
					if (count>0) JOptionPane.showMessageDialog(null, "Hai vinto!");
					else JOptionPane.showMessageDialog(null, "Hai perso!");
					continue;
				}
				else if (pos.equals("-1") && num.equals("-1")) {
					running = false;
					JOptionPane.showMessageDialog(null, "Connessione interrotta...Hai perso!");
					continue;
				}
				else {
					if (casella[Integer.valueOf(pos)].getValue() == Integer.valueOf(num)) {
						casella[Integer.valueOf(pos)].setSelected(true);
					}
					else casella[Integer.valueOf(pos)].setSelected(false);
					
				}
			}
		}
		
	}

}
