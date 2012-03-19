package uk.ac.ox.softeng.dpa.creational;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Builder pattern
 *
 */
public class BuilderDemo {
	
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
		Drink drink = new DrinkBuilder(new Coffee()).build();
		printDrink(drink);
		
		drink = new DrinkBuilder(new Tea()).addMilk().build();
		printDrink(drink);
		
		drink = new DrinkBuilder(new Coffee()).addMilk().addIce().build();
		printDrink(drink);
		
		drink = new DrinkBuilder(new Tea()).setDecaffe().build();
		printDrink(drink);
	}
}

class DrinkBuilder {
	private Drink drink;
	
	public DrinkBuilder(Drink drink) {
		this.drink = drink;
	}
	
	public DrinkBuilder addIce() {
		drink = new Ice(drink);
		return this;
	}
	
	public DrinkBuilder addMilk() {
		drink = new Milk(drink);
		return this;
	}
	
	public DrinkBuilder setDecaffe() {
		drink = new Decaffe(drink);
		return this;
	}
	
	public Drink build() {
		return drink;
	}
}

interface Drink {
	boolean isHot();
	boolean isCaffeinated();
	double cost();
}

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

class Tea implements Drink {

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
		return 0.5;
	}
	
	@Override
	public String toString() {
		return "tea";
	}
}

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

class Decaffe extends DrinkDecorator {

	public Decaffe(Drink drink) {
		super(drink);
	}
	
	@Override
	public boolean isCaffeinated() {
		return false;
	}
}