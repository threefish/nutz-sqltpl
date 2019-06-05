nutz-sqltpl SQL模板实现
==================================
### 配合[NutzCodeInsight](https://plugins.jetbrains.com/plugin/10311-nutzcodeinsight)插件使用更香
[源码](https://github.com/threefish/nutz-sqltpl)
### 支持多种模板引擎

默认采用beetl引擎（注意,这不是与BeetlSql的集成），其他模版引擎可以自己扩展，详情看源码

###用法
然后在pom.xml中
```xml
<dependency>
  <groupId>com.github.threefish</groupId>
  <artifactId>nutz-sqltpl</artifactId>
  <version>最新版本</version>
</dependency>
```
maven坐标 [https://mvnrepository.com/artifact/com.github.threefish/nutz-sqltpl](https://mvnrepository.com/artifact/com.github.threefish/nutz-sqltpl)
##### 然后在ioc.js中
```javascript
var ioc = {
   sqlTplIocEventListener: {
           type: "com.github.threefish.nutz.sqltpl.SqlTplIocEventListener",
           args: [{refer: '$ioc'}]
       },
       beetlSqlTemplteEngineImpl: {
           type: "com.github.threefish.nutz.sqltpl.BeetlSqlTemplteEngineImpl",
           events: {
               create: "init"
           },
           fields: {
                statementStart : "<exp>",//可修改(如需要使用xmltag的只支持<exp></exp> xml tag,不支持其他xml tag)
                statementEnd :"</exp>"//可修改
            }
       }
    }
}
```

然后，在MainSetup.init内加入下面的语句, 启用热加载(注意：JAR包中的xml无法进行热加载)

```java
SqlsTplHolder.DEVELOPER_MODE = true;
```

然后,你需要一个Service文件 实现 ISqlDaoExecuteService 接口

```java
@IocBean(args = {"refer:dao"}, name = "companyService")
@SqlsXml("CompanyService.xml")
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService, ISqlDaoExecuteService {
    /**
    * 1、我是必须要有的
    * 2、可以不实现 ISqlDaoExecuteService 接口，用 SqlsTplHolder 直接渲染sql自己再进行操作
    */
    private SqlsTplHolder sqlsTplHolder;

    public CompanyServiceImpl(Dao dao) {
        super(dao);
    }

    @Override
    public SqlsTplHolder getSqlsTplHolder() {
        return this.sqlsTplHolder;
    }

    @Override
    public Dao getDao() {
        return dao;
    }

    @Override
    public Entity getEntity() {
        return super.getEntity();
    }

    @Override
    public Class getEntityClass() {
        return super.getEntityClass();
    }
    /**
     *  分页查询列表
     * @param param
     * @param pager
     * @return 
     */
    @Override
    public List<NutMap> queryAllBySql(NutMap param, Pager pager) {
        //此处queryAll对应
        return queryMapBySql("queryAll", param);
    }
}


```
你需要一个XML文件来管理当前service的Sql（请把我和CompanyServiceImpl放在一起，或采用相对路径自己摸索）
#### 当模版语法 <exp> if()... for()...等等 </exp> 取值表达式 ${}
来看一下例子
```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE Sqls PUBLIC "nutz-sqltpl"
           "https://threefish.gitee.io/nutz-sqltpl/nutz-sqltpl.dtd">
   <Sqls>
        <!--var是当前文件的共享变量，var中不能有表达式-->
       <var name="tableName">logistics_company</var>
       <!--wrap=true 表示将会清除换行符\n 默认不清除-->
       <sql id="queryAll" wrap="true">
           SELECT * from ${tableName}
           <exp> if(isNotEmpty(name)){ </exp>
           where name like @name
           <exp>}</exp>
       </sql>
   </Sqls>
```
![NutzCodeInsight](https://github.com/threefish/NutzCodeInsight/raw/master/image/NutzSqlTpl.gif)
