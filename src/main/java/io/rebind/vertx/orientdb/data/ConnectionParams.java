package io.rebind.vertx.orientdb.data;

import io.vertx.core.json.JsonObject;

public class ConnectionParams
{

	private String uri;
	private String username;
	private String password;

	public ConnectionParams()
	{
	}

	public ConnectionParams(ConnectionParams params)
	{
		this.uri = params.getUri();
		this.username = params.getUsername();
		this.password = params.getPassword();
	}

	public ConnectionParams(JsonObject json)
	{
		this.uri = json.getString("uri");
		this.username = json.getString("username");
		this.password = json.getString("password");
	}

	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.put("connectionPath", uri);
		json.put("username", username);
		json.put("password", password);

		return json;
	}

	public String getUri()
	{
		return uri;
	}

	public void setUri(String uri)
	{
		this.uri = uri;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return toJson().encode();
	}
}