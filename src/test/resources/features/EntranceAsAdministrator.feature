#language: ru
Функция: Вход в систему в качестве Администратора системы

  Предыстория:
    Пусть Существует пользователь "user1" с параметрами:
      | admin  | true |
      | status | 1    |
    И Открыт браузер на главной странице


  Сценарий:
    Если Авторизоваться пользователем "user1"
    То На главной странице отображается поле "Проекты"
    То На главной странице отображается поле "Администрирование"