#!/usr/bin/env bash
set -e
echo "开始打包管理后台"
cd vue
npm run build
cd ../
rm -rf src/main/resources/static/admin
cp -r vue/dist/admin src/main/resources/static/admin
echo "开始打包java代码"
mvn clean package -Dmaven.test.skip=true
echo "开始构建docker镜像"
docker build . -t=blog