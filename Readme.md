# Disclaimer: licence info
Although being licenced under MIT licence, the library uses LibrePDF dependency, which is licenced under LGPL licence.

# Quickstart
3 steps to get going. Make sure You have Java, Gradle and Docker installed and `dockerhost` defined in hosts file.
1) Run `gradle build`.
2) Go to `client/src/script/constants.ts` and change on the first line `http://localhost:7701` to 
`http://dockerhost:7701`.
3) Run `docker-compose up`.

# Setting up for development

## Docker
Make sure You have Docker installed and `dockerhost` defined in the hosts file. In Windows 10 for example, 
there is a `hosts` file in `C:\Windows\System32\drivers\etc` folder. This file needs to contain a line with 
`dockerhost x.x.x.x`, where `x.x.x.x` is the IP for Docker.

## Database
To create the database instance, run `docker-compose up postgres`.

Database runs on `dockerhost:7702`.

Default user is `user` and password is `pass`. These values can be changed in `application.properties` file.  

## Back-end
During development Java 14 and Gradle 6.4.0 were used.

Back-end runs on `localhost:7701`.

### To use your own Google credentials:
Go to `https://console.cloud.google.com/apis/credentials` and create OAuth 2.0 Client ID. 
In `application.properties` file, change the client `security.oauth2.google.clientId` value to 
the value provided by Google. 

### To log in as editor
Add to `application_roles` table your username and corresponding role. 

## Front-end
1) `cd client`
2) `npm install`
3) `npm run serve`

Front-end runs on `localhost:8080`.
