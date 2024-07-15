package Operations

import Graphs.DirectedGraph
import org.scalatest.funsuite.AnyFunSuiteLike

class CycleDetectionTest extends AnyFunSuiteLike {

  test("CycleDetectionTest") {
    val graph = new DirectedGraph(Map("A" -> List("B", "C", "D"), "B" -> List("C"), "C" -> List("D"), "D" -> List()))
    val cycleDetection = new CycleDetection()
    val hasCycle = cycleDetection.hasCycle(graph)
    assert(!hasCycle)
  }

  test("CycleDetectionTestWithCycle") {
    val graph = new DirectedGraph(Map("A" -> List("B", "C", "D"), "B" -> List("C"), "C" -> List("D"), "D" -> List("A")))
    val cycleDetection = new CycleDetection()
    val hasCycle = cycleDetection.hasCycle(graph)
    assert(hasCycle)
  }
}
