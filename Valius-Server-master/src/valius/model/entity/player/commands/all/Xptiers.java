package valius.model.entity.player.commands.all;

import java.util.Optional;

import valius.model.entity.player.Player;
import valius.model.entity.player.XpTiersInterface;
import valius.model.entity.player.commands.Command;

public class Xptiers extends Command {

    @Override
    public void execute(Player c, String input) {
        XpTiersInterface.open(c);
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Shows XP tier scaling multipliers based on total level");
    }
}
