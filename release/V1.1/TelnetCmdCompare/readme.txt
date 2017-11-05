程序名称：
    Telnet Command Comparer

程序功能：
    对网元的show running-config进行两次快照，再比较快照之间的区别。
    在两次快照之间加入SNMP的一些设置动作，那么通过比较快照区别，可以查询到对应的命令行参数的变化。

使用步骤：
1.) 打开start.bat，编辑JAVA_HOME和BeyondCompare_dir。
其中，JAVA_HOME必须为JDK 1.6版本
      BeyondCompare_dir指beyondCompare根目录
      
2.) 运行start.bat。具体步骤不介绍，大家随意使用，反馈改进建议给我。

作者：
陈多多 10087118


版本更新记录：

v1.0  2012.10.31 第一版

v1.1  2012.11.2 网元提示符prompt可以自动刷新，提高易用性。