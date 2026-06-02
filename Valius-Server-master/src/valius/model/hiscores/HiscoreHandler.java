package valius.model.hiscores;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;
import valius.model.entity.player.Player;
import valius.model.entity.player.skills.Skill;

@Slf4j
public class HiscoreHandler {
	
	public static final boolean DISABLE_HISCORES = true;
	private static final ScheduledExecutorService THREAD_EXECUTOR = Executors.newScheduledThreadPool(1);
	private static final String DATABASE_NAME = "u826873053_score";
	private static final String DATABASE_USERNAME = "u826873053_score";
	private static final String DATABASE_PASSWORD = "4781BonHXEr4";
	private Sql2o database;
	private final String UPDATE_STRING;
	
	public HiscoreHandler() {
		database = new Sql2o("jdbc:mysql://valius.net:3306/" + DATABASE_NAME, DATABASE_USERNAME, DATABASE_PASSWORD);
		
		String updateString = "REPLACE INTO hiscores (";
		String midString = " username, mode) VALUES(";
		String endString = " :username, :mode)";
		StringBuilder skillStringBuilder = new StringBuilder();
		StringBuilder skillValueBuilder = new StringBuilder();
		Stream.of(Skill.values()).forEach(skill -> {
			skillStringBuilder.append(skill.name().toLowerCase() + ", ");
			skillValueBuilder.append(":" + skill.name().toLowerCase() + ", ");
		});
		
		String skillStrings = skillStringBuilder.toString();
		String skillValues = skillValueBuilder.toString();
		
		UPDATE_STRING = updateString + skillStrings + midString + skillValues + endString;

		if(DISABLE_HISCORES)
			return;
		THREAD_EXECUTOR.scheduleAtFixedRate(() -> {
			if(pendingHiscores.isEmpty())
				return;
			Map<String, HiscoreModel> pendingUpdates = Maps.newConcurrentMap();
			pendingHiscores.entrySet().stream().limit(100).forEach(entry -> pendingUpdates.put(entry.getKey(), entry.getValue()));
			pendingUpdates.keySet().stream().forEach(pendingHiscores::remove);
			pendingUpdates.entrySet().stream().forEach(entry -> {
				String username = entry.getKey();
				HiscoreModel model = entry.getValue();

				try (Connection con = database.open()) {
				    Query query = con.createQuery(UPDATE_STRING)
				    	.addParameter("mode", model.getGameMode().name().toLowerCase())
					    .addParameter("username", username);
				    for(Skill skill : Skill.values()) {
				    	query.addParameter(skill.name().toLowerCase(), model.getPlayerXP()[skill.getId()]);
				    }
					  
				    query.executeUpdate();
				}
			});
			log.info("Updated {} hiscores", pendingUpdates.size());
		}, 10, 10, TimeUnit.MINUTES);
		log.info("Finished initializing HiscoreHandler");
	
	}
	private Map<String, HiscoreModel> pendingHiscores = Maps.newConcurrentMap();
	
	public void prepare(Player player) {

		if(DISABLE_HISCORES)
			return;
		pendingHiscores.put(player.getName(), new HiscoreModel(player));
	}
	

}
