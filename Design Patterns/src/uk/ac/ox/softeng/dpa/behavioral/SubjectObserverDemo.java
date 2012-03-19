package uk.ac.ox.softeng.dpa.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Subject-Observer pattern
 *
 */
public class SubjectObserverDemo {
	
	public static void main(String[] args) {
		ConcreteSubject subject = new ConcreteSubject();
		ConcreteObserver observer = new ConcreteObserver();
		Disposable unsubscriber = subject.subscribe(observer);
		
		subject.setState(2010);
		subject.setState(2011);
		unsubscriber.dispose();
		subject.setState(2012);
	}

}

interface Observer<S> {
	void update(S state);
}

interface Disposable {
	void dispose();
}

abstract class Subject<S> {
	
	private List<Observer<S>> observers = new ArrayList<Observer<S>>();
	
	public abstract S getState();
	
	public abstract void setState(S state);
	
	public Disposable subscribe(Observer<S> observer)
	{
		if (! observers.contains(observer)) {
			observers.add(observer);
			observer.update(getState());
		}
		return new Unsubscriber(observer);
	}
	
	protected void tell() {
		for (Observer<S> observer : observers) {
			observer.update(this.getState());
		}
	}
	
	private class Unsubscriber implements Disposable {

		private Observer<S> observer;
		
		public Unsubscriber(Observer<S> observer) {
			this.observer = observer;
		}
		
		@Override
		public void dispose() {
			observers.remove(observer);
		}
		
	}
}

class ConcreteObserver implements Observer<Integer> {
	private int state = 0;
	
	public void update(Integer state) {
		this.state = state;
		show();
	}
	
	public void show() {
		System.out.println("Hi");
		System.out.println(state);
	}
}

class ConcreteSubject extends Subject<Integer> {
	
	private int state = 4711;

	@Override
	public Integer getState() {
		return state;
	}

	@Override
	public void setState(Integer state) {
		this.state = state;
		this.tell();
	}
}
