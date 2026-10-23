import kotlin.math.floor
class HeapNode<T>(
  var data:T,
  val left:HeapNode<T>?=null,
  right:HeapNode<T>?=null)
class Heap<T : Comparable<T>>
  (private var root:HeapNode<T>? = null){
    private val listOfData = mutableListOf<T>()
  fun insert(data : T){
    listOfData.add(data)
    var i = listOfData.size-1
    var parentIndex = floor((i-1) / 2.0).toInt()
    while(i!=0 && listOfData[i]<listOfData[parentIndex]){
      swap(i,parentIndex)
      i = parentIndex
      parentIndex = floor((i-1) / 2.0).toInt()
    }
  }
  fun pop():T?{
    if (listOfData.isEmpty()) return null
    var i = 0 
    var data = listOfData[i]
    listOfData[i] = listOfData.last()
    listOfData.removeLast()
    while(true){
     val left = 2 * i + 1
     val right = 2 * i + 2
     var smallest = i
     if (left < listOfData.size && listOfData[left] < listOfData[smallest]) smallest = left
     if (right < listOfData.size && listOfData[right] < listOfData[smallest]) smallest = right
     if (smallest == i) break
     swap(i, smallest)
     i = smallest
    }
    return data
  }
   private fun swap(a: Int, b: Int) {
        val temp = listOfData[a]
        listOfData[a] = listOfData[b]
        listOfData[b] = temp
    }
   fun isEmpty():Boolean = listOfData.size == 0
  fun drawTree() {
        if (listOfData.isEmpty()) {
            println("(empty heap)")
            return
        }
        println(listOfData[0])
        drawChildren(0, "")
    }
 
    private fun drawChildren(index: Int, prefix: String) {
        val children = listOf(2 * index + 1, 2 * index + 2).filter { it < listOfData.size }
        children.forEachIndexed { k, child ->
            val isLast = k == children.lastIndex
            println(prefix + (if (isLast) "└── " else "├── ") + listOfData[child])
            drawChildren(child, prefix + if (isLast) "    " else "│   ")
        }
    }
 
    override fun toString(): String = listOfData.toString()
}
fun main(){
  val heap = Heap<Int>()
  for (value in listOf(5, 3, 8, 1, 9, 2, 7)) {
        println("Insert $value")
        heap.insert(value)
        heap.drawTree()
        println("array: $heap\n")
  }
  val popped = mutableListOf<Int>()
    while (!heap.isEmpty()) {
        val data = heap.pop()?:return
        popped.add(data)
        println("after pop -> $heap")
        heap.drawTree()
    }
    println("\npopped order: $popped")   // must be sorted ascending
}