package valius.content;

import valius.Config;
import valius.model.entity.player.GlobalMessages;

public class OneYearQuiz {
	
	public static String answer = "";
	
	public static void configureEvent(String config) {
		switch (config) {
		case "start":
			Config.ONE_YEAR_QUIZ = true;
			break;
			
		case "end":
			Config.ONE_YEAR_QUIZ = false;
			Config.QUESTION = "";
			Config.ANSWER = "";
			answer = "";
			break;
		}
	}
	
	public static void setQA(String q, String a) {
		GlobalMessages.send(q, GlobalMessages.MessageType.EVENT);
		GlobalMessages.send("Answer by using ::answer (your answer)", GlobalMessages.MessageType.EVENT);
		Config.QUESTION = q;
		Config.ANSWER = a;
		answer = a;
	}

}
