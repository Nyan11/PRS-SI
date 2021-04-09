package logic.command;

import java.awt.image.BufferedImage;

import graphicLayer.GSpace;
import logic.Reference;
import stree.parser.SNode;
import utils.ImageControl;

public class PrintScreen implements Command {

	private GSpace space;

	public PrintScreen(GSpace space) {
		this.space = space;
	}

	@Override
	public Reference run(Reference reference, SNode method) {
		BufferedImage bytes = ImageControl.createImage(space);
		String encodedfile = ImageControl.encodeToString(bytes, "png");
		return new Reference(encodedfile);
	}
}