package uk.ac.ox.softeng.dpa.behavioral;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Iterator pattern
 *
 */
public class IteratorDemo {
	public static void main(String[] args) {
		System.out.println("foreach demo:");
		for (int i : List.And(List.Between(0, 10), List.Between(100, 110))) {
			System.out.println(i);
		}
		
		Node<Integer> root = Tree.Node(Tree.Leaf(12), 4711, Tree.Leaf(19));
		System.out.println("inorder traversal (root):");
		for (int i : Tree.Inorder(root)) {
			System.out.println(i);
		}
		System.out.println("filtering (root):");
		Predicate<Integer> predicate = new Predicate<Integer>() {
			@Override
			public boolean test(Integer elem) {
				return elem > 100;
			}
		};
		for (int i : Tree.Filter(root, predicate)) {
			System.out.println(i);
		}
		System.out.println("preorder traversal (root):");
		for (int i : Tree.Preorder(root)) {
			System.out.println(i);
		}
		
		Node<String> pedigree =
				Tree.Node(Tree.Node(Tree.Leaf("Amy"),
						            "Claire",
						            Tree.Leaf("Brian")),
						  "Graham",
						  Tree.Node(Tree.Leaf("Denise"),
								    "Felix",
								    Tree.Leaf("Eric")));
		System.out.println("inorder traversal (pedigree):");
		for (String s : Tree.Inorder(pedigree)) {
			System.out.println(s);
		}
		
		System.out.println("internal iterator (pedigree):");
		pedigree.map(new Action<String>() {
			@Override
			public void run(String elem) {
				System.out.println(elem);
			}
		});
	}
}

class List {
	public static Iterable<Integer> Between(final int left, final int right) {
		return new Iterable<Integer>() {
			@Override
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {
					int i = left;
					@Override
					public boolean hasNext() {
						return i < right;
					}
					@Override
					public Integer next() {
						if (hasNext()) {
							return i++;
						} else {
							throw new NoSuchElementException();
						}
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public static <E> Iterable<E> Empty() {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					@Override
					public boolean hasNext() {
						return false;
					}
					@Override
					public E next() {
						throw new NoSuchElementException();
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public static <E> Iterable<E> Single(final E elem) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					boolean hasNext = true;
					@Override
					public boolean hasNext() {
						return hasNext;
					}
					@Override
					public E next() {
						if (hasNext) {
							hasNext = false;
							return elem;
						} else {
							throw new NoSuchElementException();
						}
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public static <E> Iterable<E> And(final Iterable<E> fst, final Iterable<E> snd) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					Iterator<E> fstI = fst.iterator();
					Iterator<E> sndI = snd.iterator();
					@Override
					public boolean hasNext() {
						return fstI.hasNext() || sndI.hasNext();
					}
					@Override
					public E next() {
						if (fstI.hasNext()) {
							return fstI.next();
						} else {
							return sndI.next();
						}
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}

interface Action<E> {
	void run(E elem);
}

interface Mapable<E> {
	void map(Action<E> action);
}

class Node<E> implements Mapable<E> {
	
	public Node<E> left;
	public E label;
	public Node<E> right;
	
	public Node(Node<E> left, E label, Node<E> right) {
		this.left  = left;
		this.label = label;
		this.right = right;
	}
	
	public void map(Action<E> action) {
		if (left != null) {
			left.map(action);
		}
		action.run(label);
		if (right != null) {
			right.map(action);
		}
	}
}

interface Predicate<E> {
	boolean test(E elem);
}

class Tree {
	public static <E> Node<E> Leaf(E label) {
		return new Node<E>(null, label, null);
	}
	
	public static <E> Node<E> Node(Node<E> left, E label, Node<E> right) {
		return new Node<E>(left, label, right);
	}
	
	public static <E> Iterable<E> Inorder(final Node<E> root) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					Iterator<E> saved;
					{
						if (root != null)
							saved = Inorder(root.left).iterator();
					};
					boolean inRight = false;
					@Override
					public boolean hasNext() {
						if (inRight) {
							return saved.hasNext();
						} else {
							return root != null;
						}
					}
					@Override
					public E next() {
						if (root == null) {
							throw new NoSuchElementException();
						} else if (inRight) {
							return saved.next();
						} else {
							if (saved.hasNext()) {
								return saved.next();
							} else {
								inRight = true;
								saved = Inorder(root.right).iterator();
								return root.label;
							}
						}
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public static <E> Iterable<E> Filter(final Node<E> root, final Predicate<E> predicate) {
		return new Iterable<E>() {
			@Override
			public Iterator<E> iterator() {
				return new Iterator<E>() {
					Iterator<E> saved;
					{
						if (root != null) {
							saved = Filter(root.left, predicate).iterator();
						}
					};
					boolean inRight = false;
					@Override
					public boolean hasNext() {
						if (inRight) {
							return saved.hasNext();
						} else if (root == null) {
							return false;
						} else {
							return (saved.hasNext() || predicate.test(root.label) || Filter(root.right, predicate).iterator().hasNext());
						}
					}
					@Override
					public E next() {
						if (root == null) {
							throw new NoSuchElementException();
						} else if (inRight) {
								return saved.next();
						} else {
							if (saved.hasNext()) {
								return saved.next();
							} else {
								inRight = true;
								saved = Filter(root.right, predicate).iterator();
								if (predicate.test(root.label)) {
									return root.label;
								} else {
									return saved.next();
								}
							}
						}
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	public static <E> Iterable<E> Preorder(final Node<E> root) {
		if (root == null) {
			return List.Empty();
		} else {
			return List.And(List.Single(root.label),
					        List.And(Preorder(root.left),
					        		 Preorder(root.right)));
		}
	}
}