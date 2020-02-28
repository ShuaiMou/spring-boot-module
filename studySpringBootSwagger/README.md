# springboot-swagger
## 1. 介绍
  Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。总体目标是使客户端和文件系统作为服务
  器以同样的速度来更新。文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。
  
  Swagger是一组开源项目，其中主要要项目如下：
  
  1.   Swagger-tools:提供各种与Swagger进行集成和交互的工具。例如模式检验、Swagger 1.2文档转换成Swagger 2.0文档等功能。
  
  2.   Swagger-core: 用于Java/Scala的的Swagger实现。与JAX-RS(Jersey、Resteasy、CXF...)、Servlets和Play框架进行集成。
  
  3.   Swagger-js: 用于JavaScript的Swagger实现。
  
  4.   Swagger-node-express: Swagger模块，用于node.js的Express web应用框架。
  
  5.   Swagger-ui：一个无依赖的HTML、JS和CSS集合，可以为Swagger兼容API动态生成优雅文档。
  
  6.   Swagger-codegen：一个模板驱动引擎，通过分析用户Swagger资源声明以各种语言生成客户端代码。
  
## 2. dependency
    <dependency>
        <groupId>com.spring4all</groupId>
        <artifactId>swagger-spring-boot-starter</artifactId>
        <version>1.9.1.RELEASE</version>

        <exclusions>
            <exclusion>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-swagger-ui</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>swagger-bootstrap-ui</artifactId>
        <version>1.9.6</version>
    </dependency>
    
 ## 3. ui访问地址
    ip 和 port 为部署服务的IP和对应端口号（如果部署本地 ip=localhost， port默认8080）
    
    2.1 使用springfox-swagger-ui： http://ip:port/swagger-ui.html
    2.2 使用swagger-bootstrap-ui： http://ip:port/doc.html
    
 ## 4. 项目中常用注解说明
 
 |注解               |属性      |属性值类型      |说明 + 例子|
 |:----|:----|:----|:----|
 |@Api              |tags       |字符串        |可用在class头上,class描述|
 |                  |description|字符串        | @Api(tags = "用户管理")|
 |                  |value      |字符串        |  @Api(value = "xxx", description = "xxx")|
 |@ApiOperation     |value      |字符串        |可用在方法头上.参数的描述容器| 
 |                  |notes      |字符串        | @ApiOperation(value = "登录检查",consumes = "测试", notes = "测试用")| 
 |@ApiImplicitParams|{}         |@ApiImplicitParam数组|可用在方法头上.参数的描述容器| 
 |@ApiImplicitParam |name       |字符串 与参数命名对应|可用在@ApiImplicitParams里| 
 |                  |value      |字符串        |参数描述| 
 |                  |required   |布尔值        |true/false| 
 |                  |dataType   |字符串        |参数类型| 
 |                  |paramType  |字符串        |参数请求方式:query/path| 
 |                  |           |             |query:对应@RequestParam?传递| 
 |                  |           |             |path: 对应@PathVariable{}path传递| 
 |                  |defaultValue|字符串       |在api测试中默认值|
 |                  |           |              |@ApiImplicitParams({@ApiImplicitParam(name = "email", value = "用户唯一标识符-邮箱",required = true, paramType = "delete", dataType = "String")})|
 |@ApiResponses     |{}         |@ApiResponse数组|可用在方法头上.返回对象描述容器|
 |@ApiResponse      |code       |整型         |状态码|
 |                  |message    |字符串       |返回状态的描述|
 |                  |response   |Class对象    | 返回对象的class对象  |
 |                  |           |             |@ApiResponse(code = 404, message = "not found", response = JsonData.class)|
 |@ApiModel         |value      |字符串        |对实体类对描述|
 |                  |           |              |@ApiModel(value = "返回数据对象",description = "前后端交互协议数据类型")|
 |@ApiModelProperty |value      |字符串        |对实体类属性对描述|
 |                  |           |              |@ApiModelProperty(value = "数据",notes = "后端实体数据存储位置")|