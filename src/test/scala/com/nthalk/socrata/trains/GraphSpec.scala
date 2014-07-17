package com.nthalk.socrata.trains

import org.scalatest.Spec
import org.scalatest.Matchers._
import com.nthalk.socrata.graph._

class GraphSpec extends Spec {

  object `A Graph` {
    def `should be able to add Verticies` {
      val subject = new InMemoryGraph
      subject.addV(3).obj should be(3)
    }

    def `should be able to add Edges` {
      val subject = new InMemoryGraph
      val con = subject.addE(subject.addV(1), subject.addV(2), "conn")
      con.from should be(Vertex(subject, 1))
      con.to should be(Vertex(subject, 2))
      con.tpe should be("conn")
    }

    def `should be able to iterate Edges` {
      val subject = new InMemoryGraph
      val v1 = subject.addV(1)
      val v2 = subject.addV(2)
      val v3 = subject.addV(3)
      subject.addE(v1, v2, "conn")
      subject.addE(v1, v3, "conn")
      subject.outE(v1).toList should be(List(
        Edge(subject, v1, v2, "conn"),
        Edge(subject, v1, v3, "conn")))
    }
  }

}