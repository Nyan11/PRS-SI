package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;


public class Exercice2_1_0 {
	GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
	GRect robi = new GRect();
	String script = "(space setColor black) (robi setColor yellow)";

	public Exercice2_1_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	private void runScript() {
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try {
			rootNodes = parser.parse(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}
	
	private void run(SNode expr) {
		SNode identifier, action, arg1, arg2;
		expr.children().forEach(e -> System.out.print(e.contents() + " "));
		System.out.println();
		
		identifier = expr.children().get(0);
		if(identifier.contents().compareTo("space") == 0) {
			action = expr.children().get(1);
			if(action.contents().compareTo("setColor") == 0) {
				arg1 = expr.children().get(2);
				space.setColor(Tools.getColorByName(arg1.contents()));
				System.out.println("ok");
			}
			
		} else if(identifier.contents().compareTo("robi") == 0) {
			action = expr.children().get(1);
			if(action.contents().compareTo("setColor") == 0) {
				arg1 = expr.children().get(2);
				robi.setColor(Tools.getColorByName(arg1.contents()));
				System.out.println("ok");
			}
		}
	}

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}