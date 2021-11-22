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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Listener implements ActionListener {

	public static final String connect = "connect", disconnect = "disconnect", start = "start", stop = "stop";
	
	private Socket socket;
	private Scanner scanner;
	private PrintWriter w;
	private Downloader d = null;
	private TicketCell[][] caselle;
	private JTextArea log; 
	private boolean trasmesso = false;
	private boolean connesso = false;
	private JPanel center_sx;
	private ArrayList<Integer> arr;
	private int i = 700;
	
	public Listener(TicketCell[][] caselle, JTextArea log, JPanel center_sx, ArrayList<Integer> arr) {
		// TODO Auto-generated constructor stub
		this.caselle = caselle;
		this.log = log;
		this.center_sx=center_sx;
		this.arr=arr;
	}
	
	private void setupConnection() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", 4400);
		OutputStream os = socket.getOutputStream();
		w = new PrintWriter(new OutputStreamWriter(os));
		scanner = new Scanner(socket.getInputStream());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals(Listener.connect)) {
			try {
				setupConnection();
				connesso = true;
				JOptionPane.showMessageDialog(null, "Collegamento creato");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Collegamento fallito");
				return;
			}
		}
		else if (e.getActionCommand().equals(Listener.disconnect)) {
			w.println(Listener.disconnect);
			w.flush();
			w.close();
			scanner.close();
			try {
				socket.close();
			}catch (IOException e1) {
				e1.printStackTrace();
			}
			connesso = false;
			JOptionPane.showMessageDialog(null, "Connessione interrotta");
		}
		else if (e.getActionCommand().equals(Listener.start)) {
			center_sx.removeAll();
			arr = Frame.Rand();
			caselle = new TicketCell[3][5];
			for (int i=0;i<3;i++) {
				for (int j=0; j<5;j++) {
					caselle[i][j] = new TicketCell((int)arr.get(j*3+i));
					caselle[i][j].setSelected(false);
					center_sx.add(caselle[i][j],i*5+j);
				}
			}
			if (i==700) i=701; else i=700;
			Frame.setSize(1000,i);
			d = new Downloader(caselle, scanner, log);
			trasmesso = true;
			w.println(Listener.start);
			w.flush();
			Thread t = new Thread(d);
			t.start();
			JOptionPane.showMessageDialog(null, "Inizia l'estrazione!");
		}
		else if (e.getActionCommand().equals(Listener.stop)) {
			w.println(Listener.stop);
			w.flush();
			trasmesso = false;
			JOptionPane.showMessageDialog(null, "Estrazione terminata!");
		}
		Frame.setbuttons(trasmesso,connesso);
	}
	

}
