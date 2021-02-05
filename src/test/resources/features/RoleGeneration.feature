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
      | Позиция                               | 2                     |
      | Видимость задач                       | Только общие задачи       |
      | Видимость пользователей               | Все активные пользователи |
      | Видимость трудозатрат                 | Все трудозатраты          |