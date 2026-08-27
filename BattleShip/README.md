# BattleShip
### Langauages: Java & Kotlin
### Frameworks: JavaFX & Ktor
### Build System: Maven

---
## BattleShip Core
### Languages: Java & Kotlin
### Package: `com.leemra.battleship.core`
One of my latest educational projects, and one I am rather proud of.
This system is designed to simulate a game of BattleShip. <br/>
The primary challenge of the project was building the computer's strategy system from scratch.<br/>
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

The biggest challenge of implementing this method was that it had to be in a method that saved its own state elsewhere. I could not just use a loop or a recursive algorithm, because it needed to stop to let the User play.

Battleship Core contains the necessary code for the backend of the Battleship game and the Launcher application.

---

## BattleShip Classic
### Language: Java
### Package: `com.leemra.battleship.classic`

To run this game, download the repository, download the dependencies with Maven, and run com.leemra.battleship.core.Launch.
Then click Classic Game.


Note that it is not possible to return to the Launcher from the Classic Game.


The lag in the computer's turn is intentionally programmed in, to make it look like the computer is
taking its time and to give the user a chance to view the computer's progress in the game.

The game board is designed to look like a radar display.

---

## BattleShip Ktor
### Langauge: Kotlin
### Package: `com.leemra.battleship.ktor`
This game is a second implementation of Battleship Core. 
The primary reason for creating this was to gain practice with Ktor 
and to create a GUI that I am actually happy to show off. 
(Note: I did not actually create any stylesheets for this project except for resources/Styles.css for the original project.
All credit to the AtlantaFX developers for the stylesheets. The fxml files, on the other hand, are entirely my own.)

To run, follow the instructions to launch BattleShip Classic, but click "Ktor Game" instead of "Classic Game"

#### Frontend
Due to the UI changes - namely, my choice to display both boards at once -
the game no longer requires a delay on the computer side. 
Additionally, theming is now possible on both the Launcher and the Ktor BattleShip game, 
and it remains consistent between the two.
<br>
(Disclaimer: The *Theme* dropdown menu does not remain selected between the Launcher and Ktor BattleShip.)

The JavaFX frontend uses a Ktor client to communicate with the backend.

#### Backend
The backend is run by a Server object that contains a Ktor EmbeddedServer,
a BattleShipCore and a Computer. There are three URL endpoints set up: `/ship`, `/fire`, and `/quit`.
<br>
To be fair, I am not entirely sure if the `/quit` endpoint works. 
I haven't been able to get the EmbeddedServer to shut down at the end of the program with anything less than stopping the JVM entirely.
<br>
`/ship` is the POST endpoint for placing a ship. 
It operates almost exactly like the equivalent method in BattleShipController, 
except that it only returns the locations of the placed ships, rather than updating the entire display.
<br>
`/fire` is the POST endpoint for normal play. It takes a coordinate pair from the user, and calculates if it hit and the Computer's turn (if applicable). 
It returns the coordinate pairs for any locations that the computer fired on.
(The frontend has enough data to determine if they were successful.)