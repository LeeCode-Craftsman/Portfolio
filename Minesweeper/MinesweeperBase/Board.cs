// Class written for CST-250 by Liam Emra. This is my own work.
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MinesweeperBase {
	public class Board {
		// Properties
		/// <summary>
		/// Gameboard
		/// </summary>
		public Cell[,] grid { get; set; }
		/// <summary>
		/// Size of the gameboard
		/// </summary>
		public int size { get; set; }
		/// <summary>
		/// Number of bombs found on the board
		/// </summary>
		public int difficulty { get; set; } = 10;

		/// <summary>
		/// Board constructor - Creates 2d <i>aSize</i> by <i>aSize</i> array of Cells and populates it
		/// </summary>
		/// <param name="aSize">The starting x and y dimensions of the array</param>
		public Board(int aSize) {
			size = aSize;
			grid = new Cell[size, size];

			for (int xIndex = 0; xIndex < size; xIndex++) {
				for (int yIndex = 0; yIndex < size; yIndex++) {
					grid[xIndex, yIndex] = new Cell();
				}
			}
		}

		/// <summary>
		/// setupLiveNeighbors() - Randomly sets up at most the number of bombs specified by difficulty
		/// </summary>
		public void setupLiveNeighbors() {
			Random random = new Random();

			for (int i = 0; i < difficulty; i++) {
				int randX = random.Next(0, size);
				int randY = random.Next(0, size);
				grid[randX, randY].isLive = true;
			}
		}

		/// <summary>
		/// calculateLiveNeighbors() - Recursive method that marks the number of live cells
		/// touch the cell it is started on, then moves on to the next cell
		/// </summary>
		/// <param name="xIndex">The starting x coordinate</param>
		/// <param name="yIndex">The starting y coordinate</param>
		public void calculateLiveNeighbors(int xIndex, int yIndex) { 
			if (grid[xIndex, yIndex].isLive) {
				grid[xIndex, yIndex].liveNeighbors = 9;
				if (yIndex == (size-1)) {
					if (xIndex == (size-1)) {
						return;
					} else {
						calculateLiveNeighbors(xIndex+1, 0);
						return;
					}
				} else {
					calculateLiveNeighbors(xIndex, yIndex+1);
					return;
				}
			}

			if (spaceExists(xIndex-1, yIndex)) incrementIfLive(xIndex, yIndex, xIndex - 1, yIndex);
			
			if (spaceExists(xIndex, yIndex - 1)) incrementIfLive(xIndex, yIndex, xIndex, yIndex - 1);

			if (spaceExists(xIndex + 1, yIndex)) incrementIfLive(xIndex, yIndex, xIndex + 1, yIndex);

			if (spaceExists(xIndex, yIndex + 1)) incrementIfLive(xIndex, yIndex, xIndex, yIndex + 1);

			if (spaceExists(xIndex-1, yIndex -1)) incrementIfLive(xIndex, yIndex, xIndex - 1, yIndex - 1);

			if (spaceExists(xIndex+1, yIndex-1)) incrementIfLive(xIndex, yIndex, xIndex + 1, yIndex - 1);

			if (spaceExists(xIndex + 1, yIndex+1)) incrementIfLive(xIndex, yIndex, xIndex + 1, yIndex + 1);

			if (spaceExists(xIndex - 1, yIndex + 1)) incrementIfLive(xIndex, yIndex, xIndex - 1, yIndex + 1);

			if (yIndex == (size-1)) {
				if (xIndex != (size-1)) calculateLiveNeighbors(xIndex+1, 0);
			} else {
				calculateLiveNeighbors(xIndex, yIndex+1);
				return;
			}
		}

		/// <summary>
		/// incrementIfLive() - A helper method to cut out 
		/// some of the repeated code in calculateLiveNeighbors.
		/// If the Cell at the "check" coordinates is live, it will
		/// add one to the liveNeighbors value of the Cell at (x, y)
		/// </summary>
		/// <param name="startX">The x coordinate of the Cell to increment</param>
		/// <param name="startY">The y coordinate of the Cell to increment</param>
		/// <param name="checkX">The x coordinate of the Cell to check</param>
		/// <param name="checkY">The y coordinate of the Cell to check</param>
		void incrementIfLive(int startX, int startY, int checkX, int checkY) {
			if (grid[checkX, checkY].isLive) {
				grid[startX, startY].liveNeighbors++;
			}
		}

		public void floodFill(int currentX, int currentY, int iterations) {
			Cell cell = grid[currentX, currentY];
			if (cell.isVisited) {
				return;
			}

			if (cell.isLive) {
				if (iterations == 1) {
					cell.isVisited = true;
				}
				return;
			}
			cell.isVisited = true;
			if (cell.liveNeighbors == 0) {
				if (spaceExists(currentX + 1, currentY) && !grid[currentX + 1, currentY].isVisited && !grid[currentX + 1, currentY].isFlagged) floodFill(currentX + 1, currentY, iterations + 1);
				if (spaceExists(currentX, currentY + 1) && !grid[currentX, currentY + 1].isVisited && !grid[currentX, currentY + 1].isFlagged) floodFill(currentX, currentY + 1, iterations + 1);
				if (spaceExists(currentX - 1, currentY) && !grid[currentX - 1, currentY].isVisited && !grid[currentX - 1, currentY].isFlagged) floodFill(currentX - 1, currentY, iterations + 1);
				if (spaceExists(currentX, currentY - 1) && !grid[currentX, currentY - 1].isVisited && !grid[currentX, currentY - 1].isFlagged) floodFill(currentX, currentY - 1, iterations + 1);
			}
		}

		// If any cell in grid is not visited or live, returns false. Otherwise, returns true
		public bool checkAllClear() {
			foreach (Cell cell in grid) {
				if (!(cell.isVisited || cell.isLive)) {
					return false;
				}
			}

			return true;
		}

		// Returns false if any live cell is not flagged or if any not live cell is flagged. Otherwise returns true.
		public bool checkAllFlagged() {
			var live = from Cell cell in grid where cell.isLive select cell;

			foreach(Cell cell in live) {
				if (!cell.isFlagged) {
					return false;
				}
			}

			var flagged = from Cell cell in grid where cell.isFlagged select cell;
			foreach(Cell cell in flagged) {
				if (!cell.isLive) {
					return false;
				}
			}

			return true;
		}

		public bool spaceExists(int x, int y) {
			return (x >= 0 && x < size) && (y >= 0 && y < size);
		}
	}
}