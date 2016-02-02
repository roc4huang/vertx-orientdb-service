package io.rebind.vertx.orientdb.impl;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.*;
import io.rebind.vertx.orientdb.OrientDB;
import io.rebind.vertx.orientdb.data.ConnectionParams;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrientDBImpl implements OrientDB
{
	private static final Logger LOG = LoggerFactory.getLogger(OrientDBImpl.class);

	protected Vertx vertx;

	protected OrientGraphFactory factory;

	public OrientDBImpl(Vertx vertx)
	{
		this.vertx = vertx;

		ConnectionParams params = new ConnectionParams(vertx.getOrCreateContext().config());

		LOG.info("::GraphFactory initializing with ", params.toString());

		this.factory = new OrientGraphFactory(params.getUri(), params.getUsername(), params.getPassword());

		LOG.info("::GraphFactory initialized ...");
	}

	@Override
	public JsonObject createVertex(String iClassName, String iClusterName, Optional<JsonObject> properties)
	{
		OrientGraphNoTx og = factory.getNoTx();
		try {
			OrientVertex vertex = og.addVertex(iClassName, iClusterName);

			if (properties.isPresent()) {
				properties.get()
				          .getMap()
				          .entrySet()
				          .stream()
				          .forEach(prop -> vertex.setProperty(prop.getKey(), prop.getValue()));
			}

			og.commit();

			return new JsonObject().put("success", true).put("vertex", vertex.getRecord().toJSON());
		}
		catch (Throwable t) {
			og.rollback();
			LOG.error("::createVertex failed", t);
			return new JsonObject().put("success", false).put("error", t.getMessage());
		}
	}

	@Override
	public JsonObject createEdge(String sourceId, String destinationId, String label, Optional<JsonObject> properties)
	{
		OrientGraphNoTx og = factory.getNoTx();
		try {
			OrientVertex source = og.getVertex(sourceId);
			OrientVertex destination = og.getVertex(destinationId);

			OrientEdge edge = og.addEdge(null, source, destination, label);

			if (properties.isPresent()) {
				properties.get()
				          .getMap()
				          .entrySet()
				          .stream()
				          .forEach(prop -> edge.setProperty(prop.getKey(), prop.getValue()));
			}

			og.commit();

			return new JsonObject().put("success", true).put("vertex", edge.getRecord().toJSON());
		}
		catch (Throwable t) {
			og.rollback();
			LOG.error("::createEdge failed", t);
			return new JsonObject().put("success", false).put("error", t.getMessage());
		}
	}

	@Override
	public JsonObject getVertex(Optional<JsonObject> criteria)
	{
		OrientGraphNoTx og = factory.getNoTx();

		try {
			JsonObject reply = new JsonObject();

			if (criteria.isPresent()) {
				Map<String, Object> criteriaMap = criteria.get().getMap();

				if (criteriaMap.containsKey("id")) {
					OrientVertex vertex = og.getVertex(criteriaMap.get("id"));

					return reply.put("success", true).put("result", vertex.getRecord().toJSON());
				}

				if (criteriaMap.containsKey("name")) {
					Iterable<Vertex> vertices = og.getVertices("Server", criteriaMap.keySet().toArray(new String[criteriaMap.keySet().size()]),
					                                           criteriaMap.values().toArray());

					Optional<Vertex> vertex = StreamSupport.stream(vertices.spliterator(), false)
					                                       .findFirst();

					return reply.put("success", true).put("result", vertex.isPresent() ? ((OrientVertex) vertex.get()).getRecord().toJSON() : new JsonObject
						());
				}
			}

			List<JsonObject> collect = StreamSupport.stream(og.getVertices().spliterator(), false)
			                                        .map(v -> (OrientVertex) v).map(OrientElement::getRecord)
			                                        .map(od -> new JsonObject(od.toJSON()))
			                                        .collect(Collectors.toList());

			return reply.put("success", true).put("result", new JsonArray(collect));
		}
		catch (Throwable t) {
			LOG.error("::createEdge failed", t);
			return new JsonObject().put("success", false).put("error", t.getMessage());
		}
	}

	public void close()
	{
		this.factory.close();
	}
}
