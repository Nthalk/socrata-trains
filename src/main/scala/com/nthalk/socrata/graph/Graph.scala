package com.nthalk.socrata.graph

import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

/**
 * Interface for implementing graphs
 */
trait Graph {
  def getV[T](obj: T): Option[Vertex[T]]
  def addV[T](obj: T): Vertex[T]
  def addE[T, R](from: Vertex[T], to: Vertex[R], tpe: String = ""): Edge[T, R]
  def getE[T, R](from: Vertex[T], to: Vertex[R], tpe: String = ""): Option[Edge[T, R]]
  def outE(from: Vertex[_], tpe: String = ""): Iterator[Edge[_, _]]
  def outV(from: Vertex[_], tpe: String = ""): Iterator[Vertex[_]]
  def data(of: Vertex[_]): HashMap[String, Any]
  def data(of: Edge[_, _]): HashMap[String, Any]
  def getAllV(): Iterator[Vertex[_]]
  def getAllE(): Iterator[Edge[_, _]]
}
