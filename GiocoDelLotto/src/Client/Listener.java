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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Listener implements ActionListener {

	public static final String connect="connect", disconnect="disconnect", start="start", interrompi="interrompi", clear="clear";
	private Socket socket;
	private PrintWriter w;
	private Scanner scanner;
	private ColoredButton[] casella;
	private JTextField IP;
	private boolean trasmesso,connesso;
	private Downloader d = null;
	private JPanel center;
	
	public Listener(ColoredButton[] casella, JTextField IP, JPanel center) {
		// TODO Auto-generated constructor stub
		this.casella=casella;
		this.IP=IP;
		this.center=center;
		trasmesso=false;
		connesso=false;
	}
	
	private void setupConnection() throws UnknownHostException, IOException {
		socket = new Socket(IP.getText(), 4400);
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
				connesso=true;
				JOptionPane.showMessageDialog(null, "Connessione riuscita");
			}catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Connessione fallita");
			}
		}
		else if (e.getActionCommand().equals(Listener.disconnect)) {
			w.println(Listener.disconnect);
			w.flush();
			w.close();
			scanner.close();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			connesso=false;
			JOptionPane.showMessageDialog(null, "Connessione interrotta");
		}
		else if (e.getActionCommand().equals(Listener.clear)) {
			if (JOptionPane.showInputDialog("Vuoi cambiare le caselle?").equals("si")) {
				center.removeAll();
				for (int i=0; i<casella.length; i++) {
					casella[i] = new ColoredButton((int)Integer.valueOf(JOptionPane.showInputDialog("Inserisci un numero da 0 a 9")));
					casella[i].setBackground(Color.LIGHT_GRAY);
					center.add(casella[i],i);
				}
				Frame.frame.setVisible(true);
			}else {
				for (int i=0;i<casella.length;i++) {
					casella[i].setBackground(Color.LIGHT_GRAY);
				}
			}
			trasmesso=false;
		}
		else if (e.getActionCommand().equals(Listener.start)) {
			w.println(Listener.start);
			w.flush();
			d = new Downloader(casella,scanner);
			Thread t = new Thread(d);
			t.start();
			trasmesso=true;
			JOptionPane.showMessageDialog(null, "Estrazione iniziata!");
		}
		else if (e.getActionCommand().equals(Listener.interrompi)) {
			w.println(Listener.interrompi);
			w.flush();
			trasmesso=false;
		}
		
		Frame.setButtons(trasmesso, connesso);
	}

}
