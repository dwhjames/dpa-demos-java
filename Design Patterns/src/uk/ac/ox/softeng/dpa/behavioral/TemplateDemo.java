package uk.ac.ox.softeng.dpa.behavioral;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Template pattern
 *
 */
public class TemplateDemo {
	
	public static void main(String[] args) {
		Collatz collatz = new Collatz();
		
		for (int i = 1; i <= 10; i++) {
			System.out.printf("Collatz of %d took %d steps\n", i, collatz.run(i));
		}
	}
}

abstract class Template {
	
	abstract boolean validInput(int i);
	abstract boolean loopPredicate(int i);
	abstract boolean stepPredicate(int i);
	abstract int     stepThen(int i);
	abstract int     stepElse(int i);
	
	public final int run(int i) {
		int steps = -1;
		if (validInput(i)) {
			steps++;
			while (loopPredicate(i)) {
				if (stepPredicate(i)) {
					i = stepThen(i);
				} else {
					i = stepElse(i);
				}
				steps++;
			}
		}
		return steps;
	}
}

class Collatz extends Template {

	@Override
	boolean validInput(int i) {
		return i > 0;
	}

	@Override
	boolean loopPredicate(int i) {
		return i != 1;
	}

	@Override
	boolean stepPredicate(int i) {
		return i % 2 == 0;
	}

	@Override
	int stepThen(int i) {
		return i / 2;
	}

	@Override
	int stepElse(int i) {
		return 3*i + 1;
	}
}
