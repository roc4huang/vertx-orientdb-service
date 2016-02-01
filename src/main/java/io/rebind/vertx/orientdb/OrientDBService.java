package io.rebind.vertx.orientdb;

import io.rebind.vertx.orientdb.impl.DefaultOrientDBService;
import io.vertx.codegen.annotations.ProxyGen;
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

	static OrientDBService create(Vertx vertx, JsonObject config)
	{
		return new DefaultOrientDBService(vertx, config);
	}

	static OrientDBService createProxy(Vertx vertx, String address)
	{
		return ProxyHelper.createProxy(OrientDBService.class, vertx, address);
	}

	void process(JsonObject document, Handler<AsyncResult<JsonObject>> resultHandler);



}
