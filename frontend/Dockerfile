FROM nginx

RUN apt-get update && apt-get install -y curl vim && apt-get clean

ENV \
    API_URL=${API_URL:-http://localhost:8081} \
    AUTH_URL=${AUTH_URL:-http://localhost:8090}

COPY ./dist /usr/share/nginx/html
COPY ./nginx-app.conf /etc/nginx/nginx.conf
CMD sed -i "s|API_URL|${API_URL}|g" /etc/nginx/nginx.conf
CMD sed -i "s|AUTH_URL|${AUTH_URL}|g" /etc/nginx/nginx.conf


ENTRYPOINT ["nginx", "-g", "daemon off;"]
