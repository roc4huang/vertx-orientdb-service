package io.rebind.vertx.orientdb;

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import io.rebind.vertx.orientdb.data.ConnectionParams;
import io.rebind.vertx.orientdb.impl.DefaultOrientDB;
import io.vertx.core.Vertx;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public interface OrientDB
{

	static OrientDB create(Vertx vertx, ConnectionParams params)
	{
		ServiceLoader<OrientDB> serviceLoader = ServiceLoader.load(OrientDB.class);

		Optional<OrientDB> first = StreamSupport.stream(serviceLoader.spliterator(), false)
		                                        .findFirst();

		return first.isPresent() ? first.get() : new DefaultOrientDB(vertx, params);
	}

	void open();

	OrientBaseGraph db();

	void close();
}
