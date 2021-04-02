package logic;

import java.util.HashMap;

import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GString;
import logic.command.NewElement;
import logic.command.NewImage;
import logic.command.NewString;

public class Environment {
	
	HashMap<String, Reference> variables;
	
	public Environment() {
		variables = new HashMap<String, Reference>();
	}
	
	public void addReference(String name, Reference reference) {
		this.variables.put(name, reference);
	}
	
	public Reference getReferenceByName(String name) {
		return this.variables.get(name);
	}
	
	public void deleteReferenceByName(String name) {
		TypeReference.end(this.getReferenceByName(name).getEnvironment());
		this.variables.remove(name);
	}
	
	public void show() {
		variables.forEach((k, i) -> {
			System.out.println(k + " : " + i);
		});
	}

}
