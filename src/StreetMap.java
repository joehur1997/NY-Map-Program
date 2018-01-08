/*Joseph Hur
 * jhur3
 * Project 4
 */
public class StreetMap {

	public static void main(String[] args) {
		int length = args.length;
		String file = args[0];

		if (length == 1) {
			if (file.equals("ur.txt")) {
				GraphReader graph = new GraphReader(file, "HOEING", "HOEING", false, false, false);
			} else {
				GraphReader graph = new GraphReader(file, "i0", "i0", false, false, false);
			}
		} else if (length == 2) {
			if (file.equals("ur.txt")) {
				GraphReader graph = new GraphReader(file, "HOEING", "HOEING", true, false, false);
			} else if (args[1].equals("--show")) {
				GraphReader graph = new GraphReader(file, "i0", "i0", true, false, false);
			} else {
				System.out.println("The command line arguments input is invalid.");
			}
		} else if (length == 5) {
			if (args[1].equals("--show") && args[2].equals("--directions")) {
				GraphReader graph = new GraphReader(file, args[3], args[4], false, true, false);
			} else {
				System.out.println("The command line arguments input is invalid.");
			}
		} else if (length == 4) {
			if (args[1].equals("--directions")) {
				GraphReader graph = new GraphReader(file, args[2], args[3], false, false, true);
			} else {
				System.out.println("The command line arguments input is invalid.");
			}
		}
	}
}
