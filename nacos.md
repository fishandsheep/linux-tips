192.168.2.128

# 中间件部署

1. 基于当前最新的版本

2. 单机集群，修改默认端口号

3. 完全使用终端

# 终端环境

1. `Ubuntu 22.04.4 LTS`

2. `tmux`

3. `oh my zsh` : `plugins=(git docker tmux zsh-autosuggestions)`
   
   

# `nacos-2.3.2`：[Nacos文档](https://nacos.io/docs/v2/quickstart/quick-start/)

1. 下载解压，数据库导入数据`conf/mysql-schema.sql`

2. 修改配置文件`conf/application.properties`

3. 修改集群配置文件`conf/cluster.conf`
   注意端口号: [集群部署说明 | Nacos](https://nacos.io/docs/v2/guide/admin/cluster-mode-quick-start/)
   
   - nacos1: 8858
   
   - nacos2: 8868
   
   - nacos3: 8878

4. 复制nacos，分别启动

5. nginx负载配置


