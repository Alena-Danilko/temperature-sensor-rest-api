# Temperature Sensor REST API
Этот проект представляет собой REST API сервис для работы с датчиком измерения температуры окружающего воздуха и определения наличия дождя. Проект также включает в себя клиентскую часть для отправки данных на REST API.

# Особенности:
1) Измерение температуры окружающего воздуха.
2) Определение наличия дождя.
3) REST API сервис для получения и отправки данных.
4) Клиентское приложение для отправки данных на сервер.
5) Использование Java, PostgreSQL и Hibernate.

# Требования
1) Java 8+
2) Maven
3) PostgreSQL
4) Spring Boot
5) Hibernate
6) JUnit 5

# API Endpoints
GET /measurements - Получить все измерения из базы данных.

GET /measurements//rainyDaysCount - Получить количество дождливых дней из базы данных.

POST /measurements/add - Отправить данные с датчика на сервер.

POST /sensors/registration - Регистрация нового сенсора в системе.

# Структура проекта
# Основной код
src/main/java/com/RestApiProject - Исходный код сервера.

src/main/java/com/RestApiProject/controllers - REST контроллеры.

src/main/java/com/RestApiProject/dto - Объекты передачи данных (DTO).

src/main/java/com/RestApiProject/models - Модели данных.

src/main/java/com/RestApiProject/repositories - Репозитории для работы с базой данных.

src/main/java/com/RestApiProject/service - Бизнес-логика.

src/main/java/com/RestApiProject/util - Вспомогательные классы для обработки исключений и валидации.

src/main/java/com/RestApiProject/target/classes/application.properties - Конфигурационный файл.

src/main/java - Исходный код клиента.

src/main/java/dto - Объекты передачи данных.

# Тестирование
src/test/java/com/RestApiProject - Тесты для серверной части.

src/main/java/com/RestApiProject/controllersTest - Тесты для REST контроллеров.

src/main/java/com/RestApiProject/dtoTest - Тесты для объектов передачи данных (DTO).

src/main/java/com/RestApiProject/modelsTest - Тесты для моделей данных.

src/main/java/com/RestApiProject/repositoriesTest - Тесты для репозиториев.

src/main/java/com/RestApiProject/serviceTest - Тесты для сервисов.

src/main/java/com/RestApiProject/utilTest - Тесты для вспомогательных классов для обработки исключений и валидации.

src/test/java - Тесты для клиентской части.

# Конфигурация базы данных
Настройки подключения к базе данных находятся в файле src/main/java/com/RestApiProject/target/classes/application.properties. Убедитесь, что вы правильно настроили URL, имя пользователя и пароль для вашей PostgreSQL базы данных.
