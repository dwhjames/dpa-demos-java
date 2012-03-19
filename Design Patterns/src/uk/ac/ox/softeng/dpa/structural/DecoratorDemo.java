package uk.ac.ox.softeng.dpa.structural;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Decorator pattern
 *
 */
public class DecoratorDemo {
	
	private static void printDrink(Drink drink) {
		StringBuilder s = new StringBuilder();
		s.append(drink.isHot() ? "A hot" : "An iced");
		if (!drink.isCaffeinated()) s.append(", decaffe");
		s.append(' ');
		s.append(drink.toString());
		s.append(" for $");
		s.append(drink.cost());
		System.out.println(s.toString());
	}
	
	public static void main(String[] args) {
		Drink drink = new Coffee();
		printDrink(drink);
		
		drink = new Milk(drink);
		printDrink(drink);
		
		drink = new Decaffe(drink);
		printDrink(drink);
		
		drink = new Ice(drink);
		printDrink(drink);
	}
}

/**
 * The basic interface
 *
 */
interface Drink {
	boolean isHot();
	boolean isCaffeinated();
	double cost();
}

/**
 * A base implementation
 *
 */
class Coffee implements Drink {

	@Override
	public boolean isHot() {
		return true;
	}

	@Override
	public boolean isCaffeinated() {
		return true;
	}

	@Override
	public double cost() {
		return 1.0;
	}
	
	@Override
	public String toString() {
		return "coffee";
	}
}

/**
 * An abstract decorator
 *
 */
abstract class DrinkDecorator implements Drink {
	
	protected Drink drink;
	
	public DrinkDecorator(Drink drink) {
		this.drink = drink;
	}

	@Override
	public boolean isHot() {
		return drink.isHot();
	}

	@Override
	public boolean isCaffeinated() {
		return drink.isCaffeinated();
	}

	@Override
	public double cost() {
		return drink.cost();
	}
	
	@Override
	public String toString() {
		return drink.toString();
	}
}

/**
 * A concrete decorator
 *
 */
class Milk extends DrinkDecorator {
	
	public Milk(Drink drink) {
		super(drink);
	}

	@Override
	public double cost() {
		return drink.cost() + 0.1;
	}
	
	@Override
	public String toString() {
		return drink.toString() + " with milk";
	}
}

/**
 * A concrete decorator
 *
 */
class Ice extends DrinkDecorator {
	
	public Ice(Drink drink) {
		super(drink);
	}
	
	@Override
	public boolean isHot() {
		return false;
	}
	
	@Override
	public double cost() {
		return drink.cost() + 0.2;
	}
}

/**
 * A concrete decorator
 *
 */
class Decaffe extends DrinkDecorator {

	public Decaffe(Drink drink) {
		super(drink);
	}
	
	@Override
	public boolean isCaffeinated() {
		return false;
	}
}
