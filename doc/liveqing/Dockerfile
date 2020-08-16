# Version 0.1
FROM centos:7

MAINTAINER zhushuai_it@163.com

WORKDIR /app

# http port
EXPOSE 10080
# rtmp port
EXPOSE 10085
# iot port (可选)
EXPOSE 10191

# 修改时区
RUN rm -rf /etc/localtime && ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# 安装中文支持
RUN yum -y install kde-l10n-Chinese && yum -y reinstall glibc-common
# 配置显示中文
RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
# 设置环境变量
ENV LC_ALL zh_CN.utf8

# 本地文件方式
# ADD LiveQing-linux-3.9.6.tar.gz .

# 在线文件方式
RUN yum install -y wget \
    && wget -O LiveQing-linux-3.9.6.tar.gz "http://static.xzcoder.com/LiveQing-linux-3.9.6.tar.gz" \
    && tar -zxvf LiveQing-linux-3.9.6.tar.gz

# rename dir
RUN mv LiveQing-linux-3.9.6 LiveQing

WORKDIR /app/LiveQing

# install service
RUN ./liveqing install

# /etc/init.d/LiveQing_Servcie start
CMD ./start.sh