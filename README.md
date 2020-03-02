## AutoScanner

基于Spring boot的服务，用于SonarQube多版本自动扫描

### 使用方法

手动启动SonarQube，配置一个全局的webhook

> http://localhost:8088/callback

在Config.java中填入SonarQube相关配置，包括启动扫描的命令。将指定扫描版本的文本文件**version list.txt**存放于根目录，每行一个commit版本，例如

> 81dc6ca
> 3c3cc79d3a4155e10b918893417bd47ecfbce88e

之后启动本服务即可。结果存放在version list同目录。



### Tips

1. 暂时不支持配置文件式的配置，只能改源代码，如有需要可以实现。
2. 由于只是一个半成品，所以没有在生产环境下测试过，目前只能用idea运行。打包放进服务器运行可能出现路径问题。
3. 原因同上暂时没有详尽的异常处理。