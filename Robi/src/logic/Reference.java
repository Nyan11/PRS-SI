package logic;

import java.util.HashMap;
import java.util.Map;

import logic.command.Command;
import stree.parser.SNode;

public class Reference {
	private Object receiver;
	private Map<String, Command> primitives;
	private Environment environment = null;

	public Reference(Object receiver) {
		this.receiver = receiver;
		primitives = new HashMap<String, Command>();
	}

	public void addCommand(String selector, Command primitive) {
		this.primitives.put(selector, primitive);
	}
	
	public Command getCommandByName(String selector) {
		return this.primitives.get(selector);
	}
	
	public Reference run(SNode expr) {
		String primitiveName = expr.get(1).contents();
		Command command = this.getCommandByName(primitiveName);
		if (command != null)
			return command.run(this, expr);
		else
			return null;
	}
	
	public Object getReceiver() {
		return this.receiver;
	}
	
	public Environment getEnvironment() {
		if(this.environment == null) {
			environment = new Environment();
			TypeReference.init(environment);
		}
		return this.environment;
	}
	
	public void show() {
		primitives.forEach((k, i) -> {
			System.out.println(k + ":" + i);
		});
	}
	
	public String toString() {
		String ret = getEnvironment().toString();
		ret += "Primitives: [";
		for(String methodeName : primitives.keySet())
			ret += methodeName + ", ";
		ret = ret.substring(0, ret.length() - 2);
		ret += "]\n";
		return ret;
	}
}
