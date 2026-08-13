// Form written by Liam Emra for CST-250; This is my own work.
using System;
using MinesweeperBase;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using Microsoft.VisualBasic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Diagnostics;

namespace MinesweeperGUI {
	public partial class Minesweeper : Form {
		// Properties
		Button[,] button;
		Board board;
		const int SIZE = 30;
		bool gameOver = false;
		Stopwatch timer = new Stopwatch();
		HighScoresForm scores;

		// Constructor - Takes "numSquares"
		public Minesweeper(int numSquares, Action<object, FormClosedEventArgs> onClose) {
			InitializeComponent();
			button = new Button[numSquares, numSquares];
			setupBoard(numSquares);
			switch (numSquares) {
				case 15: 
					scores = new HighScoresForm(1);
					scores.FormClosed += new FormClosedEventHandler(onClose);
					break;
				case 12:
					scores = new HighScoresForm(2);
					scores.FormClosed += new FormClosedEventHandler(onClose);
					break;
				case 9:
					scores = new HighScoresForm(3);
					scores.FormClosed += new FormClosedEventHandler(onClose);
					break;
			}
		}

		// Sets up numSquares by numSquares board.
		void setupBoard(int numSquares) {
			board = new Board(numSquares);
			board.setupLiveNeighbors();
			board.calculateLiveNeighbors(0, 0);
			for (int xIndex = 0; xIndex < numSquares; xIndex++) {
				for (int yIndex = 0; yIndex < numSquares; yIndex++) {
					button[xIndex, yIndex] = new Button();
					button[xIndex, yIndex].Location = new Point(SIZE * xIndex, SIZE * yIndex);
					button[xIndex, yIndex].Size = new Size(SIZE, SIZE);
					button[xIndex, yIndex].TabIndex = xIndex + yIndex;
					button[xIndex, yIndex].Text = "";
					button[xIndex, yIndex].TextAlign = ContentAlignment.MiddleCenter;
					button[xIndex, yIndex].Tag = $"{xIndex}|{yIndex}";
					button[xIndex, yIndex].MouseDown += new MouseEventHandler(clickHandler);
					gamePanel.Controls.Add(button[xIndex, yIndex]);
				}
			}
			timer.Start();
		}

		// OnClick handler for all Button objects in the Minesweeper form.
		public void clickHandler(object sender, MouseEventArgs e) {

			if (gameOver) return;

			string[] coordinates = (sender as Button).Tag.ToString().Split('|');
			int xCoordinate = int.Parse(coordinates[0]);
			int yCoordinate = int.Parse(coordinates[1]);

			switch (e.Button) {
				case MouseButtons.Left:

					if (!board.grid[xCoordinate, yCoordinate].isFlagged) {
						board.floodFill(xCoordinate, yCoordinate, 1);
						updateDisplay();
						if (board.grid[xCoordinate, yCoordinate].isLive) {
							endGame(false);
							return;
						} else if (board.checkAllClear()) {
							endGame(true);
							return;
						}
					}
					break;

				case MouseButtons.Right:
					if (board.grid[xCoordinate, yCoordinate].isFlagged) {
						board.grid[xCoordinate, yCoordinate].isFlagged = false;
					} else {
						board.grid[xCoordinate, yCoordinate].isFlagged = true;
					}
					updateDisplay();
					if (board.checkAllFlagged()) {
						endGame(true);
						return;
					}
					break;
			}
		}

		// Reveals every square on the board
		private void displayFinal() {
			for (int xIndex = 0; xIndex < board.size; xIndex++) {
				for (int yIndex = 0; yIndex < board.size; yIndex++) {
					button[xIndex, yIndex].BackColor = Color.MintCream;
					button[xIndex, yIndex].BackgroundImage = null;

					if (board.grid[xIndex, yIndex].isLive) {
						button[xIndex, yIndex].BackgroundImage = Properties.Resources.bomb;
						button[xIndex, yIndex].BackgroundImageLayout = ImageLayout.Zoom;
					} else if (board.grid[xIndex, yIndex].liveNeighbors > 0) {
						button[xIndex, yIndex].Text = $"{board.grid[xIndex, yIndex].liveNeighbors}";
					}
				}
			}
		}

		// Reveals all visited squares on the board
		private void updateDisplay() {
			for (int xIndex = 0; xIndex < board.size; xIndex++) {
				for (int yIndex = 0; yIndex < board.size; yIndex++) {
					if (board.grid[xIndex, yIndex].isVisited) {
						button[xIndex, yIndex].BackColor = Color.PaleTurquoise;
						if (board.grid[xIndex, yIndex].isLive) {
							button[xIndex, yIndex].BackgroundImage = Properties.Resources.bomb;
							button[xIndex, yIndex].BackgroundImageLayout = ImageLayout.Zoom;
						} else if (board.grid[xIndex, yIndex].liveNeighbors > 0) {
							button[xIndex, yIndex].Text = $"{board.grid[xIndex, yIndex].liveNeighbors}";
						}
					} else {
						if (board.grid[xIndex, yIndex].isFlagged) {
							button[xIndex, yIndex].BackgroundImage = Properties.Resources.flag;
							button[xIndex, yIndex].BackgroundImageLayout = ImageLayout.Zoom;
						} else {
							button[xIndex, yIndex].BackgroundImage = null;
						}
					}
				}
			}
		}

		// Updates the timer element
		private void gameTick_Tick(object sender, EventArgs e) {
			var ts = timer.Elapsed;
			timeLabel.Text = $"{ts.Hours}:{ts.Minutes}:{ts.Seconds}";
		}

		// Ends the game. If victory is true, it will ask for the user's name, the score to the highscore list,
		// and display a HighScoresForm of the current game's level
		private void endGame(bool victory) {
			timer.Stop();
			if (victory) {
				TimeSpan time = timer.Elapsed;
				MessageBox.Show($"You Won! \r\nYou took {time.Hours} hours, {time.Minutes} minutes, and {time.Seconds} seconds",
					"Congratulations",
					MessageBoxButtons.OK);
				displayFinal();
				gameOver = true;
				Random rand = new Random();
				string name = Interaction.InputBox("Please enter your name:", "", $"Anonymous{rand.Next(10)}{rand.Next(10)}{rand.Next(10)}");
				scores.stats.add(name, time.Hours, time.Minutes, time.Seconds, scores.level);
				Close();
			} else {
				displayFinal();
				gameOver = true;
				MessageBox.Show("Game Over", "Boom", MessageBoxButtons.OK);
				Close();
			}
		}

		private void Minesweeper_FormClosed(object sender, FormClosedEventArgs e) {
			scores.Show();
		}
	}
}
