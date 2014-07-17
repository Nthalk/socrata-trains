package com.nthalk.socrata.graph

import scala.collection.mutable.Map

/**
 * Defines a vertex with an identifying object of obj
 */
case class Vertex[T](graph: Graph, val obj: T) {

  def data(): Map[String, Any] = {
    graph.data(this)
  }

}
