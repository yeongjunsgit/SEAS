FROM node:20.11 as build-stage

WORKDIR /app

COPY ./seas-fe/package.json .

RUN npm install
COPY ./seas-fe .

RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
RUN ["pwd"]
# COPY ./docker/dockerfile/nginx.conf /etc/nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]