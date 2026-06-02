package valius.content.donations;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import valius.model.entity.player.Player;

public class DonateRequest {

	/**
	 * The consumer to be called when the request returns a valid set of donation data
	 */
	private Consumer<List<Donation>> onSuccess;
	/**
	 * The consumer to be called when the request returns an invalid status
	 */
	private Consumer<DonationReturnStatus> onFailure;
	/**
	 * The player making the request
	 */
	private Player player;

	
	/**
	 * Represents a request to the donation web API
	 * @param player The player making the claim
	 * @param onSuccess The consumer to be called on success
	 * @param onFailure The consumer to be called on failure
	 */
	public DonateRequest(Player player, Consumer<List<Donation>> onSuccess, Consumer<DonationReturnStatus> onFailure) {
		this.onSuccess = onSuccess;
		this.onFailure = onFailure;
		this.player = player;
	}

	/**
	 * Begins the calls to the web API on a seperate thread
	 */
	public void begin() {
		new Thread( () -> {
			URL url = null;
			HttpURLConnection request;
			try
			{

				url = new URL(DonationConstants.API_URL + "&username=" + player.getName().toLowerCase().replace(" ", "%20"));
				request = (HttpURLConnection)url.openConnection();
				request.setDoOutput(true);
				request.setRequestMethod("GET");

				request.connect();
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(new InputStreamReader(request.getInputStream()));
				JsonObject grabbedJson = element.getAsJsonObject();

				if (request.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
					DonationReturnStatus returnStatus = DonationReturnStatus.getByString(grabbedJson.get("status").getAsString());
					switch(returnStatus) {
					case SUCCESS:
						JsonArray rows = parser.parse(grabbedJson.get("data").getAsString()).getAsJsonArray();

						List<Donation> items = Lists.newArrayList();

						rows.forEach(jsonElem -> {
							JsonObject row = jsonElem.getAsJsonObject();

							int id = row.get("id").getAsInt();
							int tokens = row.get("tokens").getAsInt();

							items.add(new Donation(id, tokens));
						});
						this.onSuccess.accept(items);
						break;
					default:
						onFailure.accept(returnStatus);
						break;
					}

				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				onFailure.accept(DonationReturnStatus.CONNECT_ERROR);
			}
		}).run();
	}




}
