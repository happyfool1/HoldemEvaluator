package holdemevaluator;

/*- *********************************************************************************************
 * These are flops that I analyzed for wet/dry and know the results.
 * I use them to test my wet/dry analysis.
 * There are also PIO flops.
 * 
 * @author PEAK_
 * 
 **********************************************************************************************/
public class StaticFlops implements Constants {

	static String[] wetdry = { "AhThKc", "7cKh3h", "7cKh3h", "6c8d5s", "8cAc7c", "3cQhQs", "8h7h6h", "9s7s5c", "Ah2hQc",
			"KdTs9c", "9cQs7s", "Th2s5h", "2dQs9c", "8h9d8c", "2s7hJs", "Jc6cAs", "6dJc6h", "JcTh9s", "4h3s5c",
			"5d8s5c", "8hTc3c", "9d9h7c", "4s3cAh", "Ts8d3s", "7s4dQc", "8d9c3d", "3sTc9c", "5h7c9c", "3h2dAh",
			"6d2s3s", "2s2h6d", "6c6s2h", "5dQs9d", "2dJcAd", "AsQh9c", "Jc7h2d", "5d7c3h", "2c2h7d", "4s5cKh",
			"5cQdAh", "2s2c5s", "5d8s2d", "6d7h3c", "4c2dJd", "JhTs7s", "6c8sKc", "5hTdKs", "QdJd9h", "Ts3c4d",
			"6s8sTd", "JdAdQd", "9h6hQs", "JsTd6h", "Qc4dQs", "Jd7s9c", "3hAd5s", "9d3h5c", "5hKs9h", "Jh3s5c",
			"9s6d3d", "QcKh3s", "2c5c6d", "Qd3h6h", "6sAsJs", "5hAs2h", "3h8c4d", "KhAh9h", "9s8sTc", "2c4s7h",
			"Ts6s3h", "TsKs8c", "4cTcQc", "8cTc4d", "4sQd7h", "2s6s6h", "Qd3hTh", "2sQs5d", "9s6h5h", "QcJdJh",
			"6d7c8d", "Th4c5h", "5h5c7c", "2c9cQc", "6dTd3d", "8h9s9d", "Th4c3h", "5cJs7c", "6s8d4h", "8s2cKh",
			"9cQs8d", "TdKc9d", "Qh8s8h", "8s8hQs", "3s7h4h", "Kc8dQh", "Kd4hAc", "Jh9sQs", "3c3sKs", "2h5sAc",
			"7cTsJc", "5h4hQd", "9sJd7h", "6s9c2c", "4d6dAs", "Qh9s5c", "Ad9c3c", "Kc5sTs", "7hAhQh", "4s6s8c",
			"8cKs4h", "3h2cTh", "AsTh9c", "4h4d9c", "Ah6h7c", "3cKh5c", "2s6s4h", "3s4d4c", "KhTh3d", "4s5h5c",
			"7sKc9s", "6hKc2h", "5s4s7d", "Jd8hAd", "QhKs5d", "JcAh6c", "8dJhKs", "JdQc3c", "3s3dKs", "7s7d6s",
			"QsQd7s", "2d3sAs", "2s4d8c", "2s5dQc", "2s6dQc", "2d9sKs", "2sQsKd", "3s5d8c", "3sTdJc", "3sJsAd",
			"4s6sJd", "4s9dTc", "4sTsJd", "4dTsKs", "5s6dTc", "5s6dAc", "5d7s9s", "5s9sKd", "7d8sTs", "7d8sJs",
			"7sQsAs", "7sKsAd", "8s9dAc" };

	private StaticFlops() {
		throw new IllegalStateException("Utility class");
	}

}
