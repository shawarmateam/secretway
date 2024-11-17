# OffaccDB structure

This is the *documentation* about structure of **OffaccDB**.

> [!WARNING]
> SecretWay uses **MongoDB** databases.
> You need to have **mongosh** for minimum.

## What the structure looks like?

Structure have **servers_bd** and **users_bd**:
```tree
SecretWay-db
├── admin
├── config
├── local
│   └── startup_log
├── servers_bd
│    ├── msgs_servers
│    └── offacc_servers
└── users_bd
    └── users

```

## Msgs_servers

Example:
```json
{
	_id: ObjectId('...'),
	online: true,
	layer: 0,
	server_ip: 'localhost:1200'
}
```

## Offacc_servers

Example:
```json
{
	_id: ObjectId('...'),
	server_ip: 'localhost:1201'
}
```

## Users

Example:
```json
{
	_id: ObjectId('...'),
	user_id: 'id',
	password: 'SHA256',
	usertag: '@usertag',
	username: 'username',
	current_srv: 'localhost:1201'
}
```