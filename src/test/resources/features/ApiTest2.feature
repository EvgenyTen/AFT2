#language: ru

Функция: 02. Создание пользователя. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь21" с параметрами:
      | Администратор | false |
      | Статус        | 1     |

  @CucumberTests
  Сценарий:02. Создание пользователя. Пользователь без прав администратора
    Если Отправить запрос на создание пользователя "Пользователь21" "пользователем" со статусом:1
    То Получен статус код ответа 403

