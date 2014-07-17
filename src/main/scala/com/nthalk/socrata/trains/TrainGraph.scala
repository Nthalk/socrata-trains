package com.nthalk.socrata.trains

import scala.language.implicitConversions
import scala.language.existentials

import com.nthalk.socrata.graph.Edge
import com.nthalk.socrata.graph.Graph
import com.nthalk.socrata.graph.InMemoryGraph
import com.nthalk.socrata.graph.Path
import com.nthalk.socrata.graph.Vertex
import com.nthalk.socrata.graph.path.BreadthPather

/**
 * *****************************************************************************
 * Implicits
 * ****************************************************************************
 */

object Implicits {
  implicit def stringToTrainStation(s: String)(implicit trainGraph: TrainGraph): TrainStation = {
    TrainStation(Vertex(trainGraph.graph, s))
  }
}

/**
 * *****************************************************************************
 * Specialty Models
 * ****************************************************************************
 */

case class TrainStation(val vertex: Vertex[_])
case class TrainConnection(edge: Edge[_, _]) {
  def dist: Int = {
    edge.data.get("dist").get.asInstanceOf[Int]
  }
}
case class TrainRoute(path: Path) {
  def dist: Int = {
    path.edges.map { TrainConnection(_) }.foldLeft(0) { (t, c) => t + c.dist }
  }
}

/**
 * The TrainGraph wraps a generic Graph and interfaces with basic Train Models
 */
class TrainGraph(val graph: Graph = new InMemoryGraph) {

  def findOrCreateTrainStation(name: String): TrainStation = {
    TrainStation(graph.getV(name) match {
      case Some(v) => v
      case _ => graph.addV(name)
    })
  }

  def getConnection(from: TrainStation, to: TrainStation): Option[TrainConnection] = {
    graph.getE(from.vertex, to.vertex).map { TrainConnection(_) }
  }

  def createOrUpdateConnection(from: TrainStation, to: TrainStation, dist: Int): TrainConnection = {
    val edge = graph.getE(from.vertex, to.vertex) match {
      case Some(e) => e
      case _ => graph.addE(from.vertex, to.vertex)
    }
    edge.data.put("dist", dist)
    TrainConnection(edge)
  }

}
