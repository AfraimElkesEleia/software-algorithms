# dart-linkedist-single

```dart
class LinkedListNode<T>{
  LinkedListNode<T>? next = null;
  T data;
  LinkedListNode({required this.data,next = null});
}
class LinkedListIterator<T>{
  late LinkedListNode<T>? _currentNode;
  LinkedListIterator({required LinkedListNode<T>? node }){
    _currentNode = node;
  }
  LinkedListNode<T>? next(){
    _currentNode = _currentNode?.next;
    return _currentNode;
  }
  T? data(){
    return _currentNode?.data;
  }
  LinkedListNode<T>? current(){ return _currentNode; }
}
class LinkedList<T>{
  LinkedListNode<T>? _head = null;
  LinkedListNode<T>? _tail = null;
  int _length = 0;
  void addNewItem(T data){
    final LinkedListNode<T> newNode = LinkedListNode<T>(data : data);
    if(_head == null&&_tail == null) {
      _head = newNode;
    }
    else{
      _tail?.next = newNode;
    }
    _tail = newNode;
    _length++;
  }
  LinkedListNode<T>? findNode(T data){
    LinkedListIterator<T> it = LinkedListIterator(node:_head);
    while(it.current() != null){
      if(it.data() == data){
        return it.current();
      }
      it.next();
    }
    return null;
  }
  LinkedListNode<T>? findParent(T data){
    LinkedListIterator<T> it = LinkedListIterator(node:_head);
    while(it.current() != null){
      if(it.current()?.next?.data == data){
        return it.current();
      }
      it.next();
    }
    return null;
  }
  void printItems(){
    LinkedListIterator<T> it = LinkedListIterator(node:_head);
    StringBuffer buffer = StringBuffer();
    buffer.write("${it.data()} -> ");
    while(it.next() != null){
      buffer.write("${it.data()} -> ");
    }
    print(buffer.toString());
  }
  void deleteNode(T data){
    LinkedListNode<T>? node = findNode(data);
    if(node == null){
        throw Exception("Cannot find node you want to delete");
    }
    if(_head == _tail && _head == node){
      _head = null;
      _tail = null;
    }
    else if(_head?.data == data){
      _head = _head?.next;
    }
    else{
      LinkedListNode<T>? parentNode = findParent(data);
      if(parentNode == null){
        throw Exception("Cannot find node you want to delete");
      }
      if(parentNode.next == _tail){
        _tail = parentNode;
        parentNode.next = null;
      }else{
        parentNode.next = node.next;
      }
    }
    _length-- ;
  }
  int getLength(){
    return _length;
  }
  LinkedListIterator<T> getIterator() {
    return LinkedListIterator<T>(node: _head);
  }
  void insertAfter(T targetData , T newData){
    if(_head == null) throw Exception("LinkedList is Empty");
    final LinkedListNode<T>? node = findNode(targetData);
    if(node == null ) throw Exception("InsertAfter : Node Doesnt Exist");
    final LinkedListNode<T> newNode = LinkedListNode(data:newData);
    newNode.next = node.next;
    node.next = newNode;
    if(node == _tail){
      _tail?.next = newNode;
      _tail = newNode;
    }
   _length++;
  }
  void insertBefore(T targetData , T newData){
    if(_head == null) throw Exception("LinkedList is Empty");
     final LinkedListNode<T> newNode = LinkedListNode(data:newData);
     if (_head!.data == targetData) {
         newNode.next = _head;
         _head = newNode;
         _length++;
         return;
    }
    final LinkedListNode<T>? node = findParent(targetData);
    if(node == null ) throw Exception("InsertBefore : Node Doesnt Exist");
    newNode.next = node.next;
    node.next = newNode;
    _length++;
  }
}
extension LinkedListExtension<T> on LinkedList<T>{
  LinkedList<T> copy(){
    final LinkedListIterator<T> it = this.getIterator();
    final LinkedList<T> copiedLinkedList = LinkedList();
    while(it.current() != null){
      copiedLinkedList.addNewItem(it.current()!.data);
      it.next();
    }
    return copiedLinkedList;
  }
}
void main(){
  LinkedList<int> linkedList = LinkedList();
  linkedList.addNewItem(5);
  linkedList.addNewItem(70);
  linkedList.addNewItem(60);
  linkedList.addNewItem(10);
  linkedList.addNewItem(20);
  linkedList.addNewItem(110);
  linkedList.printItems();
  linkedList.deleteNode(20);
  linkedList.printItems(); 
  linkedList.deleteNode(5);
  linkedList.printItems(); 
  linkedList.addNewItem(80);
  linkedList.printItems(); 
  linkedList.insertAfter(60,70);
  linkedList.printItems();
  linkedList.insertAfter(110,120);
  linkedList.printItems();
  linkedList.insertBefore(10,9);
    linkedList.printItems();
   print("Length : ${linkedList.getLength()}");
  LinkedList<int> newLinkedList = linkedList.copy();
  print("Copied Linked List : ");
  newLinkedList.printItems();
}

```
