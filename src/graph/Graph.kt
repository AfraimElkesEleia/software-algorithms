package graph

import java.util.ArrayDeque

class Graph<T>() {
    private val edges  = mutableSetOf<Edge<T>>()
    private val vertices = mutableListOf<Vertex<T>>()
    private val adjacencyMatrix = mutableListOf<MutableList<Int?>>()
    private val adjacencyList = mutableMapOf<Vertex<T>, MutableList<Edge<T>>>()
    private val selectedPMST = mutableListOf<Boolean>()
    private val selectedEdges = mutableSetOf<Edge<T>>()
    fun addVertex(data: T): Vertex<T> {
        val vertex = Vertex(index = vertices.size, data = data)
        vertices.add(vertex)
        selectedPMST.add(false)
        adjacencyMatrix.forEach { row -> row.add(null) }
        adjacencyMatrix.add(MutableList(vertices.size) { null })
        adjacencyList[vertex] = mutableListOf()
        return vertex
    }

    fun addEdge(from: Vertex<T>, to: Vertex<T>, weight: Int = 1) {
        require(from in vertices && to in vertices) {
            "Both vertices must be added to the graph first."
        }

        val forwardEdge = Edge(from, to, weight)
        val backwardEdge = Edge(to, from, weight)
        edges.add(forwardEdge)
        edges.add(backwardEdge)

        adjacencyMatrix[from.index][to.index] = weight
        adjacencyMatrix[to.index][from.index] = weight
        adjacencyList.getValue(from).add(forwardEdge)
        adjacencyList.getValue(to).add(backwardEdge)
    }

    fun weightBetween(from: Vertex<T>, to: Vertex<T>): Int? =
        adjacencyMatrix[from.index][to.index]

    fun edgesFrom(vertex: Vertex<T>): List<Edge<T>> = adjacencyList[vertex].orEmpty()



    fun solvePrimSpanningTree():List<Edge<T>>{
        if (vertices.isEmpty()) return emptyList()
        selectedPMST.fill(false)
        selectedPMST[0] = true

        while (selectedEdges.size < vertices.size - 1) {
            var lightestEdge: Edge<T>? = null
            var lightestWeight = Int.MAX_VALUE

            for (fromIndex in vertices.indices) {
                if (!selectedPMST[fromIndex]) continue

                for (toIndex in vertices.indices) {
                    if (selectedPMST[toIndex]) continue

                    val weight = adjacencyMatrix[fromIndex][toIndex] ?: continue

                    if (weight < lightestWeight) {
                        lightestWeight = weight
                        lightestEdge = Edge(vertices[fromIndex], vertices[toIndex], weight)
                    }
                }
            }
            checkNotNull(lightestEdge) { "The graph must be connected." }
            selectedEdges.add(lightestEdge)
            selectedPMST[lightestEdge.to.index] = true
        }
        return selectedEdges.toList()
    }
    fun bfs(start: Vertex<T>): List<Vertex<T>> {
        require(start in vertices){
            "The start vertex must be in the graph first."
        }
        val visitedVertices = mutableSetOf(start)
        val queue = ArrayDeque<Vertex<T>>()
        val traversal = mutableListOf<Vertex<T>>()
        queue.addLast(start)

        while (queue.isNotEmpty()) {
            val currentVertex = queue.removeFirst()
            traversal.add(currentVertex)
            val destinations = adjacencyList[currentVertex].orEmpty()
            for(destination in destinations){
                val toVertex = destination.to
                if (visitedVertices.add(toVertex)) {
                    queue.addLast(toVertex)
                }
            }
        }

        return traversal
    }

}
