package valius.model.entity.player.commands.moderator;

import valius.Config;
import valius.content.OneYearQuiz;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;

public class Q extends Command {

	@Override
	public void execute(Player player, String input) {
		
		String[] args = input.split("-");
		
		switch (args[0]) {
		
		case "":
			player.sendMessage("@red@Usage: ::q start, end, show, check or set-question-answer");
			break;
		
		case "start":
			OneYearQuiz.configureEvent("start");
			player.sendMessage("@red@Quizmode started");
			GlobalMessages.send("Quizmode started, get ready..", GlobalMessages.MessageType.EVENT);
			break;
			
		case "end":
			OneYearQuiz.configureEvent("end");
			player.sendMessage("@red@Quizmode ended");
			GlobalMessages.send("Quizmode ended, make sure to try your luck on the next one!", GlobalMessages.MessageType.EVENT);
			break;
			
		case "check":
			player.sendMessage("Question: " + Config.QUESTION);
			player.sendMessage("Answer: " + Config.ANSWER);
			break;
			
		case "show":
			GlobalMessages.send(Config.QUESTION, GlobalMessages.MessageType.EVENT);
			GlobalMessages.send("Answer by using ::answer (your answer)", GlobalMessages.MessageType.EVENT);
			break;
			
		case "set":
			OneYearQuiz.configureEvent("start");
			OneYearQuiz.setQA(args[1], args[2]);
			player.sendMessage("Questions set: " + args[1]);
			player.sendMessage("Answer set: " + args[2]);
			break;
		}
	}
}
