package graph

fun main() {
    val graph = Graph<Int>()

    // Add vertices 0 through 8
    val v0 = graph.addVertex(0)
    val v1 = graph.addVertex(1)
    val v2 = graph.addVertex(2)
    val v3 = graph.addVertex(3)
    val v4 = graph.addVertex(4)
    val v5 = graph.addVertex(5)
    val v6 = graph.addVertex(6)
    val v7 = graph.addVertex(7)
    val v8 = graph.addVertex(8)

    // Add edges according to the graph in the image
    graph.addEdge(v0, v1, 4)
    graph.addEdge(v0, v7, 8)

    graph.addEdge(v1, v2, 8)
    graph.addEdge(v1, v7, 11)

    graph.addEdge(v2, v3, 7)
    graph.addEdge(v2, v8, 2)
    graph.addEdge(v2, v5, 4)

    graph.addEdge(v3, v4, 9)
    graph.addEdge(v3, v5, 14)

    graph.addEdge(v4, v5, 10)

    graph.addEdge(v5, v6, 2)

    graph.addEdge(v6, v7, 1)
    graph.addEdge(v6, v8, 6)

    graph.addEdge(v7, v8, 7)

    // Run Dijkstra's algorithm starting from vertex 0
    val solution = graph.dijkstra(v0)

    println("Shortest distance from vertex ${solution.start.data} to last vertex : ")
    println(solution.pathTo(v4).map { it.data }.toList())
}