简介
----

基于[SpringBoot](https://github.com/spring-projects/spring-boot)搭建的开源个人博客系统，主题基于Hexo主题[hexo-theme-gal](https://github.com/ZEROKISEKI/hexo-theme-gal)进行修改。

技术栈：SpringBoot、Thymeleaf、MySQL、MyBatis-Plus、Lombok、Gson、ehcache、validation、Bootstrap、jQuery、FontAwesome、Jsoup……

示例博客：[周华个人博客](https://www.iszhouhua.com)

## 快速开始

1. 下载本项目，并使用IDE打开
2. 新建数据库blog并运行数据库脚本`data/blog.sql`
3. 修改`application-dev.yml`中的数据库配置信息
4. 运行`BlogApplication.java`，启动项目
5. 浏览器访问`http://127.0.0.1:8080/`

> 使用 Idea，Eclipse 等IDE运行需要安装Lombok插件，JDK版本要求1.8+。

部署
----

### jar部署

配置好`application-prod.yml`中的配置信息，然后打包：

```bash
mvn clean package -Dmaven.test.skip=true
```

将打包好的`blog.jar`和data文件夹下的`blog.sh`放到同一文件夹下，执行命令：

```bash
# 使脚本具有执行权限
chmod +x ./blog.sh
# 启动项目
./blog.sh start
# 或者直接使用sh命令运行脚本
sh blog.sh start
```

### tomcat部署

修改`application.yml`中`spring.profiles.active`为`prod`，并配置好`application-prod.yml`中的配置信息。

直接修改`pom.xml`中的打包方式为war后进行打包，或直接运行命令：

```bash
clean package war:war -Dmaven.test.skip=true
```

然后将打包好的`blog.war`丢进tomcat中运行即可！

### docker部署

将整个项目丢到服务器，进入项目文件夹`blog`，执行命令`docker-compose up -d`即可（需先安装`docker-compose`）。

后台管理
--------

后台访问地址：`http://127.0.0.1:8080/admin`

后台采用前后端分离的方式实现，如需二次开发，需在此下载前端源码：<https://github.com/iszhouhua/blog-vue>

------

## 界面展示

![1557279251039](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508093436.png)

![20190508095012](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508095012.png)

![20190508095714](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508095714.png)

![20190508101138](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101138.png)

![20190508101254](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101254.png)

![20190508101400](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101400.png)

![20190508101440](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101440.png)

![20190508101544](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101544.png)

![20190508101622](https://raw.githubusercontent.com/iszhouhua/blog/master/data/screenshots/20190508101622.png)

## 2019.5.1 

添加规则管理，转载文章时可直接根据规则自动装配内容。

## 2019.5.8

添加云存储支持，包括七牛云、阿里云、腾讯云。