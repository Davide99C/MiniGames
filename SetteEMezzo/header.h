// This file is part of SetteEMezzo.

//     SetteEMezzo is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.

//     SetteEMezzo is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.

//     You should have received a copy of the GNU General Public License
//     along with SetteEMezzo.  If not, see <http://www.gnu.org/licenses/>.

//     Copyright 2021, Davide Chiarabini, All rights reserved.

#include <iostream>
#include <array>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

using namespace std;
#define SETTE_E_MEZZO 7.5

double* remove(double mazzo[], double to_remove);
void print_mazzo(int len, double* mazzo);
//int len_mazzo(double* mazzo);