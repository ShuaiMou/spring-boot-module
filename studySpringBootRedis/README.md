# SpringBoot + redis
[未装redis服务端，可以通此网址进行测试 http://try.redis.io/](http://try.redis.io/)

[redis tutorial学习网站 https://www.runoob.com/redis/redis-tutorial.html](https://www.runoob.com/redis/redis-tutorial.html)

[redis 官网 https://redis.io/](https://redis.io/)

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
    ./redis-cli -h 127.0.0.1 -p 6379 -a myPassword
    
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
    
    smembers key 返回所有元素
    
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
    作用：发布订阅类似于信息管道，用来进行系统之间消息解耦，类似于MQ。 主要有消息发布者和消息订阅者。比如应用于 订单支付成功 -> 会员系统加积分，钱包进       
        行扣钱操作，发货系统等等
    
    publish channel message 将信息message发送到指定的频道channel，返回收到消息的客户端数量
    
    subscribe channel 订阅指定频道的消息
    
    Unsubscribe channel 退订给定的一个或多个频道的信息。
   
    和MQ对比：
        redis发布订阅功能比较薄弱但比较轻量级， MQ消息持久化， Redis数据可靠性比较差。无后台功能可以msgId， msgKey进行查询消息。
        
# 3. 传统关系型数据库事务与redis 事务
## 3.1 传统关系型数据库事务
    - 一个数据库事务通常包含了一个序列的对数据库的读/写操作。他的存在包含两个目的：
        1.为数据库操作序列提供了一个从失败中恢复到正常状态的方法，同时提供了数据库即使在异常状态下仍能保持一致性的方法。(原子性)
        2.当多个应用程序在并发访问数据库时，可以在这些应用程序之间提供一个隔离方法，以防止彼此的操作互相干扰。（隔离性）
        
    - 事务的ACID四大特性
        1.原子性（Atomicity）：事务作为一个整体被执行，包含在其中的对数据库的操作要么全部被执行，要么都不执行。
        2.一致性（Consistency）：事务应该确保数据库的状态从一个一致性的状态转变为另一个一致性的状态。一致性状态的含义是数据库中的数据应该满足完整性
           约束。
        3.隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行。
        4.持久性（Durability）：已被提交的事务对数据库的修改应该永久保存在数据库中。
        
    - 事务隔离机制
        语法：set global transaction isolation level read uncommitted;
        种类：read uncommitted（读到未提交的数据）, read committed（只能读到提交了的数据）, repeatable read, serializable
        

    - Multiversion concurrency control 多版本并发控制

        并发访问（读或者写）数据库时，对正在事务内处理的数据做多版本的管理，用来避免由于写操作的堵塞，而引发读操作失败的并发问题。
## 3.2 redis事务
    单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 Redis 事务的执行并不是原子性的。
    事务可以理解为一个打包的批量执行脚本，但批量指令并非原子化的操作，中间某条指令的失败不会导致前面已做指令的回滚，也不会造成后续的指令不做。
    
    DISCARD 取消事务，放弃执行事务块内的所有命令。
    
    XEC 执行所有事务块内的命令。
    
    MULTI 标记一个事务块的开始。
    
    WATCH key [key ...]监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
    
    UNWATCH 取消 WATCH 命令对所有 key 的监视。
    
   
    
    
## 3.3 redis事务与传统关系型事务的比较
    1. 原子性
         单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 Redis 事务的执行并不是原子性的。
         事务可以理解为一个打包的批量执行脚本，但批量指令并非原子化的操作，中间某条指令的失败不会导致前面已做指令的回滚，也不会造成后续的指令不做。
         
    2. 一致性
        入队错误
            在命令入队的过程中，如果客户端向服务器发送了错误的命令，比如命令的参数数量不对，那么服务器将向客户端返回一个出错信息，并且将客户端的事务状
            态设为 REDIS_DIRTY_EXEC（服务端会discard整个事务）。
            
        执行错误
            如果命令在事务执行的过程中发生错误，比如对一个不同类型的key执行了错误的操作，那么redis只会讲错误包含在事务的结果中，这不会引起整个事务中
            断或整个失败，不会影响已执行事务命令的结果，也不会影响后面要执行的事务命令，所以它对事务的一致性也没有影响。
            
    3. 隔离性
        WATCH命令用于在事务开始之前监视任意数量的键。 当调用EXEC执行事务时，如果任意一个被监视的键已经被其他客户修改了，那么整个事务不再执行，直接返
        回失败。
    4.持久性
        因为事务不过是用队列包裹起了一组redis命令，并没有提供任何额外的持久性功能，所以事务的持久性由redis所使用的持久化模式决定。（rdb， aof）
        
# 4. SpringBoot和redis深度整合和应用
  [redis作为mybatis二级缓存整合](https://github.com/ShuaiMou/spring-boot-module/blob/master/studySpringBootMybatisRedis/README.md)

# 5.redis实现分布式集群环境session共享(会话共享)
   [结合mybatisDemo]()
   
    cookie和session介绍:
        由于http的无状态性，为了使某个域名下的所有网页能够共享某些数据，session和cookie出现了。客户端访问服务器的流程如下
            首先，客户端会发送一个http请求到服务器端。
            服务器端接受客户端请求后，建立一个session，并发送一个http响应到客户端，这个响应头，其中就包含Set-Cookie头部。该头部包含了sessionId。Set-Cookie格式如下，具体请看Cookie详解
            Set-Cookie: value[; expires=date][; domain=domain][; path=path][; secure]
            在客户端发起的第二次请求，假如服务器给了set-Cookie，浏览器会自动在请求头中添加cookie
            服务器接收请求，分解cookie，验证信息，核对成功后返回response给客户端

    redis分布式集群配置过程：
       1. <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
          </dependency>
       2. @EnableRedisHttpSession//开启redis集中式session管理，把所有的session都存放到了redis中

       3. maxInactiveIntervalInSeconds 设置过期时间
    
# 6. 实现缓存预热（springBoot实现初始化加载配置）
        方式1 实现Springboot ApplicationRunner
                该方法仅在SpringbootApplication.run(...)完成之前调用
                
        方式2 实现InitializingBean
                InitiallizingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet()方法。在Spring初始化bean的时候，
                如果bean实现了InitiallizingBean接口，在对象的所有属性被初始化后才会调用afterPropertiesSet()方法
                    
# 7.面试相关题目
    7.1 缓存的收益和成本
        7.1.1 缓存带来的回报
                - 高速读写
                - 降低后端负载
                    后端服务器通过前端缓存降低负载，业务端使用Redis降低后端MySQL负载等
                    
        7.1.2 缓存带来的代价
                - 数据不一致
                    缓存层和数据层有时间窗口不一致，和更新策略有关
                - 代码维护成本
                    原本只需要读写MySQL就能实现功能，但加入缓存之后就要去维护缓存但数据，增加了代码的复杂度。
                - 堆内缓存可能带来内存溢出的风险，从而影响用户进程，如ehCache， loadingCache
                
        7.1.3 堆内存和远程服务器缓存Redis的选择
                - 堆内缓存一般性能更好，远程缓存需要套接字传输
                - 用户级别缓存尽量采用远程缓存
                - 大数据量尽量采用远程缓存，服务节点化原则

    7.2 缓存雪崩
        当某一时刻发生大规模的缓存失效的情况，比如你的缓存服务宕机了，会有大量的请求进来直接打到DB上面。结果就是DB 撑不住，挂掉。
        
            -解决办法
                1. 使用集群缓存，保证缓存服务的高可用
                     这种方案就是在发生雪崩前对缓存集群实现高可用，如果是使用 Redis，可以使用 主从+哨兵 ，Redis Cluster 来避免 
                     Redis 全盘崩溃的情况。
                     
                2. ehcache本地缓存 + Hystrix限流&降级,避免MySQL被打死
                     使用 ehcache 本地缓存的目的也是考虑在 Redis Cluster 完全不可用的时候，ehcache 本地缓存还能够支撑一阵。使用 
                     Hystrix进行限流 & 降级 ，比如一秒来了5000个请求，我们可以设置假设只能有一秒 2000个请求能通过这个组件，那么其他
                     剩余的 3000 请求就会走限流逻辑。然后去调用我们自己开发的降级组件（降级），比如设置的一些默认值呀之类的。以此来保
                     护最后的 MySQL 不会被大量的请求给打死。 
                     
                3.开启Redis持久化机制，尽快恢复缓存集群
                    一旦重启，就能从磁盘上自动加载数据恢复内存中的数据。
                    
                4.数据预热
                    缓存预热就是系统上线后，将相关缓存数据直接加载到缓存系统。
                    
                5. 设置不同的过期时间，让缓存失效的时间点尽量均匀。（解决热点数据集中失效问题）
                      
                 
        
    7.3 缓存穿透
        缓存穿透说简单点就是大量请求的 key 根本不存在于缓存中，导致请求直接到了数据库上，根本没有经过缓存这一层。
            -解决办法
                1. 缓存无效 key 
                    如果缓存和数据库都查不到某个 key 的数据就写一个到 redis 中去并设置过期时间，具体命令如下：SET key value EX 10086。
                    这种方式可以解决请求的 key 变化不频繁的情况，如果黑客恶意攻击，每次构建不同的请求key，会导致 redis 中缓存大量无效的 
                    key 。很明显，这种方案并不能从根本上解决此问题。如果非要用这种方式来解决穿透问题的话，尽量将无效的 key 的过期时间设
                    置短一点比如 1 分钟。
                    
                2. 布隆过滤器
                    BloomFilter 类似于一个hbase set 用来判断某个元素（key）是否存在于某个集合中。这种方式在大数据场景应用比较多，
                    比如 Hbase 中使用它去判断数据是否在磁盘上。还有在爬虫场景判断url 是否已经被爬取过。这种方案可以加在第一种方案中，
                    在缓存之前在加一层 BloomFilter ，在查询的时候先去 BloomFilter 去查询 key 是否存在，如果不存在就直接返回，存在
                    再走查缓存 -> 查 DB。
                  