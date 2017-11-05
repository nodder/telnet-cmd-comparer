@echo off
title Telnet Command Comparer

set JAVA_HOME=C:\jdk1.6.0_22
set BeyondCompare_dir=D:\Tool\BeyondCompare3\

set JAVA=%JAVA_HOME%\bin\java
set MAIN_LIB=.\lib
set classpath=%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%2\lib\tools.jar;%MAIN_LIB%\cdd-telnet-cmd-compare.jar;%MAIN_LIB%\jta20.jar;%MAIN_LIB%\uep-protocol-snmp.jar;

rem set JAVA_OPTS=-classic -Xdebug -Xnoagent -XX:+PrintGCDetails -verbose:gc -Xloggc:..\log\gc.log -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8789,server=y,suspend=y %JAVA_OPTS%

rem set runProcess=%JAVA%
 set runProcess=start javaw

%runProcess% %JAVA_OPTS% -Xms64m -Xmx128m  cdd.product.zte.telnetcompare.Start %BeyondCompare_dir%