# 📝 Notes API

Простий REST API для керування нотатками на **Spring Boot + MongoDB**.

## 🚀 Технології
Java 17, Spring Boot 3, MongoDB, Maven, Docker

## ⚙️ Запуск

docker run -d --name mongo -p 27017:27017 mongo

mvn spring-boot:run

**Налаштування:**
spring.data.mongodb.uri=mongodb://localhost:27017/notesdb - локально

spring.data.mongodb.uri=mongodb://mongo:27017/notesdb - через Docker

**Основні ендпоінти**
POST	/api/notes	Створити нотатку

GET	/api/notes?page=0&size=5&tag=BUSINESS	Отримати нотатки з пагінацією та фільтрацією

GET	/api/notes/stats	Статистика слів

PUT	/api/notes/{id}	Оновити нотатку

DELETE	/api/notes/{id}	Видалити нотатку


**Приклад**
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title": "Hello", "text": "note is just a note", "tags": ["BUSINESS"]}'
