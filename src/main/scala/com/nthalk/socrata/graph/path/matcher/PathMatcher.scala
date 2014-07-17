package com.nthalk.socrata.graph.path.matcher

import com.nthalk.socrata.graph.Graph
import com.nthalk.socrata.graph.Path

/**
 * This matcher
 *  A) verifies verticies that match the sequence of objects exist
 *  B) verifies a sliding window of those verticies correspond to existing edges
 *
 *  Because determining if a path exists between known nodes can be easily
 *  determined with a series of gets on a Graph, this operates on a Graph
 */
case class PathMatcher(objs: Any*) {

  def unapply(g: Graph): Option[Path] = {
    val verticies = objs.
      map { g.getV(_) }.
      flatten.toList

    // Ensure we have all vertexes
    if (verticies.length != objs.length) {
      None
    } else {
      // We use a sliding window because an edge is defined as a from-to
      val edges = verticies.
        sliding(2).
        map { (lst) => g.getE(lst(0), lst(1)) }.
        flatten.toList

      // Ensure we can get all edges
      if (edges.length + 1 != objs.length) {
        None
      } else {
        Some(Path(g, edges))
      }
    }

  }

}
