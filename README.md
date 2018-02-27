Yuna企业通用后台
===============
![Yuna](./files/screenshot/yuna.jpg "Yuna")


简介
-----------------------------------
Yuna是一款

技术特点
-----------------------------------


技术架构
===============

架构图
-----------------------------------

![架构图](./files/screenshot/yuna_framework.png "架构图")
（有些丑）

简单使用说明
-----------------------------------
* 导入sql/init.sql文件到mysql数据库
* 导入项目到IDE.
* 修改console模块下配置文件
  ![配置文件](./files/screenshot/yuna_code_properties.png "配置文件")
  ![配置文件详情](./files/screenshot/yuna_code_properties_detail.png "配置文件详情")
* 启动项目,管理员账号在数据库中设置


平台目录结构说明
-----------------------------------
```
├─client: 业务系统接入Yuan平台的SDK
├─common: Yuna公共代码包含一些工具类等
├─console: Yuna核心代码，页面
├─domain: Model、Bean 
└─files: 项目文件 
   ├─screenshot: 系统截图 
   └─sql: 初始化SQL + DDL + DML
```
系统部分页面演示
-----------------------------------
系统登录
![登录页面](./files/screenshot/yuna_login.png "登录页面")

主页
![主页](./files/screenshot/yuna_main.png "主页")

业务系统管理
![业务系统管理](./files/screenshot/yuna_sysetm_manager.png "业务系统管理")

权限管理
![权限管理](./files/screenshot/yuna_permission.png "权限管理")

配置外部系统(Grafana)
![配置外部系统(Grafana)](./files/screenshot/yuna_grafana.png "配置外部系统(Grafana)")

配置外部系统(Kibana)
![配置外部系统(Kibana)](./files/screenshot/yuna_kibana.png "配置外部系统(Kibana)")
