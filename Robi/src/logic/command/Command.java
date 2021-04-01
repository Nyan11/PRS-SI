package logic.command;

import logic.Reference;
import stree.parser.SNode;

public interface Command {
	abstract public Reference run(Reference reference, SNode method);
}
