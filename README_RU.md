# SecretWay

**SecretWay** - это приватный мессенджер, работающий на *community servers*.

## Важная информация
Это серверная часть **SecretWay**. Чтобы получить клиент, посетите этот [репозиторий](https://github.com/shawarmateam/secretway-client).

## Псевдонимы

- **msgS** - "messages server", сообщественный сервер, который содержит и отправляет сообщения.
- **OffAccS (OffAcc Server)** - "официальный сервер аккаунтов", сервер, который работает как `API`, клиент подключается к *OffAccS* и получает и отправляет сообщения через него.
- **Server layer** - каждый *msgS* имеет **layer**. Чем быстрее сервер, тем выше его уровень.

## Как это работает

Клиент устанавливает соединение с [OffAccS](#псевдонимы), затем [OffAccS](#псевдонимы) отправляет или получает сообщения. Все сообщения шифруются с использованием `RSA`. Затем *OffAccS* отправляет их на несколько [msgS](#псевдонимы) (в зависимости от лимита на OffAccS). У самого быстрого сервера повышается [layer](#псевдонимы) (0 - лучший сервер; N - самый медленный сервер или сервер, который не работает). [MsgS](#псевдонимы) отправляет сообщения на [OffAccS](#псевдонимы) получателя. [OffAccS](#псевдонимы) получателя запрашивает у отправителя, действительно ли он отправил это сообщение. Затем [OffAccS](#псевдонимы) получателя отправляет сообщение получателю.

## Используемые фреймворки

- **MongoDB** (NoSQL) **(driver ver.: 5.1.4)**
- **Bson** (для формирования пакетов) **(ver.: 5.1.4)**
- **JBcrypt** (для хеширования паролей и т.д.) **(ver.: 0.4)**
- **Log4J** (для устранения уязвимостей) **(ver.: 2.20.0)**

### Либы

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

## Лицензия

Этот проект лицензирован под [MIT License](https://github.com/shawarmateam/secretway/blob/main/LICENCE).

## Текущая версия

*SecretWay (серверная часть). Версия: [$version](https://github.com/shawarmateam/secretway/blob/main/VERSION)*
