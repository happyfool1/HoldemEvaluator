package holdemevaluator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * This class contains 1755 flops that represent all possible flops.
 * 
 * flop is designed for performance.
 * The  array is filled with data by FlopCombinations.
* Then it is written as a disk file to be read back by an application.
 * To find an flop simply index one element and use the three Card values.
 * 
 * The Card class is: 	int card;  int suit;	int value; String st = "";
 * The value in this array is card (0 - 51) and converts to Card as:
 * Card a = new Card(flop[0][0]);  Card b = new Card(flop[0][1]);  Card c = new Card(flop[0][2]); is a flop
* a.st is the String representation of Card a
 * card 0 is As, card 13 is Ac, card 26 is Ad 
* The three int values in one element is a flop
* private static int[][] flop = new int[1755][3]; 3 cards, value 0 - 51
* flop 0 13 26 
 * 
  * @author PEAK_
 */
public class Flops1755 implements java.io.Serializable {
	static final long serialVersionUID = 0x12D687;

	// That is it, just array.
	static int[][] flops = new int[1755][3];

	/*- **************************************************************************************************
	 * Return a flop as FlopAsNumbers
	 * 
	 * Arg0 - Index into array of flops
	   ***************************************************************************************************/
	static FlopAsNumbers getFlopAsNumbers(int index) {
		return new FlopAsNumbers(index);
	}

	/*-
	 * - Write Object to file - this
	 */
	void writeToFile(String path) {
		final var filename = path;
		// Saving of object in a file
		try (var file = new FileOutputStream(filename); var out = new ObjectOutputStream(file)) {
			// Method for serialization of object
			out.writeObject(this);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*- ****************************************************************************
		 * Read a Range from a disk file. 
		 * Arg0 - The full path name.
		  *****************************************************************************/
	HandRange readFromFile(String path) {
		HandRange r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (HandRange) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return r;
		}
		return r;
	}

}
