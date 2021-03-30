package logic.command;

import logic.Reference;
import stree.parser.SNode;

public interface Command {
	abstract public void run(Reference receiver, SNode method);
}
