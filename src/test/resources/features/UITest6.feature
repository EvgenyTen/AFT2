#language: ru
Функция: UI 6. Администрирование. Сортировка списка пользователей по пользователю

  Предыстория:
    Пусть В системе существует пользователь "Пользователь" с параметрами:
      | Администратор | true |
      | Статус        | 1    |
    И В системе существует пользователь "Пользователь2" с параметрами:
      | Администратор | false |
      | Статус        | 1     |
    И В системе существует пользователь "Пользователь3" с параметрами:
      | Администратор | false |
      | Статус        | 1     |
    И В системе существует пользователь "Пользователь4" с параметрами:
      | Администратор | false |
      | Статус        | 1     |
    И Открыт браузер на главной странице

  @UI
  Сценарий:6. Администрирование. Сортировка списка пользователей по пользователю
    Если Авторизоваться пользователем "Пользователь"
    То На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" нажать на элемент "Администрирование"
    И На странице "Страница Администрирование" нажать на элемент "Пользователи"
    То На странице "Страница Пользователи" отображается таблица "Таблица пользователей"
    И Таблица пользователей отсортирована по столбцу "Логин", по возрастанию
    Если В шапке таблицы пользователей нажать на "Пользователь"
    То Таблица пользователей отсортирована по столбцу "Логин", по убыванию



