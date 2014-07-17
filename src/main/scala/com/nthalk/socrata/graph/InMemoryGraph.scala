package com.nthalk.socrata.graph

import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

/**
 * An Implementation of graph that uses in memory objects to store verticies,
 * edges, and data.
 * Edges are currently optimized for moving outward
 */
class InMemoryGraph extends Graph {

  private val verticies = Map[Any, Vertex[_]]()
  private val edges = HashMap[Vertex[_], ListBuffer[Edge[_, _]]]()
  private val data = HashMap[Any, HashMap[String, Any]]()

  def getAllV(): Iterator[Vertex[_]] = {
    verticies.valuesIterator
  }

  def getV[T](obj: T): Option[Vertex[T]] = {
    verticies.get(obj).asInstanceOf[Option[Vertex[T]]]
  }
  def addV[T](obj: T): Vertex[T] = {
    verticies.getOrElseUpdate(obj, Vertex(this, obj)).asInstanceOf[Vertex[T]]
  }

  def getAllE(): Iterator[Edge[_, _]] = {
    edges.valuesIterator.flatten
  }

  def getE[T, R](from: Vertex[T], to: Vertex[R], tpe: String): Option[Edge[T, R]] = {
    edges.get(from) match {
      case Some(vertexEdges) => vertexEdges.filter { e => e.to == to }.headOption.asInstanceOf[Option[Edge[T, R]]]
      case _ => None
    }
  }

  def addE[T, R](from: Vertex[T], to: Vertex[R], tpe: String): Edge[T, R] = {
    val c = Edge(this, from, to, tpe)
    edges.getOrElseUpdate(from, ListBuffer()) += c
    c
  }

  def outE(from: Vertex[_], tpe: String = ""): Iterator[Edge[_, _]] = {
    edges.get(from) match {
      case Some(lst) => lst.iterator
      case _ => Iterator.empty
    }
  }

  def outV(from: Vertex[_], tpe: String = ""): Iterator[Vertex[_]] = {
    outE(from).map { _.to }
  }

  def data(of: Edge[_, _]): HashMap[String, Any] = {
    data.getOrElseUpdate(of, new HashMap)
  }

  def data(of: Vertex[_]): HashMap[String, Any] = {
    data.getOrElseUpdate(of, new HashMap)
  }

}
