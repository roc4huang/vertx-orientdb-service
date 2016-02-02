package io.rebind.vertx.orientdb;

import io.rebind.vertx.orientdb.impl.OrientDBServiceImpl;
import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.ProxyIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;

@ProxyGen
@VertxGen
public interface OrientDBService
{

	static OrientDBService create(Vertx vertx)
	{
		return new OrientDBServiceImpl(vertx);
	}

	static OrientDBService createProxy(Vertx vertx, String address)
	{
		return ProxyHelper.createProxy(OrientDBService.class, vertx, address);
	}

	@Fluent
	OrientDBService createVertex(JsonObject vertexDoc, Handler<AsyncResult<JsonObject>> handler);

	@Fluent
	OrientDBService createEdge(JsonObject edgeDoc, Handler<AsyncResult<JsonObject>> handler);

	@Fluent
	OrientDBService getVertex(JsonObject vertexQuery, Handler<AsyncResult<JsonObject>> handler);

	@Fluent
	OrientDBService getEdge(JsonObject edgeQuery, Handler<AsyncResult<JsonObject>> handler);

	@ProxyIgnore
	void close();
}
