#图片搜索APP

Material Design风格，使用Rxjava，MVP快速开发框架，封装的RecyclerView，retrofit 2.0网络请求库，Fresco图片加载库，图片瀑布流和错位式布局。具有热门推荐、每日一笑、板块分类、一键下载图片、分享图片、收藏图片、设为桌面壁纸、设为锁屏壁纸。

#Screenshot

![1](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro1.jpg "")
![2](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro2.jpg "")
![3](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro3.jpg "")

![4](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro4.jpg "")
![5](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro5.jpg "")
![6](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro6.jpg "")

![7](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro7.jpg "")
![8](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro8.jpg "")
![9](https://github.com/wenhuaijun/SearchPictureTool/blob/master/introduce/intro9.jpg "")

#下载地址

应用宝市场：http://android.myapp.com/myapp/detail.htm?apkName=com.example.administrator.searchpicturetool

豌豆荚市场：http://www.wandoujia.com/apps/com.example.administrator.searchpicturetool

小米应用市场：http://app.mi.com/detail/235067?ref=search

#dependencies(技术相关)

 Material Design 官方支持库   http://www.jianshu.com/p/1078568e859f
 
 响应式编程框架 Rxjava   http://gank.io/post/560e15be2dca930e00da1083
 
 网络请求库 retrofit   https://github.com/square/retrofit
 
 强大的图片加载组件 fresco   http://www.fresco-cn.org/
 
 如果不想用比较复杂的图片加载库（依赖后apk增加1M多）
 
 可以使用我做的轻量级图片加载库 EasyImageLoader  https://github.com/wenhuaijun/EasyImageLoader
 
 专为移动端提供的后端服务平台（热门推荐后台是这个做的） bmob   http://www.bmob.cn
 
 友盟社会化分享组件：意见反馈、分享、数据统计、更新推送全家桶  http://mobile.umeng.com/

以下是好朋友Jude写的快速开发库，亲测好用，强烈推荐给大家

 Beam——MVP快速开发框架   https://github.com/Jude95/Beam
 
 EasyRecyclerView   https://github.com/Jude95/EasyRecyclerView

 工具库   https://github.com/Jude95/Utils

#API
搜索图片接口：通过抓包抓到的接口，由于版权问题不好直接公布，请clone项目后在项目中查找

搞笑图片接口：http://www.laifudao.com/api.asp（该接口已经不行了，返回的搞笑图片json数据每天都不变）

#补充说明
由于本人才疏学浅，代码难免还是有很多的问题（例如某些页面沉侵式布局），请大家多多见谅，有问题多提提意。

其实这项目主要是给我朋友jude做的一些开源框架的项目demo案例，我用的他的开源框架我都已经熟读，完全能够自己写出来，所以大家不要吐槽我用太多别人的库的问题了 - -。我用的库我都从源码上学习过。

由于最近在找实习工作好久没维护代码，请各位挚友见谅见谅。（目前已拿到百度和阿里的offer）

后期会加入更多功能，例如未实现的图片剪辑、缩放、搜索标签管理、搜索排行、图片广场（用户图片交流平台）等，再优化下代码（Rx用得太渣了）。

#阿里2017届内推

1. 研发工程师Java
   职位方向：
           应用框架开发、Java消息中间件、Java数据访问技术、高性能Java网络服务器、Java网络编程、Web应用开发,Java应用服务化,分布式集群通信技术等；
   面试城市：
          远程
   岗位描述：
          如果你想了解JAVA开发在阿里巴巴互联网生态系统中无与伦比的应用广度与深度 如果你对基础技术感兴趣，你可以参与基础软件的设计、开发和维护，如分布式文件系统、缓存系统、Key/Value存储系统、数据库、Linux操作系统和Java优化等； 
          如果你热衷于高性能分布式技术，你可以参与高性能分布式服务端程序的系统设计，为阿里巴巴的产品提供强有力的后台支持，在海量的网络访问和数据处理中，设计并设施最强大的解决方案；  
          如果你喜欢研究搜索技术，你可以参与搜索引擎各个功能模块的设计和实现， 构建高可靠性、高可用性、高可扩展性的体系结构，满足日趋复杂的业务需求；
          如果你对电子商务产品技术感兴趣，你可以参与产品的开发和维护，完成从需求到设计、开发和上线等整个项目周期内的工作； 
          如果你对数据敏感，你可以参与海量数据处理和开发，通过sql、pl/sql、java进行etl程序开发，满足商业上对数据的开发需求； 
   岗位要求：
         或许，你来自计算机专业，机械专业，甚至可能是学生物的， 但是，你酷爱着计算机以及互联网技术，热衷于解决挑战性的问题，追求极致的用户体验； 
         或许，你痴迷于数据结构和算法，热衷于ACM，常常为看到“accept”而兴奋的手足舞蹈； 或许，你熟悉Unix/Linux/Win32环境下编程，并有相关开发经验，熟练使用调试工具，并熟悉Perl，Python，shell等脚本语言； 
         或许，你熟悉网络编程和多线程编程，对TCP/IP，HTTP等网络协议有很深的理解，并了解XML和HTML语言； 或许，你热衷于数据库技术，能够熟练编写SQL脚本，有MySql或Oracle应用开发经验； 
         或许，你并不熟悉Java编程语言，更精通C，C++，PHP，.NET等编程语言中的一种或几种，但你有良好和快速的学习能力； 有可能，你参加过大学生数学建模竞赛，“挑战杯”，机器人足球比赛等； 
         也有可能，你在学校的时候作为骨干参与学生网站的建设和开发； 这些，都是我们想要的。来吧，加入我们！

2. 客户端开发工程师
   职位方向：
         android/iOS
   面试城市：
          远程
   岗位描述：
          如果你，期望参与跨平台Native中间件开发； 如果你，期望参与跨平台网络中间件、H5容器、Native容器的开发； 如果你，期望参与语音识别、图像识别、地理围栏、虚拟试妆、3D建模、AR/VR等领域的开发； 
          如果你，期望参与无线电商的首页、交易主链路、登陆、店铺等基础组件维护与开发； 如果你，期望参与iOS、Android等系统平台整体架构设计、运行期性能优化、设计动态化可扩展的组件、框架、容器,提升整体研发质量和效率； 
          那还犹豫什么，赶紧加入我们吧！          
   岗位要求：
           熟悉iOS/Android平台原理机制,具备客户端性能优化的经验有一定软件架构设计能力，熟悉常见的异步、同步、多线程、跨进程、组件、容器的设计方法 具备扎实的数据结构和计算机系统基础，
           编码功底扎实 具备C++跨平台开发经验，熟悉NDK开发优先 具备创新业务技术攻关和落地能力者优先（不限于算法、生物识别、图形图像、3D建模、AR、多媒体等领域） 具有优秀的分析和解决实际问题的能力和态度，
           有创业的激情 重视用户体验积极尝试各种新技术选择最佳实现与我们的产品有效结合，从中获取喜悦和成就感，英雄不论出身，专业和学历都是过眼云烟，我们在乎的只有你，有你的加入，是我们最大的荣幸！
   
   以上职位的简历请投递到邮箱 huaijun.whj@alibaba-inc.com

#License#

Copyright 2016 wenhuaijun

###该开源项目遵循GPL v3 开源协议。
