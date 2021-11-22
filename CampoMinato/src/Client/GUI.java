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
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


class GUI extends JFrame {
    public static Logger log = Logger.getLogger("GUI");
    private final BoardButton[][] grid = new BoardButton[10][10];
    private final JTextField ipField = new JTextField("127.0.0.1");
    private final JTextField portField = new JTextField("4400");
    private final JButton connectBtn = new JButton("Connect");
    private final JButton disconnectBtn = new JButton("Disconnect");
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JButton revealBtn = new JButton("Rivela");
    private final ActionHandler act;

    private boolean connected = false;
    private boolean transmitting = false;


    GUI() {
        setLayout(new BorderLayout());

        // Upper panel
        JPanel upperPanel = new JPanel();
        upperPanel.add(new JLabel("Server Address"));
        upperPanel.add(ipField);
        upperPanel.add(new JLabel("Port"));
        upperPanel.add(portField);
        upperPanel.add(connectBtn);
        upperPanel.add(disconnectBtn);
        add(upperPanel, BorderLayout.NORTH);
        // Lower Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startBtn);
        bottomPanel.add(stopBtn);
        bottomPanel.add(revealBtn);
        add(bottomPanel, BorderLayout.SOUTH);
        // Middle grid
        JPanel middleGrid = new JPanel();
        middleGrid.setLayout(new GridLayout(10,10));
        int k=0;
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                grid[i][j] = new BoardButton();
                middleGrid.add(grid[i][j]);
                grid[i][j].setAdjacentMines(k++);
            }
        }
        add(middleGrid);


        act = new ActionHandler(this);
        connectBtn.addActionListener(act);
        startBtn.addActionListener(act);
        disconnectBtn.addActionListener(act);
        stopBtn.addActionListener(act);
        revealBtn.addActionListener(act);

        update();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        log.log(Level.INFO, "Set up and ready to go");
    }




    public BoardButton getCell(int i, int j) {
        return grid[i][j];
    }

    void resetBoard() {
        for(int i=0; i<10;i++) {
            for (int j=0;j<10;j++) {
                getCell(i,j).reset();
            }
        }
    }

    public void toggleBoard(boolean toggle) {
        for(int i=0; i<10;i++) {
            for (int j=0;j<10;j++) {
                getCell(i,j).setEnabled(toggle);
            }
        }
    }

    private void update() {
        if (!connected) {
            connectBtn.setEnabled(true);
            disconnectBtn.setEnabled(false);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(false);
            revealBtn.setEnabled(false);
            toggleBoard(false);
        } else if (connected && !transmitting) {
            connectBtn.setEnabled(false);
            disconnectBtn.setEnabled(true);
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            revealBtn.setEnabled(true);
            toggleBoard(true);
        } else if (transmitting) {
            connectBtn.setEnabled(false);
            disconnectBtn.setEnabled(false);
            startBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            revealBtn.setEnabled(true);
            toggleBoard(false);
        }
    }

    public int checkGrid() {
        int res = 0;
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                if (getCell(i,j).isSelected()) {
                    res += 1;
                }
                if (getCell(i,j).hasMine()) {
                    res += 1;
                    if (getCell(i,j).isSelected()) {
                        return -1;
                    }
                }
            }
        }
        return res;
    }

    public JTextField getIpField() {
        return ipField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
        update();
    }

    public void setTransmitting(boolean transmitting) {
        this.transmitting = transmitting;
        update();
    }
}
