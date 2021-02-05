#language: ru
Функция: Примеры генерации роли

  Сценарий:Генерация роли с полями по умолчанию
    Пусть В системе существует роль "роль" с параметрами по умолчанию
    Тогда Роль "роль" имеет параметры:
      | Позиция                               | 1                         |
      | Встроенная                            | 0                         |
      | Задача может быть назначена этой роли | true                      |
      | Видимость задач                       | Только общие задачи       |
      | Видимость пользователей               | Все активные пользователи |
      | Видимость трудозатрат                 | Все трудозатраты          |

  Сценарий:Генерация роли с параметрами
    Пусть В системе существует роль "роль" с параметрами:
      | Позиция                 | 2                                             |
      | Видимость задач         | Задачи созданные или назначенные пользователю |
      | Видимость пользователей | Участники видимых проекток                    |
      | Видимость трудозатрат   | Только собственные трудозатраты               |
    Тогда Роль "роль" имеет параметры:
      | Позиция                               | 2                                             |
      | Встроенная                            | 0                                             |
      | Задача может быть назначена этой роли | true                                          |
      | Видимость задач                       | Задачи созданные или назначенные пользователю |
      | Видимость пользователей               | Участники видимых проекток                    |
      | Видимость трудозатрат                 | Только собственные трудозатраты               |

  Сценарий: Генерация роли с определёнными правами
    Пусть Существует список прав роли "права" с правами:
      | Создание проекта              |
      | Редактирование проекта        |
      | Закрывать / открывать проекты |
      | Выбор модулей проекта         |
      | Управление участниками        |
    И В системе существует роль "роль" с параметрами:
      | Позиция | 2     |
      | Права   | права |
    Тогда Роль "роль" имеет параметры:
      | Позиция                               | 2                         |
      | Встроенная                            | 0                         |
      | Задача может быть назначена этой роли | true                      |
      | Видимость задач                       | Только общие задачи       |
      | Видимость пользователей               | Все активные пользователи |
      | Видимость трудозатрат                 | Все трудозатраты          |
      | Права                                 | права                     |

  Сценарий: Несколько проектов и пользователь с разными условиями
    Пусть В системе существует пользователь "пользак" с параметрами:
      | status | 1
      | admin  | false |
    Пусть Существует список прав роли "права роли" с правами:
      | Просмотр задач |
    И В системе существует роль "роль пользака" с параметрами:
      | Позиция | 1          |
      | Права   | права роли |
    И В системе существует проект "проект1" с параметрами:
      | Публичный | true |
    И В системе существует проект "проект2" с параметрами:
      | Публичный | false |
    И В системе существует проект "проект3" с параметрами:
      | Публичный | false |
    И В "проекте3" есть доступ у пользователей с ролями:
      | пользак | роль пользака |