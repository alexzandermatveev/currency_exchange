## Используемые технологии

- Spring Boot 2.7
- Maven 3
- Lombok
- Mapstruct
- Liquibase
- PostgreSQL

## Требования

### JDK 17

Проект использует синтаксис Java 17. Для локального запуска потребуется
установленный JDK 17.

### Docker
Для запуска проекта потребуется установленный и запущенный Docker.
Для запуска БД (PostgreSQL) требуется запустить соответствующий сервис в Docker.

### Подключение к интернету

Подключение к интернету для получения курсов валют

## Полезные команды

### Запуск контейнера с базой данных

Запуск контейнера при помощи [docker-compose.yml](docker/docker-compose.yml)
```bash
docker-compose up -d
```
или
```bash
docker run -p 5433:5432 --name currency_DB -e POSTGRES_PASSWORD=postgres -d postgres
```

Пользователь для подключения к контейнеру `postgres`.

### IntelliJ IDEA

Запустите main метод класса Application

### Запросы API

Создание новой записи о валюте

```bash
curl --request POST \
  --url http://localhost:8080/api/currency/create \
  --header 'Content-Type: application/json' \
  --data '{
  "name": "Доллар Готэм-Сити",
  "nominal": 3,
  "value": 32.2,
  "isoNumCode": 1337
}'
```

Получение Валюты по id

```bash
curl --request GET \
  --url http://localhost:8080/api/currency/1333
```

Получение всех Валют

```bash
curl --request GET \
  --url http://localhost:8080/api/currency
```

Обновление валют через API банка, которые задаются в [файле конфигурации](src/main/resources/application.yml)

```bash
curl --request GET \
  --url http://localhost:8080/api/currency/actual
```

Конвертация валюты по числовому коду

```bash
curl --request GET \
--url http://localhost:8080/api/currency/convert?value=100&numCode=840
```