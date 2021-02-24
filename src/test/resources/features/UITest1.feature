#language: ru
Функция: UI 1. Авторизация администратором

  Предыстория:
    Пусть В системе существует пользователь "Пользователь" с параметрами:
      | Администратор | true |
      | Статус        | 1    |
    И Открыт браузер на главной странице

  @UI
  Сценарий:1. Авторизация администратором
    Если Авторизоваться пользователем "Пользователь"

    То На странице "Заголовок" отображается элемент "Домашняя страница"
    То На странице "Заголовок" элемент "Вошли как" имеет текст "Вошли как ${Пользователь->логин}"
    И На странице "Заголовок" отображается элемент "Домашняя страница"
    И На странице "Заголовок" отображается элемент "Моя страница"
    И На странице "Заголовок" отображается элемент "Проекты"
    И На странице "Заголовок" отображается элемент "Администрирование"
    И На странице "Заголовок" отображается элемент "Помощь"
    И На странице "Заголовок" отображается элемент "Моя учётная запись"
    И На странице "Заголовок" отображается элемент "Выйти"

    И На странице "Заголовок" не отображается элемент "Войти"
    И На странице "Заголовок" не отображается элемент "Регистрация"

    И На главной странице отображается поле "Лейбл поиск"
    И На главной странице отображается поле "Поле поиск"
