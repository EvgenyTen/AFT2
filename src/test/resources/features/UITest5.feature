#language: ru
Функция: UI 5. Видимость проектов. Пользователь

  Предыстория:
    Пусть В системе существует пользователь "Пользователь51" с параметрами:
      | Администратор | false |
      | Статус        | 1     |
    Пусть Существует список прав роли "права роли" с правами:
      | Просмотр задач |
    И В системе существует роль "роль пользователя" с параметрами:
      | Позиция | 1          |
      | Права   | права роли |
    И В системе существует проект "проект1" с параметрами:
      | Публичный | true |
    И В системе существует проект "проект2" с параметрами:
      | Публичный | false |
    И В системе существует проект "проект3" с параметрами:
      | Публичный | false |
    И В "проект3" есть доступ у пользователя "Пользователь51" с ролью "роль пользователя"
    И Открыт браузер на главной странице

  @UI
  Сценарий:5. Видимость проектов. Пользователь
    Если Авторизоваться пользователем "Пользователь51"
    То На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" нажать на элемент "Проекты"
    То На странице "Страница Проекты" отображается элемент "Проекты"
    То Отображается проект "проект1"
    То Не Отображается проект "проект2"
    То Отображается проект "проект3"