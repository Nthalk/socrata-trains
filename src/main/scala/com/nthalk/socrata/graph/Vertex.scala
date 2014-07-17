package com.nthalk.socrata.graph

import scala.collection.mutable.Map

/**
 * Defines a vertex with an identifying object of obj
 */
case class Vertex[T](graph: Graph, val obj: T) {
  def outE(): Iterator[Edge[_, _]] = {
    graph.outE(this)
  }

  def outPath(): Iterator[Path] = {
    outE.map { o => Path(graph, List(o)) }
  }

  def data(): Map[String, Any] = {
    graph.data(this)
  }

}
