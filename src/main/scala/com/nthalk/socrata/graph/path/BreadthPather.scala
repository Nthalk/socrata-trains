package com.nthalk.socrata.graph.path

import com.nthalk.socrata.graph._

case class BreadthPather(graph: Graph, filter: (Path) => Boolean = (p) => true) {
  def outP(from: Any): Iterator[Path] = {
    graph.getV(from) match {
      case Some(v) => outP(v)
      case _ => Iterator.empty
    }
  }

  def outP(from: Vertex[_]): Iterator[Path] = {
    val paths = graph.outE(from).
      map { e => Path(graph, List(e)) }.
      // Apply the filters
      filter { filter(_) }.
      toList
    paths.iterator ++ outP(paths)
  }

  def outP(paths: List[Path]): Iterator[Path] = {
    if (paths.isEmpty) {
      Iterator.empty
    } else {
      val next = paths.
        // Extend the paths
        map { p =>
          graph.outE(p.edges.last.to).
            map { e => p.copy(edges = p.edges :+ e) }
        }.
        // The map outE produces List[List[]], we want to breadth this guy
        flatten.
        // Apply filters
        filter { filter(_) }
      next.iterator ++ outP(next)
    }
  }
}
