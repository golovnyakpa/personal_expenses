1. Сделать базовые операции трат.
    1. Сохранение новой траты. Делаем внешний ключ в трате на таблицу тэгов для трат
    2. Извлечение траты по айди
    3. Извлечение всех трат
    4. Изменение сохраненной траты
    5. Удаление траты по айди
2. Проверить, что в пункте 1:
    1. Корректно обрабатываются кривые запросы
    2. Есть валидация входных данных (нельзя предать отрицательную трату)
    3. Есть  dao, dto, service. Каждый из них решает свою задачу
3. Покрыть тестами результат пункта 1 (сходить в GPT для ускорения работы)
4. Добавить аутентификацию в сервис. Переделать функционал из пункта 1 на функционал для конкретного пользователя
5. 