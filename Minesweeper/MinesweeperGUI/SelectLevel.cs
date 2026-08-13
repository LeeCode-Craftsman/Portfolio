// Form written by Liam Emra for CST-250; This is my own work.
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MinesweeperGUI {
	public partial class SelectLevel : Form {
		// Constructor
		public SelectLevel() {
			InitializeComponent();
		}

		// OnClick handler for startButton
		// Triggers the creation of a Minesweeper game form and hides this form
		private void startButton_Click(object sender, EventArgs e) {
			if (easyRadio.Checked) {
				Minesweeper easy = new Minesweeper(15, formClosed);
				Hide();
				easy.Show();
			} else if (normalRadio.Checked) {
				Minesweeper normal = new Minesweeper(12, formClosed);
				Hide();
				normal.Show();
			} else if (hardRadio.Checked) {
				Minesweeper hard = new Minesweeper(9, formClosed);
				Hide();
				hard.Show();
			} else {
				MessageBox.Show("Please select a difficulty", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
			}
		}

		// OnClick handler for scoreButton
		// triggers the creation of a new HighScoresForm of level 4 - meaning that the highest overal scores will be shown.
		private void scoreButton_Click(object sender, EventArgs e) {
			var highScores = new HighScoresForm(4);
			highScores.FormClosed += new FormClosedEventHandler(formClosed);
			highScores.Show();
			Hide();
		}

		// OnFormClosed handler for HighScoresForm object. Allows for starting a new game
		public void formClosed(object sender, FormClosedEventArgs e) {
			Show();
		}
	}
}
