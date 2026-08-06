package huffman

data class HuffmanNode (
    var left: HuffmanNode? = null,
    var right: HuffmanNode? = null,
    val freq:Int,
    val char: Char? = null
){
    val isLeaf:Boolean
        get() = left==null && right==null
}