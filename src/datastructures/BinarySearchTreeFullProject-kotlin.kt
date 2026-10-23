class TreeNode<T>(var data:T,var left:TreeNode<T>? = null,var right:TreeNode<T>? = null)
class ParentAndChild<T>(val parent:TreeNode<T>?=null,val child:TreeNode<T>?=null,val isLeft:Boolean = false)
class BinaryTree<T : Comparable<T>>(private var root:TreeNode<T>? = null){
  fun insert(data:T){
    val newNode = TreeNode(data,null,null)
    if(root == null){
      root = newNode
      return
    }
    val queue = Queue<TreeNode<T>>()
    queue.enqueue(root!!)
    while(queue.hasData()){
      val node = queue.dequeue()?: continue
      if(node.left == null){
        node.left = newNode
        break
      }else{
        queue.enqueue(node.left!!)
      }
      if(node.right == null){
        node.right = newNode
        break
      }else{
        queue.enqueue(node.right!!)
      }
    }
  }
  fun balanceTree(){
    val nodes = mutableListOf<TreeNode<T>>()
    val rootNode = root?:return
    inOrderArray(rootNode,nodes)
    root = recursiveBalanceTree(0,nodes.size -1,nodes)
  }
  private fun inOrderArray(node:TreeNode<T>? ,nodes:MutableList<TreeNode<T>>){
    if(node == null) return
    inOrderArray(node.left,nodes)
    nodes.add(node)
    inOrderArray(node.right,nodes)
  }
  private fun recursiveBalanceTree(start:Int,end:Int,nodes:List<TreeNode<T>>):TreeNode<T>?{
  if(start>end) return null
  val mid = (end + start) / 2        
  val node = nodes[mid]
  node.left = recursiveBalanceTree(start, mid - 1, nodes)
  node.right = recursiveBalanceTree(mid + 1, end, nodes)
  return node
  }
  fun findChildAndParent(data:T):ParentAndChild<T>?{
     var currentNode = root
     var parent:TreeNode<T>? = null
     var nodeAndParentInfo:ParentAndChild<T>? = null
     var isLeft:Boolean = false
     while(currentNode != null ){
       if(data == currentNode.data){
         nodeAndParentInfo = ParentAndChild<T>(parent,currentNode,isLeft)
         break
       }else if(data < currentNode.data){
         parent = currentNode
         isLeft = true
         currentNode = currentNode.left
       }else{
         parent= currentNode
         isLeft = false
         currentNode = currentNode.right
       }
     }
     return nodeAndParentInfo
  }
  fun BsInsert(data:T){
    val newNode = TreeNode<T>(data)
    if(root == null){
      root = newNode
      return
    }
    var currentNode = root
    while(currentNode != null){
      if(data < currentNode.data ){
        if(currentNode.left == null){
          currentNode.left = newNode
          return
         }
          currentNode = currentNode.left
      }else{
        if(currentNode.right == null){
          currentNode.right = newNode
          return
        }
        currentNode = currentNode.right
      }
    }
  }
  fun BsDelete(data:T){
    val info = findChildAndParent(data) ?: return
    val node = info.child ?: return    
    val parent = info.parent
    when {
    node.left != null && node.right != null -> BsNodeHasChilds(node)
    node.left != null || node.right != null -> BsNodeHasChild(node)
    else -> {                                
      if (parent == null) root = null
      else if (info.isLeft) parent.left = null
      else parent.right = null
      }
    }
  }
  fun BsNodeHasChild(nodeToDelete:TreeNode<T>){
   val nodeToReplace = nodeToDelete.right ?: nodeToDelete.left ?: return
    nodeToDelete.data = nodeToReplace.data
    nodeToDelete.left = nodeToReplace.left
    nodeToDelete.right = nodeToReplace.right
  }
  fun BsNodeHasChilds(nodeToDelete:TreeNode<T>){
   var parent: TreeNode<T>? = null
  var current: TreeNode<T> = nodeToDelete.right ?: return
  while (current.left != null) {
    parent = current
    current = current.left!!
  }
  nodeToDelete.data = current.data           
  if (parent == null) {
    nodeToDelete.right = current.right       
  } else {
    parent.left = current.right      
  }
  } 
  // Visual ASCII Tree Printer
  fun drawTree() {
    println("\n--- Visual Tree Structure ---")
    if (root == null) {
      println("Tree is empty")
      return
    }
    printNodeVisual(root, "", false) // Root is technically neither left nor right
    println("-----------------------------")
  }

  private fun printNodeVisual(node: TreeNode<T>?, padding: String, isRight: Boolean) {
    if (node == null) return

    print(padding)
    if (padding.isEmpty()) {
      print("Root: ")
    } else if (isRight) {
      print("└── R: ")
    } else {
      print("├── L: ")
    }
    println(node.data)

    // Build padding for children
    val pointerRight = "└── R: "
    val pointerLeft = "├── L: "
    
    // Check if children exist and print them properly
    val hasLeft = node.left != null
    val hasRight = node.right != null

    if (hasLeft || hasRight) {
      val childPadding = padding + if (padding.isEmpty()) "" else if (isRight) "    " else "│   "
      
      // We should print both if they exist, or show placeholders if needed, 
      // but standard traversal can just print non-null or handle both branches:
      printNodeVisual(node.left, childPadding + "    ", false)
      printNodeVisual(node.right, childPadding + "    ", true)
    }
  }
}
class Queue<T>(){
  private val list = LinkedList<T>()

    fun enqueue(data: T) {
        list.insertLast(data)
    }

    fun dequeue(): T? {
        return list.removeFirst()
    }
    fun peek():T?{
      if(list.isEmpty()){
        return null
      }else{
        return list.begin().current()?.data
      }
    }
    fun hasData() = !list.isEmpty()
    fun printQueue() {
        print("Queue (Front to Back): ")
        list.printList()
    }
}
class LinkedList<T>(
  private var head:LinkedListNode<T>?= null, 
  private var tail:LinkedListNode<T>? = null){
  private var length = 0
  fun isEmpty() = length == 0 
  fun insertLast(data : T){
    val newNode = LinkedListNode<T>(data)
    if(head == null){
      head = newNode
      tail = newNode
      length++
      return
    }
    tail?.next = newNode
    tail = newNode
    length++
  }
  fun insertAfter(dataToInsert:T,  dataToInsertAfter:T){
    val it = LinkedListIterator<T>(head)
    val newData = LinkedListNode(dataToInsert)
    val nodeToInsertAfter = findNode(dataToInsertAfter)
    if(nodeToInsertAfter == null)throw Exception("Cannot Find The Data")
    newData.next = nodeToInsertAfter.next
    nodeToInsertAfter.next = newData
    length++    
  }
  fun begin() = LinkedListIterator<T>(head)
  private fun findNode(data:T):LinkedListNode<T>?{
    val it = begin()
    while(it.current() != null){
      if(it.current()?.data == data){
        return it.current()
      }
      it.next()
    }
    return null
  }
  fun removeFirst():T?{
    val firstNode = head
    if(head == null){
      return null
    }
    if(head==tail){
      tail = null
      head = null
      length--
      return firstNode?.data
    }
    head = head?.next
    length--
    return firstNode?.data
  }
  fun printList() {
        var current = head
        while (current != null) {
            print("${current.data} -> ")
            current = current.next
        }
        println("null (Length: $length)")
    }
  }
class LinkedListIterator<T>(
  private var currentNode:LinkedListNode<T>? = null){
  fun hasNext() = currentNode?.next != null
  fun next() { currentNode = currentNode?.next }
  fun data() = currentNode?.data
  fun current() = currentNode
}
class LinkedListNode<T>(val data:T,var next:LinkedListNode<T>? = null)
fun main(){
  val tree = BinaryTree<Int>()
  tree.BsInsert(8)
  tree.BsInsert(4)
  tree.BsInsert(1)
  tree.BsInsert(5)
  tree.BsInsert(6)
  tree.BsInsert(7)
  tree.BsInsert(12)
  tree.BsInsert(14)
  tree.BsInsert(11)
  tree.drawTree()
  tree.BsDelete(8)   
tree.drawTree()
tree.BsDelete(14)   
tree.BsDelete(5)   
tree.drawTree()
val tree2 = BinaryTree<Int>()
  for (i in 1..7) tree2.BsInsert(i)
  tree2.drawTree()      
  tree2.balanceTree()
  tree2.drawTree()      
  
}