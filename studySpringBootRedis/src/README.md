# SpringBoot + redis

# 1. redis 介绍
## 1.1 一些概念
    NoSQL: 是不同于传统的关系型数据库的数据库管理系统的统称。两者最重要的区别是NoSQL不实用SQL作为查询语言，NoSQl也不需要固定的
    表格模式， 它是基于键值对的。
    
    NoSQL： redis, memcached, mongodb, guava(google的基于应用级别的内存数据库，数据存在JVM（单机）)
    
    redis 定义：redis是一个开源的，内存中的数据结构存储系统，他可以用作数据库，缓存和消息中间件。它支持多种类型的数据结构，如 字符串（String），
    散列（hashes），列表（lists），集合（sets），有序集合（sorted sets）。
## 1.2 Redis和memcached， MySQL的对比
|数据库            |数据库类型                                               | 数据存储方式                       |特色功能 |
|:---:           |:---:                                                  |:---:|:---:|
|MySQL          |硬盘持久化（传统关系型数据库）                                |  库表类型存储                     | 支持ACID，主从复制|
|Memcached      |内存缓存（传统类型内存缓存，redis出现之前很多公司都会选择等内存工具|K/V存储（单一key与value存储方式）    | 高性能多线程服务器      |
|Redis          |内存 + 硬盘持久化（继承了高效缓存的特点，同时兼备数据持久化方案）|多种数据类型（字符串，列表，集合，散列表，有序集合）|发布与订阅，主从复制，持久化，动态扩容，脚本操作|

     作为同款功能的的内存缓存产品，redis和memcached各有什么优势。
        1）内存管理机制
            - Memcached 默认使用slab Allocation机制管理内存。其主要思想是按照预先规定的大小，将分配的内存分割成特定长度的块以存储相应长度的key-value记录。已完全解决内存碎片问题，空闲列表进行判断存储状态。
            - Redis使用现场申请内存的方式来存储数据，并且很少使用free-list等方式来优化内存分配，会在一定程度上存在内存碎片。
        2）数据持久化方案
        	- Memcached 不支持内存数据的持久化操作，所有数据都以in-memory的形式存储。
            -redis 支持持久化操作。redis 提供了两种不同的持久化方法来将数据存储到硬盘里面，分别是 rdb 形式和 aof 形式。
            -rdb：将内存进行同步，就是内存快照。
            -aof：append only if，存的是命令，类似set del这些命令
        3）缓存过期机制
        	-Memcached 在删除失效主键时采用的是惰性删除机制， 即Memcached内部也不会监视主键是否失效，而是在通过get 访问主键时才会检查其是否已经失效
            -redis 定时定期等多种缓存失效机制，减少内存泄漏。
        4）支持的数据类型
        	-Memcached支持单一数据类（k，v）
            -redis支持 5 种数据类型

## 1.3 redis作为数据库和作为缓存的选择
    redis作为数据库使用的优缺点
        - 优点：没有Schema 约束，数据结构的变更相对容易，不需要一开始确定数据类型，抗压能力强，性能极高，10万/qps
        - 缺点：没有索引，没有外键，缺少int/date等基本数据类型，多条件查询需要通过集合内联，开发效率低，可维护性不佳。
    redis作为缓存使用，搭配数据库的两种方案。
        - 整合RedisTemplate 和 Jedis 使用方案。
        - 作为myBatis/hibernate二级缓存使用
## 1.4 rdis启动的三种方式
    直接启动
    根据配置文件启动
    使用redis脚本设置开机启动（启动脚本redis_init_script位于redis的 /utils/目录下）
# 2. redis 五种数据类型和消息订阅
# 3. redis 事务和 mysql事务 MVCC
# 4. SpringBoot和redis深度整合和应用
# 5. redis实现排行榜
# 6。 面试相关题目

