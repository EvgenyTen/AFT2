#language: ru

Функция: 1. Создание, изменение, получение, удаление пользователя. Администратор системы

  Предыстория:
    Пусть В системе существует пользователь "Пользователь11" с параметрами:
      | Администратор | true |
      | Статус        | 1     |

  @CucumberTests
  Сценарий:1. Создание, изменение, получение, удаление пользователя. Администратор системы

    Если Отправить запрос на создание пользователя "Пользователь12" "пользователем" "Пользователь11" со статусом:2
    То Получен статус код ответа 201
    То Тело содержит данные созданного пользователя "Пользователь12"
    То В базе данных появилась запись с данными пользователя "Пользователь12"

    Если Отправить повторный запрос на создание пользователя "Пользователь12" пользователем "Пользователь11" с тем же телом запроса
    То Получен статус код ответа 422
    То Тело ответа содержит 2 ошибки,с текстом:"Email уже существует","Пользователь уже существует",""

    Если Отправить НЕ корректный запрос на создание пользователя "Пользователь12" пользователем "Пользователь11"
    То Получен статус код ответа 422
    То Тело ответа содержит 3 ошибки,с текстом:"Email уже существует","Пользователь уже существует","Пароль недостаточной длины (не может быть меньше 8 символа)"

    Если Отправить запрос на изменение пользователя "Пользователь12" пользователем "Пользователь11"
    То Получен статус код ответа 204
    То В базе данных изменилась запись с данными пользователя "Пользователь12",изменился "статус"и он равен 1


