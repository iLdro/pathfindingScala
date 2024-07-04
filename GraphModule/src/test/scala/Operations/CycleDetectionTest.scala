package Operations

import Graphs.UnDirectedGraph
import org.scalatest.funsuite.AnyFunSuiteLike

class CycleDetectionTest extends AnyFunSuiteLike {
  val cycleDetection = new CycleDetection()

  test("testHasCycle") {
    val graph = new UnDirectedGraph(Map("A" -> List("B", "C", "D"), "B" -> List("A"), "C" -> List("D", "E"), "D" -> List("A", "C"), "E" -> List("C")))
    val hasCycle = cycleDetection.hasCycle(graph)
    
    assert(hasCycle == true)
  }

}
