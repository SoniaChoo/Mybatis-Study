## 1.入门,第一个Mybatis程序
- 在这里我遇到过的坑:

    - 1.org.apache.ibatis.binding.BindingException: Type interface com.sonia.dao.UserDao is not known to the MapperRegistry.
    这个问题是因为我们的mapper.xml文件没有在mybatis核心配置xml文件中注册.
    解决方法,在mybatis核心配置文件mybatis-config中注册,代码见mybatis-config.xml:
    
    - 2 mapper.xml中绑定的接口名称没有写成全限定名
    
    - 3 mapper.xml中的id和接口中对应的方法名没有对上
    
    - 4 mapper.xml中的returntype也要写成全限定名,不然会出现这样的错误:java.lang.ExceptionInInitializerError,Cause: java.lang.ClassNotFoundException: Cannot find class: User
    
    - 5 在pom.xml中没有解决 mybatis映射mapper.xml文件不编译的问题-,如果不解决这个,在java文件下的xml是不会被读的.
    
    - 6 因为写了中文而报错,需要在xml头文件中改为utf-8;
    
    -7 resource中的核心配置文件绑定mapper,需要写全路径
    
- 如何配置mabatis-config(mybatis核心配置文件)中的datasource:
    - 步骤一 连接数据库:右侧任务栏中点击database,出现的+号选择datasource,在datasource中找到mysql,在general中输入相应的用户名和密码,就可以登陆
    - 步骤二 获取url:连接数据库之后,点击刚刚登陆界面的schemas,在里面找到我们要连接的database,得到这个url,不过url后面还要加上具体的数据库和其他一些参数信息,包括useSSL,useUnicode,characterencoding,最新版好像还要配置时区;


## 2.CRUD

####1.namespace
namespace中的包名需要和Dao/Mapper接口的包名一致;

####2.select
- id:就是对应的namespace的方法名

- resultType:是Sql语句的返回值

- paramterType:对应的namespace的参数

####注意点:
- 增删改需要提交事物,不提交事物没有办法成功

- 在标签()里面不可以出现/* */


## 3.万能的Map


假设我们的实体类或者数据库中的表参数过多, 我们应当考虑Map!

```xml
<!--如果使用了万能的map,那么values后面传入的值不需要和User实体中的属性对应,也不需要和数据库中的字段对应,只需要和test中传入的map的key对应就可以了-->
    <insert id="insertUser2" parameterType="map">
        insert into mybatis.user(id, name, pwd) values (#{id}, #{name}, #{password});
    </insert>
```

Map传递参数, 直接在Sql中取出Key就可以了[parameterTyep = "map"]
对象传递参数,需要直接在sql中取对象的属性[parameterTyep = "Object"]
只有一个 **基本类型** 的参数的情况下,可以直接在sql取[可以不写]

多个参数用map或者注解!

## 4.思考题

模糊查询怎么写:

- 1.在java代码层面传递通配符:

```java
public void getUserByLikeTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name","%o%");
        List<User> userByLike = mapper.getUserByLike(map);
        for (User user : userByLike) {
            System.out.println(user);
        }
        sqlSession.close();
    }
```

- 2.在sql拼接中使用通配符

```xml
<!--s使用like进行模糊查询-->
    <select id="getUserByLike" resultType="com.sonia.user.User" parameterType="map">
        select * from mybatis.user where name like "%"#{name}"%";
    </select>
```
- 注意:如果双引号换成单引号就查不出东西来:
```xml
select * from mybatis.user where name like '%'#{name}'%';
```

- 3 方法二的另外一种写法:

```xml
 <select id="getUserByLike" resultType="com.sonia.user.User" parameterType="map">
        select * from mybatis.user where name like  concat("%"#{name}"%");
    </select>
```
    
## 5.配置解析(对应mybatis-02)

#### 5.1 核心配置文件

- mybatis-config.xml

    - configuration（配置）
    - properties（属性）
    - settings（设置）
    - typeAliases（类型别名）
    - typeHandlers（类型处理器）
    - objectFactory（对象工厂）
    - plugins（插件）
    - environments（环境配置）
    - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）
    - databaseIdProvider（数据库厂商标识）
    - mappers（映射器）
    
#### 5.2 环境配置(environments)

- MyBatis 可以配置成适应多种环境

- 不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。
    学会使用配置多套运行环境.
- mybatis默认的事务管理器就是JDBC, 连接池是:POLED: 

#### 5.3 属性 properties

- 这些属性可以在外部进行配置，并可以进行动态替换。
你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置[db.properties]
 
- 1.编写一个配置文件 db.properties:
```properties
driver=com.mysql.jdbc.Driver
url= jdbc:mysql://127.0.0.1:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8
username=root
pasword=qq@982709437
```    
- 2.在核心配置文件中引用(在xml中,所有的标签都有其规定的顺序):

#### 5.4 类型别名 typeAliases

- 类型别名可为 Java 类型设置一个缩写名字。 

- 它仅用于 XML 配置，意在降低冗余的全限定类名书写。

方式一:
   ```xml
<!--设置别名-->
    <typeAliases>
        <typeAlias type="com.sonia.pojo.User" alias="useruser"></typeAlias>
    </typeAliases>
```

方式二: (扫描实体类的包, 默认别名是类名或者首字母小写的类名)
```xml
 <typeAliases>
        <package name="com.sonia.pojo"/>
    </typeAliases>
```
在实体类比较少的时候使用第一种方式,在实体类比较多的时候使用第二种方式,但是第一种实体类可以diy别名,第二种则不行 
第二种要diy别名的话需要在实体类上加上注解,加上注解的话默认的别名就失效了

#### 5.5 设置 Setting   

#### 5.6 其他配置
- typeHandlers(类型处理器)
- objectFactory(对象工厂)
- plugins(插件)
    - mybatis-generator-core
    - mybais-plus
    - 通用mapper

#### 5.7 映射器(mappers) 

方式一:
```xml
<!-- 以下这个是错误示范:
   1.不能用.,需要用/,
   2.注册的是接口实现类或者是xml文件,而不是接口
    <mappers>
        <mapper resource="com.sonia.dao.UserMapper"></mapper>
    </mappers>-->
    <!--mapper.xml注册方式一-->
    <!--<mappers>
        <mapper resource="com/sonia/dao/UserMapper.xml"></mapper>
    </mappers>-->
```
方式二:
```xml
<!--mapper.xml注册方式二:注意点:非注解方式下需要同包同名-->
    <mappers>
        <package name="com.sonia.dao"/>
    </mappers>
```

方式三: 
```xml
<!--mapper.xml注册方式三:注意点:非注解方式下需要同包同名-->
    <!--<mappers>
        <mapper class="com.sonia.dao.UserMapper"></mapper>
    </mappers>-->
```  

如果想要解决方式二和方式三的弊端,只需要在resource下建和接口所在相同名字的包,

因为这样编译后,class文件就会和xml文件在同一个包下,这些是JVM的类加载器的内容,

出自张龙老师60h那个视频.

https://baijiahao.baidu.com/s?id=1626496550792151955&wfr=spider&for=pc


#### 5.8 生命周期和作用域

- SqlSessionFactoryBuilder:
    - 一旦创建了 SqlSessionFactory,就不再需要它了
    - 所以它应该为局部变量

- SqlSessionFactory:
    - 说白了,就是可以想象为数据库连接池
    - 一旦创建就一直存在,多次重建浪费资源
    - 全局变量, 最简单的就是单例模式或者静态单例模式.

- SqlSession:
    - 连接到连接池的一个请求
    - 用完之后一定要关闭,否则资源被占用.
    - 它的实例不是线程安全的,因此不能被共享,最佳作用域是请求或者方法里面. 


## 6.解决属性名和字段名不一致的问题(mabatis-03)

新建一个项目 mybatis-03    

```java
public class User {
    private int id;
    private String haha;
    private String hehe;
}
```  
- 解决方法一 起别名:
```xml
<!--select根据id查询用户-->
    <select id="getUserById" resultType="user" parameterType="_Integer">
        select id,name as haha ,pwd as hehe from mybatis.user where id = #{id};
    </select>
``` 

- 解决方法二  resultMap:
```xml
<!--解决字段和属性名不匹配方式二-->
    <select id="getUserById" resultMap="UserMap">
        select * from mybatis.user where id = #{id}
    </select>

    <resultMap id="UserMap" type="user">
        <result column="name" property="haha"></result>
        <result column="pwd" property="hehe"></result>
    </resultMap>
```     

- 之后还会遇到别的更加高级的映射,比如一对多(associati), 多对一(collection)

## 7 日志工厂(mybatis-04)

这个可以参考着百度学:
https://baike.baidu.com/item/log4j/480673?fr=aladdin

#### 7.1 标准日志实现


在Mybatis具体使用哪一个日志实现,在核心配置文件中设置中设定!

- STDOUT_LOGGING

```xml
<!--设置日志,name千万不要写错了,比如写成LogImpl或者出现空格-->
    <!--<settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>-->
```

#### 7.2 lOG4J

- 先导入LOG4J的包
 ```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```
- db.properties文件配置
```properties
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/kuang.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```
- 配置LOG4J为日志的实现:
```xml
<!--记得导包-->
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
```

- LOG4J的使用
直接测试运行

#### 7.2.3 LOG4J的简单使用

- 1.在要使用LOG4J的类中导入包(记得是apache下面的,不要导成别的包了);

- 2.日志对象,参数为当前类的class.
```java
static Logger logger = Logger.getLogger(Mytest.class);
```
- 3.日志级别
```java
@Test
    public void log4jTest() {
        logger.info("info:进入了log4Test");
        logger.debug("debug:进入了log4Test");
        logger.error("error:进入了log4Test");
    }
```

## 8 分页

#### 8.1 使用limit分页:
```xml
select * from user limit startIndex, pageSize;
```

- 使用Mybatis实现分页,核心SQL:

    1.接口
```java
List<User> getUserByLimit(Map<String,Integer> map);
```
    2.Mapper.xml
```xml
<!--根据分页查找-->
    <select id="getUserByLimit" parameterType="map" resultType="user">
        select * from mybatis.user limit #{startIndex},#{pageSize};
    </select>`
```

    3.测试
```java
@Test
    public void limitTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("startIndex",1);
        map.put("pageSize",2);
        List<User> userByLimit = mapper.getUserByLimit(map);
        for (User user : userByLimit) {
            System.out.println(userByLimit);
        }
        sqlSession.close();
    }
``` 

#### 8.2 使用Rowbounds类实现

1.接口
```java
//根据分页查找rowbounds实现
     List<User> getUserByRowbounds();
```

2.Mapper.xml
```xml
<!--使用rowbounds分页-->
    <select id="getUserByRowbounds" resultType="user">
        select * from mybatis.user;
    </select>
```

3.测试
```java
@Test
    public void rowboundsTest() {
        SqlSession sqlSession = UserUtil.getSqlSession();
        //RowBounds
        RowBounds rowBounds = new RowBounds(1, 2);
        List<User> user = sqlSession.selectList("com.sonia.dao.UserMapper.getUserByRowbounds", null, rowBounds);
        for (User user1 : user) {
            System.out.println(user1);
        }
        sqlSession.close();
    }
```
#### 8.3分页插件

pageHelper:https://pagehelper.github.io/


## 9 使用注解开发(mybatis-05)

- 如果是字段名和属性名不一致,注解开发解决不了.

- 底层主要应用反射.

 #### 9.1注解的CRUD
 
 - 方法存在多个参数,所有的参数前面必须加上@Param("id")
 ```java
@Select("select * from user where id= #{uid}")
     User getUserById(@Param("uid") int id);
```
注解@param中的参数名称和sql语句中的参数名称要一样


#### 关于@param()的注解:
- 基本类型的参数或者String类型,需要加上
- 引用类型不需要加
- 如果只有一个基本类型的话,可以忽略,但是建议大家都加上
- 我们在SQL中引用的就是我们这里在@Param()中设定的属性名

## 10 Lombok

使用步骤:
- 在idea中安装lombok插件
- 在项目中导入lombok的jar包
```xml
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
    <scope>provided</scope>
</dependency>
```
- 3.在实体类上加注解即可
    -  @Getter and @Setter
    -  @FieldNameConstants
    -  @ToString
    -  @EqualsAndHashCode
    -  @AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
    -  @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
    -  @Data
    -  @Builder
    -  @SuperBuilder
    -  @Singular
    -  @Delegate
    -  @Value
    -  @Accessors
    -  @Wither
    -  @With
    -  @SneakyThrows
    -  @val
    -  @var
    -  experimental @var
    -  @UtilityClass
    -  Lombok config system
    
- 最常用的:

    - @Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。    
    - @NoArgsConstructor
    - @AllArgsConstructor
    - @ToString
    
## 11 多对一处理(mybatis-06)

多对一的时候都是javaType

- 例子:多个学生对应一个老师    

    - 对于学生:多对一(关联)
    - 对于老师:一对多(集合)

#### 11.1  测试环境搭建

- 1.导入lombok依赖包
- 2.创建实体类
- 3.创建Mapper接口
- 4.使用注解写sql语句或者Mapper.xml文件
- 5. 在核心配置文件中注册第四步的接口或者文件
- 6. 测试 

#### 11.2 子查询(按照查询嵌套处理)
```xml
<mapper namespace="com.sonia.dao.StudentMapper">
    <!--1.查询所有的学生信息
        2.根据查询出来的学生的tid去寻找对应的老师
        子查询-->

    <select id="getStudent" resultMap="StudentTeacher">
        select * from mybatis.student;
    </select>

    <resultMap id="StudentTeacher" type="com.sonia.pojo.Student">
        <id property="id" column="id"></id>
        <result property="name" column="name"></result>
        <!--复杂的属性我们需要单独处理
        对象使用:association
        集合使用:collection-->
        <association property="teacher" column="tid" javaType="com.sonia.pojo.Teacher" select="getTeacher">
        </association>
    </resultMap>

    <select id="getTeacher" resultType="com.sonia.pojo.Teacher">
        select * from mybatis.teacher where id = #{tid};
    </select>
</mapper>
```

#### 11.3 联表查询(按照结果查询嵌套处理)

```xml
<!--按照结果嵌套查询-->
<mapper namespace="com.sonia.dao.StudentMapper">
    <select id="getStudent" resultMap="StudentByTeacher">
        select s.id sid, s.name sname, t.name tname from mybatis.student s, mybatis.teacher t where s.tid=t.id;
    </select>

    <resultMap id="StudentByTeacher" type="com.sonia.pojo.Student">
        <id property="id" column="sid"></id>
        <result property="name" column="sname"></result>
        <association property="teacher" javaType="com.sonia.pojo.Teacher">
            <result property="name" column="tname"></result>
        </association>
    </resultMap>
</mapper>
```

## 12 一对多(mybatis-07)
    一对多的时候都是ofType
1.环境搭建,和刚才一样

2.按结果嵌套:
```xml
<mapper namespace="com.sonia.dao.TeacherMapper">
    <!--<select id="getTeacher" resultType="com.sonia.pojo.Teacher">
        select  * from mybatis.teacher;
    </select>-->
<!--按结果嵌套查询:
1.注意点:取别名
2.ofType-->
    <select id="getTeacher" resultMap="TeacherStudent">
        select s.id sid, s.name sname, t.name tname, t.id tid from mybatis.teacher t ,mybatis.student s where s.tid = t.id and t.id = #{tid};
    </select>
    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="tid"></result>
        <result property="name" column="tname"></result>
        <collection property="students" ofType="com.sonia.pojo.Student">
            <result property="id" column="sid"></result>
            <result property="name" column="sname"></result>
            <!--<result property="id" column="tid"></result>-->
        </collection>
    </resultMap>
</mapper>
```

3.按查询嵌套

```xml
<!--按查询嵌套-->
    <select id="getTeacher" resultMap="TeacherStudent">
        select * from mybatis.teacher where id = #{tid};
    </select>

    <resultMap id="TeacherStudent" type="com.sonia.pojo.Teacher">
        <collection property="students" ofType="Student" select="getStudent" column="id"></collection>
    </resultMap>



    <select id="getStudent" resultType="com.sonia.pojo.Student">
        select * from mybatis.student where tid = #{tid};
    </select>
```

## 13 动态SQL(mybatis-08)

#### 13.1 SQL片段
- 使用sql标签抽取公共部分
- 在需要使用的地方使用Include标签引用即可
- 注意事项:最好基于单表定义SQL片段, 不要存在where标签,因为where和set有优化功能
```xml
<sql id="if-author-title">
        <if test="title != null">
            and title=#{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </sql>

    <select id="selectBlogIf" parameterType="map" resultType="blog">
        select * from mybatis.blog where 1=1
        <include refid="if-author-title"></include>
    </select>
```

#### 13.2 foreach
方式一:
```xml
<!--collection怎么写,我们可以传递一个万能的map,这个map中可以存在一个集合
    注意,开头的open可以是and (也可是单独的(-->
    <!--id=#{id},如果写成#{id},就是查询全部了-->
    <select id="selectBlogForEach" parameterType="map" resultType="blog">
        select * from mybatis.blog
        <where>
            <foreach collection="ids" item="id" open="(" separator="or" close=")">
               id=#{id}
            </foreach>
        </where>
    </select>
```

方式二:
```xml
<select id="selectBlogForEach" parameterType="map" resultType="blog">
        select * from mybatis.blog where id in
        <foreach collection="ids" item="id" open="(" separator="," close=")">
            #{id}
        </foreach>
    </select>
```

测试:
```java
@Test
    public void selectBlogForEachTest(){
        SqlSession sqlSession = UserUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        //ids.add(2);
        map.put("ids",ids);
        List<Blog> blogs = mapper.selectBlogForEach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
```

