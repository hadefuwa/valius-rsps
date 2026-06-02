package valius.content.wogw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import valius.Config;
import valius.discord.DiscordBot;
import valius.model.entity.player.GlobalMessages;
import valius.model.entity.player.Player;
import valius.model.entity.player.PlayerHandler;
import valius.model.items.ItemAssistant;
import valius.util.Misc;

public class Wogw {

	public static String[] lastDonators = new String[5];
	private static int slot = 0;
	
	private static final int LEAST_ACCEPTED_AMOUNT = 1000000; //1m 
	
	private static final int WOGW_OBJECT = 100;
	
	private static final int INTERFACEID = 38000;

	public static long EXPERIENCE_TIMER = 0, PC_POINTS_TIMER = 0, DOUBLE_DROPS_TIMER = 0;
	public static int MONEY_TOWARDS_EXPERIENCE = 0, MONEY_TOWARDS_PC_POINTS = 0, MONEY_TOWARDS_DOUBLE_DROPS = 0;

	@SuppressWarnings("resource")
	public static void init() {
        try {
            File f = new File("./data/wogw.txt");
            Scanner sc = new Scanner(f);
            int i = 0;
            while(sc.hasNextLine()){
            	i++;
                String line = sc.nextLine();
                String[] details = line.split("=");
                String amount = details[1];
                
                switch (i) {
                case 1:
                	MONEY_TOWARDS_EXPERIENCE = (int) Long.parseLong(amount);
                	break;
                case 2:
                	EXPERIENCE_TIMER = (int) Long.parseLong(amount);
                	break;
                case 3:
                	MONEY_TOWARDS_PC_POINTS = (int) Long.parseLong(amount);
                	break;
                case 4:
                	PC_POINTS_TIMER = (int) Long.parseLong(amount);
                	break;
                case 5:
                	MONEY_TOWARDS_DOUBLE_DROPS = (int) Long.parseLong(amount);
                	break;
                case 6:
                	DOUBLE_DROPS_TIMER = (int) Long.parseLong(amount);
                	break;
                }
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
	}
	
	public static void open(Player player, int interfaceid) {

		player.getPA().sendFrame171(1, 38020);
		player.getPA().sendChangeSprite(38006, (byte) 1);
		player.getPA().sendChangeSprite(38007, (byte) 1);
		player.getPA().sendChangeSprite(38008, (byte) 1);
		player.getPA().sendString("Enter amount", 38011);
		player.getPA().showInterface(INTERFACEID);
	}
	
	public static void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("./data/wogw.txt"));
			out.write("experience-amount=" + MONEY_TOWARDS_EXPERIENCE);
			out.newLine();
			out.write("experience-timer=" + EXPERIENCE_TIMER);
			out.newLine();
			out.write("pc-amount=" + MONEY_TOWARDS_PC_POINTS);
			out.newLine();
			out.write("pc-timer=" + PC_POINTS_TIMER);
			out.newLine();
			out.write("drops-amount=" + MONEY_TOWARDS_DOUBLE_DROPS);
			out.newLine();
			out.write("drops-timer=" + DOUBLE_DROPS_TIMER);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void donate(Player player, int amount) {
		if (amount < LEAST_ACCEPTED_AMOUNT) {
			player.sendMessage("You must donate at least 1 million coins.");
			return;
		}
		if (!player.getItems().playerHasItem(995, amount)) {
			player.sendMessage("@cr10@You do not have " + Misc.getValueWithoutRepresentation(amount) + ".");
			return;
		}
		player.getItems().deleteItem(995, amount);
		player.getPA().sendFrame171(1, 38020);
		
		/**
		 * Updating latest donators
		 */
		String towards =Objects.equals(player.wogwOption, "pc") ? "+5 bonus PC Points!" : Objects.equals(player.wogwOption, "experience") ? "50% bonus experience!" : Objects.equals(player.wogwOption, "drops") ? "double drops!" : "";
		player.sendMessage("You successfully donated " + Misc.format((int) player.wogwDonationAmount) + "gp to the well of goodwill towards");
		player.sendMessage(towards);
		Wogw.lastDonators[Wogw.slot] = "" + Misc.formatPlayerName(player.playerName) + " donated " + Misc.getValueWithoutRepresentation(player.wogwDonationAmount) + " towards " + towards;
		player.getPA().sendFrame126(Wogw.lastDonators[Wogw.slot], 38033 + Wogw.slot);
		
		/**
		 * Setting sprites back to unticked
		 */
		player.getPA().sendChangeSprite(38006, (byte) 1);
		player.getPA().sendChangeSprite(38007, (byte) 1);
		player.getPA().sendChangeSprite(38008, (byte) 1);
		/**
		 * Announcing donations over 10m
		 */
		if (player.wogwDonationAmount >= 10_000_000) {
			GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " donated " + Misc.getValueWithoutRepresentation(player.wogwDonationAmount) + " to the well of goodwill!", GlobalMessages.MessageType.NEWS);
			int mbox = Misc.random(100);
			if (mbox == 1) {
				player.getItems().addItemUnderAnyCircumstance(6199, 1);
				GlobalMessages.send(Misc.formatPlayerName(player.playerName) + " has been rewarded a Mystery box for donating to the well!", GlobalMessages.MessageType.NEWS);
			}
		}
		/**
		 * Setting the amounts and enabling bonus if the amount reaches above required.
		 */
		switch (player.wogwOption) {
		case "experience":
			handleMoneyToExperience(amount);
			break;
			
		case "pc":
			if (MONEY_TOWARDS_PC_POINTS + amount >= 30000000 && PC_POINTS_TIMER == 0) {
				MONEY_TOWARDS_PC_POINTS = MONEY_TOWARDS_PC_POINTS + amount - 30000000;
				GlobalMessages.send("The Well of Goodwill has been filled!", GlobalMessages.MessageType.NEWS);
				GlobalMessages.send("It is now granting everyone 1 hour of +5 bonus pc points.", GlobalMessages.MessageType.NEWS);
				DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**+5 PC POINTS!**__ for the next hour!");
				PC_POINTS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				Config.BONUS_PC_WOGW = true;
			} else {
				MONEY_TOWARDS_PC_POINTS += amount;
			}
			break;
			
		case "drops":
			if (MONEY_TOWARDS_DOUBLE_DROPS + amount >= 75000000 && DOUBLE_DROPS_TIMER == 0) {
				MONEY_TOWARDS_DOUBLE_DROPS = MONEY_TOWARDS_DOUBLE_DROPS + amount - 75000000;
				GlobalMessages.send("The Well of Goodwill has been filled!", GlobalMessages.MessageType.NEWS);
				GlobalMessages.send("It is now granting everyone 1 hour of double drop rate..", GlobalMessages.MessageType.NEWS);
				DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**DOUBLE DROPS!**__ for the next hour!");
				DOUBLE_DROPS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				Config.DOUBLE_DROPS = true;
			} else {
				MONEY_TOWARDS_DOUBLE_DROPS += amount;
			}
			break;
		}
		player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012);
		player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/30M", 38013);
		player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/75M", 38014);
		player.refreshQuestTab(8);
		Wogw.slot++;
		if (Wogw.slot == 5) {
			Wogw.slot = 0;
		}
		player.wogwOption = "";
		player.wogwDonationAmount = 0;
	}
	
	public static void donateItems(Player player, int amount) {
		//if (amount < LEAST_ACCEPTED_AMOUNT) {
		//	player.sendMessage("You must donate at least 1 million worth of items.");
		//	return;
		//}
		//for (final Wogwitems.itemsOnWell t : Wogwitems.itemsOnWell.values()) {
		if (!player.getItems().playerHasItem(player.wellItem, 1)) {
			player.sendMessage("You do not have any items to donate.");
			return;
			//}
		}
		//player.getItems().deleteItem(995, amount);
		player.getPA().sendFrame171(1, 38020);
		
		/**
		 * Updating latest donators
		 */
		//String towards = player.wogwOption == "pc" ? "+5 bonus PC Points!" : player.wogwOption == "experience" ? "double experience!" : player.wogwOption == "drops" ? "double drops!" : "";
		//player.sendMessage("You successfully donated " + Misc.format((int) player.wogwDonationAmount) + "gp to the well of goodwill towards");
		//player.sendMessage(towards);
		//Wogw.lastDonators[Wogw.slot] = "" + Misc.formatPlayerName(player.playerName) + " donated " + Misc.getValueWithoutRepresentation(player.wogwDonationAmount) + " towards " + towards;
		player.getPA().sendFrame126(Wogw.lastDonators[Wogw.slot], 38033 + Wogw.slot);
		
		/**
		 * Setting sprites back to unticked
		 */
		player.getPA().sendChangeSprite(38006, (byte) 1);
		player.getPA().sendChangeSprite(38007, (byte) 1);
		player.getPA().sendChangeSprite(38008, (byte) 1);
		/**
		 * Announcing donations over 1m
		 */
		String name = ""+ItemAssistant.getItemName(player.wellItem)+"";
		String determine = "a";
		if (name.startsWith("A") || name.startsWith("E") || name.startsWith("I") || name.startsWith("O") || name.startsWith("U"))
			determine = "an";
		if (player.wogwDonationAmount >= 1_000_000) {
			GlobalMessages.send( Misc.formatPlayerName(player.playerName) + "@bla@ donated "+ determine +" @blu@" + ItemAssistant.getItemName(player.wellItem) + " @bla@worth @blu@" + Misc.getValueWithoutRepresentation(player.wogwDonationAmount) + "@bla@ to the well of goodwill!", GlobalMessages.MessageType.NEWS);
			}
		/**
		 * Setting the amounts and enabling bonus if the amount reaches above required.
		 */
		switch (player.wogwOption) {
		case "experience":
			handleMoneyToExperience(amount);
			break;
		}
		player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012);
		player.refreshQuestTab(8);
		Wogw.slot++;
		if (Wogw.slot == 5) {
			Wogw.slot = 0;
		}
		player.wogwOption = "";
		player.wogwDonationAmount = 0;
	}

	private static void handleMoneyToExperience(int amount) {

		if (MONEY_TOWARDS_EXPERIENCE + amount >= 50000000 && EXPERIENCE_TIMER == 0) {
			MONEY_TOWARDS_EXPERIENCE = MONEY_TOWARDS_EXPERIENCE + amount - 50000000;
			GlobalMessages.send("The Well of Goodwill has been filled!", GlobalMessages.MessageType.NEWS);
			GlobalMessages.send("It is now granting everyone 1 hour of 50% bonus experience.", GlobalMessages.MessageType.NEWS);
			DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**50% bonus experience!**__ for the next hour!");
			EXPERIENCE_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
			Config.BONUS_XP_WOGW = true;
		} else {
			MONEY_TOWARDS_EXPERIENCE += amount;
		}
	}

	public static void appendBonus() {
			if (MONEY_TOWARDS_EXPERIENCE >= 50000000) {
				GlobalMessages.send("The Well of Goodwill was filled above the top!", GlobalMessages.MessageType.NEWS);
				GlobalMessages.send("It is now granting everyone 1 more hour of 50% boosted experience.", GlobalMessages.MessageType.NEWS);
				DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**50% boosted experience!**__ for the next hour!");
				EXPERIENCE_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_EXPERIENCE -= 50000000;
				PlayerHandler.nonNullStream().forEach(player -> {
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014);
				});
				Config.BONUS_XP_WOGW = true;
				return;
			}
			if (MONEY_TOWARDS_PC_POINTS >= 30000000) {
				GlobalMessages.send("The Well of Goodwill was filled above the top!", GlobalMessages.MessageType.NEWS);
				GlobalMessages.send("It is now granting everyone 1 more hour of +5 bonus pc points.", GlobalMessages.MessageType.NEWS);
				DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**+5 PC POINTS!**__ for another hour!!");
				PC_POINTS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_PC_POINTS -= 30000000;
				PlayerHandler.nonNullStream().forEach(player -> {
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014);
				});
				Config.BONUS_PC_WOGW = true;
				return;
			}
			if (MONEY_TOWARDS_DOUBLE_DROPS >= 75000000) {
				GlobalMessages.send("The Well of Goodwill was filled above the top!", GlobalMessages.MessageType.NEWS);
				GlobalMessages.send("It is now granting everyone 1 more hour of double drop rate.", GlobalMessages.MessageType.NEWS);
				DiscordBot.sendMessage("well-of-goodwill", "The Well of Goodwill has been filled! It is now granting __**DOUBLE DROPS!**__ for another hour!");
				DOUBLE_DROPS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
				MONEY_TOWARDS_DOUBLE_DROPS -= 75000000;
				PlayerHandler.nonNullStream().forEach(player -> {
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_EXPERIENCE) + "/50M", 38012);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_PC_POINTS) + "/50M", 38013);
					player.getPA().sendFrame126("" + Misc.getValueWithoutRepresentation(Wogw.MONEY_TOWARDS_DOUBLE_DROPS) + "/100M", 38014);
				});
				Config.DOUBLE_DROPS = true;
			}
	}

}
