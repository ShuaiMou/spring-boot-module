# SpringBoot + redis
[未装redis服务端，可以通此网址进行测试](http://try.redis.io/)

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
## 2.1 String
    string 是最常用的数据类型，普通的key/value都可以归为此类
        set/get: 设置对应的值为String类型的value； 获取key对应的值。(set/get key)
        
        mget: 批量获取多个key的值，如果不存在则返回nil( mget key1 key2 ....)
        
        incr key && incrby: incr对key对应的值进行 ‘++’操作，并返回新值； incrby加指定值。(incr key)(incrby key increment)
        
        decr && decrby： decr对key对应的值进行 ‘--’操作，并返回新值； decrby减指定值。
        
        setnx: 设置key对应的值为string类型的value， 如果key已经存在则返回0，set失败,如果key不存在，返回1，set成功。(setnx key value)
        
        setex： 设置key对应的值为string类型的value，并设置有效期（单位为秒）(setex key value expireTime)
        
        getRange 获取key对应value的子字符串 （getRange key start end）包头包尾
        
        mset 批量设置多个key的值，如果成功表示所有值都被设置，返回0则表示没有任何值被设置
        
        msetnx，同mset
        
        getset 设置key的值，并返回key旧的值
        
        append 给指定key的value追加字符串，并返回新字符串的长度
        
## 2.2 Hash 类型       
    类似于HashMap key,value Map<String, Map<String,String>>. 第一个String 是 Map<String,String>的名称， 存多个Map<String,String>通过
    第一个String作为key来区分。
    
    hset key1 entryKey entryValue
    hget key1 entryKey
    hgetall key1(返回所有 entryKey 和 entryValue)
    hmset key1 entryKey1 entryValue1 entryKey2 entryValue2 ...
    hmget key1 entryKey1 entryKey2 ...
    hlen key1(返回key1对应的entry的组数)
    hdel key1 entryKey1(删除key1对应entryKey1的entry)
    
## 2.3 List 类型
    可以作为消息队列
    
    lpush key value1 value2 ... 在key对应的list头部添加一个元素 数据栈
    
    lrange key start end 获取key对应list的指定下标范围的元素， -1表示获取所有元素
    
    lpop key 从key对应的list头部删除一个元素，并返回该元素。
    
    rpush key value1 value2 ... 在key对应的list尾部添加一个元素
    
    rpop key 从key对应的list尾部删除一个元素，并返回该元素。
    
    llen key 返回该list长度
    
    lindex key index 返回下标为index的元素
    
    lrem key count value 删除value的元素
    
## 2.4 Set 类型
    sadd key1 value1 value2 .. 自动去重
    
    smember key 返回所有元素
    
    spop key 随机返回一个元素
    
    sdiff key1 key2 得出key1 减去key2的所有元素
    
    sunion key1 key2 得出并集
    
    sinter key1 key2 得出交集
    
## 2.5 SortSet 类型
    set的基础上增加score， 再根据score进行排序
    应用：通过SortSet实现排行榜
    
    zadd key score1 value1 score2 value2 在key对应的SortSet中添加元素
    
    zrange key start end 返回指定范围内的元素
    
    zrange key start end withscores  返回元素和对应分数
    
    zrem key value 删除key对应SortSet的一个元素
    
    zrangebyscore key start end 返回有序集key中，指定分数范围内的元素，可以运用在排行榜中
    
    zcard key 返回元素个数
    
    zrank key member 返回key对应的zset中指定member的排名 
    
## 2.6 发布订阅
        
# 3. redis 事务和 mysql事务 MVCC
# 4. SpringBoot和redis深度整合和应用
# 5. redis实现排行榜
# 6.面试相关题目

