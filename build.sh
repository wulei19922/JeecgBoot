git  pull
cd jeecg-boot
mvn clean install -DskipTest
cd ../jeecgboot-vue3
pnpm run build
docker-compose down
docker-compose up -d
