package uk.ac.ox.softeng.dpa.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Composite pattern
 *
 */
public class CompositeDemo {

	public static void main(String[] args) {
		Paragraph p1 = new Paragraph("Object-oriented Design");
		Paragraph p2 = new Paragraph("Design Patterns");
		Section s = new Section("Courses");
		s.add(p1).add(p2);
		
		Section c = new Section("Programme");
		c.add(s);
		
		System.out.println(c.title + " contains Design: " + c.contains("Design"));
		System.out.println(c.title + " does not contain Haskell: " + !c.contains("Haskell"));
	}
}

/**
 * The interface for the objects in the composition
 *
 */
interface Document {
	boolean contains(String s);
}

/**
 * Represents leaf objects in the composition
 *
 */
class Paragraph implements Document {
	
	public String paragraph;
	
	public Paragraph(String paragraph) {
		this.paragraph = paragraph;
	}
	
	@Override
	public boolean contains(String s) {
		return paragraph.contains(s);
	}
}

/**
 * Represents a composite component, having children
 *
 */
class Section implements Document {
	
	public String title;
	private List<Document> children = new ArrayList<Document>();
	
	public Section(String title) {
		this.title = title;
	}
	
	public Section add(Document doc) {
		children.add(doc);
		return this;
	}
	
	@Override
	public boolean contains(String s) {
		for (Document child : children) {
			if (child.contains(s))
				return true;
		}
		return false;
	}
}
