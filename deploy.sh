#!/bin/bash
##编译+部署项目

##需要export PROJ_PATH=jenkins任务在部署机器上的路径,已在任务中的shell脚本上配置

##编译
cd $PROJ_PATH/ls_crm
mvn clean install -DskipTests
##war包部署
docker cp $PROJ_PATH/ls_crm/target/crm2.war 6e2c4b531df9:/usr/local/tomcat/webapps
##重启docker容器
docker restart 6e2c4b531df9