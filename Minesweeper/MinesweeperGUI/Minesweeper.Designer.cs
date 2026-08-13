namespace MinesweeperGUI {
	partial class Minesweeper {
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing) {
			if (disposing && (components != null)) {
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent() {
			this.components = new System.ComponentModel.Container();
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Minesweeper));
			this.timeLabel = new System.Windows.Forms.Label();
			this.gamePanel = new System.Windows.Forms.Panel();
			this.gameTick = new System.Windows.Forms.Timer(this.components);
			this.SuspendLayout();
			// 
			// timeLabel
			// 
			this.timeLabel.Dock = System.Windows.Forms.DockStyle.Top;
			this.timeLabel.Font = new System.Drawing.Font("Segoe Print", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.timeLabel.Location = new System.Drawing.Point(0, 0);
			this.timeLabel.Name = "timeLabel";
			this.timeLabel.Size = new System.Drawing.Size(578, 29);
			this.timeLabel.TabIndex = 0;
			this.timeLabel.Text = "0:0:0";
			this.timeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// gamePanel
			// 
			this.gamePanel.AutoSize = true;
			this.gamePanel.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
			this.gamePanel.Location = new System.Drawing.Point(5, 32);
			this.gamePanel.Name = "gamePanel";
			this.gamePanel.Size = new System.Drawing.Size(0, 0);
			this.gamePanel.TabIndex = 1;
			// 
			// gameTick
			// 
			this.gameTick.Enabled = true;
			this.gameTick.Interval = 1000;
			this.gameTick.Tick += new System.EventHandler(this.gameTick_Tick);
			// 
			// Minesweeper
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.AutoSize = true;
			this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
			this.BackColor = System.Drawing.Color.Honeydew;
			this.ClientSize = new System.Drawing.Size(578, 518);
			this.Controls.Add(this.gamePanel);
			this.Controls.Add(this.timeLabel);
			this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F);
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
			this.MaximizeBox = false;
			this.Name = "Minesweeper";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Minesweeper";
			this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Minesweeper_FormClosed);
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Label timeLabel;
		private System.Windows.Forms.Panel gamePanel;
		private System.Windows.Forms.Timer gameTick;
	}
}