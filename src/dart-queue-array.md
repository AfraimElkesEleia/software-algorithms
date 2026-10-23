# dart-queue-array

```dart
class Queue<T>{
  List<T> _queue = [];
  void enqueue(T data){
    _queue.add(data);
  }
  T dequeue(){
    if(_queue.isEmpty){
      throw Exception("Cannot dequeue into an empty queue.");
    }
    final T data = _queue.last; 
    _queue.removeAt(0);
    return data;
  }
  T peek(){
    if(_queue.isEmpty){
      throw Exception("Cannot peek into an empty queue.");
    }
    return _queue.first;
  }
  bool hasData(){
     return _queue.isNotEmpty;
  }
  void printQueue(){  
       print("Queue : ${_queue.join(' -> ')} -> ");
  }
}
void main(){
  final Queue<int> queue = Queue();
  queue.enqueue(5);
  queue.enqueue(20);
  queue.enqueue(25);
  queue.printQueue();
  queue.dequeue();
  queue.printQueue();
  queue.enqueue(50);
  print("Peek: ${queue.peek()}");
  queue.printQueue();
  List<int> processedQueue = [];
  while(queue.hasData()){
    processedQueue.add(queue.peek()+1);
    queue.dequeue();
  }
  print("Peocesed Queue: ${processedQueue.join(" -> ")}");
}
```
