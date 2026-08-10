# Portfolio
Portfolio of my various projects, drawn primarily from college assignments. Do not copy this work.

## Battleship
### Language: Java
One of my latest educational projects, and one I am rather proud of. 
This system is designed to simulate a game of BattleShip. <br/>
It is a single-player game. The primary challenge of the project was building the computer's strategy system from scratch.<br/>
I set the computer to use my own strategy in BattleShip:
1. Randomly fire at a tile
2. If I hit a ship:
   1. Fire at an adjacent tile that I haven't hit yet
   2. Repeat until I find the next ship tile
   3. Continue firing in a line from that point, until I reach the end of the ship
   4. Fire along that same line, at the other end of the ship.
   5. If I find another point: Keep firing in that direction, until the ship is sunk
   6. Otherwise, the ship is square-shaped, fire directionally (see steps i and ii) until it is sunk.
3. Repeat until one side wins

The biggest challenge of implementing this method was that it had to be in a method that saved it's own state elsewhere. I could not just use a loop or a recursive algorithm, because it needed to stop to let the User play. <br/>
As you can see, I was successful:
![](/Screenshots/BattleShip.png)
This image displays a player's turn, after the computer has found two of their ships. The computer accomplished this quite quickly, taking only a few turns to demolish two out of the three ships once it found them.
