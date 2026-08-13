namespace MinesweeperGUI {
	partial class SelectLevel {
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
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SelectLevel));
			this.groupBox1 = new System.Windows.Forms.GroupBox();
			this.hardRadio = new System.Windows.Forms.RadioButton();
			this.normalRadio = new System.Windows.Forms.RadioButton();
			this.easyRadio = new System.Windows.Forms.RadioButton();
			this.startButton = new System.Windows.Forms.Button();
			this.scoreButton = new System.Windows.Forms.Button();
			this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
			this.groupBox1.SuspendLayout();
			this.tableLayoutPanel1.SuspendLayout();
			this.SuspendLayout();
			// 
			// groupBox1
			// 
			this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.groupBox1.Controls.Add(this.hardRadio);
			this.groupBox1.Controls.Add(this.normalRadio);
			this.groupBox1.Controls.Add(this.easyRadio);
			this.groupBox1.Font = new System.Drawing.Font("Segoe Script", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.groupBox1.Location = new System.Drawing.Point(52, 30);
			this.groupBox1.Name = "groupBox1";
			this.groupBox1.Size = new System.Drawing.Size(330, 177);
			this.groupBox1.TabIndex = 0;
			this.groupBox1.TabStop = false;
			this.groupBox1.Text = "Choose a Difficulty";
			// 
			// hardRadio
			// 
			this.hardRadio.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.hardRadio.AutoSize = true;
			this.hardRadio.Font = new System.Drawing.Font("Segoe Print", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.hardRadio.Location = new System.Drawing.Point(29, 140);
			this.hardRadio.Name = "hardRadio";
			this.hardRadio.Size = new System.Drawing.Size(71, 32);
			this.hardRadio.TabIndex = 2;
			this.hardRadio.Text = "Hard";
			this.hardRadio.UseVisualStyleBackColor = true;
			// 
			// normalRadio
			// 
			this.normalRadio.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.normalRadio.AutoSize = true;
			this.normalRadio.Font = new System.Drawing.Font("Segoe Print", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.normalRadio.Location = new System.Drawing.Point(29, 86);
			this.normalRadio.Name = "normalRadio";
			this.normalRadio.Size = new System.Drawing.Size(90, 32);
			this.normalRadio.TabIndex = 1;
			this.normalRadio.Text = "Normal";
			this.normalRadio.UseVisualStyleBackColor = true;
			// 
			// easyRadio
			// 
			this.easyRadio.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.easyRadio.AutoSize = true;
			this.easyRadio.Font = new System.Drawing.Font("Segoe Print", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.easyRadio.Location = new System.Drawing.Point(29, 35);
			this.easyRadio.Name = "easyRadio";
			this.easyRadio.Size = new System.Drawing.Size(66, 32);
			this.easyRadio.TabIndex = 0;
			this.easyRadio.Text = "Easy";
			this.easyRadio.UseVisualStyleBackColor = true;
			// 
			// startButton
			// 
			this.startButton.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.startButton.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
			this.startButton.Location = new System.Drawing.Point(232, 3);
			this.startButton.Margin = new System.Windows.Forms.Padding(10, 3, 10, 3);
			this.startButton.Name = "startButton";
			this.startButton.Size = new System.Drawing.Size(202, 42);
			this.startButton.TabIndex = 1;
			this.startButton.Text = "Start";
			this.startButton.UseVisualStyleBackColor = true;
			this.startButton.Click += new System.EventHandler(this.startButton_Click);
			// 
			// scoreButton
			// 
			this.scoreButton.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
			this.scoreButton.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
			this.scoreButton.Location = new System.Drawing.Point(10, 3);
			this.scoreButton.Margin = new System.Windows.Forms.Padding(10, 3, 10, 3);
			this.scoreButton.Name = "scoreButton";
			this.scoreButton.Size = new System.Drawing.Size(202, 42);
			this.scoreButton.TabIndex = 2;
			this.scoreButton.Text = "High Scores";
			this.scoreButton.UseVisualStyleBackColor = true;
			this.scoreButton.Click += new System.EventHandler(this.scoreButton_Click);
			// 
			// tableLayoutPanel1
			// 
			this.tableLayoutPanel1.ColumnCount = 2;
			this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.Controls.Add(this.scoreButton, 0, 0);
			this.tableLayoutPanel1.Controls.Add(this.startButton, 1, 0);
			this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Bottom;
			this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 233);
			this.tableLayoutPanel1.Name = "tableLayoutPanel1";
			this.tableLayoutPanel1.RowCount = 1;
			this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this.tableLayoutPanel1.Size = new System.Drawing.Size(444, 48);
			this.tableLayoutPanel1.TabIndex = 3;
			// 
			// SelectLevel
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(10F, 28F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = System.Drawing.Color.Honeydew;
			this.ClientSize = new System.Drawing.Size(444, 281);
			this.Controls.Add(this.tableLayoutPanel1);
			this.Controls.Add(this.groupBox1);
			this.Font = new System.Drawing.Font("Segoe Print", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.Margin = new System.Windows.Forms.Padding(4, 7, 4, 7);
			this.MinimumSize = new System.Drawing.Size(460, 320);
			this.Name = "SelectLevel";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			this.Text = "Select Level";
			this.groupBox1.ResumeLayout(false);
			this.groupBox1.PerformLayout();
			this.tableLayoutPanel1.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.RadioButton hardRadio;
		private System.Windows.Forms.RadioButton normalRadio;
		private System.Windows.Forms.RadioButton easyRadio;
		private System.Windows.Forms.Button startButton;
		private System.Windows.Forms.Button scoreButton;
		private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
	}
}

