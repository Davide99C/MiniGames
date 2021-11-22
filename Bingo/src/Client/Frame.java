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

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Frame {
	static JFrame frame = new JFrame("Davide Chiarabini 1844615");
	private JPanel nord = new JPanel();	
	private JPanel center_sx = new JPanel();
	private JPanel center_dx = new JPanel();
	private JPanel sud = new JPanel();
		
	private JTextArea log = new JTextArea(24,20);
	private JScrollPane log1 = new JScrollPane(log);
	
	private JLabel IP = new JLabel("IP Address");
	private JTextField IP1 = new JTextField(10);
	
	private JLabel porta = new JLabel("Porta");
	private JTextField porta1 = new JTextField(10);
	
	private static JButton start = new JButton("Start");
	private static JButton stop = new JButton("Stop");
	private static JButton connect = new JButton("connect");
	private static JButton disconnect = new JButton("disconnect");
	
	private ArrayList<Integer> arr;
	private TicketCell[][] caselle;
	
	public Frame() {
		frame.setSize(1000,700);
		frame.setVisible(true);
		
		frame.add(nord, BorderLayout.NORTH);
		nord.add(IP);nord.add(IP1);IP1.setText("127.0.0.1");
		nord.add(porta); nord.add(porta1); porta1.setText("4400");
		nord.add(connect); nord.add(disconnect);
		
		frame.add(center_sx, BorderLayout.CENTER);
		center_sx.setBorder(BorderFactory.createTitledBorder("Cartella"));
		center_sx.setLayout(new GridLayout(3,5));
		arr = Rand();
		caselle = new TicketCell[3][5];
		for (int i=0;i<3;i++) {
			for (int j=0; j<5;j++) {
				caselle[i][j] = new TicketCell((int)arr.get(j*3+i));
				caselle[i][j].setSelected(false);
				center_sx.add(caselle[i][j],i*5+j);
			}
		}
		
		frame.add(center_dx, BorderLayout.EAST);
		center_dx.setBorder(BorderFactory.createTitledBorder("Log"));
		center_dx.add(log1); 
		//center_dx.add(log);  //COMMENTARE PER ABILITARE LO SCROLLPANE MA POI NON FUNZIONA BENE IL PROGRAMMA
		log.setEditable(false);
		
		frame.add(sud, BorderLayout.SOUTH);
		sud.add(start); sud.add(stop);
		
		ActionListener list = new Listener(caselle, log, center_sx, arr);
		connect.setActionCommand(Listener.connect);
		connect.addActionListener(list);
		disconnect.setActionCommand(Listener.disconnect);
		disconnect.addActionListener(list);
		start.setActionCommand(Listener.start);
		start.addActionListener(list);
		stop.setActionCommand(Listener.stop);
		stop.addActionListener(list);
		
		
		frame.setLocationRelativeTo(null);
		Frame.setbuttons(false,false);
	}
	
	public static void setbuttons(boolean trasmesso, boolean connesso) {
		if (connesso) {
			connect.setEnabled(false);
			if (trasmesso) {
				disconnect.setEnabled(false);
				stop.setEnabled(true);
				start.setEnabled(false);
			}else {
				stop.setEnabled(false);
				start.setEnabled(true);
				disconnect.setEnabled(true);
			}
		}else {
			connect.setEnabled(true);
			disconnect.setEnabled(false);
			start.setEnabled(false);
			stop.setEnabled(false);
		}
	}
	
	public static ArrayList<Integer> Rand() {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		int value;
		for (int i = 0 ; i<15; i++) {
			value=(int)(Math.random()*90+1);
			if (!arr.contains(value) && value!=0) arr.add(value);
			else i--;
		}
		arr.sort(null);
		print(arr);System.out.println();
		return arr;
	}

	private static void print(ArrayList<Integer> arr) {
		// TODO Auto-generated method stub
		for (int i=0;i<arr.size();i++) {
			System.out.print(arr.get(i)+" ");
		}
	}

	public static void setSize(int i, int j) {
		frame.setSize(i,j);
		// TODO Auto-generated method stub
		
	}
}
