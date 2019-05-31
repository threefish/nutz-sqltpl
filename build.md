####发布相关

```bash
mvn clean deploy -P release
```


idea生成JAVADOC 报java.lang.IllegalArgumentException解决方案[终极]
idea生成javadoc文档，总是会报  
```bash
java.lang.IllegalArgumentException 
    at sun.net.www.ParseUtil.decode(ParseUtil.java:202) 
```
解决方案：原因是classpath环境变量中使用%JAVA_HOME%相对路径，改成绝对路径可解决此问题 