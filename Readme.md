# Система учета показаний

[1. ДЗ №1](https://github.com/AlekseiPetrovJ/Y_LAB_internship)

[2. ДЗ №2](https://github.com/AlekseiPetrovJ/Y_LAB_internship/pull/2)

[3. ДЗ №3](https://github.com/AlekseiPetrovJ/Y_LAB_internship/pull/3)

[4. ДЗ №4](https://github.com/AlekseiPetrovJ/Y_LAB_internship/pull/4)

[5. ДЗ №5](https://github.com/AlekseiPetrovJ/Y_LAB_internship/pull/5)

Позволяет регистрировать показания приборов учета.

Данные для авторизации:

login: user  
password: 123

login: admin  
password: 123

Пункты меню отображаются в соответствии с правами пользователя.

Показания можно подавать один раз в месяц.

Ранее поданные показания редактировать запрещено.

Последние поданные показания считаются актуальными.

Пользователь может видеть только свои показания, администратор может видеть показания всех пользователей.

Возможности:

## Регистрация пользователя
### Request

`POST /measurement/user`

        curl --location 'http://localhost:8080/measurement/user' \
        --header 'Content-Type: application/json' \
        --data '{
        "name": "Fedor",
        "password": "qwerty7"
        }'

### Response
    Content-Type: application.json 
    Status: 201 OK

    {"name":"Fedor","id":100004}

## Получение пользователя
### Request

`GET /measurement/user/<id>`

        curl --location 'http://localhost:8080/measurement/user/100004'

### Response

    Content-Type: application.json 
    Status: 200 OK

    {"name":"Fedor","id":100004}

## Добавление нового вида показаний

### Request

`POST /measurement/type`

        curl --location 'http://localhost:8080/measurement/type' \
        --header 'Content-Type: application/json' \
        --data '{
        "name": "trash",
        "unitOfMeasurement": "m3"
        }'

### Response

    Content-Type: application.json 
    Status: 201 OK

## Получение вида показаний

### Request

`GET /measurement/type/<id>`

        curl --location 'http://localhost:8080/measurement/type/100001'

### Response

    Content-Type: application.json 
    Status: 200 OK

     {"id" : 100001, "name" : "cold water", "unitOfMeasurement" : "m3" }


- регистрация пользователя (Любой пользователь)
- авторизация пользователя  (Неавторизованный пользователь)
- просмотр существующих типов показаний (Автозированный пользователь)
- добавление типов показаний (Адмнистратор)
- получение актуальных показаний счетчиков (Автозированный пользователь)

[//]: # (- реализовать эндпоинт подачи показаний)

[//]: # (- реализовать эндпоинт просмотра показаний за конкретный месяц)

[//]: # (- реализовать эндпоинт просмотра истории подачи показаний)

[//]: # (- реализовать контроль прав пользователя)

[//]: # (- Аудит действий пользователя &#40;авторизация, завершение работы, подача показаний, получение истории подачи показаний и тд&#41;)
Пример интерфейса неавторизованного пользователя:
![Иллюстрация к проекту](img.png)
