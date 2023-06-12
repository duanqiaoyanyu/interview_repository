1. 造成粘包和拆包现象的原因:
 - TCP 发送缓冲区剩余空间不足以发送一个完整的数据包, 将发生拆包;
 - 要发送的数据超过了最大报文长度的限制, TCP 传输数据时进行拆包;
 - 要发送的数据包小于 TCP 发送缓冲区剩余空间, TCP 将多个数据包写满发送缓冲区一次发送出去, 将发生粘包：
 - 接收端没有及时读取 TCP 发送缓冲区里的数据包, 将会发生粘包。

粘包拆包的解决方法:
- 发送端给数据包添加首部, 首部中添加数据包的长度属性, 这样接收端通过首部中的长度字段就可以直到数据包的实际长度啦
- 针对发送的数据包小于缓冲区大小的情况, 发送端可以将不同的数据包规定成同样的长度, 不足这个长度的补充0, 接收端从缓冲区读取固定的长度数据这样就可以区分
不同的数据包了;
- 发送端通过给不同的数据包添加间隔符号确定边界, 接收端通过这个间隔符号就可以区分不同的数据包。

非阻塞IO多路复用(Non-blocking I/O Multiplexing) 是一种用于实现高效的并发网络编程的技术。它结合了非阻塞I/O 和多路复用的概念。
1. 非阻塞 I/O (Non-blocking I/O): 传统的阻塞 I/O 在进行读写操作时, 如果数据没有准备好或者无法立即写入, 会导致线程被阻塞, 直到数据准备好或者
写入完成。 而非阻塞 I/O 通过设置文件描述符为非阻塞模式, 使得读写操作变为非阻塞的, 即无论数据是否准备好或能否立即写入, 都能立即返回。这样可以避免
线程被长时间阻塞, 提高了并发处理能力。
2. 多路复用(Multiplexing): 多路复用是指通过一个线程同时监控多个 I/O 操作, 根据 I/O 操作的就绪状态进行处理。 在传统的阻塞 I/O 模型中, 每个 I/O
操作需要一个线程进行处理, 导致线程数量的增加和线程切换的开销。而多路复用技术通过一个线程来监控多个 I/O 操作, 只有当某个 I/O 操作准备就绪时才会
通知应用程序进行读写操作, 避免了不必要的线程创建和切换开销。

非阻塞 I/O 多路复用结合了上述两个概念, 它使用非阻塞 I/O 方式处理网络 I/O 操作, 并通过多路复用技术监听多个 I/O 操作的就绪状态, 一次性处理多个 I/O
事件。常见的非阻塞 I/O 多路复用的技术包括:
- Select: 使用 select 系统调用来监控多个文件描述符的就绪状态
- Poll: 使用 poll 系统调用来监控多个文件描述符的就绪状态
- Epoll: 在 linux 系统中使用 epoll 系统调用来监控多个文件描述符的就绪状态, 具有更高的性能和可收缩性。

使用 非阻塞 I/O 多路复用技术, 可以在单个线程中同时处理多个连接, 避免了线程创建和切换的开销, 提高了并发处理能力和系统的性能。