package uk.ac.ox.softeng.dpa.creational;

/**
 * @author Daniel W.H. James
 * 
 * Demo for the Prototype pattern
 *
 */
public class PrototypeDemo {
	
	public static void main(String[] args) {
		System.out.println("Whole prototype: " + Whole.prototype);
		System.out.println("PartA prototype: " + PartA.prototype);
		System.out.println("PartB prototype: " + PartB.prototype);
		
		Whole w = Whole.prototype.clone();
		System.out.println("Whole clone: " + w);
		w.partA = PartA.prototype.clone();
		System.out.println("Whole clone with new part: " + w);
	}
}

/**
 * Generic interface for object cloning
 *
 * @param <T>
 */
interface Cloneable<T> {
	T clone();
}

class Whole implements Cloneable<Whole> {
	
	public static final Whole prototype = new Whole();
	static {
		prototype.partA = PartA.prototype;
		prototype.partB = PartB.prototype;
	}
	
	public PartA partA;
	public PartB partB;
	
	private Whole() { }
	
	public Whole clone() {
		Whole whole = new Whole();
		// Deep clone
		/* whole.partA = this.partA.clone();
		 * whole.partB = this.partB.clone();
		 */
		// Shallow clone
		whole.partA = this.partA;
		whole.partB = this.partB;
		return whole;
	}
	
	@Override
	public String toString() {
		return "Whole@" + Integer.toHexString(this.hashCode()) + "(" + partA.toString() + ", " + partB.toString() + ")";
	}
}

class PartA implements Cloneable<PartA> {
	
	public static final PartA prototype = new PartA();
	
	private PartA() { }
	
	public PartA clone() {
		return new PartA();
	}
	
	@Override
	public String toString() {
		return "PartA@" + Integer.toHexString(this.hashCode());
	}
}

class PartB implements Cloneable<PartB> {
	
	public static final PartB prototype = new PartB();
	
	private PartB() { }
	
	public PartB clone() {
		return new PartB();
	}
	
	@Override
	public String toString() {
		return "PartB@" + Integer.toHexString(this.hashCode());
	}
}

