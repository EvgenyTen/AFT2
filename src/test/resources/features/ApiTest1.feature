#language: ru

Функция: Api 01. Создание, изменение, получение, удаление пользователя. Администратор системы

  Предыстория:
    Пусть В системе существует пользователь "Пользователь" с параметрами:
      | Администратор | true |
      | Статус        | 1     |

  @API
  Сценарий:01. Создание, изменение, получение, удаление пользователя. Администратор системы

    Если Отправить запрос на создание пользователя "Пользователь2" пользователем "Пользователь" со статусом: 2
    То Получен статус код ответа 201
    То Тело содержит данные созданного пользователя "Пользователь2"
    То В базе данных появилась запись с данными пользователя "Пользователь2"

    Если Отправить повторный запрос на создание пользователя "Пользователь2" пользователем "Пользователь" с тем же телом запроса
    То Получен статус код ответа 422
    То Тело ответа содержит 2 ошибки,с текстом:
     |"Email уже существует"|
     |"Пользователь уже существует"|

    Если Отправить НЕ корректный запрос на создание пользователя "Пользователь2" пользователем "Пользователь"
    То Получен статус код ответа 422
    То Тело ответа содержит 3 ошибки,с текстом:
     |"Email уже существует"|
     |"Пользователь уже существует"|
     |"Пароль недостаточной длины (не может быть меньше 8 символа)"|

    Если Отправить запрос на изменение пользователя "Пользователь2" пользователем "Пользователь"
    То Получен статус код ответа 204
    То В базе данных изменилась запись с данными пользователя "Пользователь2"

    Если Отправить запрос на получении инфо о пользователе "Пользователь2" пользователем "Пользователь"
    То Получен статус код ответа 200
    То В базе данных изменилась запись с данными пользователя "Пользователь2"

    Если Отправить запрос на удаление пользователя "Пользователь2" пользователем "Пользователь"
    То Получен статус код ответа 204
    То В базе данных отсутствует информация о пользователе "Пользователь2", созданном администратором

    Если Отправить запрос на удаление несуществующего пользователя "Пользователь2" пользователем "Пользователь"
    То Получен статус код ответа 404

