# Ubuntu 集群部署 Rabbitmq

1. 安装最新的版本，不用docker，不使用安装工具(yum)

2. 单机模拟集群

3. 修改默认端口

### 1. 准备

1. [检查依赖关系](https://www.rabbitmq.com/docs/which-erlang)

2. 下载linux通用包：[Generic Binary Build | RabbitMQ](https://www.rabbitmq.com/docs/install-generic-unix)

### 2. 安装

1. 安装[Erlang](https://www.erlang-solutions.com/downloads/#)
   
   ```shell
   # `/etc/apt/sources.list`目录下添加仓库
   deb http://binaries2.erlang-solutions.com/ubuntu/ jammy-esl-erlang-26 contrib
   
   # 下载密钥
   wget https://binaries2.erlang-solutions.com/GPG-KEY-pmanager.asc
   sudo apt-key add GPG-KEY-pmanager.asc
   
   # 更新安装
   sudo apt-get update
   sudo apt-get install esl-erlang
   
   # 验证
   erl

   # TODO 如何查看erl的版本
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
