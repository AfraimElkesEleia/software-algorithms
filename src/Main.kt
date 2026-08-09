import dynamic_programming.stagecoach.StageCoachEdge
import dynamic_programming.stagecoach.StageCoachGraph
import dynamic_programming.stagecoach.StageCoachVertex

fun main() {

   val graph = StageCoachGraph<String>()

   val a = StageCoachVertex("A")
   val b = StageCoachVertex("B")
   val c = StageCoachVertex("C")
   val d = StageCoachVertex("D")
   val e = StageCoachVertex("E")
   val f = StageCoachVertex("F")
   val g = StageCoachVertex("G")
   val h = StageCoachVertex("H")
   val i = StageCoachVertex("I")
   val j = StageCoachVertex("J")

   // Add vertices in topological/stage order.
   graph.addVertex(a)
   graph.addVertex(b)
   graph.addVertex(c)
   graph.addVertex(d)
   graph.addVertex(e)
   graph.addVertex(f)
   graph.addVertex(g)
   graph.addVertex(h)
   graph.addVertex(i)
   graph.addVertex(j)
   graph.addEdge(
      StageCoachEdge(
         from = a,
         to = b,
         weight = 2.0
      )
   )

   graph.addEdge(
      StageCoachEdge(
         from = a,
         to = c,
         weight = 4.0
      )
   )

   graph.addEdge(
      StageCoachEdge(
         from = a,
         to = d,
         weight = 3.0
      )
   )
   graph.addEdge(StageCoachEdge(b, e, 7.0))
   graph.addEdge(StageCoachEdge(b, f, 4.0))
   graph.addEdge(StageCoachEdge(b, g, 6.0))
   graph.addEdge(StageCoachEdge(c, e, 3.0))
   graph.addEdge(StageCoachEdge(c, f, 2.0))
   graph.addEdge(StageCoachEdge(c, g, 4.0))
   graph.addEdge(StageCoachEdge(d, e, 4.0))
   graph.addEdge(StageCoachEdge(d, f, 1.0))
   graph.addEdge(StageCoachEdge(d, g, 5.0))
   graph.addEdge(StageCoachEdge(e, h, 1.0))
   graph.addEdge(StageCoachEdge(e, i, 4.0))
   graph.addEdge(StageCoachEdge(f, h, 6.0))
   graph.addEdge(StageCoachEdge(f, i, 3.0))
   graph.addEdge(StageCoachEdge(g, h, 3.0))
   graph.addEdge(StageCoachEdge(g, i, 3.0))
   graph.addEdge(StageCoachEdge(h, j, 3.0))
   graph.addEdge(StageCoachEdge(i, j, 4.0))
   val result = graph.solveStageCoachProblem()
   println("Minimum cost = ${result.cost}")
   println("Path = ${result.path.joinToString(" -> ")}")
}