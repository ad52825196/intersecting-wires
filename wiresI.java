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
 * This class computes the number of intersection points.
 * 
 * @author Zhen Chen
 *
 */

public class wiresI {
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final int MAX_NUMBER_OF_WIRES = 50000;
	// number of wires
	private static int n;
	private static Wire[] wires = new Wire[MAX_NUMBER_OF_WIRES];

	private static class Wire implements Comparable<Wire> {
		private int start;
		private int end;

		public Wire(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public final int getStart() {
			return start;
		}

		public final int getEnd() {
			return end;
		}

		@Override
		public int compareTo(Wire obj) {
			// ascending order by start point
			return this.getStart() - obj.getStart();
		}
	}

	private static class InversionAndWires {
		private int inversion;
		private Wire[] wires;

		public InversionAndWires(int inversion, Wire[] wires) {
			this.inversion = inversion;
			this.wires = wires;
		}

		public int getInversion() {
			return inversion;
		}

		public Wire[] getWires() {
			return wires;
		}
	}

	private static void getWires() throws IOException {
		String line;
		String[] list;
		for (int i = 0; i < n; i++) {
			line = reader.readLine();
			list = line.split("\\s");
			wires[i] = new Wire(Integer.parseInt(list[0]), Integer.parseInt(list[1]));
		}
	}

	private static InversionAndWires sortAndCount(Wire[] wires, int start, int end) {
		if (end - start <= 1) {
			return new InversionAndWires(0, Arrays.copyOfRange(wires, start, end));
		}
		InversionAndWires left = sortAndCount(wires, start, (start + end) / 2);
		InversionAndWires right = sortAndCount(wires, (start + end) / 2, end);
		return mergeAndCount(left, right);
	}

	private static InversionAndWires mergeAndCount(InversionAndWires left, InversionAndWires right) {
		Wire[] leftWires = left.getWires();
		Wire[] rightWires = right.getWires();
		Wire[] wires = new Wire[leftWires.length + rightWires.length];
		int inversion = 0;

		int i = 0;
		int j = 0;
		int k = 0;
		while (i < leftWires.length && j < rightWires.length) {
			if (leftWires[i].getEnd() < rightWires[j].getEnd()) {
				wires[k] = leftWires[i++];
			} else {
				wires[k] = rightWires[j++];
				inversion += leftWires.length - i;
			}
			k++;
		}
		while (i < leftWires.length) {
			wires[k++] = leftWires[i++];
		}
		while (j < rightWires.length) {
			wires[k++] = rightWires[j++];
		}

		return new InversionAndWires(left.getInversion() + right.getInversion() + inversion, wires);
	}

	public static void main(String[] args) {
		try {
			int numberOfTestCases = Integer.parseInt(reader.readLine());
			for (int i = 1; i <= numberOfTestCases; i++) {
				n = Integer.parseInt(reader.readLine());
				getWires();
				Arrays.sort(wires, 0, n);
				System.out.printf("Case #%d: %d%n", i, sortAndCount(wires, 0, n).getInversion());
			}
		} catch (Exception e) {
			System.exit(0);
		}
	}

}
