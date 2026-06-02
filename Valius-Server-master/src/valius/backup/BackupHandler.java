package valius.backup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.collect.Maps;

import valius.model.entity.player.PlayerHandler;
import valius.model.entity.player.PlayerSave;

public class BackupHandler {
	
	private static final boolean SAVE_PLAYERS = true;
	private static final long BACKUP_TIMER = 5;
	
	public static void begin() {
		ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
		threadPool.scheduleAtFixedRate(BackupHandler::doBackup, BACKUP_TIMER, BACKUP_TIMER, TimeUnit.MINUTES);
	}
	private static Map<File, BackupType> backupRequests = Maps.newConcurrentMap();
	
	public static boolean exists(File file) {
		return backupRequests.containsKey(file);
	}
	public static void requestBackup(BackupType type, File file) {
		backupRequests.put(file, type);
	}
	
	public static void doBackup() {
		if(SAVE_PLAYERS) {
			PlayerHandler.nonNullStream().forEach(player -> {
				player.saveCharacter = true;
				player.saveFile = true;
				PlayerSave.save(player);
			});
		}
		if(backupRequests.isEmpty())
			return;
		File backupFolder = new File("./data/backup/saves/");
		backupFolder.mkdirs();
		List<File> playerBackups = backupRequests.entrySet().stream()
				.filter(entry -> entry.getValue() == BackupType.PLAYER)
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());	

		playerBackups.stream().forEach(file -> backupRequests.remove(file));
		try(FileOutputStream fos = new FileOutputStream(new File(backupFolder, DateFormatUtils.format(System.currentTimeMillis(), "MMM-dd-yyyy HH-mm") + ".zip"))){
			ZipOutputStream archive = new ZipOutputStream(fos);

			playerBackups.forEach(file -> {
				try {
					byte[] data = Files.readAllBytes(file.toPath());
					ZipEntry entry = new ZipEntry(file.getName());
					archive.putNextEntry(entry);
					archive.write(data);
					archive.closeEntry();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});
			archive.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public enum BackupType {
		PLAYER, CONFIG;
	}
}
