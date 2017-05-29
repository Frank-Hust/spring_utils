# spring_utils
by haoyifen on 2017/5/29 16:51

介绍Spring核心框架自带的一些好用的工具类

版本为 4.3

## Assert
org.springframework.util.Assert 断言条件, 表达式, String, 并抛出相应的错误

## AOP和反射相关
AopUtils: 能够判断一个实例是否是JDK代理, CGlib代理, 以及获取代理的目标类型.

org.springframework.util.ClassUtils: 能够获取默认的classLoader, 能够获取int, java.lang.String[]这种表示的类型(forName), 能够获取代理的目标类型(getUserClass), 获取类的包名,获取方法.

ReflectionUtils 获取方法, 获取接口, 获取field, 获取包括父类的方法, 接口, field等.

ResolvableType: 对类型,field, 方法参数, 返回值进行解析的类, 可以获取它们的原生类型, 以及泛型参数

## 网络
SocketUtils: 获取可用的tcp, udp端口

## web
ServletRequestUtils: 获取request中的int, String等基本类型参数.

RequestContextUtils: 通过request获取spring的application.




# 参考:
对以下资料进行了参考以及增添

强烈推荐关注Josh Long
https://github.com/joshlong/spring-tips/tree/master/utility-classes

Spring Tips: The Spring Framework *Utils Classes
https://www.youtube.com/watch?v=Zo9e3i8HxX4

http://blog.csdn.net/wh351531104/article/details/7432906