package valius.model.entity.player.commands.all;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import valius.Config;
import valius.content.OneYearQuiz;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.Right;
import valius.model.entity.player.commands.Command;
import valius.util.Misc;

public class Answer extends Command {

	@Override
	public void execute(Player player, String input) {
		// OneYearQuiz.answerQA(c, input);

		if (!Config.ONE_YEAR_QUIZ ||Objects.equals(Config.QUESTION, "")) {
			player.sendMessage("There is currently no question to answer.");
			return;
		}
		
		player.sendMessage("You answered: " + input);
		
		int right = player.getRights().getPrimary().getValue() - 1;

		if (input.equalsIgnoreCase(Config.ANSWER)) {
			GlobalMessages.send(player.playerName + " answered the question correctly!", GlobalMessages.MessageType.EVENT);
			GlobalMessages.send("Question: " + Config.QUESTION, GlobalMessages.MessageType.EVENT);
			GlobalMessages.send("Answer: " + Config.ANSWER, GlobalMessages.MessageType.EVENT);
			player.sendMessage("Your answer was correct! A staff-member will contact you shortly.");
			
			List<Player> staff = PlayerHandler.nonNullStream().filter(Objects::nonNull).filter(p -> p.getRights().isOrInherits(Right.ADMINISTRATOR)).collect(Collectors.toList());
			
			if (staff.size() > 0) {
				PlayerHandler.sendMessage("@blu@[Quiz] " + Misc.capitalize(player.playerName) + "" + " answered the question correctly, contact them.", staff);
			}
			
			OneYearQuiz.configureEvent("end");
		} else {
			player.sendMessage("Your answer was incorrect");
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Answers quiz questions");
	}
}
