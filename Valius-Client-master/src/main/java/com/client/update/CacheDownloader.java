package com.client.update;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.client.Client;
import com.client.config.Configuration;
import com.client.sign.Signlink;

public class CacheDownloader {

	private Client client;

	private final int BUFFER = 8192;

	private String CACHE_LINK = Configuration.CACHELINK;
	private String BETA_CACHE_LINK = "http://beta.valius.net/cache.zip";

	private static String VERSION_FILE_URL = "http://server.valius.net/cache/317/version.txt";
	private static String BETA_VERSION_FILE_URL = "http://beta.valius.net/version.txt";
	private Path FILE_LOCATION = Paths.get(getCacheDir(), getArchivedName());

	public CacheDownloader(Client client) {
		this.client = client;
	}

	private void drawLoadingText(String text) {
		client.drawLoadingText(99, text);
		System.out.println(text);
	}

	private void drawLoadingText(int amount, String text) {
		client.drawLoadingText(amount, text);
	}

	private String getCacheDir() {
		return Signlink.getCacheDirectory();
	}

	private String getCacheLink() {
		return Configuration.betaClient ? BETA_CACHE_LINK : CACHE_LINK;
	}

	public CacheDownloader downloadCache() {
		try {
			
			double expectedVersion = this.getExpectedVersion();
			double currentVersion = this.getExistingVersion();
			if(expectedVersion > currentVersion) {
				downloadFile(getCacheLink(), getArchivedName());
				unZip();
				writeVersion(expectedVersion);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	private void writeVersion(double version) throws IOException {
		FileWriter fileWriter = new FileWriter(getCacheDir() + "/cacheVersion.dat");
		fileWriter.write(String.valueOf(version));
		fileWriter.close();
	}
	
	private double getExpectedVersion() {
		try {
			URL versionFile = new URL(Configuration.betaClient ? BETA_VERSION_FILE_URL : VERSION_FILE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(versionFile.openStream()));
			String line = in.readLine();
			in.close();
			return Double.parseDouble(line);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0.0;
	}
	
	private double getExistingVersion() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(getCacheDir() + "/cacheVersion.dat"));
			String line = in.readLine();
			in.close();
			return Double.parseDouble(line);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0.0;
	}

	private void downloadFile(String adress, String localFileName) {
		client.resetBackgroundImages();
		OutputStream out = null;
		URLConnection conn;
		InputStream in = null;

		try {
			URL url = new URL(adress);
			out = new BufferedOutputStream(new FileOutputStream(getCacheDir() + "/" + localFileName));

			conn = url.openConnection();
			in = conn.getInputStream();

			byte[] data = new byte[BUFFER];

			int numRead;
			long numWritten = 0;
			int fileSize = conn.getContentLength();


			while ((numRead = in.read(data)) != -1) {
				out.write(data, 0, numRead);
				numWritten += numRead;

				int percentage = (int) (((double) numWritten / (double) fileSize) * 100D);
				drawLoadingText((int)percentage, "Valius - Downloading Cache " + percentage + "%");

			}
			System.out.println(localFileName + "\t" + numWritten);
			drawLoadingText("Valius - Unzipping...");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	private String getArchivedName() {
		int lastSlashIndex = getCacheLink().lastIndexOf('/');
		if (lastSlashIndex >= 0 && lastSlashIndex < getCacheLink().length() - 1) {
			String u = getCacheLink().substring(lastSlashIndex + 1);
			String Name = u.replace("?dl=1", "");
			return Name;
		} else {
			System.err.println("error retrieving archived name.");
		}
		return "";
	}

	private void unZip() throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(FILE_LOCATION.toString()));
		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry e;
		while ((e = zin.getNextEntry()) != null) {
			String fileName = e.getName();
			File newFile = new File(getCacheDir() + File.separator + fileName);
			if (e.isDirectory()) {
				newFile.mkdirs();
			} else {
				newFile.getParentFile().mkdirs();
				if(newFile.exists())
					newFile.delete();
				unzip(zin, getCacheDir() + e.getName());
			}
			System.out.println("unzipping2 " + e.getName());
		}
		zin.close();
		Files.deleteIfExists(FILE_LOCATION);
	}

	private void unzip(ZipInputStream zin, String s) throws IOException {
		FileOutputStream out = new FileOutputStream(s);
		byte[] b = new byte[BUFFER];
		int len = 0;
		while ((len = zin.read(b)) != -1)
			out.write(b, 0, len);

		out.close();
	}

}