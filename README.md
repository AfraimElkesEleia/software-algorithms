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

## Dijkstra's Algorithm

Dijkstra's algorithm finds the shortest paths from one source vertex to every
other reachable vertex in a weighted graph. It repeatedly chooses the
unvisited vertex with the smallest known distance and relaxes its outgoing
edges. Edge weights must be non-negative.

### Calculating shortest distances

Distances start at infinity, except for the source, whose distance is zero. A
min-priority queue selects the next vertex to process. When a shorter route is
found, update the destination's distance and save the current vertex as its
predecessor.

```text
for every vertex:
    distance[vertex] = infinity
    visited[vertex] = false

distance[source] = 0
previous[source] = null
add (source, 0) to a min-priority queue

while the queue is not empty:
    (current, currentDistance) = remove the entry with the smallest distance

    if visited[current]:
        continue

    visited[current] = true

    for every edge from current to destination:
        if visited[destination]:
            continue

        candidate = currentDistance + edge.weight

        if candidate < distance[destination]:
            distance[destination] = candidate
            previous[destination] = current
            add (destination, candidate) to the queue
```

### Reconstructing a path

To reconstruct a path to a destination, follow its predecessors backward until
the source is reached, then reverse the collected vertices. If the destination
still has an infinite distance, no path exists.

```text
path = empty list
current = destination

while current exists:
    add current to path
    current = previous[current]

reverse path
```

With a binary min-priority queue, the running time is `O((V + E) log V)` and
the space complexity is `O(V + E)`, where `V` is the number of vertices and
`E` is the number of edges.

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

## 0/1 Knapsack Problem

The 0/1 knapsack problem chooses a subset of items with maximum total profit
without exceeding a weight capacity. Each item can be selected once or not at
all. Dynamic programming stores the best profit obtainable from the first
`item` items at every possible `capacity`.

### Building the DP table

```text
solveKnapsack(profits, weights, maxWeight):
    itemCount = number of items
    create dp table with (itemCount + 1) rows and (maxWeight + 1) columns,
        filled with 0

    for item from 1 to itemCount:
        for capacity from 0 to maxWeight:
            profitWithoutItem = dp[item - 1][capacity]

            if weights[item] <= capacity:
                remainingCapacity = capacity - weights[item]
                profitWithItem = profits[item] + dp[item - 1][remainingCapacity]
                dp[item][capacity] = max(profitWithoutItem, profitWithItem)
            else:
                dp[item][capacity] = profitWithoutItem

    return dp
```

### Reconstructing selected items

Compare each cell with the cell above it, starting at the bottom-right of the
table. A different value means the current item was selected.

```text
reconstructSolution(dp, weights, maxWeight):
    selectedItems = empty list
    item = last item index
    capacity = maxWeight

    while item > 0 and capacity > 0:
        if dp[item][capacity] != dp[item - 1][capacity]:
            add item to selectedItems
            capacity = capacity - weights[item]

        item = item - 1

    reverse selectedItems
    return selectedItems
```
<img width="400" alt="Knapsack drawio" src="https://github.com/user-attachments/assets/00234b0f-4e7c-47e6-97a3-71cf0d64dd54" />

Data Structures

Kotlin implementations live in src/datastructures/. Dart versions with notes live in docs/dart/.

Structure	Backing storage	Notes
Linked list (singly)	nodes + head/tail	Dart
Stack	array / list	Dart, LIFO
Queue	array / list (Dart), linked list (Kotlin)	FIFO
Min heap	array	Kotlin
Hash table	array of key/value pairs, linear probing	Kotlin, FNV-1a hash
Binary search tree	linked nodes	Kotlin
Binary Search Tree: deleting a node

Find the node and its parent first, then handle one of three cases.

Leaf node. Clear the parent's link to it.

if parent == null:        root = null
else if node is left:     parent.left = null
else:                     parent.right = null

Delete a leaf node
<img width="1200" height="562" alt="Screenshot 2026-10-16 190946" src="https://github.com/user-attachments/assets/78ab2779-71f3-4d71-b097-d69c38a099b9" />

Node with one child. Copy the child's data and links into the node being deleted.

child = node.right ?: node.left
node.data  = child.data
node.left  = child.left
node.right = child.right

Delete a node with one child
<img width="1150" height="567" alt="Screenshot 2026-10-16 190708" src="https://github.com/user-attachments/assets/1c01cd89-a533-4b30-a9cc-e4c4c9d3231c" />

Node with two children. Find the in-order successor (the leftmost node of the right subtree). Copy its data into the node being deleted, then attach the successor's right subtree to the successor's parent.

parent = null
current = node.right
while current.left != null:
    parent = current
    current = current.left

node.data = current.data

if parent == null:
    node.right = current.right
else:
    parent.left = current.right

Delete a node with two children
<img width="1180" height="637" alt="Screenshot 2026-10-16 190702" src="https://github.com/user-attachments/assets/3000ff4f-24b8-4404-ba37-0694b1840be4" />

Deleting takes O(h) time, where h is the height of the tree.

Min Heap

The heap is stored in an array. For the node at index i, the parent is at (i - 1) / 2, the left child at 2i + 1 and the right child at 2i + 2.

Insertion. Add the value at the end, then swap it upward while it is smaller than its parent.

add data to the end of the array
i = last index

while i != 0 and array[i] < array[parent(i)]:
    swap(i, parent(i))
    i = parent(i)

Min heap insertion
<img width="1526" height="682" alt="Screenshot 2026-10-16 200111" src="https://github.com/user-attachments/assets/ec7d8816-8a61-499d-8ded-4c96934812aa" />

Pop. Remove the root, move the last element to the root, then swap it downward with its smaller child until the heap order is restored.

data = array[0]
array[0] = last element
remove the last element
i = 0

while true:
    left  = 2i + 1
    right = 2i + 2
    smallest = i

    if left  < size and array[left]  < array[smallest]: smallest = left
    if right < size and array[right] < array[smallest]: smallest = right
    if smallest == i: break

    swap(i, smallest)
    i = smallest

return data

Min heap pop
<img width="980" height="575" alt="Screenshot 2026-10-16 234835" src="https://github.com/user-attachments/assets/6785d355-4682-4cbe-b9fe-639993b22809" />

Insertion and pop both take O(log n) time. Space is O(n).

Building the table takes `O(n × W)` time and `O(n × W)` space, where `n` is
the number of items and `W` is the maximum weight. Reconstruction takes
`O(n)` time.

