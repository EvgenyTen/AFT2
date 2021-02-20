#language: ru

Функция: Api 03. Получение пользователей. Пользователь без прав администратора

  Предыстория:
    Пусть В системе существует пользователь "Пользователь31" с параметрами:
      | Администратор | false |
      | Статус        | 1     |
    Пусть В системе существует пользователь "Пользователь32" с параметрами:
      | Администратор | false |
      | Статус        | 1     |

  @API
  Сценарий:03. Получение пользователей. Пользователь без прав администратора
    Если Отправить запрос на "получение" пользователя "Пользователь31" пользователем "Пользователь31"
    То Получен статус код ответа 200
    То В теле содержится информация пользователя "Пользователь31" о самом себе, присутсутвуют поля admin и apikey



