package logic;

import java.util.HashMap;
import java.util.Map;

import logic.command.AddElement;
import logic.command.Command;
import logic.command.DelElement;
import logic.command.InfoReference;
import logic.command.SetColor;
import stree.parser.SNode;

public class Reference {
	private Object receiver;
	private Map<String, Command> primitives;
	private Environment environment = null;

	public Reference(Object receiver) {
		this.receiver = receiver;
		primitives = new HashMap<String, Command>();
		this.addCommand("add", new AddElement());
		this.addCommand("del", new DelElement());
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
		return (command != null) ? command.run(this, expr) : null;
	}
	
	public Object getReceiver() {
		return this.receiver;
	}
	
	public Environment getEnvironment() {
		if(this.environment == null) {
			environment = new Environment();
			Environment.initialisation(environment);
		}
		return this.environment;
	}
	
	public void show() {
		primitives.forEach((k, i) -> {
			System.out.println(k + ":" + i);
		});
	}
	
	public String toString() {
		return receiver.toString();
	}
}
