package logic;

import java.util.HashMap;

public class Environment {
	
	HashMap<String, Reference> variables;
	
	public Environment() {
		variables = new HashMap<String, Reference>();
	}
	
	public void addReference(String name, Reference ref) {
		this.variables.put(name, ref);
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
