package io.rebind.vertx.orientdb.impl;

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import io.rebind.vertx.orientdb.OrientDB;
import io.rebind.vertx.orientdb.data.ConnectionParams;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class DefaultOrientDB implements OrientDB
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultOrientDB.class);

	protected Vertx vertx;
	protected ConnectionParams connectionParams;

	protected OrientGraphFactory factory;
	protected OrientBaseGraph og;

	public DefaultOrientDB(Vertx vertx, ConnectionParams params)
	{
		this.vertx = vertx;
		this.connectionParams = params;

		LOG.info("::GraphFactory initializing with ", params.toString());

		this.factory = new OrientGraphFactory(params.getConnectionPath(), params.getUsername(), params.getPassword());

		LOG.info("::GraphFactory initialized ...");
	}

	@Override
	public void open()
	{
		this.og = factory.getNoTx();
	}

	public void close()
	{
		this.factory.close();
	}

	@Override
	public OrientBaseGraph db()
	{
		return og;
	}
}
