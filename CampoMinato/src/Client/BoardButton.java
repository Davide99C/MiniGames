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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardButton extends JToggleButton {
    private boolean mine = false;
    private int adjacentMines = 0;

    public BoardButton() {
        super("", true);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reveal();
                }
        });
    }

    private void reveal() {
        setEnabled(false);
        if (mine) {
            setText("X");
            setBackground(Color.RED);
        } else {
            setText(String.valueOf(adjacentMines));
            setBackground(Color.CYAN);
        }
    }

    @Override
    public void setSelected(boolean b) {
        if (b) {
            reveal();
        } else {
            reset();
        }
    }

    public boolean isSelected() {
        return !getText().equals("");
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    public int getAdjacentMineCounts() {
        return adjacentMines;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void reset() {
        super.setSelected(true);
        setEnabled(true);
        setText("");
        setMine(false);
        setAdjacentMines(0);
        setBackground(Color.LIGHT_GRAY);
    }
}

