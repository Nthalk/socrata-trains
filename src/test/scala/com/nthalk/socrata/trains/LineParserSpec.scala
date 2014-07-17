package com.nthalk.socrata.trains

import org.scalatest.Spec
import org.scalatest.Matchers._
import scala.collection.immutable.WrappedString
import scala.collection.mutable.HashSet
import scala.collection.mutable.Seq
import java.util.ArrayList
import scala.collection.JavaConversions._
import org.scalamock.scalatest.MockFactory
import org.scalamock.Mock
import com.nthalk.socrata.trains.TrainGraph._
import com.nthalk.socrata.trains.parser.LineParser
import com.nthalk.socrata.graph.Graph
import com.nthalk.socrata.graph.InMemoryGraph

class LineParserSpec extends Spec with MockFactory {
  class SubjectGraph extends TrainGraph(mock[Graph])

  object `A LineParser` {
    def `should be able to parse lines` {
      val trainGraph = new TrainGraph
      val subject = new LineParser(trainGraph)
      subject.parse("af999")
      println(trainGraph.graph.getAllE.toList)
      val a = trainGraph.findOrCreateTrainStation("a")
      val f = trainGraph.findOrCreateTrainStation("f")
      
      val result = trainGraph.getConnection(a,f)
      result should be(defined)
      result.get.dist should be(999)
    }

    def `should throw errors for bad stations` {
      intercept[IllegalArgumentException] {
        val subject = new LineParser(new TrainGraph)
        subject.parse("asdf999")
      }
    }
  }
}