package graph

data class Edge<T>(val from: Vertex<T>, val to: Vertex<T>, val weight: Int = 1)
