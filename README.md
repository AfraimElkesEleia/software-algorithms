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
