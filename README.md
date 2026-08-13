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
<br/>
To run this game, download the repository, download the dependencies with Maven, and run com.leemra.battleship.BattleShip in a modern JVM. (I'm using Java 25, but it should be compatible with at least *some* earlier versions. I believe it was written with Java 21.)

## Minesweeper
### Language: C#/.NET Foundation (Depreciated)
This is one of my oldest college projects - so much so, that the framework that it used, .NET Foundation, is no longer supported. 
I had to do quite a bit of reformatting (only in the .csproj files) to get the thing to run on my laptop. However, it does demonstrate my abilities in C# and Windows Forms, in a completely self-sufficient program that requires little-to-no explanation.
<br>
This project has two modes: GUI and Console. 
<br/> <br/>
**The GUI version is dependent on the Console version, so make sure you have both of them downloaded, and do not touch the project structure (unless you are ready to do a *lot* of editing to the .csproj files).**
<br><br>
This was a very simple project. If I remember correctly, the most difficult part was in the GUI version: I had to programatically create a 2d array of buttons, and sync those buttons to the existing array of Cells from the Base program.
<br><br>
To run this program, download the repository (again, please do not change the folder structure), open in a modern edition of VS Code, navigate to either Minesweeper/MinesweeperBase or Minesweeper/MinesweeperGUI, and run:
```bash
dotnet clean
dotnet build
dotnet run
```
Screenshots can be found in Minesweeper/README.md