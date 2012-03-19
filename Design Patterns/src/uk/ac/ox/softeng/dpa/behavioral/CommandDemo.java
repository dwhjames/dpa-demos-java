package uk.ac.ox.softeng.dpa.behavioral;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Command pattern
 *
 */
public class CommandDemo {

	public static void main(String[] args) {
		final Calculator calculator = new Calculator();
		User user = new User();
		
		user.doIt(new Command() {
			@Override
			public void execute() {
				calculator.operation(Operator.ADD, 4711);
			}
		});
		user.doIt(new Command() {
			@Override
			public void execute() {
				calculator.operation(Operator.SUB, 815);
			}
		});
		user.redoIt();
		user.doIt(new Command() {
			@Override
			public void execute() {
				calculator.operation(Operator.MUL, 2);
			}
		});
		user.redoIt();
	}

}

enum Operator { ADD, SUB, MUL, DIV }


/**
 * The command interface.
 * 
 * There are no explicit concrete commands in
 * this demo: anonymous classes are used instead
 *
 */
interface Command {
	void execute();
}


/**
 * A receiver with an action that will
 * be wrapped up in a command
 *
 */
class Calculator {
	private int val = 0;
	
	public void operation(Operator operator, int operand) {
		switch (operator)
		{
		case ADD: val += operand; break;
		case SUB: val -= operand; break;
		case MUL: val *= operand; break;
		case DIV: val /= operand; break;
		}
		System.out.println("value = " + val);
	}
}


/**
 * The invoker that will execute a command
 *
 */
class User {
	Command current;
	
	public void doIt(Command command) {
		current = command;
		current.execute();
	}
	
	public void redoIt() {
		current.execute();
	}
}
