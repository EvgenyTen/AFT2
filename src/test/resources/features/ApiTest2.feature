#language: ru

Функция: 2. Создание пользователя. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь21" с параметрами:
      | Администратор  | false |
      | Статус         | 1     |

  @generation_sample
  Сценарий:
    Если Отправить запрос на создание пользователя "Пользователь22" с параметрами:
    |Администратор|false|
    |Статус       |1    |
    То Получен статус код ответа 403

