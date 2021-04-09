package logic;

import java.io.PrintStream;

import stree.parser.SNode;
import utils.Message;

public class Interpreter {
	
	private PrintStream ps;
	
	public Interpreter(PrintStream ps) {
		this.ps = ps;
	}

	public void compute(Environment environment, SNode next) {
		Reference exec;
		try {
			Reference receiver = getReceiver(environment, next);
			exec = receiver.run(next);
			if(exec == null) {
				ps.println(Message.toJson(new Message("error", "CannotRunCommand")));
				ps.println(Message.toJson(new Message("error", recoverCommand(next))));
			} else {
				ps.println(Message.toJson(new Message("trace", recoverCommand(next))));
			}
			switch(next.get(1).contents()) {
				case "info":
					ps.println(Message.toJson(new Message("info", exec.toString().replace("\n", "§"))));
					break;
				case "print":
					ps.println(Message.toJson(new Message("print", exec.getReceiver().toString())));
					break;
			}
		} catch(NullPointerException e) {
			e.printStackTrace();
			ps.println(Message.toJson(new Message("error", "NullPointerException")));
			ps.println(Message.toJson(new Message("error", recoverCommand(next))));
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			ps.println(Message.toJson(new Message("error", "IndexOutOfBoundsException")));
			ps.println(Message.toJson(new Message("error", recoverCommand(next))));
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
	
	private String recoverCommand(SNode command) {
		String ret = "";
		int i = 0;
		while(i < command.size() && command.get(i).isLeaf()) {
			ret += command.get(i).contents() + " ";
			i++;
		}
		return ret;
	}
}
