## 让我们校验一些行为
下列例子 mock 了一个列表，因为大多数人对接口比较熟悉（比如 新增、获取、清除 方法）.
事实上，人们不 mock List 类. 使用一个真实的实例代替.
```java
//Let's import Mockito statically so that the code looks clearer
 import static org.mockito.Mockito.*;

 //mock creation
 List mockedList = mock(List.class);

 //using mock object
 mockedList.add("one");
 mockedList.clear();

 //verification
 verify(mockedList).add("one");
 verify(mockedList).clear();
```
一旦创建，一个 mock 对象将会记住所有的交互. 然后你可以选择性地验证你感兴趣的交互.

## 2. 关于一些存根
```java

 //You can mock concrete classes, not just interfaces
 LinkedList mockedList = mock(LinkedList.class);

 //stubbing
 when(mockedList.get(0)).thenReturn("first");
 when(mockedList.get(1)).thenThrow(new RuntimeException());

 //following prints "first"
 System.out.println(mockedList.get(0));

 //following throws runtime exception
 System.out.println(mockedList.get(1));

 //following prints "null" because get(999) was not stubbed
 System.out.println(mockedList.get(999));

 //Although it is possible to verify a stubbed invocation, usually it's just redundant
 //If your code cares what get(0) returns, then something else breaks (often even before verify() gets executed).
 //If your code doesn't care what get(0) returns, then it should not be stubbed.
 verify(mockedList).get(0);
```
- 默认情况下，对于所有的方法返回一个值, 一个 mock 对象将会返回要么 null, 要么是一个基本类型/包装类型值, 或者一个空集合，作为一个合适的. 举例对于 `int/Interger` 将会返回 `0`, `boolean/Boolean` 将会返回 `false`, `List` 将会返回一个空的列表.