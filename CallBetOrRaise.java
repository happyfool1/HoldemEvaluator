package holdemevaluator;

import java.math.BigDecimal;

public class CallBetOrRaise {
	public static void bet(int player, BigDecimal amount) {
		final var stack = GameControl.stackBD[player];

		if (stack.compareTo(amount) < 0) {
			System.out.println(new StringBuilder().append("Player ").append(player)
					.append(" does not have enough chips to bet ").append(amount).toString());
			return;
		}

		GameControl.stackBD[player] = stack.subtract(amount);
		GameControl.moneyInBD[player] = GameControl.moneyInBD[player].add(amount);
		GameControl.betNowBD = GameControl.betNowBD.add(amount);
		GameControl.potBD = GameControl.potBD.add(amount);
	}

	public static void call(int player) {
		final var requiredCallAmount = GameControl.betNowBD.subtract(GameControl.moneyInBD[player]);
		final var stack = GameControl.stackBD[player];

		if (stack.compareTo(requiredCallAmount) < 0) {
			System.out.println(new StringBuilder().append("Player ").append(player)
					.append(" does not have enough chips to call").toString());
			return;
		}

		GameControl.stackBD[player] = stack.subtract(requiredCallAmount);
		GameControl.moneyInBD[player] = GameControl.moneyInBD[player].add(requiredCallAmount);
		GameControl.potBD = GameControl.potBD.add(requiredCallAmount);
	}

	public static void main(String[] args) {
		// Example usage
		// Initialize stacks
		for (int i = 0; i < EvalData.PLAYERS; i++) {
			GameControl.stackBD[i] = new BigDecimal(100);
			GameControl.moneyInBD[i] = EvalData.zeroBD;
		}

		// Player 0 bets 10
		bet(0, new BigDecimal(10));
		System.out.println("Player 0 bets 10");

		// Player 1 calls
		call(1);
		System.out.println("Player 1 calls");

		// Print stacks and pot
		for (int i = 0; i < EvalData.PLAYERS; i++) {
			System.out.println(new StringBuilder().append("Player ").append(i).append(" stack: ")
					.append(GameControl.stackBD[i]).toString());
		}
		System.out.println("Pot: " + GameControl.potBD);
	}

}
