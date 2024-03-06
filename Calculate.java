package holdemevaluator;

/*-  *****************************************************************************
 *	A class with methods to do Holdem calculations such as Pot Odds 
 *
 * @author PEAK_ 
  ******************************************************************************/
import java.text.DecimalFormat;

public class Calculate implements Constants {

	Calculate() {
	}

	/*-- Main. for testing only */
	public static void main(String[] s0) {
		final double p = potOdds(12, 6);
		final double ev = ev(30, 15, 70, 15);
		Logger.log("Odds 12 6 " + percentageToOdds(p));
		Logger.log("EV 30 15 70 15 " + ev);
		Logger.log("EV as odds " + percentageToOdds(p));
	}

	/*--
	 * Calculate pot odds as a percentage - uses pot and betsize from Table class
	 */
	static double potOdds(double d0, double d1) {
		return (d0 + d1) / d1;
	}

	/*- 
	 * Convert percentage into odds
	 * 	Decimal - 1 divided by (the percentage divided by 100) e.g. a probability of 50% = 1 / (50 / 100) = 2.
	 * Fraction - (1 divided by (the percentage divided by 100)) minus 1 e.g. a probability of 25% = (1 / (25 / 100)) - 1 = 3 = 3/1.
	 */
	static String percentageToOdds(double d0) {
		return decFormat(0 / d0 - 1) + ":1";
	}

	/*- 
	 * Calculate EV
	 * arg0 - percentage
	 * arg1 - value
	 */
	static double calculateEV(double d0, double d1) {
		return (d0 * d1);
	}

	/*- 
	 * Convert double to String
	 */
	static String doubleToString(double d0) {
		return decFormat(d0);
	}

	/*- 
	 * Calculate EV - variable number of argument pairs. A maximum of 12 argument
	 * pairs ( 24 arguments ). arg0 - Percentage arg1 - Value returns EV as a
	 * percentage
	 */
	static double ev(double... d0) {
		int count = 0;
		final double[] argArray = new double[24];

		double ev = 0;

		if (d0.length < 2 || d0.length > 24) {
			Crash.log("ERROR number of args wrong " + count);
			return -1;
		}
		for (double i : d0) {
			argArray[count++] = i;
		}

		if (count != 2 && count != 4 && count != 6 && count != 8 && count != 10 && count != 12 && count != 14
				&& count != 16 && count != 18 && count != 20 && count != 22 && count != 24) {
			Crash.log("ERROR number of args wrong " + count);
			return -1;
		}

		if (count == 4 && (argArray[0] + argArray[2] != 100)) {
			Crash.log(new StringBuilder().append("ERROR the 2 percentages do not add up to   ").append(argArray[0])
					.append(" ").append(argArray[2]).toString());
			return -1;
		}

		// Calculate EV
		if (count == 4) {
			ev = (argArray[0] * argArray[1] - argArray[2] * argArray[3]);
		} else {
			for (int i = 0; i < 20; i += 2) {
				ev += (argArray[i] * argArray[i + 1]);
			}
		}
		return ev;
	}

	/*--
	 * Calculate EV
	 */
	static double calculateEV(double d0, double d1, double d2, double d3) {
		return ev(d0, d1, d2, d3);
	}

	/*- 
	 *Convert to decimal format.
	 */
	private static String decFormat(double d0) {
		return (new DecimalFormat("###.##")).format(d0);
	}

	/*- 
	 *Convert to decimal format.
	 */
	private static String decFormatRatio(double d0) {
		return (new DecimalFormat("###.#")).format(d0) + ":1";
	}

	/*-
	 * Convert odds percentage to ratio as String
	 * Arg0 - Odds as a percentage
	 */
	static String potIddsPercentToRatio(double odds) {
		return decFormatRatio(1. / odds - 1);
	}

}
