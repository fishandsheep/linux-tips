# redis主从哨兵模式`7.2.5` [Downloads - Redis](https://redis.io/downloads/)

1. 下载，安装，配置环境变量
   
   ```shell
   # 安装
   make PREFIX=/root/soft/redis install -j 8
   # 配置环境变量
   ```

2. 创建 `mkdir master slave1 slave2 sentinel1 sentinel2 sentinel3` 6个目录

3. 配置 `redis.conf`文件
   
   ```shell
   port 6479
   daemonize yes
   # replicaof 127.0.0.1 6379
   pidfile /var/run/redis_6479.pid
   masterauth yourpassword
   requirepass yourpassword
   ```
   
   1. 验证主从节点状态  
   
   2. 验证主写从读

4. 配置 `sentinel.conf` 文件
   
   ```shell
   port 26379
   dir /tmp
   sentinel monitor mymaster 192.168.1.100 6379 2
   sentinel auth-pass mymaster mypassword
   sentinel down-after-milliseconds mymaster 5000
   ```
   
   1. 查看主从节点状态
   
   2. 验证主节点宕机，故障自动转移
      
      ```shell
      # 检查单个sentinel的状态
      redis-cli -p 26379 INFO Sentinel
      # 检查sentinel集群的状态
      redis-cli -p 26379 SENTINEL SENTINELS mymaster
      # 主节点列表
      redis-cli -p 26379 SENTINEL MASTERS
      # 从节点详细状态
      redis-cli -p 26379 SENTINEL SLAVES mymaster
      ```
