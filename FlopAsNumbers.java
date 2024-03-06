package holdemevaluator;

/*
 * This class contains an instance of a Flop in the format used by Flops1755.
 * The flop is represented by 3 numbers 0 - 51 that correspond to a 52 card deck.
 * 
 * 
  * @author PEAK_
 */
public class FlopAsNumbers implements Constants {
	int card3 = -1;
	int card4 = -1;
	int card5 = -1;

	// Constructor
	FlopAsNumbers() {
	}

	// Constructor
	FlopAsNumbers(int index) {
		this.card3 = Flops1755.flops[index][0];
		this.card4 = Flops1755.flops[index][1];
		this.card5 = Flops1755.flops[index][2];
	}

	/*- String value for flop  */
	@Override
	public String toString() {
		return new StringBuilder().append((new Card(this.card3)).toString()).append((new Card(this.card4)).toString())
				.append(new Card(this.card5).toString()).toString();
	}

}
