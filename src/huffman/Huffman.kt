package huffman
import java.util.PriorityQueue

class Huffman (
    val text:String,
){
    private val _frequency: MutableMap<Char, Int> = mutableMapOf<Char,Int>()
    val frequency: Map<Char, Int>
        get() = _frequency
    private val _codes = mutableMapOf<Char, String>()
    val codes
        get() = _codes
    private val _encodedText = StringBuilder()
    val encodedText: String
        get() = _encodedText.toString()
    private val priorityQueue = PriorityQueue<HuffmanNode>(compareBy { it.freq })
    private var root:HuffmanNode? = null
    init {
        buildFreqTable()
        fillPriorityQueue()
        buildHuffmanTree()
        encode()
    }
    private fun buildFreqTable(){
        if (text.isEmpty()) throw IllegalArgumentException("text can not be empty")
        for(char in text){
            _frequency[char] = _frequency.getOrDefault(char,0)+1
        }
    }
    private fun fillPriorityQueue(){
        for ((char, freq) in frequency) {
            priorityQueue.add(
                HuffmanNode(
                    freq = freq,
                    char = char
                )
            )
        }
    }
    private fun buildHuffmanTree(){
        while (priorityQueue.size > 1){
            val leftNode = priorityQueue.remove()
            val rightNode = priorityQueue.remove()
            val parentNode = HuffmanNode(freq =  leftNode.freq+rightNode.freq)
            parentNode.left = leftNode
            parentNode.right = rightNode
            priorityQueue.add(parentNode)
        }
        root = priorityQueue.remove()
        generateCodes(root,"")
    }
    private fun generateCodes(root:HuffmanNode?,currentCode:String){
        if(root == null) return
        if(root.isLeaf && root.char != null){
            // case : one character in text
         _codes[root.char] = currentCode.ifEmpty { "0" }
         return
        }
        generateCodes(root.left, currentCode+"0")
        generateCodes(root.right, currentCode+"1")
    }
    private fun encode(){
        for (char in text){
           _encodedText.append(codes[char])
        }
    }
}