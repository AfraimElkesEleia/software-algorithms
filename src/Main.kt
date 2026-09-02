import graph.Graph

fun main() {
    val graph = Graph<Int>()

    val v0 = graph.addVertex(0)
    val v1 = graph.addVertex(1)
    val v2 = graph.addVertex(2)
    val v3 = graph.addVertex(3)
    val v4 = graph.addVertex(4)

    graph.addEdge(v0, v1)
    graph.addEdge(v0, v2)
    graph.addEdge(v1, v2)
    graph.addEdge(v2, v3)
    graph.addEdge(v2, v4)

    val bfsTraversal = graph.bfs(v0)
    val dfsTraversal = graph.dfs(v0)

    println("BFS starting from vertex 0:")
    println(bfsTraversal.joinToString(" -> ") { it.data.toString() })

    println()
    println("DFS starting from vertex 0:")
    println(dfsTraversal.joinToString(" -> ") { it.data.toString() })
}
