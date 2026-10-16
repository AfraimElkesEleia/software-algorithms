package graph

data class DijkstraSolution<T>(
    val start: Vertex<T>,
    val distances: Map<Vertex<T>, Long>,
    val previousVertices: Map<Vertex<T>, Vertex<T>?>,
) {
    fun distanceTo(destination: Vertex<T>): Long? =
        distances[destination]?.takeUnless { it == Long.MAX_VALUE }

    fun pathTo(destination: Vertex<T>): List<Vertex<T>> {
        if (distanceTo(destination) == null) return emptyList()

        val reversedPath = mutableListOf<Vertex<T>>()
        var currentVertex: Vertex<T>? = destination

        while (currentVertex != null) {
            reversedPath.add(currentVertex)

            if (currentVertex == start) {
                return reversedPath.asReversed()
            }

            currentVertex = previousVertices[currentVertex]
        }

        return emptyList()
    }

    fun nextVerticesTo(destination: Vertex<T>): Map<Vertex<T>, Vertex<T>> =
        pathTo(destination).zipWithNext().toMap()
}
