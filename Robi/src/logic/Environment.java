package logic;

import java.util.HashMap;

public class Environment {
	
	HashMap<String, Reference> variables;
	
	public Environment() {
		variables = new HashMap<String, Reference>();
	}
	
	public Object addReference(String name, String type) {
		if(this.variables.containsKey(name)) {
			return null;
		}
		else {
			
		}
	}

}
