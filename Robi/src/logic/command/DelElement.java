package logic.command;

import graphicLayer.GContainer;
import graphicLayer.GElement;
import logic.Environment;
import logic.Reference;
import stree.parser.SNode;

public class DelElement implements Command {

	@Override
	public Reference run(Reference reference, SNode method) {
		Environment environment = reference.getEnvironment();
		String name = method.get(2).contents();
		Reference ref = environment.getReferenceByName(name);
		environment.deleteReferenceByName(name);
		
		((GContainer) reference.getReceiver()).removeElement((GElement) ref.getReceiver());
		((GContainer) reference.getReceiver()).repaint();
		return ref;
	}

}
