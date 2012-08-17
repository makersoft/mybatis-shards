[![][Logo]][1] 
# [Mybatis-Shards][1] 专业的Mybatis数据库切分框架

## Mybatis Shards简介

Mybatis Shards在实现方式上完全借鉴于Hibernate Shards，目前可以认为是Hibernate Shards的一个迁移版本。

## Mybatis Shards概述

Mybatis Shards采用无侵入性的方式，无需更改现有程序代码，只要根据现有业务编写合理的分区策略即可。

在多数据源事物管理方面借鉴[Cobar Client][2]，采用Best Efforts 1PC Pattern的方式来实现多数据源的管理。


## 同类产品比较

 - [Cobar Client][2]
 - [Shardbatis][3]


联系方式：admin(#)makersoft.org

  [1]: http://www.makersoft.org
  [2]: http://code.alibabatech.com/wiki/display/CobarClient/Home
  [3]: http://code.google.com/p/shardbatis/
  [Logo]:https://raw.github.com/makersoft/makersoft.github.com/master/images/makersoft-logo.png