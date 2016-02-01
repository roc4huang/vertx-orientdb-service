package io.rebind.vertx.orientdb.impl;

import io.rebind.vertx.orientdb.OrientDB;
import io.rebind.vertx.orientdb.OrientDBService;
import io.rebind.vertx.orientdb.data.ConnectionParams;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DefaultOrientDBService implements OrientDBService, AutoCloseable
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultOrientDBService.class);

	private Vertx vertx;
	private OrientDB db;

	public DefaultOrientDBService(Vertx vertx, JsonObject config)
	{
		this.vertx = vertx;
		this.db = OrientDB.create(vertx, new ConnectionParams(config));

		db.open();
	}

	@Override
	public void process(JsonObject document, Handler<AsyncResult<JsonObject>> resultHandler)
	{
		LOG.info("::process called " + document);
		JsonObject result = document.copy();
		result.put("approved", true);
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void close() throws Exception
	{
		db.close();
	}
}