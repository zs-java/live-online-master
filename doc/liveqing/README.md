
## LiveQing 流媒体服务器搭建（Docker）

> 提供本地文件以及在线文件方式（网络良好时推荐在线文件），如要切换为本地文件，请修改 Dockerfile

### 1. 构建 Docker 镜像
```shell script
docker build -t liveqing .
```

### 2. 创建 Docker 容器
```shell script
docker run -p 10080:10080 -p 10085:10085 -p 10191:10191 --name liveqing -d liveqing
```

### 3. 服务启停
```shell script
# start
docker start liveqing
# stop
docker stop liveqing
```

---
> @Author: zhushuai_it@163.com