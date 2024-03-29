redis cluster最最基础的一些知识

redis cluster: 自动，master+slave复制和读写分离，master+slave高可用和主备切换，支持多个master的hash slot支持数据分布式存储

停止之前所有的实例，包括redis主从和哨兵集群

# redis cluster的重要配置

cluster-enabled <yes/no>

cluster-config-file <filename>：这是指定一个文件，供cluster模式下的redis实例将集群状态保存在那里，包括集群中其他机器的信息，比如节点的上线和下限，故障转移，不是我们去维护的，给它指定一个文件，让redis自己去维护的

cluster-node-timeout <milliseconds>：节点存活超时时长，超过一定时长，认为节点宕机，master宕机的话就会触发主备切换，slave宕机就不会提供服务

# 在三台机器上启动6个redis实例

（1）在eshop-cache03上部署目录

/etc/redis（存放redis的配置文件），/var/redis/6379（存放redis的持久化文件）

（2）编写配置文件

redis cluster集群，要求至少3个master，去组成一个高可用，健壮的分布式的集群，每个master都建议至少给一个slave，3个master，3个slave，最少的要求

正式环境下，建议都是说在6台机器上去搭建，至少3台机器

保证，每个master都跟自己的slave不在同一台机器上，如果是6台自然更好，一个master+一个slave就死了

3台机器去搭建6个redis实例的redis cluster

```
mkdir -p /etc/redis-cluster
mkdir -p /var/log/redis
mkdir -p /var/redis/7001
```



```
port 7001
cluster-enabled yes
cluster-config-file /etc/redis-cluster/node-7001.conf
cluster-node-timeout 15000
daemonize	yes							
pidfile		/var/run/redis_7001.pid 						
dir 		/var/redis/7001		
logfile /var/log/redis/7001.log
bind 192.168.31.187		
appendonly yes
```



至少要用3个master节点启动，每个master加一个slave节点，先选择6个节点，启动6个实例

将上面的配置文件，在/etc/redis下放6个，分别为: 7001.conf，7002.conf，7003.conf，7004.conf，7005.conf，7006.conf

（3）准备生产环境的启动脚本

在/etc/init.d下，放6个启动脚本，分别为: redis_7001, redis_7002, redis_7003, redis_7004, redis_7005, redis_7006

每个启动脚本内，都修改对应的端口号

（4）分别在3台机器上，启动6个redis实例

将每个配置文件中的slaveof给删除

3、创建集群

```
yum install -y ruby
yum install -y rubygems
gem install redis
```

`cp /usr/local/redis-3.2.8/src/redis-trib.rb /usr/local/bin`

redis-trib.rb create --replicas 1 192.168.130.187:7001 192.168.130.187:7002 192.168.130.19:7003 192.168.130.19:7004 192.168.130.227:7005 192.168.130.227:7006

--replicas: 每个master有几个slave

6台机器，3个master，3个slave，尽量自己让master和slave不在一台机器上

yes

redis-trib.rb check 192.168.31.187:7001

4、读写分离+高可用+多master

读写分离：每个master都有一个slave
高可用：master宕机，slave自动被切换过去
多master：横向扩容支持更大数据量