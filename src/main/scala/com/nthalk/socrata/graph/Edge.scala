package com.nthalk.socrata.graph

import scala.collection.mutable.Map

/**
 * Defines a typed directed connection between two verticies on a graph that
 * may have data associated with it
 */
case class Edge[T, R](graph: Graph, from: Vertex[T], to: Vertex[R], tpe: String) {
  def data(): Map[String, Any] = {
    graph.data(this)
  }
}
