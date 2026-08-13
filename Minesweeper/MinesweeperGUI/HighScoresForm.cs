// Form written for CST-250 by Liam Emra. This is my own work.
using MinesweeperBase;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Printing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MinesweeperGUI {
	public partial class HighScoresForm : Form {
		public PlayerStatList stats {get;}
		public int level {get;}

		public HighScoresForm(int level) {
			InitializeComponent();
			stats = new PlayerStatList("../../../scores.minesweeperscorelist");
			this.level = level;
		}

		// Calls printlevel with a lambda expression that displayes each score in it's own series of Labels
		private void HighScoresForm_Load(object sender, EventArgs e) {
			stats.printLevel(level, (text, index) => {
				int y = 56 + (28 * index * 2);

				var parts = text.Split('|');

				Label placeLabel = new Label();
				placeLabel.Text = parts[0];
				placeLabel.AutoSize = false;
				placeLabel.Width = 20;
				placeLabel.Height = 28;
				placeLabel.TextAlign = ContentAlignment.MiddleLeft;
				placeLabel.Location = new Point(8, y);
				placeLabel.Margin = new Padding(5);
				placeLabel.Anchor = ( AnchorStyles.Top | AnchorStyles.Left);

				TextBox scoreLabel = new TextBox();
				scoreLabel.Text = $"\t{parts[1]}\t\t\t{parts[2]}";
				scoreLabel.AutoSize = false;
				scoreLabel.Width = 470;
				scoreLabel.Height = 28;
				scoreLabel.TextAlign = HorizontalAlignment.Center;
				scoreLabel.BorderStyle = BorderStyle.None;
				scoreLabel.ReadOnly = true;
				scoreLabel.BackColor = Color.Honeydew;
				scoreLabel.Location = new Point(38, y + 1);
				scoreLabel.Margin = new Padding(5);
				scoreLabel.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;

				Label levelLabel = new Label();
				levelLabel.Text = parts[3];
				levelLabel.AutoSize = false;
				levelLabel.Width = 90;
				levelLabel.Height = 28;
				levelLabel.TextAlign = ContentAlignment.TopRight;
				levelLabel.Location = new Point(525, y);
				levelLabel.Margin = new Padding(5);
				levelLabel.Anchor = (AnchorStyles.Top | AnchorStyles.Right);

				Controls.Add(placeLabel);
				Controls.Add(scoreLabel);
				Controls.Add(levelLabel);
				});
		}

		// Saves the high score list on close
		private void HighScoresForm_FormClosed(object sender, FormClosedEventArgs e) {
			stats.save();
		}

		private void closeButton_Click(object sender, EventArgs e) {
			Close();
		}
	}
}
