简介
----

基于[SpringBoot](https://github.com/spring-projects/spring-boot)搭建的开源个人博客系统，主题基于Hexo主题[hexo-theme-gal](https://github.com/ZEROKISEKI/hexo-theme-gal)进行修改。

技术栈：SpringBoot、MySQL、MyBatis-Plus、Lombok、Gson、ehcache、Bootstrap、jQuery、FontAwesome……

示例博客：[周华个人博客](https://www.iszhouhua.com)

## 快速开始

1. 下载本项目，并使用IDE打开
2. 新建数据库blog并运行数据库脚本`blog.sql`
3. 修改`application-dev.yml`中的数据库配置信息
4. 运行`BlogApplication.java`，启动项目
5. 浏览器访问`http://127.0.0.1:8080/`

> 注意：使用 Idea，Eclipse 等IDE运行需要安装Lombok插件，同时请保证JDK版本不低于1.8。

部署
----

### jar部署

修改`application-prod.yml`中的数据库配置信息，然后打包：

```bash
mvn clean package -Dmaven.test.skip=true
```

将打包好的`blog.jar`和deploy文件夹下的`start.sh`放到同一文件夹下，执行命令：

```bash
# 使脚本具有执行权限
chmod +x ./start.sh
# 执行脚本
./start.sh
```

### tomcat部署

修改`application.yml`中`spring.profiles.active`为`prod`，并配置好`application-prod.yml`中的相关信息。

直接修改`pom.xml`中的打包方式为war后进行打包，或直接运行命令：

```bash
clean package war:war -Dmaven.test.skip=true
```

然后将打包好的`blog.war`丢进tomcat中运行即可！

> tomcat部署请确保项目为tomcat直接访问项目（即直接通过ip:端口即可访问项目，而非ip:端口/项目名的方式访问），否则会出现路径访问错误的问题，因为项目中许多地方都是以`/xx`的形式进行访问。

后台管理
--------

项目后台采用前后端分离的方式实现，后台前端源码<https://github.com/iszhouhua/blog-vue>，目前后台尚未制作完成。。。