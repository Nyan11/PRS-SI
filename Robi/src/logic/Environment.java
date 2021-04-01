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
		this.variables.remove(name);
	}
	
	public static void initialisation(Environment e) {
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);
		
		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());
		
		e.addReference("rect.class", rectClassRef);
		e.addReference("oval.class", ovalClassRef);
		e.addReference("image.class", imageClassRef);
		e.addReference("label.class", stringClassRef);
	}
	
	public void show() {
		variables.forEach((k, i) -> {
			System.out.println(k + " : " + i);
		});
	}

}
