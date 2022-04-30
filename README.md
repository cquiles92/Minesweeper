# Minesweeper
HyperSkill Project #12

https://hyperskill.org/projects/77

Minesweeper/Minesweeper/task/src/minesweeper/
Source Code Location 

In this project I made a simple Minesweeper clone that functions in the terminal. This project taught me how to use the flood fill algorithm and theory to apply. The sequence of events is as follows: The game state is initially created as an empty board or "minefield". The program then creates a few data structures to help organize the locations of the mines, the spaces that contain hints, and the areas that are already dug. A map gives the hints a value instead of creating an object and the game itself is saved as a simple character array as an attempt to be more memory efficient. After the first move is initiated by the player, the program will generate the mines afterwards to ensure that the first click guarantees a new game state where it is not possible to lose instantly. Afterwards the hints are generated and kept in a set of locations. A set of dug locations is also managed everytime the dig function (AKA left click) is invoked. The win conditions are simple and as follows, if the game can detect an equal amount of empty spaces to mines, or the mines are flagged correctly in each spot.
