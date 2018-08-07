echo off

set APP_NAME=liugh-starter.jar
set APP_PATH=%~sdp0
echo %APP_PATH%
set CONFIG= -Dspring.profiles.active=prod -Dlogging.path=%APP_PATH%..\logs -Dlogging.config=%APP_PATH%..\env\${spring.profiles.active}\logback.yml -Dspring.config.location=%APP_PATH%..\config\application.yml,%APP_PATH%..\config\application-prod.yml

set DEBUG_OPTS=
if ""%1"" == ""debug"" (
   set DEBUG_OPTS= -Xloggc:%APP_PATH%..\logs\gc.log -verbose:gc -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%APP_PATH%..\logs
   goto debug
)

set JMX_OPTS=
if ""%1"" == ""jmx"" (
   set JMX_OPTS= -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9888 -Dcom.sun.management.jmxremote.ssl=FALSE -Dcom.sun.management.jmxremote.authenticate=FALSE
   goto jmx
)

echo "Starting the %APP_NAME%"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %JMX_OPTS% %CONFIG% -jar %APP_PATH%..\lib\%APP_NAME%
goto end

:debug
echo "debug"
java -Xms512m -Xmx512m -server %DEBUG_OPTS% %CONFIG% -jar %APP_PATH%..\lib\%APP_NAME%
goto end

:jmx
java -Xms512m -Xmx512m -server %JMX_OPTS% %CONFIG% -jar %APP_PATH%..\lib\%APP_NAME%
goto end

:end
pause