package dynamic_programming.stagecoach

import kotlin.Double.Companion.POSITIVE_INFINITY

data class StageCoachVertex<T>(
    val data: T,
)

data class StageCoachEdge<T>(
    val from: StageCoachVertex<T>,
    val to: StageCoachVertex<T>,
    val weight: Double,
)

data class StageCoachResult<T>(
    val cost: Double,
    val path: List<T>,
)

class StageCoachGraph<T> {
    private val vertexList = mutableListOf<StageCoachVertex<T>>()
    private val edgeList = mutableListOf<StageCoachEdge<T>>()
    private val adjMatrix = mutableListOf<DoubleArray>()
    private val vertexToIndex = mutableMapOf<StageCoachVertex<T>, Int>()
    private val minCostMap = mutableMapOf<StageCoachVertex<T>, Double>()
    private val minCostFromVertexMap = mutableMapOf<StageCoachVertex<T>, StageCoachVertex<T>?>()
    private val solution = mutableListOf<T>()
    fun addVertex(node: StageCoachVertex<T>) {
        if (vertexList.contains(node)) {
            throw IllegalArgumentException("Duplicate vertex")
        }
        val index = vertexToIndex.size
        vertexList.add(node)
        vertexToIndex[node] = index
    }
    fun addEdge(edge: StageCoachEdge<T>) {
        require(edge.from in vertexToIndex) {
            "Vertex ${edge.from} was not added"
        }
        require(edge.to in vertexToIndex) {
            "Vertex ${edge.to} was not added"
        }
        edgeList.add(edge)
    }

    private fun convertToAdjMatrix() {
        adjMatrix.clear()
        val size = vertexList.size

        repeat(size) { row ->
            adjMatrix.add(
                DoubleArray(size) { column ->
                    if (row == column) 0.0 else POSITIVE_INFINITY
                }
            )
        }

        edgeList.forEach { edge ->
            val fromIndex = vertexToIndex.getValue(edge.from)
            val toIndex = vertexToIndex.getValue(edge.to)
            adjMatrix[fromIndex][toIndex] = minOf(
                adjMatrix[fromIndex][toIndex],
                edge.weight,
            )
        }
    }

    fun solveStageCoachProblem(): StageCoachResult<T> {
        require(vertexList.isNotEmpty()) { "Vertex list is empty" }

        minCostMap.clear()
        minCostFromVertexMap.clear()
        convertToAdjMatrix()

        val destinationIndex = vertexList.lastIndex
        val destination = vertexList[destinationIndex]
        minCostMap[destination] = 0.0
        minCostFromVertexMap[destination] = null

        for (i in destinationIndex - 1 downTo 0) {
            val currentVertex = vertexList[i]
            minCostMap[currentVertex] = POSITIVE_INFINITY

            for (j in i + 1..destinationIndex) {
                val nextVertex = vertexList[j]
                val edgeCost = adjMatrix[i][j]

                if (edgeCost == POSITIVE_INFINITY) continue

                val candidate = minCostMap.getValue(nextVertex) + edgeCost
                if (candidate < minCostMap.getValue(currentVertex)) {
                    minCostMap[currentVertex] = candidate
                    minCostFromVertexMap[currentVertex] = nextVertex
                }
            }
        }

        val start = vertexList.first()
        val minimumCost = minCostMap.getValue(start)
        check(minimumCost != POSITIVE_INFINITY) {
            "No path exists from $start to $destination"
        }

        val path = getSolution()
        printSolution(path)
        return StageCoachResult(minimumCost, path)
    }

    private fun getSolution(): List<T> {
        solution.clear()
        var currentVertex: StageCoachVertex<T>? = vertexList.first()
        while (currentVertex != null) {
            solution.add(currentVertex.data)
            currentVertex = minCostFromVertexMap.getValue(currentVertex)
        }
        return solution.toList()
    }

    private fun printSolution(path: List<T>) {
        println(path.joinToString(" -> "))
    }
}
