package com.nthalk.socrata.trains

import org.scalatest.Spec
import org.scalatest.Matchers._
import com.nthalk.socrata.graph._
import com.nthalk.socrata.graph.path.BreadthPather

class BreadthPatherSpec extends Spec{
  
  object `A BreadthPather` {
    def `should be able to iterate deep Paths` {
      val graph = new InMemoryGraph
      val subject = new BreadthPather(graph)
      val v1 = graph.addV(1)
      val v2 = graph.addV(2)
      val v3 = graph.addV(3)
      val v4 = graph.addV(4)
      val e1 = graph.addE(v1, v2, "conn")
      val e2 = graph.addE(v2, v3, "conn")
      val e3 = graph.addE(v3, v4, "conn")
      val result = subject.outP(v1).toList

      result.length should be(3)
      result(0) should be(Path(graph, List(e1)))
      result(1) should be(Path(graph, List(e1, e2)))
      result(2) should be(Path(graph, List(e1, e2, e3)))

    }
    
    def `should be able to iterate branched Paths` {
      val graph = new InMemoryGraph
      val subject = new BreadthPather(graph)
      val v1 = graph.addV(1)
      val v2 = graph.addV(2)
      val v3 = graph.addV(3)
      val v4 = graph.addV(4)
      val e1 = graph.addE(v1, v2, "conn")
      val e2 = graph.addE(v1, v3, "conn")
      val e3 = graph.addE(v3, v4, "conn")
      val result = subject.outP(v1).toList

      result.length should be(3)
      result(0) should be(Path(graph, List(e1)))
      result(1) should be(Path(graph, List(e2)))
      result(2) should be(Path(graph, List(e2, e3)))

    }
    
    def `should be able to iterate looped Paths` {
      val graph = new InMemoryGraph
      val subject = new BreadthPather(graph)
      val v1 = graph.addV(1)
      val v2 = graph.addV(2)
      val e1 = graph.addE(v1, v2, "conn")
      val e2 = graph.addE(v2, v1, "conn")
      val result = subject.outP(v1)
      result.take(100).toList(0) should be(Path(graph, List(e1)))
    }

  }

}