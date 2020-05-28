## 1.入门,第一个Mybatis程序
- 在这里我遇到过的坑:

    - 1.org.apache.ibatis.binding.BindingException: Type interface com.sonia.dao.UserDao is not known to the MapperRegistry.
    这个问题是因为我们的mapper.xml文件没有在mybatis核心配置xml文件中注册.
    解决方法,在mybatis核心配置文件mybatis-config中注册,代码见mybatis-config.xml:
    
    - 2 mapper.xml中绑定的接口名称没有写成全限定名
    
    - 3 mapper.xml中的id和接口中对应的方法名没有对上
    
    - 4 mapper.xml中的returntype也要写成全限定名,不然会出现这样的错误:java.lang.ExceptionInInitializerError,Cause: java.lang.ClassNotFoundException: Cannot find class: User
    
    - 5 在pom.xml中没有解决 mybatis映射mapper.xml文件不编译的问题-,如果不解决这个,在java文件下的xml是不会被读的.
    
- 如何配置mabatis-config(mybatis核心配置文件)中的datasource:
    - 步骤一 连接数据库:右侧任务栏中点击database,出现的+号选择datasource,在datasource中找到mysql,在general中输入相应的用户名和密码,就可以登陆
    - 步骤二 获取url:连接数据库之后,点击刚刚登陆界面的schemas,在里面找到我们要连接的database,得到这个url,不过url后面还要加上具体的数据库和其他一些参数信息,包括useSSL,useUnicode,characterencoding,最新版好像还要配置时区;


## CRUD

####1.namespace
namespace中的包名需要和Dao/Mapper接口的包名一致;

####2.select
- id:就是对应的namespace的方法名

- resultType:是Sql语句的返回值

- paramterType:对应的namespace的参数

####注意点:
- 增删改需要提交事物,不提交事物没有办法成功

- 在标签()里面不可以出现/* */
    
    
    

