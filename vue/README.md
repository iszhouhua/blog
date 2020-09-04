## 简介

[开源个人博客系统](https://github.com/iszhouhua/blog)的后台前端部分代码。项目基于 [vue](https://github.com/vuejs/vue) 和 [element](https://github.com/ElemeFE/element)，以[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)为模板完成开发。

## 开发

```bash
# 克隆项目
git clone https://github.com/iszhouhua/blog.git

# 进入项目目录
cd blog/vue

# 安装依赖
npm install
# 或
yarn install

# 建议不要用 cnpm 安装 会有各种诡异的bug 可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

浏览器访问 <http://127.0.0.1:8888>

账号：admin

密码：123456

## 发布

> 将`prod.env.js`中的`BLOG_URL`改为自己的博客链接。

```bash
npm run build
```

将`dist`文件夹中的`admin`文件夹复制到后端项目中的`src/main/resources/static`文件夹中即可。

或者配置好`nginx`映射，则无需将构建好的文件放在Java项目中。

```bash
location /admin {
	root   /blog-vue/dist;
	index  index.html index.htm;
}
```

------

## 界面展示

![20190508101138](http://img.iszhouhua.com/printscreen/20190508101138.png)

![20190508101254](http://img.iszhouhua.com/printscreen/20190508101254.png)

![20190508101400](http://img.iszhouhua.com/printscreen/20190508101400.png)

![20190508101440](http://img.iszhouhua.com/printscreen/20190508101440.png)

![20190508101544](http://img.iszhouhua.com/printscreen/20190508101544.png)

![20190508101622](http://img.iszhouhua.com/printscreen/20190508101622.png)

------