package uk.ac.ox.softeng.dpa.creational;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Abstract Factory pattern
 *
 */
public class AbstractFactoryDemo {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Creator[] creators =
			{
				new ConcreteCreatorA(),
				new ConcreteCreatorB(),
			};
		for (Creator creator : creators) {
			Product product = creator.create();
			System.out.println("Created " + product.getClass().getSimpleName());
		}
		
		ParametricCreator pcreator = new ParametricCreator();
		ProductId[] productIds = { ProductId.ProductA, ProductId.ProductB };
		for (ProductId productId : productIds) {
			Product product = pcreator.create(productId);
			System.out.println("Created " + product.getClass().getSimpleName());
		}
		
		Class[] types = { ConcreteProductA.class, ConcreteProductB.class};
		for (Class type : types) {
			GenericCreator.type = type;
			Product product = GenericCreator.create();
			System.out.println("Created " + product.getClass().getSimpleName());
		}
	}
}

/**
 * Abstract interface for objects to be created
 * 
 */
interface Product {}

/**
 * Abstract interface for creating objects
 *
 */
abstract class Creator {
	public abstract Product create();
	
	public void operation() {
		// ...
		// Product product = this.create();
		// ...
	}
}

class ConcreteProductA implements Product {}
class ConcreteProductB implements Product {}

class ConcreteCreatorA extends Creator {
	@Override
	public Product create() {
		return new ConcreteProductA();
	}
}

class ConcreteCreatorB extends Creator {
	@Override
	public Product create() {
		return new ConcreteProductB();
	}
}


enum ProductId { ProductA, ProductB }

/**
 * Alternative implementation:
 * Select concrete creator via an enumeration
 *
 */
class ParametricCreator {
	public Product create(ProductId id) {
		switch (id) {
			case ProductA: return new ConcreteProductA();
			case ProductB:
			default: return new ConcreteProductB();
		}
	}
}

/**
 * Alternative implementation:
 * Select concrete creator via reflection
 *
 */
class GenericCreator {
	public static Class<? extends Product> type = ConcreteProductA.class;
	
	public static Product create() {
		Product product = null;
		try {
			product = type.newInstance();
		} catch (IllegalAccessException ex) {
		} catch (InstantiationException ex) {
		}
		return product;
	}
}
