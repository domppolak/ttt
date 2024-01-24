FROM node:latest AS build
WORKDIR /usr/app
COPY tttface/package.json tttface/package-lock.json ./
RUN npm install
COPY tttface ./
RUN npm run build

FROM nginx:alpine AS frontend
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/app/dist/tttface/browser /usr/share/nginx/html
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.sample.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]
