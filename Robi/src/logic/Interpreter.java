package logic;

import stree.parser.SNode;

import java.util.stream.Stream;

import graphicLayer.GSpace;

public class Interpreter {

	public void compute(Environment environment, SNode next) {
		try {
			Reference receiver = getReceiver(environment, next);
			receiver.run(next);
		} catch(NullPointerException e) {
			System.out.println("Wrong");
		}
	}
	
	private Reference getReceiver(Environment environment, SNode next) {
		String receiverName = next.get(0).contents();
		String[] allRefNames = receiverName.split("\\.");
		Reference receiver = environment.getReferenceByName(receiverName);
		Environment refEnv = environment;
		
		for(String refName : allRefNames) {
			receiver = refEnv.getReferenceByName(refName);
			refEnv = receiver.getEnvironment();
		}
		
		return receiver;
	}
}
