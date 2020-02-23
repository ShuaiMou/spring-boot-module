# spring-boot-Mybatis-Redis

## 数据库建表
    create TABLE user(
    email VARCHAR(50),
    username VARCHAR(50),
    password varchar(20),
    PRIMARY KEY(email)
    );

## [redis 作为mybatis 缓存整合讲解 checkLogin方法](https://github.com/ShuaiMou/spring-boot-module/blob/master/studySpringBootMybatisRedis/src/main/java/com/unimelb/saul/studySpringbootMybatisRedis/service/serviceImpl/UserServiceImpl.java)
    1）用户第一次访问的时候获取数据库的值，再次访问时直接从缓存中获取数据。
    2）设置缓存过期时间
    3）项目8080端口是对外端口，查内部端口用 ps -ef|grep port， 查外部端口用 lsof -i:8080
    
    
##  redis 作为mybatis 二级缓存 - springBoot cache的使用
         springBoot cache可以结合redis， ehcache等缓存
            Spring Cache是作用在方法上的，其核心思想是这样的：当我们在调用一个缓存方法时会把该方法参数和返回结果作为一个键值对存放在缓
            存中，等到下次利用同样的参数来调用该方法时将不再执行该方法，而是直接从缓存中获取结果进行返回。所以在使用Spring Cache的时候我
            们要保证我们缓存的方法对于相同的方法参数要有相同的返回结果。
            
         一级缓存：sqlSession，sql建立连接到关闭连接的数据缓存
         二级缓存：全局
         
         引入步骤：
                1）引入pom.xml
                     <dependency>
                         <groupId>org.springframework.boot</groupId>
                         <artifactId>spring-boot-starter-cache</artifactId>
                     </dependency>
                2)开启缓存注解
                     @EnableCaching
                     
                3)在方法上加 SpEL 表达式
                
        @CacheConfig    ：抽取缓存公共配置，可以标注在类上
                @CacheConfig(cacheNames = "emp")
                @Service
                public class EmployeeService 
         
        @Cacheable(查)
            可以标记在一个方法上，也可以标记在一个类上。当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方
            法都是支持缓存的。对于一个支持缓存的方法，Spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以
            直接从缓存中获取结果，而不需要再次执行该方法。Spring在缓存方法的返回值时是以键值对进行缓存的，值就是方法的返回结果，至于键的
            话，Spring又支持两种策略，默认策略和自定义策略.
            
            @Cacheable可以指定三个属性，value、key和condition。
            
            key
                自定义策略是指我们可以通过Spring的EL表达式来指定我们的key。这里的EL表达式可以使用方法参数及它们对应的属性。
                使用方法参数时我们可以直接使用“#参数名”或者“#p参数index”。
                
                    /**
                    * key 是指传入时的参数
                    *
                    */
                       @Cacheable(value="users", key="#id")
                       public User find(Integer id) {
                          return null;
                       }
                    // 表示第一个参数
                      @Cacheable(value="users", key="#p0")
                       public User find(Integer id) {
                          return null;
                       }
                    // 表示User中的id值
                       @Cacheable(value="users", key="#user.id")
                       public User find(User user) {
                          return null;
                       }
                     // 表示第一个参数里的id属性值
                       @Cacheable(value="users", key="#p0.id")
                       public User find(User user) {
                          return null;
                       }
                       
                Spring还为我们提供了一个root对象可以用来生成key。通过该root对象我们可以获取到以下信息。
                当我们要使用root对象的属性作为key时我们也可以将“#root”省略，因为Spring默认使用的就是root对象的属性。如：
                
                       // key值为: user中的name属性的值
                      @Cacheable(value={"users", "xxx"}, key="caches[1].name")
                       public User find(User user) {
                          return null;
                       }
                
|属性名称|描述|例子|
| :--- | :--- | :--- |
|methodName|当前方法名|root.methodName|
|method|当前方法|#root.method.name|
|target|当前被调用的对象|#root.target|
|targetClass|当前被调用的对象的class|#root.targetClass|
|args|当前方法参数组成的数组|#root.args[0]|
|caches|当前被调用的方法使用的Cache|#root.caches[0].name|

                      
            
|参数|解释|例子|
| :--- | :--- | :--- |
|value|缓存的名称，在 spring 配置文件中定义，必须指定至少一个|@Cacheable(value=”mycache”) @Cacheable(value={”cache1”,”cache2”}|
|key|缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合|@Cacheable(value=”testcache”,key=”#userName”)|
|condition|缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存|@Cacheable(value=”testcache”,condition=”#userName.length()>2”)|
            
        @CachePut（修改，增加）
            在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
            如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。@CachePut也可以声明一个
            方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都
            会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
            
             //@CachePut也可以标注在类上和方法上。使用@CachePut时我们可以指定的属性跟@Cacheable是一样的。
               @CachePut("users")//每次都会执行方法，并将结果存入指定的缓存中
               public User find(Integer id) {
                  returnnull;
               }
        
        @CacheEvict（删除）
            @CacheEvict是用来标注在需要清除缓存元素的方法或类上的。当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。
            @CacheEvict可以指定的属性有value、key、condition、allEntries和beforeInvocation。其中value、key和condition的语义与
            @Cacheable对应的属性类似。即value表示清除操作是发生在哪些Cache上的（对应Cache的名称）；key表示需要清除的是哪个key，
            如未指定则会使用默认策略生成的key；condition表示清除操作发生的条件。下面我们来介绍一下新出现的两个属性allEntries和
            beforeInvocation。
            
            allEntries
                  allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。当指定了allEntries为true时，
                  Spring Cache将忽略指定的key。有的时候我们需要Cache一下清除所有的元素，这比一个一个清除元素更有效率。
            
                   @CacheEvict(value="users", allEntries=true)
                   public void delete(Integer id) {
                      System.out.println("delete user by id: " + id);
                   }
            
             beforeInvocation
                   清除操作默认是在对应方法成功执行之后触发的，即方法如果因为抛出异常而未能成功返回时也不会触发清除操作。使用
                   beforeInvocation可以改变触发清除操作的时间，当我们指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素。
            
                   @CacheEvict(value="users", beforeInvocation=true)
                   public void delete(Integer id) {
                      System.out.println("delete user by id: " + id);
                   }
        @Caching
               @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。其拥有三个属性：cacheable、put和evict，
               分别用于指定@Cacheable、@CachePut和@CacheEvict。
        
               @Caching(cacheable = @Cacheable("users"), evict = { @CacheEvict("cache2"),
               @CacheEvict(value = "cache3", allEntries = true) })
               public User find(Integer id) {
                  returnnull;
               }
        使用自定义注解
               Spring允许我们在配置可缓存的方法时使用自定义的注解，前提是自定义的注解上必须使用对应的注解进行标注。如我们有如下这么一个
               使用@Cacheable进行标注的自定义注解。
        
                @Target({ElementType.TYPE, ElementType.METHOD})
                @Retention(RetentionPolicy.RUNTIME)
                @Cacheable(value="users")
                public @interface MyCacheable {
                }
        
               那么在我们需要缓存的方法上使用@MyCacheable进行标注也可以达到同样的效果。
        
               @MyCacheable
               public User findById(Integer id) {
                  System.out.println("find user by id: " + id);
                  User user = new User();
                  user.setId(id);
                  user.setName("Name" + id);
                  return user;
               }
 
 ## springBoot cache 存在的问题
    1)生成的key过于简单，容易造成冲突
    2）无法设置过期时间，默认过期时间为永久不过期
    3）配置序列化方式，默认是序列化JDKSerializable
    
    解决(springboot 自定义项)
    1）自定义keyGenerator
    2)自定义cacheManager，设置缓存过期时间
    3）自定义序列化方式，例如jackson
              
