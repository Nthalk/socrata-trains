package com.nthalk.socrata.graph.path.matcher

import com.nthalk.socrata.graph.Path

/**
 * Simply matches whether or not the start of a path and the end of a path
 * match a certain start object and end object.
 */
case class TripMatcher(from: Any, to: Any) {

  def unapply(ps: Iterator[Path]): Option[Path] = {
    ps.find(unapply(_).isDefined)
  }

  def unapply(p: Path): Option[Path] = {
    if (p.edges.head.from.obj == from && p.edges.last.to.obj == to) {
      Some(p)
    } else {
      None
    }
  }

}
