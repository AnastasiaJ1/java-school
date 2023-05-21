# База данных #

![](diagram.png)

# employees #
1. id уникальный идентификатор (not null) (т.к. account может быть null)
2. lastname (not null) Фамилия
3. firstname (not null) Имя
4. surname Отчество
5. job_title Должность
6. account (unique) Учетная запись
7. email Адрес электронной почты
8. status (not null) Статус сотрудника

# projects #
1. id уникальный идентификатор 
2. code (unique, not null) Код проекта
3. name (not null) Наименование
4. description Описание
5. status (not null) Статус проекта

# project_participants #
1. id (not null) уникальный идентификатор
2. employee_id (not null) идентификатор сотрудника
3. project_id (not null) идентификатор проекта
4. role роль сотрудника

# tasks #
1. id (not null) уникальный идентификатор
2. name (not null) Наименование задачи
3. description Описание
4. executor Исполнитель задачи
5. labor_costs_hours (not null) Трудозатраты
6. deadline (not null) Крайний срок
7. status Статус задачи
8. author Автор задачи
9. creation_date Дата создания
10. change_date Дата последнего изменения задачи
11. project_id Проект