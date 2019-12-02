# Java代码到底如何运行起来的？

首先假设咱们写好了一份Java代码，那这份Java代码中，是不是会包含很多的“.java”为后缀的代码文件？

比如User.java，OrderService.java，CustomerManager.java

其实咱们Java程序员平时在Eclipse、Intellij IDEA等开发工具中，就有很多类似这样的Java源代码文件。

那么大家现在思考一下，当我们写好这些“.java”后缀的代码文件之后，接下来你要部署到线上的机器上去运行，你会怎么做？

一般来说，都是把代码给打成“.jar”后缀的jar包，或者是“.war”后缀的war包，是不是？

然后呢，就是把你打包好的jar包或者是war包给放到线上机器去部署。

这个部署就有很多种途径了，但是最基本的一种方式，就是通过Tomcat这类容器来部署代码，也可以是你自己手动通过“java”命令来运行一个jar包中的代码。