FROM nginx

ENV API_URL=${API_URL:-http://localhost:8081}

COPY ./dist /usr/share/nginx/html
COPY ./nginx-app.conf /etc/nginx/conf.d/default.tmpl

ENTRYPOINT ["nginx", "-g", "daemon off;"]
