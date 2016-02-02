package io.rebind.vertx.orientdb;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ProxyHelper;


public class PersistenceVerticle extends AbstractVerticle
{
	protected OrientDBService dbService;

	@Override
	public void start() throws Exception
	{
		super.start();
		this.dbService = OrientDBService.create(vertx);

		ProxyHelper.registerService(OrientDBService.class, vertx, dbService, "vertx.persistence.service");
	}

	@Override
	public void stop() throws Exception
	{
		dbService.close();
	}
}
