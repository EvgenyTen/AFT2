#language: ru

Функция: 03. Получение пользователей. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь21" с параметрами:
      | Администратор  | false |
      | Статус | 1     |
    И У пользователя есть доступ к API и ключ API
  @generation_sample
  Сценарий:03. Получение пользователей. Пользователь без прав администратора
    Если Отправить запрос на создание пользователя "user22" с параметрами:
    |Администратор|false|
    |Статус|1   |
    То Получен статус код ответа 403

