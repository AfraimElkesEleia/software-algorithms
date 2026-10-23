# dart-stack-array

```dart
class Stack<T> {
  final List<T> _stack = [];

  void push(T data) {
    _stack.add(data);
  }

  T pop() {
    if (_stack.isEmpty) {
      throw StateError("Cannot pop from an empty stack.");
    }
    return _stack.removeLast();
  }

  T peek() {
    if (_stack.isEmpty) {
      throw StateError("Cannot peek into an empty stack.");
    }
    return _stack.last;
  }

  bool hasData() {
    return _stack.isNotEmpty;
  }

  void printStack() {
    print("Stack : ${_stack.join(' -> ')}");
  }
}

void main() {
  final Stack<int> stack = Stack();
  stack.push(5);
  stack.push(20);
  stack.push(25);
  stack.printStack(); 
  stack.pop();        
  stack.printStack(); 
  stack.push(50);
  print("Peek: ${stack.peek()}"); 
  stack.printStack(); 
}
```
