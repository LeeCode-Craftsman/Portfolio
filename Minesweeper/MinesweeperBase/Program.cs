// Program written for CST-250 by Liam Emra. This is my own work.
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MinesweeperBase {
	internal class Program {
		// Property
		public static Board gameBoard { get; set; }

		//Field
		public const int DIMENSIONS = 12;

		/// <summary>
		/// Main method - Creates gameboard and prints it.
		/// </summary>
		/// <param name="args"></param>
		static void Main(string[] args) {
			bool isGameOver = false;
			var stats = new PlayerStatList("../../../scores.minesweeperscorelist");
			var watch = new Stopwatch();

			while (!isGameOver) {
				gameBoard = new Board(DIMENSIONS);
				gameBoard.setupLiveNeighbors();
				gameBoard.calculateLiveNeighbors(0, 0);
				watch.Start();


				while (!isGameOver) {
					Console.Clear();

					printBoardForPlay();

					int x;
					int y;
					bool flag;
					getUserInput(out x, out y, out flag);

					if (flag) {
						gameBoard.grid[x, y].isFlagged = !gameBoard.grid[x, y].isFlagged;

						if (gameBoard.checkAllFlagged()) {
							isGameOver = true;
							watch.Stop();
							Console.Clear();
							printBoard();
							Console.WriteLine($"Congratulations! You took {watch.Elapsed.Hours}:{watch.Elapsed.Minutes}:{watch.Elapsed.Seconds}");
							Console.Write("Please enter your name: ");
							string name = Console.ReadLine();
							stats.add(name, watch.Elapsed.Hours, watch.Elapsed.Minutes, watch.Elapsed.Seconds, 2);
						}
					} else {

						gameBoard.floodFill(x, y, 1);
					
						if (gameBoard.grid[x, y].isLive) {
							isGameOver = true;
							watch.Stop();
							Console.Clear();
							printBoard();
							Console.WriteLine("BOOM!");
						}
					}
					if (gameBoard.checkAllClear()) {
						isGameOver = true;
						watch.Stop();
						Console.Clear();
						printBoard();
						Console.WriteLine($"Congratulations! You took {watch.Elapsed.Hours}:{watch.Elapsed.Minutes}:{watch.Elapsed.Seconds}");
						Console.Write("Please enter your name: ");
						string name = Console.ReadLine();
						stats.add(name, watch.Elapsed.Hours, watch.Elapsed.Minutes, watch.Elapsed.Seconds, 2);
					}
				}

				
				stats.printLevel(2, (text, index) => {
						string[] parts = text.Split('|');
						Console.WriteLine($"{parts[0]}\t{parts[1]}\t\t{parts[2]}\t\t{parts[3]}");
					});
				watch.Reset();
				Console.Write("Would you like to play again? (Y/N): ");
				if (Console.ReadLine().ToLower().Equals("y")) {
					isGameOver = false;
				}
				stats.save();
			}
		}

		/// <summary>
		/// printBoard() - Displays gameboard in ASCII format
		/// </summary>
		static void printBoard() {
			Console.Write("+ ");
			for (int i = 0; i < DIMENSIONS - 1; i++) {
				Console.Write(i);
				if (i != 10) {
					Console.Write(" + ");
				} else {
					Console.Write("+ ");
				}
			}
			if (DIMENSIONS - 1 > 9) {
				Console.WriteLine($"{DIMENSIONS - 1}+");
			} else {
				Console.WriteLine($"{DIMENSIONS - 1} +");
			}

			for (int i = 0; i < DIMENSIONS; i++) {
				for (int j = 0; j < DIMENSIONS; j++) {
					Console.Write("+---");
				}
				Console.WriteLine("+");
				for (int j = 0; j < DIMENSIONS; j++) {
					Console.Write("| ");
					if (!gameBoard.grid[i, j].isLive && gameBoard.grid[i, j].liveNeighbors > 0) {
						Console.Write($"{gameBoard.grid[i, j].liveNeighbors} ");
					} else if (gameBoard.grid[i, j].liveNeighbors == 0) {
						Console.Write("  ");
					} else {
						Console.Write("* ");
					}
				}
				Console.WriteLine($"|  {i}");
			}
			for (int j = 0; j < DIMENSIONS; j++) {
				Console.Write("+---");
			}
			Console.WriteLine("+");
		}

		static void printBoardForPlay() {
			Console.Write("+ ");
			for (int i = 0; i < DIMENSIONS - 1; i++) {
				Console.Write(i);
				if (i != 10) {
					Console.Write(" + ");
				} else {
					Console.Write("+ ");
				}
			}
			if (DIMENSIONS - 1 > 9) {
				Console.WriteLine($"{DIMENSIONS - 1}+");
			} else {
				Console.WriteLine($"{DIMENSIONS - 1} +");
			}

			for (int i = 0; i < DIMENSIONS; i++) {
				for (int j = 0; j < DIMENSIONS; j++) {
					Console.Write("+---");
				}
				Console.WriteLine("+");
				for (int j = 0; j < DIMENSIONS; j++) {
					Console.Write("| ");
					if (!gameBoard.grid[i, j].isVisited) {
						if (gameBoard.grid[i, j].isFlagged) {
							Console.Write("! ");
							continue;
						}
						Console.Write("? ");
					} else if (!gameBoard.grid[i, j].isLive && gameBoard.grid[i, j].liveNeighbors > 0) {
						Console.Write($"{gameBoard.grid[i, j].liveNeighbors} ");
					} else if (gameBoard.grid[i, j].liveNeighbors == 0) {
						Console.Write("  ");
					} else {
						Console.Write("* ");
					}
				}
				Console.WriteLine($"|  {i}");
			}
			for (int j = 0; j < DIMENSIONS; j++) {
				Console.Write("+---");
			}
			Console.WriteLine("+");
		}



		// Helper Methods
		static void getUserInput(out int x, out int y, out bool flag) {
			bool sucessful = false;
			x = 0; y = 0; flag = false;

			while (!sucessful) {
				Console.Write("Enter a Row Number: ");
				sucessful = int.TryParse(Console.ReadLine(), out x);
				if (x < 0 || x >= DIMENSIONS) {
					sucessful = false;
				}
				if (!sucessful) {
					Console.WriteLine($"Please enter an integer 0-{DIMENSIONS - 1}");
				}
			}

			sucessful = false;
			while (!sucessful) {
				Console.Write("Enter a Column Number: ");
				sucessful = int.TryParse(Console.ReadLine(), out y);
				if (y < 0 || y >= DIMENSIONS) {
					sucessful = false;
				}
				if (!sucessful) {
					Console.WriteLine($"Please enter an integer 0-{DIMENSIONS - 1}");
				}
			}

			Console.Write("Would you like to (u)ncover or (f)lag: ");
			if (Console.ReadLine().ToLower().Equals("f")) flag = true;
		}

	}
}
