package logic;

import java.util.HashMap;
import java.util.Map;

import logic.command.Command;

public class Reference {
	Object receiver;
	Map<String, Command> primitives;

	public Reference(Object receiver) {
		this.receiver = receiver;
		primitives = new HashMap<String, Command>();
	}
}
