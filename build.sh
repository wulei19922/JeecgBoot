git  pull
cd /data/code/JeecgBoot/jeecg-boot
mvn clean install -DskipTest
cd  /data/code/JeecgBoot/jeecg-boot/jeecg-module-system/jeecg-system-start
docker build  -t  jeecg-boot-system:latest  .
cd /data/code/JeecgBoot/jeecgboot-vue3
pnpm run build
docker build  -t  jeecgboot-vue3:latest  .
cd /data/code/JeecgBoot
docker-compose up -d
