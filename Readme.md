# Licence info
Although being licenced under MIT licence, the library uses LibrePDF dependancy, which is licenced under LGPL licence.

# Quickstart
3 steps to get going. Make sure You have Java, Gradle and Docker installed and `dockerhost` defined in hosts file.
1) Run `gradle build`.
2) Go to `client/src/script/constants.ts` and change on the first line `http://localhost:7701` to 
`http://dockerhost:7701`.
3) Run `docker-compose up`.

# Getting started

## Docker
Make sure You have Docker installed and `dockerhost` defined in the hosts file. In Windows 10 for example, 
there is hosts file in `C:\Windows\System32\drivers\etc` folder. This file needs to contain a line with 
`dockerhost x.x.x.x`, where `x.x.x.x` is the IP for docker.

## Database
`docker-compose up postgres`

Database runs on `dockerhost:7702`.

Default user is `user` and password is `pass`. Theses values can be changed in `application.properties` file.  

## Back-end
During development Java 13 and Gradle 6.2.2 were used.
Go to `https://console.cloud.google.com/apis/credentials` and create OAuth 2.0 Client ID. 
In `application.properties` file, change the client `security.oauth2.google.clientId` value to 
the value provided by Google. 

Back-end runs on `localhost:7701`.

## Front-end
1) `cd client`
2) `npm install`
3) `npm run serve`
Front-end runs on `localhost:8080`.

