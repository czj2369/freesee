# freesee
基于SpringBoot的一个简单的javaWeb项目，实现了在线搜索电影、美剧、日剧的功能，并提供在线观看的功能。</br>

环境：JDK1.8</br>
编译工具：Idea</br>
# 效果图展示
![首页](https://github.com/czj2369/freesee/blob/freesee/1.png)
![首页](https://github.com/czj2369/freesee/blob/freesee/2.png)
![首页](https://github.com/czj2369/freesee/blob/freesee/3.png)
![搜索结果](https://github.com/czj2369/freesee/blob/freesee/4.png)
![搜索结果](https://github.com/czj2369/freesee/blob/freesee/5.png)
![观看](https://github.com/czj2369/freesee/blob/freesee/6.png)

# 如何搭建项目
1、首先将项目download到本地。</br>
2、打开IDEA，选择Import项目。</br>
3、找到项目的位置，一直默认进入到编辑界面。</br>
4、第一次进入项目，在右下角会有提示，选择Add as Maven Project。</br>
5、等待依赖的导入，成功后打开App.java，启动，在浏览器地址栏上打http://localhost:8080/ 即可进入首页。</br>

# 可能出现的问题：
1、依赖导入完后会报错无法运行项目</br>
解决：进入项目的根目录，删除除了src文件夹和pom.xml以外的所有文件，重新进入项目，此时右下角会有提示，选择Add as Maven Project，并且等待依赖的导入。</br>
2、提示没有SDK</br>
解决：File->Project Structure->Project，在Project SDK，选择JDK所在的位置即可。</br>
**注：本软件不得用于商业用途,仅做学习交流。**
