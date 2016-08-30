import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Intersecting wires problem: There are many wires connecting ports on two
 * switches. Wires are straight lines and no two wires share the same port on a
 * switch. We want to know the number of intersection points created by these
 * wires. This is actually an inversion counting problem.
 * 
 * This class computes a list of wires that are the longest in length.
 * 
 * @author Zhen Chen
 *
 */

public class wiresL {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int MAX_NUMBER_OF_WIRES = 50000;
	// number of wires
	private static int n;
	private static Wire[] wires = new Wire[MAX_NUMBER_OF_WIRES];

	private static class Wire implements Comparable<Wire> {
		private int id;
		private int length;

		public Wire(int id, int start, int end) {
			this.id = id;
			this.length = Math.abs(start - end);
		}

		public final int getId() {
			return id;
		}

		public final int getLength() {
			return length;
		}

		@Override
		public int compareTo(Wire obj) {
			// descending order by length
			return obj.getLength() - this.getLength();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Wire) {
				return this.getLength() == ((Wire) obj).getLength();
			}
			return false;
		}
	}

	private static void getWires() throws IOException {
		String line;
		String[] list;
		for (int i = 0; i < n; i++) {
			line = reader.readLine();
			list = line.split("\\s");
			wires[i] = new Wire(i + 1, Integer.parseInt(list[0]), Integer.parseInt(list[1]));
		}
	}

	private static void output() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			if (i > 0 && !wires[i].equals(wires[i - 1])) {
				break;
			}
			sb.append(' ');
			sb.append(wires[i].getId());
		}
		System.out.println(sb);
	}

	public static void main(String[] args) {
		try {
			int numberOfTestCases = Integer.parseInt(reader.readLine());
			for (int i = 1; i <= numberOfTestCases; i++) {
				n = Integer.parseInt(reader.readLine());
				getWires();
				Arrays.sort(wires, 0, n);
				System.out.printf("Case #%d:", i);
				output();
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

}
