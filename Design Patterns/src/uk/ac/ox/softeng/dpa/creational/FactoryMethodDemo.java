package uk.ac.ox.softeng.dpa.creational;

/**
 * @author Daniel W.H. James
 * 
 * Demo for the Factory Method pattern
 *
 */
public class FactoryMethodDemo {
	
	public static void main(String[] args) {
		Complex cart = Complex.fromCartesian(3.0, 4.0);
		System.out.printf("real: %f, img: %f, abs: %f, arg: %f\n", cart.real(), cart.img(), cart.abs(), cart.arg());
		
		Complex polar = Complex.fromPolar(5, Math.PI / 6.0);
		System.out.printf("real: %f, img: %f, abs: %f, arg: %f\n", polar.real(), polar.img(), polar.abs(), polar.arg());
	}
}

/**
 * A basic representation of complex numbers
 *
 */
class Complex {
	
	private double real, imaginary;
	
	private Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public static Complex fromCartesian(double real, double imaginary) {
		return new Complex(real, imaginary);
	}
	
	public static Complex fromPolar(double absolute, double argument) {
		return new Complex(absolute * Math.cos(argument), absolute * Math.sin(argument));
	}
	
	public double real() {
		return real;
	}
	
	public double img() {
		return imaginary;
	}
	
	public double abs() {
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	public double arg() {
		return Math.atan2(imaginary, real);
	}
}
