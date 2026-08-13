using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MinesweeperBase {
	public class Cell {
		/// <summary>
		/// Pretty much useless constructor
		/// </summary>
		public Cell() {
			
		}

		/// <summary>
		/// Whether or not the Cell has been clicked
		/// </summary>
		public bool isVisited { get; set; } = false;

		/// <summary>
		/// The number of neighboring Cells that are bombs
		/// </summary>
		public int liveNeighbors { get; set; } = 0;

		/// <summary>
		/// Is this cell a bomb?
		/// </summary>
		public bool isLive { get; set; } = false;

		/// <summary>
		/// Is this cell flagged?
		/// </summary>
		public bool isFlagged { get; set; } = false;
	}
}