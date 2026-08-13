// Class written for CST-250 by Liam Emra. This is my own work.
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace MinesweeperBase {
	public class PlayerStatList {
		private List<PlayerStats> stats;
		private string path;

		// Turns a file (with line format name|hours:minutes:seconds|level) into a list of PlayerStats
		public PlayerStatList(string path) {
			this.path = path;
			stats = new List<PlayerStats>();
			if (File.Exists(path)) {
				List<string> lines = File.ReadAllLines(path).ToList();
				foreach (string line in lines) {
					string[] parts = line.Split('|');
					string[] time = parts[1].Split(':');
					stats.Add(new PlayerStats(parts[0], int.Parse(time[0]), int.Parse(time[1]), int.Parse(time[2]), int.Parse(parts[2])));
				}
			}
		}

		// Gets the five best scores in the requested level and sends them to the print delegate
		// If level is 4, it will send the five best scores in the list (sorted by level, time, then name)
		public void printLevel(int level, Action<string, int> print) {
			List<PlayerStats> levelStats = new List<PlayerStats>();
			if (level == 4) {
				levelStats = (
					from stat in stats
					orderby stat ascending
					select stat).ToList();
			} else {
				levelStats = (
					from stat in stats
					where stat.level == level
					orderby stat ascending
					select stat).ToList();
			}

			for (int i = 0; i < levelStats.Count && i < 5; i++) {
				string printed = "";
				if (levelStats[i].seconds > 10) {
					printed = $"{i+1}.|" +
						$"Name: {levelStats[i].name}|" +
						$"Time: {levelStats[i].hours}:{levelStats[i].minutes}:{levelStats[i].seconds}|" +
						$"Level: {levelStats[i].level}";
				} else {
					printed = $"{i+1}.|" +
						$"Name: {levelStats[i].name}|" +
						$"Time: {levelStats[i].hours}:{levelStats[i].minutes}:0{levelStats[i].seconds}|" +
						$"Level: {levelStats[i].level}";
				}
				print(printed, i);
			}
		}

		// Adds a new PlayerStats object to the list
		public void add(string name, int hours, int minutes, int seconds, int level) {
			stats.Add(new PlayerStats(name, hours, minutes, seconds, level));
		}

		// Saves the list in the provided path. Alternate paths are not supported, nor should they be nessicary.
		public void save() {
			string data = "";
			for (int i = 0; i < stats.Count; i++) {
				data += $"{stats[i].name}|{stats[i].hours}:{stats[i].minutes}:{stats[i].seconds}|{stats[i].level}";
				if (i <= stats.Count - 1) data += "\r\n";
			}
			File.WriteAllText(path, data);
		}
	}
}
