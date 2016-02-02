package io.rebind.vertx.orientdb;

import io.rebind.vertx.orientdb.impl.OrientDBImpl;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Optional;

public interface OrientDB
{

	static OrientDB create(Vertx vertx)
	{
		return new OrientDBImpl(vertx);
	}

	JsonObject createVertex(final String iClassName, final String iClusterName, Optional<JsonObject> properties);

	JsonObject createEdge(final String sourceId, final String destinationId, final String label, Optional<JsonObject> properties);

	JsonObject getVertex(Optional<JsonObject> criteria);

	void close();
}
