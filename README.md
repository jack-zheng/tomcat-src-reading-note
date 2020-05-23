# Tomcat 8.5.55 源码阅读

该项目用于实践拉钩教育 Tomcat源码剖析 课程

## 实验环境搭建

1. 去到 Tomcat 官网下载 src 源码
1. 在源码下新建 `pom.xml` 并添加项目依赖
1. 根目录下新建 source 文件夹，并将 webapps, conf 文件夹移入其中
1. 配置运行 VM 参数

```默认启动参数
-Dcatalina.home=C:\Users\jack\gitStore\apache-tomcat-8.5.55-src\source
-Dcatalina.base=C:\Users\jack\gitStore\apache-tomcat-8.5.55-src\source
-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager
-Djava.util.logging.config.file=C:\Users\jack\gitStore\apache-tomcat-8.5.55-src\source\conf\logging.properties
```

**问题**

1. 运行 Bootstrap 类，访问 localhost:8080 报错 `org.apache.jasper.JasperException: 无法为JSP编译类`, 到 ContextConfig 中的 `configureStart` 方法中添加语句 `context.addServletContainerInitializer(new JasperInitializer(), null);` 就行了
1. 运行时编译错误，什么 rmi 之类的 error, 应该时 java1.8 之后兼容性问题，更具 idea 提示修改就行了
    + 最终这个方案好像不太好使，通过在 pom.xml 中添加 `compilerArgs` 参数解决问题
    
## 追踪 xml 解析过程

**想要了解的问题**

1. Tomcat 是怎么加载 xml 文件的
1. 他是怎么给对应的对象赋值的
1. 过程中有没有类似 context 的这种概念

简单的 review 了一下 Bootstrap 类的 main 方法，然后看了一下只要的 process（init, load, start）, 发现加载 xml 文件的逻辑是在 load 方法中的

在 Tocmat 的实现中，主要通过 Digester（蒸馏器）这个类来处理 xml

SAXParser.parse() 和 XMLReader.parse() 效果时一样的，前者底层解析是用的还是后面的那个方法

过了一下 Catalina 类中的 load 和 里面的 digester.parse() 不过感觉解析完 server.xml 之后并没有什么卵用啊。。。难道我哪里看漏了，算了后面看容器的时候看看能不能找到什么线索吧

### SAX 的简单使用

SAX: Simple API for XML, 一个事件驱动的 XML 解析器，流式解析文件，换句话说就是在遇到目标节点时触发事件，然后我们通过定制出发事件的实现来实现我们想要的功能

* 自上而下解析文件
* 处理顺序和文件顺序相同
* 事件的 handler 需要注册到 parser 里
* tag 被识别时， handler 中相关的方法就会被调用

**使用场景**

* 当 xml 嵌套不是很深的时候可以使用
* 文件很大的时候，使用 DOM 解析可能占用太多内存，可以使用 SAX
* 只需要解析部分文件就能处理问题
* 数据在 parser 看到时就有效 - 不是很懂这个意思？

ContentHandler interface 提供了解析需要的方法，比如 `startDocument`, `startElement`, `characters` 等。

## 其他问题

* 怎么运行 tomcat 自带的测试，右键 run 会失败？