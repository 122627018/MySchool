前言
====================

> 本项目服务端源代码：https://github.com/122627018/Server_MySchool

之所以写这个项目，是由于自己之前做过的俩个项目:

> 嘉大助手APP
> 嘉大助手是为嘉大学子提供的一款集众多功能于一身的app,本软件不仅在线上为学生提供了很多帮助，例如失物找回，校园活动等等，而且为学生的生活也提供了很多便利，好像宿舍报修或者移动图书馆之类的。另外，此软件也为学生及时了解学校动态提供了桥梁，各种通知或者新闻可以直接通过app首页资讯获取，不必再使用电脑pc登陆官网首页去查看。
> 传送门： https://github.com/122627018/jyu

> TeachingOffice2
> 使用网络爬虫对校园网进行抓包并解析然后数据展示
> 传送门：https://github.com/122627018/TeachingOffice2

因为在完成这俩个项目的时候(其实也不算完成，还差很多细节方面的东西没处理)，发现自己其实还有很多不足，于是一时冲动就写了上面俩个项目的结合版，也就是这个项目。但是由于完成到一半的时候实验室的老师要求必须去帮忙写其他项目，于是这个项目写到一半就停了。后面换了电脑导致这个项目的很多资料都丢了。所以也没继续写下去。

app功能简介
===================
 此app是上面俩个app的结合升级版，采用Material Design设计，主要的实现的功能是通过对正方系统的账号验证来实现学号和app账号的绑定，从而达到每个账号都      有一个对应学号的功能，所以只需绑定学号，便可获取
   1.正方管理系统的数据(包括成绩查询，课表查询)
   2.图书馆的数据(借阅情况，馆藏查询)
   3.一卡通的消费情况
   在拥有以上这些功能的基础上，还附带社交功能，例如校园活动组织，失物招领，快递查询，新闻获取，用户模块等。
   (当时预期的功能，结果只完成了校园活动功能和用户模块，不过关于校园教务网的功能基本都实现了)
app截图
-------------------
![这里写图片描述](http://img.blog.csdn.net/20160617073128673)![这里写图片描述](http://img.blog.csdn.net/20160617073209467)![这里写图片描述](http://img.blog.csdn.net/20160617073223811)![这里写图片描述](http://img.blog.csdn.net/20160617073235764)![这里写图片描述](http://img.blog.csdn.net/20160617073245624)
app业务逻辑图和各模块功能
==================
社交模块
---------------
社交模块在此app中只是完成了校园活动模块，并且添加了评论功能，后面因为去帮老师做另外一个项目，这个app就停工了。关于校园活动模块可以参考我的第一个app：
> 嘉大助手APP
> 传送门： https://github.com/122627018/jyu

正方系统模块
------------------
主要的思路是通过服务器对学校的正方系统网页进行数据抓取，例如模拟登录，模拟获取成绩等，然后服务器封装数据返回给app。可以看看我之前关于正方系统抓包的项目：
> TeachingOffice2
> 使用网络爬虫对校园网进行抓包并解析然后数据展示
> 传送门：https://github.com/122627018/TeachingOffice2

在我的blog有一个分类是专门讲对正方系统进行抓包的：

> 从一个网页到一个APP
> http://blog.csdn.net/qq122627018/article/category/6198952

总结
=====================
在基本完成这个app的时候，数了一下，总共11个Activity和16个Fragment，其实做到后期不得不承认挺无力的，由于前期项目搭建可能不算很好，很多基类都是自己手把手抽取和封装的，到后面导致只要底层有一个简单地bean或者model稍微修改一下，整个项目基本都要改一篇。所以在做完这个项目之后，我深深发现自己身上的不足，开始积极地关注很多关于项目架构方面的知识，在blog里自己也总结出一些自己对这些知识的理解：

> 优雅地使用MVP模式
> http://blog.csdn.net/qq122627018/article/details/51684882
> 
>  Retrofit+RxJava在MVP模式中优雅地处理异常
>  http://blog.csdn.net/qq122627018/article/details/51689891

还有一些关于RxJava的，后面我整理完再贴出


