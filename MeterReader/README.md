## Meter Trainer
### Languages: Kotlin, Java
### Framework: JavaFX

---

This is one of my most recent projects. My purpose in making it was to help my father,
who supervises a meter reading team, train his employees.
<br><br>
It's function is to teach users to read analog electric and water meters, such as the one displayed below.
<br>
![meter.example.png](../Screenshots/meter.example.png)
<br><br>
It has two modes: Practice and Test. Practice mode shows the user the locations of any errors in their input, test mode does not, instead keeping track of the user's overall score.
<br>
In addition to those two modes, there are three levels. The first level displays numbers (0 and 5) on the dial, and all ten tick marks. The second one shows only the tick marks, and the third shows nothing except for the pointer of the dial.
The tricky part is that each dial spins in the opposite direction of the previous one. (In a 5-dial meter, that would be clockwise, counterclockwise, clockwise, counterclockwise, clockwise, in that order.)
<br><br>
This application displays my abilities with logic, error checking, display, and adapting existing frameworks to serve my purpose.
<br><br>
To run, download the repository, import "pom.xml" as a Maven project, in whichever IDE you please, and run com.leemra.meterReader.MeterReader.kt
<br><br>
Screenshots can be found in screenshots.md or in /Screenshots