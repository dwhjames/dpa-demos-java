package uk.ac.ox.softeng.dpa.creational;

/**
 * @author Daniel W.H. James
 * 
 * A demo for the Singleton pattern
 *
 */
public class SingletonDemo {
	
	public static void main(String[] args) {
		Object state = new Object();
		
		Singleton.getInstance().set(state);
		System.out.println(state == Singleton.getInstance().get());
		
		LazySingleton.getInstance().set(state);
		System.out.println(state == LazySingleton.getInstance().get());
		
		NestedLazySingleton.getInstance().set(state);
		System.out.println(state == NestedLazySingleton.getInstance().get());
		
		EnumSingleton.INSTANCE.set(state);
		System.out.println(state == EnumSingleton.INSTANCE.get());
	}
}

interface IState {
	Object get();
	void set(Object state);
}

abstract class State implements IState {
	private Object state = null;

	@Override
	public Object get() {
		return this.state;
	}

	@Override
	public void set(Object state) {
		this.state = state;
	}
}

/**
 * Traditional Singleton
 * 
 * The instance is created as soon as the
 * class is initialized.
 *
 */
class Singleton extends State {
	private static final Singleton instance = new Singleton();
	
	private Singleton() { }
	
	public static Singleton getInstance() {
		return instance;
	}
}

/**
 * A lazier Singleton
 * 
 * The instance is created at the call of 
 * getInstance, which is a synchronized method.
 *
 */
class LazySingleton extends State {
	private static LazySingleton instance;
	
	private LazySingleton() { }
	
	public static synchronized LazySingleton getInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}

/**
 * A lock free lazy Singleton
 * 
 * The instance is created at the call of
 * getInstance, but without locking.
 *
 */
class NestedLazySingleton extends State {
	
	private NestedLazySingleton() { }
	
	private static class SingletonHolder {
		public static final NestedLazySingleton instance = new NestedLazySingleton();
	}
	
	public static NestedLazySingleton getInstance() {
		return SingletonHolder.instance;
	}
}

/**
 * A easy Singleton
 * 
 * The easiest way to make a singleton,
 * which has similar characteristics to the
 * first version (no laziness).
 * 
 */
enum EnumSingleton implements IState {
	INSTANCE;
	
	private Object state;

	@Override
	public Object get() {
		return this.state;
	}

	@Override
	public void set(Object state) {
		this.state = state;
	}
}
