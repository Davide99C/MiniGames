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

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Frame {
	static JFrame frame = new JFrame("Davide Chiarabini 1844615");
	
	private JPanel nord = new JPanel();
	private JPanel center = new JPanel();
	private JPanel sud = new JPanel();
	
	private static JButton start = new JButton("Start");
	private JLabel IP = new JLabel("IP Address");
	private JTextField IP1 = new JTextField(10);	
	private JLabel porta = new JLabel("Porta");
	private JTextField porta1 = new JTextField(10);
	private static JButton interrompi = new JButton("Interrompi");

	private static JButton connect = new JButton("Connect");
	private static JButton disconnect = new JButton("Disconnect");
	private static JButton clear = new JButton("Clear");
	
	public Frame() {
		
		frame.add(nord, BorderLayout.NORTH);
		nord.add(start);
		nord.add(IP);nord.add(IP1);
		nord.add(porta);nord.add(porta1);
		IP1.setText("127.0.0.1");
		porta1.setText("4400");
		nord.add(interrompi);
		
		frame.add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(1,5));
		ColoredButton[] casella = new ColoredButton[1*5];
		for (int i=0; i<casella.length; i++) {
			casella[i] = new ColoredButton((int)Integer.valueOf(JOptionPane.showInputDialog("Inserisci un numero da 0 a 9")));
			casella[i].setBackground(Color.LIGHT_GRAY);
			center.add(casella[i],i);
		}
		
		frame.add(sud, BorderLayout.SOUTH);
		sud.add(connect);
		sud.add(disconnect);
		sud.add(clear);
		
		ActionListener list = new Listener(casella, IP1, center);
		connect.setActionCommand(Listener.connect);
		connect.addActionListener(list);
		disconnect.setActionCommand(Listener.disconnect);
		disconnect.addActionListener(list);
		start.setActionCommand(Listener.start);
		start.addActionListener(list);
		interrompi.setActionCommand(Listener.interrompi);
		interrompi.addActionListener(list);
		clear.setActionCommand(Listener.clear);
		clear.addActionListener(list);
		
		Frame.setButtons(false,false);
		frame.setSize(700,200);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static void setButtons(boolean trasmesso, boolean connesso) {
		if (connesso) {
			connect.setEnabled(false);
			if (trasmesso) {
				disconnect.setEnabled(false);
				interrompi.setEnabled(true);
				start.setEnabled(false);
				clear.setEnabled(false);
			}else {
				interrompi.setEnabled(false);
				start.setEnabled(true);
				disconnect.setEnabled(true);
				clear.setEnabled(true);
			}
		}else {
			connect.setEnabled(true);
			disconnect.setEnabled(false);
			start.setEnabled(false);
			interrompi.setEnabled(false);
			clear.setEnabled(true);
		}
	}
}
