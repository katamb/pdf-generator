FROM node:12.2.0-alpine

EXPOSE 8080

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . ./

CMD ["npm", "run", "serve"]
