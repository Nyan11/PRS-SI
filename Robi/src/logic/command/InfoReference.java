package logic.command;

import logic.Environment;
import logic.Reference;
import stree.parser.SNode;

public class InfoReference implements Command {

	@Override
	public Reference run(Reference reference, SNode method) {
		Environment e = reference.getEnvironment();
		e.show();
		return reference;
	}

}
