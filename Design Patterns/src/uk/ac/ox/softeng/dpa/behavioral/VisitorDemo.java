package uk.ac.ox.softeng.dpa.behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel W.H. James
 * 
 * Demo of the Visitor pattern
 *
 */
public class VisitorDemo {

	public static void main(String[] args) {
		Paragraph p1 = new Paragraph("Object-oriented Design");
		Paragraph p2 = new Paragraph("Design Patterns");
		Section s = new Section("Courses");
		s.add(p1).add(p2);
		
		Section c = new Section("Programme");
		c.add(s);
		
		c.accept(new Display());
		System.out.println();
		
		System.out.println("Programme contains Design: " + c.contains("Design"));
		System.out.println("Pogramme does not contain Haskell: " + !c.contains("Haskell"));
	}

}

interface Document {
	boolean contains(String s);
	void accept(Visitor v);
}

interface Visitor {
	void visit(Paragraph paragraph);
	void visit(Section section);
}

class Paragraph implements Document {
	
	public String paragraph;
	
	public Paragraph(String paragraph) {
		this.paragraph = paragraph;
	}
	
	@Override
	public boolean contains(String s) {
		return paragraph.contains(s);
	}
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

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
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
		for (Document child : children) {
			child.accept(v);
		}
	}
}

class Display implements Visitor {

	@Override
	public void visit(Paragraph paragraph) {
		System.out.println(paragraph.paragraph);
	}

	@Override
	public void visit(Section section) {
		System.out.println(section.title);
		for (int i = 0; i < section.title.length(); i++) {
			System.out.print('=');
		}
		System.out.println();
	}
}
