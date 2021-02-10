#language: ru
Функция: 6. Администрирование. Сортировка списка пользователей по пользователю

  Предыстория:
    Пусть В системе существует пользователь "Пользователь61" с параметрами:
      | Администратор  | true |
      | Статус         | 1    |
    И В системе существует пользователь "Пользователь62" с параметрами:
    | Администратор  | false |
    | Статус         | 1    |
    И В системе существует пользователь "Пользователь63" с параметрами:
    | Администратор  | false |
    | Статус         | 1    |
    И В системе существует пользователь "Пользователь64" с параметрами:
    | Администратор  | false |
    | Статус         | 1    |
    И Открыт браузер на главной странице

  @generation_sample
  Сценарий:6. Администрирование. Сортировка списка пользователей по пользователю
    Если Авторизоваться пользователем "Пользователь61"
    То На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" нажать на элемент "Администрирование"
    И На странице "Страница Администрирование" нажать на элемент "Пользователи"
    То На странице "Страница пользователи" отображается элемент "Таблица пользователей"
    И "Таблица пользователей" отсортирована по "Логин" по возрастанию

    Если В шапке "Таблица пользователей""нажать на Пользователь"
    То "Таблица пользователей" отсортирована по "Логин" по убыванию



