# Ubuntu 集群部署 Rabbitmq

1. 安装最新的版本，尽量通用，不用docker，不使用安装工具(yum)

2. 单机模拟集群

3. 修改默认端口

### 1. 准备

1. [检查依赖关系](https://www.rabbitmq.com/docs/which-erlang)

2. 下载linux通用包：[Generic Binary Build | RabbitMQ](https://www.rabbitmq.com/docs/install-generic-unix)

### 2. 安装

1. 安装`Erlang`
   
```shell
 # 使用homebrew安装
 brew install erlang@27
 # 使用源码安装 erlang
 # 安装依赖
libncurses5-dev libncursesw5-dev
 # 下载包安装
sudo ./configure && sudo make && sudo make install -j 8
```
   
   
3. 开启管理台插件

```shell
./rabbitmq-plugins list | grep rabbitmq_management
./rabbitmq-plugins enable rabbitmq_management
```

3. 启动rabbitmq，并修改默认端口

```shell
RABBITMQ_NODE_PORT=5673 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15673}]" RABBITMQ_NODENAME=rabbit1 ./rabbitmq-server -detached
```

4. 添加管理员用户

```shell
./rabbitmqctl -n rabbit1 delete_user guest
./rabbitmqctl -n rabbit1 add_user admin admin123
./rabbitmqctl -n rabbit1 set_user_tags admin administrator
./rabbitmqctl -n rabbit1 set_permissions -p / admin ".*" ".*" ".*"
./rabbitmqctl -n rabbit1 list_user_permissions admin
```

5. 启动其他节点

```shell
RABBITMQ_NODE_PORT=5674 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15674}]" RABBITMQ_NODENAME=rabbit2 ./rabbitmq-server -detached

RABBITMQ_NODE_PORT=5675 RABBITMQ_SERVER_START_ARGS="-rabbitmq_management listener [{port,15675}]" RABBITMQ_NODENAME=rabbit3 ./rabbitmq-server -detached
```

6. `rabbit2`节点加入到`rabbit1`节点内

```shell
./rabbitmqctl -n rabbit2 stop_app

./rabbitmqctl -n rabbit2 join_cluster rabbit1

./rabbitmqctl -n rabbit2 start_app
```

7. `rabbit3`节点加入到`rabbit1`节点内

```shell
./rabbitmqctl -n rabbit3 stop_app

./rabbitmqctl -n rabbit3 join_cluster rabbit1 --ram

./rabbitmqctl -n rabbit3 start_app
```

### 3. 登录管理台，检查
