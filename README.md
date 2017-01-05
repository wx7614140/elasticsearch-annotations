# elasticsearch-annotations
(基于 vossie的elasticsearch-annotations调整，代码可能部分有问题)

插件的作用是通过类和属性上的注解来创建json mapping(由于我是按照时间 定时创建index,index只有name的不同，所以就用了它创建template,符合条件的index请求过来就直接根据template生成索引了)

Tested using Java 1.7 & Elasticsearch 2.3.5
