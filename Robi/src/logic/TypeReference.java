package logic;

import java.util.Collection;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GString;
import logic.command.NewElement;
import logic.command.NewImage;
import logic.command.NewString;

public class TypeReference {
	private static Reference rectClassRef = new Reference(GRect.class);
	private static Reference ovalClassRef = new Reference(GOval.class);
	private static Reference imageClassRef = new Reference(GImage.class);
	private static Reference stringClassRef = new Reference(GString.class);
	
	static {
		TypeReference.rectClassRef.addCommand("new", new NewElement());
		TypeReference.ovalClassRef.addCommand("new", new NewElement());
		TypeReference.imageClassRef.addCommand("new", new NewImage());
		TypeReference.stringClassRef.addCommand("new", new NewString());
	}
	
	private static Collection<Reference> cleanEnvironment(Collection<Reference> refs) {
		refs.remove(rectClassRef);
		refs.remove(ovalClassRef);
		refs.remove(imageClassRef);
		refs.remove(stringClassRef);
		return refs;
	}
	
	public static void init(Environment e) {
		e.addReference("rect.class", rectClassRef);
		e.addReference("oval.class", ovalClassRef);
		e.addReference("image.class", imageClassRef);
		e.addReference("label.class", stringClassRef);
	}

	public static void end(Environment e) {
		for(Reference ref : cleanEnvironment(e.variables.values()))
			TypeReference.end(ref.getEnvironment());
		e.variables.clear();
	}
	
	
}
