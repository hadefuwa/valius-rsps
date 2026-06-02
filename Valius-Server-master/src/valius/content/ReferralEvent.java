package valius.content;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import valius.model.entity.player.Player;

/**
 * 
 * @author Divine | Jan. 31, 2019 , 5:41:13 a.m.
 *
 */

@Getter
@Slf4j
public class ReferralEvent {
	
	private static final String REFERRAL_FILE = "./data/referral.json";
	private static ReferralEvent instance = new ReferralEvent();
	
	public static ReferralEvent get() {
		return instance;
	}
	
	private ReferralEvent() {
		
	}
	
	public void loadEvent() {
		try(FileReader fr = new FileReader(REFERRAL_FILE)){
			Gson gson = new GsonBuilder().create();
			Type type = new TypeToken<ReferralEvent>() {}.getType();
    		ReferralEvent event = gson.fromJson(fr, type);
    		this.referralId = event.getReferralId();
    		this.completedMAC.addAll(event.getCompletedMAC());
    		this.eventActive = event.isEventActive();
    		log.info("Loaded refferal event [ID: {}, active: {}]", this.referralId, this.eventActive);
		} catch (Exception e) {
			log.warn("Error while loding RefferalEvent", e);
		}
	}
	
	public void saveEvent() {
		try(FileWriter fw = new FileWriter(REFERRAL_FILE)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(this, fw);
			log.info("Successfully saved RefferalEvent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.warn("Error while saving RefferalEvent", e);
		}
	}
	
	public String referralId = "";
	private List<String> completedMAC = Lists.newArrayList();
	private boolean eventActive = false;

	
	public void checkReferral(Player player, String input) {
		String mac = player.getMacAddress();
		if(input.equalsIgnoreCase(referralId)) {
			if(!completedMAC.contains(mac)) {
				player.sendMessage("You receive a Valius mystery box and a Pet Imp (Dmg + Droprate boosting)!");
				player.getItems().addItemUnderAnyCircumstance(33269, 1);
				player.getItems().addItemUnderAnyCircumstance(33930, 1);
				completedMAC.add(mac);
			} else {
				player.sendMessage("You can only claim this reward once!");
			}
		} else {
			player.sendMessage("The Referral ID you have entered is incorrect.");
		}
		
	}
	
	public void start() {
		eventActive = true;
	}
	
	public void setRefferalId(String input) {
		this.referralId = input;
	}

	public void end() {
		eventActive = false;
		referralId = "";
		saveEvent();
	}
	
	public void resetMac() {
		completedMAC.clear();
		saveEvent();
	}

}
