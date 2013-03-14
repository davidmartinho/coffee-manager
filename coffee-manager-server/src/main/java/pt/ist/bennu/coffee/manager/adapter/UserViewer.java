package pt.ist.bennu.coffee.manager.adapter;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.bennu.coffee.manager.domain.Person;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.ConfigurationManager;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;
import pt.ist.bennu.service.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserViewer implements JsonViewer<User> {

	private static Logger logger = LoggerFactory.getLogger(UserViewer.class);

	private static final String url = ConfigurationManager
			.getProperty("fenixdata.url");
	private static final String username = ConfigurationManager
			.getProperty("fenixdata.user");
	private static final String password = ConfigurationManager
			.getProperty("fenixdata.pass");
	private static final Client client = Client.create();
	private static final JsonParser parser = new JsonParser();

	@Service
	public void populateUserPerson(User user) {

		final String fenixdataUrl = url + user.getUsername();
		final WebResource webResource = client.resource(fenixdataUrl
				+ "&username=" + username + "&password=" + password);

		final ClientResponse response = webResource
				.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			logger.warn("Status {} : Couldn't connect to {}",
					response.getStatus(), webResource.getURI());
		} else {
			final JsonElement jsonObject = parser.parse(response
					.getEntity(String.class));
			final JsonObject asJsonObject = jsonObject.getAsJsonObject();
			String name = asJsonObject.get("nickname").getAsString();
			final JsonElement emailJsonElement = asJsonObject.get("email");
			String email = emailJsonElement == null ? user.getUsername()
					+ "@ist.utl.pt" : emailJsonElement.getAsString();
			Person.newInstance(name, email, user);
		}
	}

	@Override
	public JsonElement view(User user, JsonBuilder context) {
		if (!user.hasPerson()) {
			populateUserPerson(user);
		}
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", user.getExternalId());
		jsonObject.addProperty("name", user.getPerson().getName());
		jsonObject.addProperty("email", user.getPerson().getEmail());
		jsonObject.addProperty("avatarUrl", user.getPerson().getAvatar());
		jsonObject.addProperty("isCoffeeManager", true);

		return jsonObject;
	}
}
