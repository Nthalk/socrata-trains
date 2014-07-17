package com.nthalk.socrata.trains.parser

import com.nthalk.socrata.trains.TrainGraph
import scala.collection.immutable.WrappedString

/**
 * This is responsible for parsing lines and injecting stations, connections,
 * and connection distances into the TrainGraph
 */
class LineParser(g: TrainGraph) {
  val connectionParser = """(\w)(\w)(\d+)""".r

  def parse(line: String): Unit = {
    line.split(", ") foreach {
      case connectionParser(fromStationId, toStationId, distance) =>
        val toStation = g.findOrCreateTrainStation(toStationId)
        val fromStation = g.findOrCreateTrainStation(fromStationId)
        g.createOrUpdateConnection(fromStation, toStation, distance.toInt)
      case connection @ _ => throw new IllegalArgumentException(s"Unknown connection format: $connection")
    }
  }
}
