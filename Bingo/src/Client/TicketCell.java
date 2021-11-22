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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TicketCell extends JLabel {
    private boolean selected = false;

    public TicketCell(int value) {
        super(String.valueOf(value));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
    }

    public int getValue() {
       return Integer.parseInt(getText());
    }


    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(Color.GREEN);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public boolean isSelected() {
        return selected;
    }

}
