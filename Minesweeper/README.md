# Minesweeper
### Language: C#
### Framework: .NET Foundation

---

This is one of my oldest college projects - so much so, that the framework that it used, .NET Foundation, is no longer supported.
I had to do quite a bit of reformatting (only in the .csproj files) to get the thing to run on my laptop. However, it does demonstrate my abilities in C# and Windows Forms, in a completely self-sufficient program that requires little-to-no explanation.
<br>
This project has two modes: GUI and Console.
<br/> <br/>
**The GUI version is dependent on the Console version, so make sure you have both of them downloaded, and do not touch the project structure (unless you are ready to do a *lot* of editing to the .csproj files).**
<br><br>
This was a very simple project. If I remember correctly, the most difficult part was in the GUI version: I had to programmatically create a 2d array of buttons, and sync those buttons to the existing array of Cells from the Base program.
<br><br>
To run this program, download the repository (again, please do not change the folder structure), open in a modern edition of VS Code (with the C# extensions), navigate to either Minesweeper/MinesweeperBase or Minesweeper/MinesweeperGUI, and run:
```bash
dotnet clean
dotnet buigitld
dotnet run
```
Screenshots can be found in screenshots.md or in /Screenshots