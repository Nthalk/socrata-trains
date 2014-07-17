package com.nthalk.socrata.trains

import scala.collection.TraversableOnce.flattenTraversableOnce

import com.nthalk.socrata.graph.InMemoryGraph
import com.nthalk.socrata.graph.path.BreadthPather
import com.nthalk.socrata.graph.path.matcher.PathMatcher
import com.nthalk.socrata.graph.path.matcher.TripMatcher
import com.nthalk.socrata.trains.parser.LineParser

/**
 * Main class for PROBLEM ONE: TRAINS
 */
object Main extends App {

  // Setup
  val graph = new InMemoryGraph
  implicit val trainGraph = new TrainGraph(graph)
  val parser = new LineParser(trainGraph)

  // Parse the lines until empty
  io.Source.stdin.getLines.takeWhile { !_.isEmpty }.foreach { parser.parse(_) }

  // Print output
  println(s"Output #1: ${output1}")
  println(s"Output #2: ${output2}")
  println(s"Output #3: ${output3}")
  println(s"Output #4: ${output4}")
  println(s"Output #5: ${output5}")
  println(s"Output #6: ${output6}")
  println(s"Output #7: ${output7}")
  println(s"Output #8: ${output8}")
  println(s"Output #9: ${output9}")
  println(s"Output #10: ${output10}")

  def output1: String = {
    // 1. The distance of the route A-B-C.
    val pathMatcher = PathMatcher("A", "B", "C")
    graph match {
      case pathMatcher(path) => TrainRoute(path).dist.toString
      case _ => "NO SUCH ROUTE"
    }
  }

  def output2: String = {
    // 2. The distance of the route A-D.
    val pathMatcher = PathMatcher("A", "D")
    graph match {
      case pathMatcher(path) => TrainRoute(path).dist.toString
      case _ => "NO SUCH ROUTE"
    }
  }

  def output3: String = {
    // 3. The distance of the route A-D-C.
    val pathMatcher = PathMatcher("A", "D", "C")
    graph match {
      case pathMatcher(path) => TrainRoute(path).dist.toString
      case _ => "NO SUCH ROUTE"
    }
  }

  def output4: String = {
    // 4. The distance of the route A-E-B-C-D.
    val pathMatcher = PathMatcher("A", "E", "B", "C", "D")
    graph match {
      case pathMatcher(path) => TrainRoute(path).dist.toString
      case _ => "NO SUCH ROUTE"
    }
  }

  def output5: String = {
    // 5. The distance of the route A-E-D.
    val pathMatcher = PathMatcher("A", "E", "D")
    graph match {
      case pathMatcher(path) => TrainRoute(path).dist.toString
      case _ => "NO SUCH ROUTE"
    }
  }

  def output6: String = {
    // 6. The number of trips starting at C and ending at C with a maximum of 3
    //    stops. In the sample data below, there are two such trips:
    //    C-D-C (2stops). and C-E-B-C (3 stops).
    val tripMatcher = TripMatcher("C", "C")
    val pather = BreadthPather(graph)
    pather.outP("C").
      takeWhile { _.edges.length < 4 }.
      map { tripMatcher.unapply(_) }.
      flatten.length.toString
  }

  def output7: String = {
    // 7. The number of trips starting at A and ending at C with exactly 4
    //    stops. In the sample data below, there are three such trips:
    //    A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
    val tripMatcher = TripMatcher("A", "C")
    val pather = BreadthPather(graph)
    pather.outP("A").
      dropWhile { _.edges.length != 4 }.
      takeWhile { _.edges.length < 5 }.
      map { tripMatcher.unapply(_) }.
      flatten.length.toString
  }

  def output8: String = {
    // 8. The length of the shortest route (in terms of distance to travel)
    //    from A to C.
    val tripMatcher = TripMatcher("A", "C")
    val pather = BreadthPather(graph)
    pather.outP("A").
      takeWhile { _.edges.length < 10 }.
      map { tripMatcher.unapply(_) }.
      flatten.map { TrainRoute(_) }.
      toList.sortWith((a, b) => a.dist < b.dist).
      head.dist.toString
  }

  def output9: String = {
    // 9. The length of the shortest route (in terms of distance to travel)
    //    from B to B.
    val tripMatcher = TripMatcher("B", "B")
    val pather = BreadthPather(graph)
    pather.outP("B").
      takeWhile { _.edges.length < 10 }.
      map { tripMatcher.unapply(_) }.
      flatten.map { TrainRoute(_) }.
      toList.sortWith((a, b) => a.dist < b.dist).
      head.dist.toString
  }

  def output10: String = {
    // 10. The number of different routes from C to C with a distance of less
    //     than 30. In the sample data, the trips are:
    //     CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
    val tripMatcher = TripMatcher("C", "C")
    val pather = BreadthPather(graph, (p) => TrainRoute(p).dist < 30)
    pather.outP("C").
      map { tripMatcher.unapply(_) }.flatten.
      map { TrainRoute(_) }.
      map { _.dist }.
      length.toString
  }

}
