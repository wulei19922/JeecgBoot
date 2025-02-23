FROM registry.cn-hangzhou.aliyuncs.com/dockerhub_mirror/nginx
MAINTAINER jeecgos@163.com
VOLUME /tmp
ENV LANG en_US.UTF-8
RUN echo "server {  \
                      listen       80; \
                      location   /jeecgboot/ { \
                      proxy_pass              http://jeecg-boot-system:8080/jeecg-boot/; \
                      proxy_redirect          off; \
                      proxy_set_header        Host jeecg-boot-system; \
                      proxy_set_header        X-Real-IP \$remote_addr; \
                      proxy_set_header        X-Forwarded-For \$proxy_add_x_forwarded_for; \
                      # 添加跨域支持 \
                      add_header 'Access-Control-Allow-Origin' '*' always; \
                      add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always; \
                      add_header 'Access-Control-Allow-Headers' 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range' always; \
                      add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range' always; \
                      if (\$request_method = 'OPTIONS') { \
                          add_header 'Access-Control-Max-Age' 1728000; \
                          add_header 'Content-Type' 'text/plain; charset=utf-8'; \
                          add_header 'Content-Length' 0; \
                          return 204; \
                      } \
                  } \
                  #解决Router(mode: 'history')模式下，刷新路由地址不能找到页面的问题 \
                  location / { \
                     root   /var/www/html/; \
                      index  index.html index.htm; \
                      if (!-e \$request_filename) { \
                          rewrite ^(.*)\$ /index.html?s=\$1 last; \
                          break; \
                      } \
                  } \
                  access_log  /var/log/nginx/access.log ; \
              } " > /etc/nginx/conf.d/default.conf \
    &&  mkdir  -p  /var/www \
    &&  mkdir -p /var/www/html

ADD dist/ /var/www/html/
EXPOSE 80
EXPOSE 443
