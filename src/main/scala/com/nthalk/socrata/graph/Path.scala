package com.nthalk.socrata.graph

case class Path(graph: Graph, edges: List[Edge[_, _]]) {
  def outP(): Iterator[Path] = {
    edges.last.to.outE.map { c => copy(edges = edges :+ c) }
  }

  override def toString(): String = {
    val s = edges.foldLeft("") { (s, e) => s"${s},${e.toString}" }
    s"P(${s.substring(1)})"
  }
}

