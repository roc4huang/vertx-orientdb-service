package io.rebind.vertx.orientdb.impl;

import io.rebind.vertx.orientdb.OrientDB;
import io.rebind.vertx.orientdb.OrientDBService;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Optional;

public class OrientDBServiceImpl implements OrientDBService
{
	private static final Logger LOG = LoggerFactory.getLogger(OrientDBServiceImpl.class);

	private Vertx vertx;
	private OrientDB orientDB;

	public OrientDBServiceImpl(Vertx vertx)
	{
		this.vertx = vertx;
		this.orientDB = OrientDB.create(vertx);
	}

	@Override
	@Fluent
	public OrientDBService createVertex(JsonObject vertexDoc, Handler<AsyncResult<JsonObject>> handler)
	{
		String iClassName = vertexDoc.getString("class");
		String iClusterName = vertexDoc.getString("cluster", null);
		Optional<JsonObject> properties = Optional.of(vertexDoc.getJsonObject("properties"));

		JsonObject reply = orientDB.createVertex(iClassName, iClusterName, properties);

		handler.handle(Future.succeededFuture(reply));

		return this;
	}

	@Override
	@Fluent
	public OrientDBService createEdge(JsonObject edgeDoc, Handler<AsyncResult<JsonObject>> handler)
	{
		String sourceId = edgeDoc.getString("source_id");
		String destinationId = edgeDoc.getString("destination_id");
		String label = edgeDoc.getString("label");
		Optional<JsonObject> properties = Optional.of(edgeDoc.getJsonObject("properties"));

		JsonObject reply = orientDB.createEdge(sourceId,destinationId,label,properties);

		handler.handle(Future.succeededFuture(reply));

		return this;
	}

	@Override
	@Fluent
	public OrientDBService getVertex(JsonObject vertexQuery, Handler<AsyncResult<JsonObject>> handler)
	{
		JsonObject reply = orientDB.getVertex(Optional.of(vertexQuery));

		handler.handle(Future.succeededFuture(reply));

		return this;
	}

	@Override
	@Fluent
	public OrientDBService getEdge(JsonObject edgeQuery, Handler<AsyncResult<JsonObject>> handler)
	{
		return this;
	}

	@Override
	public void close()
	{
		orientDB.close();
	}
}