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

## 其他问题

* 怎么运行 tomcat 自带的测试，右键 run 会失败？