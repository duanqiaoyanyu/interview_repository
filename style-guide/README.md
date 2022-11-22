### 2 源文件基础
#### 2.1 文件名  
    源文件名由大小写敏感的顶级类组成, 加上 `.` 作为扩展
#### 2.2 文件编码: UTF-8  
    源文件用 UTF-8 进行编码
#### 2.3 特殊字符
##### 2.3.1 空白字符
    除了行终止符序列, `ASCII` 水平空白字符 (`0x20`) 是唯一出现在源文件中的空白字符. 这意味着:  
    1. 所有的在字符串和字面量里的其他空白字符都被转义
    2. `Tab` 符不用于缩进

##### 2.3.2 特殊转义序列
对于有特殊转义序列(`\b`, `\t`, `\n`, `\f`, `\"` `\\`)的字符, 使用转义序列而不是响应的八进制(e.g. `\012`) 或者 Unicode(e.g. `\u000a`) 转义

##### 非 ASCII 字符
对于剩余的 非ASCII 字符, 要么实际的 Unicode 字符(e.g. `∞`) 或者同样的 Unicode 转义(e.g. `\u221e`)使用. 选择取决于哪个使得代码更易读和理解,
尽管 Unicode 转义外部字符串字面值和注释是十分不推荐的

> 在 Unicode 转义, 偶然甚至当 Unicode 字符使用, 一个解释性的注释会非常有帮助

例子:

| Example                                                                                                                 | Discussion                                                                |
|:------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------|
| String unitAbbrev = "μs";	                                                                                              | Best: perfectly clear even without a comment.                             |
| String unitAbbrev = "\u03bcs"; // "μs"                                                                                  | Allowed, but there's no reason to do this.                                |
| String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"                                                                  | Allowed, but awkward and prone to mistakes.                               |
| String unitAbbrev = "\u03bcs";                                                                                          | Poor: the reader has no idea what this is.                                |
| return '\ufeff' + content; // byte order mark | Good: use escapes for non-printable characters, and comment if necessary. |

> 永远不要使得你的代码更少的可读性, 简单有些项目或许不处理 非ASCII 字符, 如果那应该发生, 这些项目就坏了并且必须被修复

### 3 源文件结构
    一个源文件组成, 按顺序  
    1. 执照或者版权信息, 如果存在  
    2. 包陈述  
    3. 导入陈述  
    4. 确切的一个顶级类  
    **精确一个空白行**分隔每一个部分  

#### 3.1 执照或者版权信息, 如果存在
    如果执照或者版权信息属于一个文件, 它应该属于这里
#### 3.2 包陈述
    包陈述是不换行的. 列限制(4.4节, 列限制: 100) 不适用于导包语句
#### 3.3 导入语句
##### 3.3.1 没有通配符导入
    通配符导入, 静态或者其他, 不使用的
##### 3.3.2 没有换行
    导入语句是不换行的. 列限制(4.4节, 列限制: 100) 不适用于导入语句
##### 3.3.3 排序和空格
    导入的顺序如下:  
    1. 所有的静态导入单独一块  
    2. 所有的非静态导入在单独一块  
    如果这里既有静态又有非静态导入, 单独一个空白行分隔两块. 导入语句之间没有其他的空白行  
    在每一块导入的名字以 ASCII 顺序(注意: 这不是同样重要语句按照 ASCII 顺序, 因为 '.' 排在 ','前面)

##### 3.3.4 没有静态类导入
静态导入不用于静态嵌套类. 他们通过正常导入

#### 3.4 类声明
##### 3.4.1 明确的一个顶级类声明
    每一个顶级类驻留在它自己的源文件中

##### 3.4.2 类内容的排序
你对你的类的成员和初始值选择的顺序会对可学习性有重大影响. 然而, 没有一个单独的正确方法说怎么做; 不同的类或许排序他们的内容用不同的方式.  
重要的是每一个类使用 `一些逻辑顺序`, 被问到的时候它维护者可以解释, 举个例子, 新方法不要只是习惯性地添加到类的最后面, 因为这会产生按照日期添加的顺序,
这不是一个逻辑顺序

##### 3.4.2.1 重载: 从不撕裂
共享相同名称的类的方法出现在一个连续的组里, 中间没有其他成员. 同样应用于多个构造器(经常有同样的名字). 这条规则运用即使当修饰符 比如 `static` 或者
`private` 在方法间不同.

### 格式
    **术语说明**: 块状构造涉及到类的 body, 方法或者构造器. 通过 4.8.3.1 节 在数组初始化器, 任意数组初始化器或许可选择性地被认为是一个块状构造

#### 4.1 括号
##### 4.1.1 可选括号的使用
    括号与 `if`, `else`, `for`, `do`, 和 `while` 语句一起使用时, 即使当语句体时空的或者只包含一个单句  
    其他可选括号, 比如在一个 `lambda` 表达式, 仍然是可选的

##### 4.1.2 非空块: K & R 风格
    括号遵循 Kernighan 和 Ritchie 风格("Egyptian brackets") 对于非空块和块状构造  
        - 在左大括号之前没有换行符, 除了以下详细描述的
        - 在开始括号后换行
        - 在结束括号前换行
        - 仅当该括号终止语句或者终止方法、构造方法、或命名类的主体时, 才在结束括号后面换行. 例如, 如果括号后面跟着 else 或 逗号, 则括号后面没有换行  

异常: 有些时候这些规则允许一个以 `;` 结尾的单句, 一块语句可以出现, 并且这块语句的开始括号前面有一个换行符. 像这样的块时典型的介绍的限制本地变量的作用范围,
举例里面有一些 switch 语句  

例子:
```java
return () -> {
  while (condition()) {
    method();
  }
};

return new MyClass() {
  @Override public void method() {
    if (condition()) {
      try {
        something();
      } catch (ProblemException e) {
        recover();
      }
    } else if (otherCondition()) {
      somethingElse();
    } else {
      lastThing();
    }
    {
      int x = foo();
      frob(x);
    }
  }
};
```
一些枚举类的异常情况在 4.8.1节中给出  
##### 4.1.3 空块: 可能简洁
    一个空快或者块状构造可能会是 K & R 风格(在 4.1.2节描述的一样) 或者, 它在它开始之后应该立即结束, 没有字符或者在之间只有换行符(`{}`), 除非它是一个
多块语句的一部分(直接包含多块语句: `if/else` 或者 `try/catch/finally`)  
例子:
```java
  // This is acceptable
  void doNothing() {}

  // This is equally acceptable
  void doNothingElse() {
  }
```

```java
  // This is not acceptable: No concise empty blocks in a multi-block statement
  try {
    doSomething();
  } catch (Exception e) {}
```

#### 4.2 块缩进: +2 空格
    每次一个新块或者一个块状构造开启, 缩进增加了两个空格. 当块结束的时候, 缩进返回到之前的缩进水平. 缩进水平适用于整个快的代码和注释.(看 4.1.2 节
的例子, 非空块 K & R 风格)

#### 4.3 每行一个语句
    每一个语句后面跟着一个换行

#### 4.4 列限制: 100
    Java 代码有一个列限制 100 字符数. 一个 "字符" 意味着任意 Unicode 代码点. 就像下面期盼的那样, 任意超过这个限制的行都必须换行, 就像 4.5节
解释的那样, 换行  
> 任意 Unicode 代码点计数作为一个字符, 即使它的展示宽度是更大或者更小. 举个例子, 如果使用全宽字符, 你可以选择这个规则严格要求的更早进行换行  

异常:
1. 不可能遵守列限制的行(举例, 一个 Javadoc 中的长 URL, 或者一个长的 JSNI 方法引用)
2. `包` 和 `导入` 语句(见 3.2节 包语句 和 3.3节 导入语句)
3. 注释中的命令行可能会被拷贝和粘贴进 shell
4. 非常长的缩进, 很少地偶然他们被调用, 允许去超过这个列限制. 在这种情况下, 周围代码合理的换行是由 google-java-format 生产的

#### 4.5 换行
**术语说明**: 代码合法占据一行被分成多行, 这个行为就叫做换行  
没有全面的, 确定性的公式精确的表明在每一种情况下如何换行. 对同一片代码经常会有多种合法的方式去换行  
> 注意: 然而换行的经典理由是为了避免溢出列限制, 即使实际上符合列限制的代码也有可能由作者的审慎去换行
> 贴士: 解析一个方法或者本地变量或许可以解决这个问题而不需要去换行

##### 4.5.1 哪里去打破
    换行的主要指示是: 更喜欢在更高级的语法级别终端. 也:  
    1. 当一行在一个非赋值运算符被打断, 打断在符号之前. (注意这和 Google Style 其他语言的实战使用不同, 比如 C++ 和 JavaScript.)  
        这也适用于下列 "操作似" 的符号:  
        - 点分隔符(`.`)
        - 一个方法引用的两个冒号(`::`)
        - 一个类型绑定的符号(`<T extends Foo & Bar>`)
        - 一个 catch 块的管道(`catch (FooException | BarException e)`)
    2. 当一个行在一个赋值运算符打断, 打断经典地跟在符号后面, 但是这种方式是可以接受的
        - 这也适用于 "赋值运算符似" 的冒号在加强 `for`("foreach") 语句
    3. 一个方法或者构造器的名字保持附加在开附带物(`(`)在它后面
    4. 一个逗号(`,`) 保持在它之前的 token 后面
    5. 一个换行从来不会打破 lambda表达式中邻近的的箭头, 除非如果 lambda 表达式的身体包含一个单无支撑表达式, 在箭头后立刻出现一个打断. 例子:  
```java
MyLambda<String, Long, Object> lambda =
    (String label, Long value, Object obj) -> {
        ...
    };

Predicate<String> predicate = str ->
    longExpressionInvolving(str);
```
> 换行的首要目标是为了有清晰的代码, 不必要的代码满足最小的行数

##### 4.5.2 缩进继续行至少 +4 个空格
    当换行, 在第一行的每一个继续行至少从原始行缩进 +4  
    当有多个连续行, 缩进或许如预期地超过 +4. 通常, 两个连续行使用同样的缩进级别当且仅当他们用语法平行元素开头  
    4.6.3节 在 Horizontal alignment 地址 劝阻实战使用一个空间的可变数量去对其确定的 token 和之前的行

#### 4.6 空格

##### 4.6.1 垂直的空格
            一个单独空白行经常出现:
            1. 在一个类的连续的成员或者初始化器: 字段, 构造器, 方法, 嵌套类, 静态初始化项, 和实例初始化项.  
                - 异常: 在两个连续的字段的空行(他们之间没有其他代码)是可选择的. 这样的空行使用是需要去创造一个字段的逻辑分组  
                - 异常: 在 4.8.1节中覆盖的枚举常量之间的空行  
            2. 这个文档的其他节需要的那样(比如 3节, 源文件结构, 和 3.3节 导入语句)  

一个单独的空白行也许会出现在能提升可读性的任何地方, 举个例子在语句中间组织代码进逻辑小节. 在类的第一个成员或者初始化项前的空行, 或者在类的最后一个成员
或者初始化项之后的空行, 既不鼓励也不鼓励.  

多个连续空行是允许的, 但是不被需要或者鼓励的.

##### 4.6.2 水平的空格
超过其他语言或者风格规则需要, 并且除了文字, 注释和 Javadoc, 一个单独的 ASCII 空格仅仅出现在下列的地方  
1. 分隔任何保留字, 比如 `if`, `for` 或 `catch`, 从一个开插入语(`(`)在那行跟随它
2. 分隔任何保留字, 比如 `else` 或 `catch`, 在那行从一个在它之前的关闭的弯括号
3. 在任何开弯括号(`{`), 有两个特殊情况:  
    - `@SomeAnnotation({a, b})` (没有使用空格)
    - `String[][] x = {{"foo"}};` (在 `{{` 之间不需要空格, 按照下面的第九项)  
4. 在任何二元或者三元操作符的两侧. 这适用于下列的 "操作符类" 符号:  
    - 在连词类型绑定的符号: `<T extends Foo & Bar>`
    - 处理多个异常的捕获块的管道: `catch (FooException | BarException e)`
    - 在一个加强 `for`("foreach") 语句的冒号
    - 在一个 lambda 表达式的箭头: `(String str) -> str.length()`  
   但是不  
    - 一个方法引用的两个冒号(`::`), 写作像是 `Object::toString`
    - 点分隔符(`.`), 写作像是 `object.toString()`
5. `,:;`或者一个转换的关闭插入语(`)`)后面
6. 在开启一个注释的双斜杠(`//`)和任意内容之间. 多个空格也是允许的
7. 在开启一个注释的双斜杠(`//`)和注释文本之间, 多个空格也是允许的
8. 在一个声明变量和类型之间: `List<String> list`
9. 仅在数组初始值设定项的两个大括号内可选  
   - `new int[] {5, 6}` 和 `new int[] { 5, 6 }` 都是合法的  
10. 在一个类型注解和 `[]` 或者 `...` 之间  

这个规则从不翻译为在一行的开始或者结果需要或者禁止额外的空格; 它只涉及内部空间  

##### 4.6.3 水平的对齐: 从不需要
术语说明: 水平对其是在你的代码中增加可变数量的额外空格的实战, 其目的是为了在之前行的其他确定 tokens 下面直接出现确定的 tokens  

这个实战是被允许的, 但是从不被 Google Style 需要. 当它已经使用的地方甚至不需要去保持水平对齐  
这里是一个没有水平对其的例子, 然后使用水平对其
```java
private int x; // this is fine
private Color color; // this too

private int   x;      // permitted, but future edits
private Color color;  // may leave it unaligned
```
> 贴士: 对其可以帮助可读性, 但是它创造了未来维护性的问题. 考虑到未来的改变仅仅需要接触一行. 这个改变会使得之前令人愉悦的格式损坏, 这时被允许的. 
更鼓励编码者(或许是你)去调整行附近的空格, 可能触发一个重新格式化的级联序列. 现在一行改变有一个"爆炸半径". 这在最坏情况可以导致无指向繁忙工作, 
但是最好它仍然腐蚀版本历史信息, 缓慢下来回顾和加剧混合冲突.

#### 4.7 分组括号: 推荐
可选的分组括号是被忽略的当且仅当作者和回顾者同意没有合理的机会代码没有他们将会被错译, 他们不会导致代码更易读. 不合理的去认为每个读者有整个 Java 操作符
优先级表记忆

#### 4.8 指定结构
在每一个逗号后面跟随一个枚举常量, 一个换行是可选的. 一个额外的空白行(通常仅仅一个)也是允许的. 这时一个可能性:  
```java
private enum Answer {
  YES {
    @Override public String toString() {
      return "yes";
    }
  },

  NO,
  MAYBE
}
```

一个枚举类没有方法和文档在他的常量上或许可选的格式化,就像它是一个初始化数组(见 4.8.3.1 节 数组初始化项)  
```java
private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
```
因为枚举类是类, 所有的对于格式化类的其他规则也全都适用  

##### 4.8.2 变量声明
###### 4.8.2.1 每个声明一个变量

每个变量声明(字段或者本地)声明仅仅一个变量: 声明比如是 `int a, b;` 是不使用的.  
异常: 多个变量声明是可以接受的在一个 `for` 循环的头部  

###### 4.8.2.2 当需要的时候才声明
本地变量不应该习惯性的声明在他们包含块或者块状构造的开始, 本地变量被声明靠近他们第一次使用的地方, 去最小化他们的范围. 本地变量声明典型的有初始化值,
或者在声明之后立即初始化.  

##### 4.8.3 数组

###### 数组初始值: 可以是 "块状"
任何一个数组初始化值可选的格式化就像他是一个 "块状构造". 举个例子, 下列的是全部合法的(不是一个详尽的清单)  
```java
new int[] {           new int[] {
  0, 1, 2, 3            0,
}                       1,
                        2,
new int[] {             3,
  0, 1,               }
  2, 3
}                     new int[]
                          {0, 1, 2, 3}
```

###### 不要 C-风格 数组声明
方括号形式的一部分类型, 不是变量: `String[] args`, 不是 `String args[]`.  

##### 4.8.4 switch 语句
术语说明: 一个 switch 块的内部的括号是一个或者更多语句组. 每一个语句组包括一个或者更多 switch 标签(要么是 `Case Foo` 要么是 `default:`),
跟随一个或者多个语句(或者, 对于最后一个语句组, 0个或者多个语句).  

###### 4.8.4.1 缩进
正如和任何其他块一样, switch 块的内容是缩进 +2.  

 在一个 switch 标签后, 有一个换行, 并且缩进级别增加了 +2, 精确的好像一个块被开启了一样. 下列 switch 标签返回之前的缩进级别, 就好像一个块已经被关闭了.  

###### 4.8.4.2 直通: 评论
在一个 switch 块里面, 每一个语句组要么突然用一个 `break`, `continue`, `return`或者抛出异常突然结束, 要么就是用一个评论去标记显示执行将会
或者粗寻进入下一个语句组. 任意一个交流直通的评论是足够的(典型的 `// fall through`). 这个特殊的注释不需要在 switch 块的最后一个语句组. 举例:  
```java
switch (input) {
  case 1:
  case 2:
    prepareOneOrTwo();
    // fall through
  case 3:
    handleOneTwoOrThree();
    break;
  default:
    handleLargeNumber(input);
}
```
注意在 `case 1:` 之后不需要注释, 仅仅在语句组的最后.  

###### 4.8.4.3 存在 `default` 标签
每一个 switch 语句包含一个 `default` 语句组, 即使里面不包含代码.  

**异常**: 一个对于一个 `enum` 类型的 switch 语句或许会忽略这个 `default` 语句组, 如果它包括明确的情况覆盖了所有枚举类型可能的值. 启用 IDEs 或者
其他静态分析工具去发布一个警告如果有任何一个情况错过了.  

##### 4.8.5 注解

###### 4.8.5.1 类型-使用 注解

在被注解类型出现前立即出现类-使用注解. 一个注解是一个类-使用注解如果他有元注解 `@Target(ElementType.TYPE_USE)`. 举个例子:  
```java
final @Nullable String name;

public @Nullable Person getPersonByName(String name);
```

###### 4.8.5.2 类注解
在文档块后立即出现适用于类的注解, 并且每一个注解都是列在一行(那是, 每一个注解一行). 这些行打断不构成换行(4.5节, 换行), 所以所及级别不增加. 举个例子:  
```java
@Deprecated
@CheckReturnValue
public final class Frozzler { ... }
```

###### 4.8.5.3 方法和构造器注解
在方法和构造器声明上的注解的规则是和之前节的一样. 举例:
```java
@Deprecated
@Override
public String getNameIfPresent() { ... }
```

**异常**: 单个参数注解或许会替换出现签名的第一行, 举个例子:
```java
@Override public int hashCode() { ... }
```

###### 4.8.5.4 字段注解
运用到一个字段的注解或许会在文档块后立即出现, 但是在这个例子中, 多个注解(可能参数化)或许会被列在同一行; 举例:  
```java
@Partial @Mock DataLoader loader;
```

###### 4.8.5.5 参数和本地变量注解
没有指定规则对于在参数和本地变量上的格式化注解(除了, 当然, 当注解是一个类-使用注解).

##### 4.8.6 注释
这节地址 实现注释. Javadoc 地址单独在 7节, Javadoc.  
任何换行符之前都可以有任意空格, 后跟实现注释. 这样的评论使该行非空白  

###### 4.8.6.1 块注释风格
块注释缩进和周围的代码一样的级别. 他们或许是在 `/* ... */` 风格或者 `// ...` 风格. 对于多行 `/* ... */` 注释, 后来的行必须以 `*` 开头,
对其之前行的 `*`  

```java
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
```

注释不包含在用星号或者其他字符绘制的框中.  

> 当写多行注释时, 使用 `/* ... */` 风格如果你想要自动代码格式化去重新换行当必要(段-风格). 大多数格式不重新换行在 `// ...` 风格注释块  

##### 4.8.7 修饰符
类和成员修饰符, 当存在, 出现在被 Java 语言指定的推荐顺序
```java
public protected private abstract default static final transient volatile synchronized native strictfp
```

##### 4.8.8 数字文字
长值整形文字使用一个大写的 `L` 后缀, 从来不用小写(为了去避免混淆与数字 `1`). 举个例子, `3000000000L` 而不是 `3000000000l`.

### 5 命名

#### 5.1 所有的标识符的公共规则
标识符仅仅使用 ASCII 字母和数字, 并且, 一下标记的例子的最小数量, 下划线. 因此每一个合理的标识符名字被正则表达式 `\w+` 匹配.  
在谷歌风格, 不使用特殊前缀或者后缀. 举个例子, 这些名字不是谷歌风格: `name_`, `mName`, `s_name` 和 `kName`.  

#### 5.2 标识符类型的规则

##### 5.2.1 包名

包名仅仅使用小写字母和数字(下划线). 连续的单词时简单的串联在一起. 举个例子, `com.example.deepspace`, 而不是 `com.example.deepSpace` 或者
`com.example.deep_space`.  

##### 5.2.2 类名

类型用大写驼峰书写.  

类名是典型的名词或者名词短语. 举例, `Character` 或者 `ImmutableList`. 接口名字或许会是名词或者名词短语(举例, `List`), 但是或许有时候会是
形容词或者形容词短语替换(举例, `Readable`).  
对于命名注解类型没有指定的规则或者甚至很好的建设性惯例  
一个测试类有一个以 `Test` 结尾的名字, 举例, `HashIntegrationTest`. 如果覆盖一个单类, 它的名字就是类名加上 `Test`, 举例 `HashImplTest`.  

##### 5.2.3 方法名字

方法名字用小写驼峰书写.  
方法名字是典型的动词或者动词短语. 举例, `sendMessage` 或者 `stop`.  

下划线或许会出现在单元测试方法名中去分隔名字的逻辑组件, 对于每一个在小写驼峰中的组件, 举例, `transferMoney_deductsFromSource`. 没有一个
正确的方式去命名测试方法.  

##### 5.2.4 常量名字

常量名字使用 `UPPER_SNAKE_CASE`: 所有大写字母, 并且每一个单词与下一个单词分隔通过一个单独的下划线. 但是什么是一个常量, 精确的?  

常量是静态最终的字段它的内容是深度不变的并且它的方法没有可探测性的副作用. 例子包括 原始值, 串, 不变值类, 并且设置为 `null` 的任何东西. 如果例子的
可观测的状态可以改变, 他不是一个常量.  
仅仅意图去从不变这个对象是不够的. 举个例子:  

```java
// Constants
static final int NUMBER = 5;
static final ImmutableList<String> NAMES = ImmutableList.of("Ed", "Ann");
static final Map<String, Integer> AGES = ImmutableMap.of("Ed", 35, "Ann", 32);
static final Joiner COMMA_JOINER = Joiner.on(','); // because Joiner is immutable
static final SomeMutableType[] EMPTY_ARRAY = {};

// Not constants
static String nonFinal = "non-final";
final String nonStatic = "non-static";
static final Set<String> mutableCollection = new HashSet<String>();
static final ImmutableSet<SomeMutableType> mutableElements = ImmutableSet.of(mutable);
static final ImmutableMap<String, SomeMutableType> mutableValues =
    ImmutableMap.of("Ed", mutableInstance, "Ann", mutableInstance2);
static final Logger logger = Logger.getLogger(MyClass.getName());
static final String[] nonEmptyArray = {"these", "can", "change"};
```
这些名字是典型的名词或者名词短语.  

##### 5.2.5 非常量字段名字

非常量字段名字(静态或者其他的)用小写驼峰书写.  
这些名字是典型的名词或者名词短语. 举个例子, `computedValues` 或者 `index`.

##### 5.2.6 参数名字

参数名字用小写驼峰写
一个字符的参数名字在公共方法中应该被避免

##### 5.2.7 本地变量名

本地变量名用小写驼峰写.  
甚至当最终或者不变, 本地变量也不考虑设置为常量, 并且不应该设置为常量的风格

##### 5.2.8 类型变量名字
每一个类型变量命名有两种风格:  
- 一个大写字母, 可选择的跟随一个数字(比如像 `E`, `T`, `X`, `T2`)
- 用给类的形式命名(见 5.2.2节, 类名), 跟随一个大写字母 `T`(举例: `RequestT`, `FooBarT`).

#### 5.3 驼峰: 定义
有时候有多种合理性的方式去转换一个英文短语为一个驼峰, 比如当缩写词或者不寻常构造比如 "IPV6" 或者 "iOS" 存在. 未来提高可预测性, 谷歌风格指定下列
决定性的约束.  

从名字的散文形式开始:  
1. 转换短语去平 ASCII 并且一处任何撇号. 举例, "Müller's algorithm" 或许会变成 "Muellers algorithm".  
2. 划分这个结果到单词, 在空间和任意一六的标点上撕裂(典型连字符).  
   - 推荐: 如果一个单词在公众使用中早已有一个惯例驼峰外形, 撕裂它为他的连续部分(e.g., "AdWords" 变成 "ad words"). 提到一个单词像 "iOS" 不是
真的是驼峰; 它定义了一个惯例, 所以这个推荐不适用.  
3. 现在小写字母一切(包括缩写词), 然后大写第一个字符:  
   - ... 每个单词, 以产生大驼峰大小写, 或
   - ... 除第一个单词外的每个单词, 以产生小驼峰大小写  
4. 最终, 加入所有的单词到一个单标识符  

注意到源单词的大小写通常整个无视. 例子:  

| Prose form         | Correct           | Incorrect |
|:-------------------|:------------------| :--- |
| "XML HTTP request" | XmlHttpRequest    | XMLHTTPRequest |
| "new customer ID"  | newCustomerId     | newCustomerID |
| "inner stopwatch" | innerStopwatch    | innerStopWatch |
| "supports IPv6 on iOS?" | supportsIpv6OnIos | supportsIPv6OnIOS |
| "YouTube importer" | YouTubeImporter   |  |
| "YouTube importer" | YoutubeImporter * |  |
  
*可接受的, 但是不推荐.

> 注意: 一些单词在英语里连字符是模棱两可的: 举个例子 "nonempty" 和 "non-empty" 都是正确的, 所以方法名字相似都是正确 `checkNonempty` 和 `checkNonEmpty`

### 6. 编程实战

#### 6.1 `@Override`: 经常使用
一个方法标记 `@Override` 注解只要方法是合法的. 者包括一个类方法覆盖一个父类的方法, 一个类方法实现一个接口的方法, 和一个接口方法相关的一个父接口方法.
  
异常: `@Override` 或许会忽略当它的父方法是 `@Deprecate`

#### 6.2 捕获异常: 不忽略
  
期待像下面标记的一样, 不做任何事在响应里去捕获一个异常是罕有正确.(典型的响应是去日志它, 或者如果它被认为 "不肯能", 重新抛出它作为一个 `AssertionError``)  

当它真的合适去不采取任何行为在一个捕获块里, 理由用一个注释解释这时合理的.  

```java
try {
  int i = Integer.parseInt(response);
  return handleNumericResponse(i);
} catch (NumberFormatException ok) {
  // it's not numeric; that's fine, just continue
}
return handleTextResponse(response);
```

异常: 在测试, 一个捕获异常或许会忽略且没有注释, 如果它的名字是或者以 `expected` 开头. 下列是一个非常常见的语法对于确保测试下的代码确实抛出了一个期望的异常,
所以在这里注释是没必要的.  

```java
try {
  emptyStack.pop();
  fail();
} catch (NoSuchElementException expected) {
}
```

#### 6.3 静态成员: 合格使用类
当引用一个静态类成员必须合格, 合格用类名, 不是一个引用或者类类型的表达式

```java
Foo aFoo = ...;
Foo.aStaticMethod(); // good
aFoo.aStaticMethod(); // bad
somethingThatYieldsAFoo().aStaticMethod(); // very bad
```

#### 6.4 最终项: 不使用
嫉妒罕有去覆盖 `Object.finalize`  

> 不要那么做, 如果你绝对必须, 第一步阅读和理解 `Effective Java Item 8`, "避免最终项和清除者" 非常仔细, 然后不要那么做.
