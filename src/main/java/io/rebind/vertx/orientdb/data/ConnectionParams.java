package io.rebind.vertx.orientdb.data;

import io.vertx.core.json.JsonObject;

public class ConnectionParams
{

	private String connectionPath;
	private String username;
	private String password;

	public ConnectionParams()
	{
	}

	public ConnectionParams(ConnectionParams params)
	{
		this.connectionPath = params.getConnectionPath();
		this.username = params.getUsername();
		this.password = params.getPassword();
	}

	public ConnectionParams(JsonObject json)
	{
		this.connectionPath = json.getString("connectionPath");
		this.username = json.getString("username");
		this.password = json.getString("password");
	}

	public JsonObject toJson()
	{
		JsonObject json = new JsonObject();
		json.put("connectionPath", connectionPath);
		json.put("username", username);
		json.put("password", password);

		return json;
	}

	public String getConnectionPath()
	{
		return connectionPath;
	}

	public void setConnectionPath(String connectionPath)
	{
		this.connectionPath = connectionPath;
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