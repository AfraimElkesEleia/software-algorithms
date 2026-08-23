# Software Algorithms

## Huffman Coding

Huffman coding is a lossless compression algorithm. It gives shorter binary
codes to frequent characters and longer codes to less frequent characters.
The generated codes are prefix-free, so no character's code is the prefix of
another character's code.

### Building the Huffman tree

```text
count the frequency of every character
add one leaf node for each character to a min-priority queue

while the queue contains more than one node:
    left  = remove the node with the lowest frequency
    right = remove the next node with the lowest frequency

    parent = a new node with frequency left.frequency + right.frequency
    parent.left  = left
    parent.right = right

    add parent back to the priority queue

root = the last node remaining in the queue
```

### Traversing the tree to generate codes

The tree is traversed using depth-first search. Moving left adds `0` to the
current code, while moving right adds `1`. When a leaf is reached, the current
code belongs to that leaf's character.

```text
generateCodes(node, currentCode):
    if node is null:
        return

    if node is a leaf:
        save currentCode for node.character
        return

    generateCodes(node.left,  currentCode + "0")
    generateCodes(node.right, currentCode + "1")
```

For an input containing only one unique character, its code can be stored as
`0` instead of an empty string.

<img width="300" align="center" alt="Huffman drawio" src="https://github.com/user-attachments/assets/b374de0e-0b5e-43a5-b2f5-56e8747d74e9" />

## Stagecoach Problem

The stagecoach problem finds the minimum-cost path from the first vertex to
the last vertex in a multistage directed graph. Each edge has a travel cost,
and dynamic programming avoids recalculating the cheapest route from every
intermediate vertex to the destination.

Vertices must be added in stage, or topological, order. The first vertex is
treated as the source, the last vertex is treated as the destination, and
edges should point from an earlier vertex to a later vertex.

### Building the adjacency matrix

The same index represents a vertex in both the rows and columns. A matrix
entry stores the cost of travelling from its row vertex to its column vertex.
Missing edges are represented by positive infinity.

```text
create a vertexCount x vertexCount matrix

for every row and column:
    if row equals column:
        matrix[row][column] = 0
    else:
        matrix[row][column] = infinity

for every edge:
    fromIndex = index of edge.from
    toIndex   = index of edge.to
    matrix[fromIndex][toIndex] = edge.weight
```

### Calculating the minimum costs

The calculation starts at the destination and moves backward. The cost from
the destination to itself is zero. For every other vertex, the algorithm
chooses the outgoing edge that produces the smallest total cost.

```text
minimumCost[destination] = 0

for current from the vertex before destination down to source:
    minimumCost[current] = infinity

    for every later vertex next:
        if there is no edge from current to next:
            continue

        candidate = edgeCost(current, next) + minimumCost[next]

        if candidate is less than minimumCost[current]:
            minimumCost[current] = candidate
            chosenNext[current] = next
```

The recurrence is:

```text
minimumCost(current) =
    min(edgeCost(current, next) + minimumCost(next))
```

### Reconstructing the path

Whenever a cheaper candidate is found, its next vertex is saved. Starting at
the source and repeatedly following these saved vertices reconstructs the
minimum-cost path.

```text
current = source

while current exists:
    add current to the path
    current = chosenNext[current]
```

### Example

```kotlin
val graph = StageCoachGraph<String>()
val a = StageCoachVertex("A")
val b = StageCoachVertex("B")
val c = StageCoachVertex("C")

graph.addVertex(a)
graph.addVertex(b)
graph.addVertex(c)

graph.addEdge(StageCoachEdge(a, b, 2.0))
graph.addEdge(StageCoachEdge(b, c, 3.0))
graph.addEdge(StageCoachEdge(a, c, 10.0))

val result = graph.solveStageCoachProblem()

println(result.cost) // 5.0
println(result.path) // [A, B, C]
```

Building and processing the adjacency matrix takes `O(V² + E)` time and
`O(V²)` space, where `V` is the number of vertices and `E` is the number of
edges.

<img width="600" alt="DynamicProgramming Stagecoach problem drawio" src="https://github.com/user-attachments/assets/99965286-f31a-4f19-88a5-de47a8584b99" />

## Longest Common Subsequence (LCS)

The longest common subsequence is the longest sequence of characters that
appears in both strings in the same order. Characters do not have to be next
to each other. The algorithm stores the best LCS length for every pair of
prefixes in a matrix. An extra empty row and column make the boundary values
zero.

### Building the matrix

`matrix[row][column]` is the LCS length for the prefixes ending at
`firstText[row]` and `secondText[column]`. If the current characters match,
extend the diagonal subsequence. Otherwise, keep the better result from the
cell above or the cell to the left.

```text
create a matrix with (firstText.length + 1) rows and
    (secondText.length + 1) columns, filled with 0

for row from 1 until firstText.length:
    for column from 1 until secondText.length:
        if firstText[row] equals secondText[column]:
            upperLeftValue = matrix[row - 1][column - 1]
            matrix[row][column] = upperLeftValue + 1
        else:
            upperValue = matrix[row - 1][column]
            leftValue = matrix[row][column - 1]
            matrix[row][column] = max(upperValue, leftValue)
```

### Reconstructing the solution

Start at the matrix's bottom-right cell. Move toward the zero row or column.
When the value came from a diagonal match, save that character. The saved
characters are collected backward, so reverse them at the end.

```text
row = firstText.lastIndex
column = secondText.lastIndex
solution = empty sequence

while row > 0 and column > 0:
    if matrix[row][column] equals matrix[row - 1][column]:
        row = row - 1
    else if matrix[row][column] equals matrix[row][column - 1]:
        column = column - 1
    else:
        add firstText[row] to solution
        row = row - 1
        column = column - 1

reverse solution
```

Building the matrix takes `O(m × n)` time and `O(m × n)` space, where `m`
and `n` are the two string lengths. Reconstructing one LCS takes `O(m + n)`
time.

<img width="400" alt="LCS drawio" src="https://github.com/user-attachments/assets/394bb08b-85db-4b6c-9f38-c379b06c3262" />
