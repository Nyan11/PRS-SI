package logic.command;

import java.awt.Point;

import graphicLayer.GElement;
import logic.Reference;
import stree.parser.SNode;

public class Translate implements Command {

	@Override
	public Reference run(Reference reference, SNode method) {
		GElement e = ((GElement)reference.getReceiver());
		
		int x = Integer.valueOf(method.get(2).contents());
		int y = Integer.valueOf(method.get(3).contents());
		
		e.translate(new Point(x, y));
		return reference;
	}

}
