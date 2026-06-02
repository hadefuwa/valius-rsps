package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.commands.Command;
import valius.model.items.bank.BankItem;

public class Placeholder extends Command {

	@Override
	public void execute(Player c, String input) {
		String[] args = input.split("-");
		//int slot = Integer.parseInt(args[0]);
		int itemID = Integer.parseInt(args[0]);
		c.getItems().removeFromBankPlaceholder(itemID, c.getBank().getCurrentBankTab().getItemAmount(new BankItem(itemID + 1)), true);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Enables placeholders");
	}
}
