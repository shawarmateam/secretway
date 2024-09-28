# SecretWay

> [!WARNING]
> **README.md** на русском находится [тут](https://github.com/shawarmateam/secretway/blob/main/README_RU.md).

The **private messenger** that works on *community servers*.

> [!IMPORTANT]
> It's a server part of **SecretWay**.<br>
> To get a client you need to visit this [repo](https://github.com/shawarmateam/secretway-client).

## Aliases

* **msgS** - "messages server", community server that contains & sends messages.
<br><br>
* **OffAccS (OffAcc Server)** - "offical account server", offical server that works like `API`, 
client connecting to *OffAccS* and get & send msgs using it.
<br><br>
* **Server layer** - every *msgS* has **layer**. It's reputation of the server.
The faster the server, the higher the layer.

## How it works

Client get connection with [OffAccS](#aliases), then [OffAccS](#aliases) sends or gets messages.
All messages crypts using `RSA`. Then *OffAccS* sends it on few [msgS](#aliases) *(depending on the limit on OffAccS)*. 
The fastest server gets [layer](#aliases) boosting *(0 - the best server; N - the slowest server or server doesn't work)*. 
[MsgS](#aliases) sends messages to [OffAccS](#aliases) of recipient. [OffAccS](#aliases) of recipient asking sender
if sender sends this message. Then [OffAccS](#aliases) of recipient sends to recipient.

## What frameworks does SecretWay (server part) using
* **MongoDB** database *(NoSql)* **(driver ver.: 5.1.4)**
  * **Bson** *(to make packages)* **(ver.: 5.1.4)**
* **JBcrypt** *(to hashing password & etc.)* **(ver.: 0.4)**
* **Log4J** *(to fix vulnerability)* **(ver.: 2.20.0)**

### Libs

```
    bson-5.1.4.jar
    bson-record-codec-5.1.4.jar
    jbcrypt-0.4.jar
    log4j-1.2-api-2.20.0.jar
    log4j-api-2.20.0.jar
    log4j-core-2.20.0.jar
    log4j-slf4j-impl-2.20.0.jar
    mongodb-driver-core-5.1.4.jar
    mongodb-driver-sync-5.1.4.jar
    slf4j-api-1.7.25.jar
```

## Licence

SecretWay has the [MIT licence](https://github.com/shawarmateam/secretway/blob/main/LICENCE).

## Current version

*SecretWay (server tools). Version: [$version](https://github.com/shawarmateam/secretway/blob/main/VERSION)*
