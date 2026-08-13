// Class written for CST-250 by Liam Emra. This is my own work.
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Text;
using System.Threading.Tasks;

namespace MinesweeperBase {
	internal class PlayerStats : IComparable<PlayerStats> {
		// Properties
		public string name {get;}
		public int hours {get; }
		public int minutes {get; }
		public int seconds {get; }
		public int level {get;}

		// Constructor
		public PlayerStats(string name, int hours, int minutes, int seconds, int level) {
			this.name = name;
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
			this.level = level;
		}

		// IComparable.CompareTo implementation
		// Sorts by level (descending), then time (ascending), then name (ascending)
		public int CompareTo(PlayerStats other) {
			if (level.CompareTo(other.level) != 0) {
				return -1 * level.CompareTo(other.level);
			} else if (hours.CompareTo(other.hours) != 0) { 
				return hours.CompareTo(other.hours);
			} else if (minutes.CompareTo(other.minutes) != 0) {
				return minutes.CompareTo(other.minutes);
			} else if (seconds.CompareTo(other.seconds) != 0) {
				return seconds.CompareTo(other.seconds);
			} else {
				return name.CompareTo(other.name);
			}
		}
	}
}
