FROM tomcat:latest
MAINTAINER Genesis King
ENV TIME_ZONE Asia/Shanghai
RUN echo "${TIME_ZONE}" > /etc/timezone \
    && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime && rm -rf ${CATALINA_HOME}/webapps/*
ADD console/target/ROOT.war ${CATALINA_HOME}/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]