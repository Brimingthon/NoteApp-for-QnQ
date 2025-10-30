# 📝 Notes API
Java 17, Spring Boot 3, MongoDB, Maven, Docker
---

````markdown
# 🧭 Run Instructions

This project is a **Spring Boot + MongoDB Notes API**.  
Below are steps to run and test the application.

---

## 🐳 Run with Docker

### 1. Build and start the application
```bash
docker compose up --build
````

This will:

* Start **MongoDB** on port **27017**
* Build and run **Spring Boot app** on port **8080**

### 2. Test API

Example request:

```bash
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title": "Hello", "text": "note is just a note", "tags": ["BUSINESS"]}'
```

---

## Run locally (without Docker)

### 1. Prerequisites

* Java 21+
* Maven 3.9+
* MongoDB running locally on `mongodb://localhost:27017`

### 2. Set up Mongo

```bash
docker run -d --name mongo -p 27017:27017 mongo
```

(or use your local MongoDB installation)

### 3. Run the application

```bash
mvn spring-boot:run
```

### 4. Verify it works

Open browser or use curl:

```bash
curl http://localhost:8080/api/notes
```

---

## Running Tests

```bash
mvn test
```

---

## Main Endpoints

| Method   | Path                                    | Description                      |
| -------- | --------------------------------------- | -------------------------------- |
| `POST`   | `/api/notes`                            | Create note                      |
| `GET`    | `/api/notes?page=0&size=5&tag=BUSINESS` | List notes (filter + pagination) |
| `GET`    | `/api/notes/{id}`                       | Get note details                 |
| `GET`    | `/api/notes/{id}/stats`                 | Word statistics                  |
| `PUT`    | `/api/notes/{id}`                       | Update note                      |
| `DELETE` | `/api/notes/{id}`                       | Delete note                      |

---

##  Environment Variables

You can override Mongo URI if needed:

```
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/notesdb
```

Example:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.uri=mongodb://localhost:27017/notesdb"
```

---

##  Expected behavior

* Notes must have **title** and **text**.
* Tags allowed: `BUSINESS`, `PERSONAL`, `IMPORTANT`.
* Notes are **sorted by newest first**.
* Listing returns only **title** and **createdDate**.
* `/stats` endpoint returns word frequency map, sorted by count (descending).

---

## 🧾 Example output

POST:

```json
{
  "id": "64f8a1b7e7c12345a9b8de91",
  "title": "Hello",
  "text": "note is just a note",
  "tags": ["BUSINESS"],
  "createdDate": "2025-10-29T14:35:43.614"
}
```

GET /stats:

```json
{
  "note": 2,
  "is": 1,
  "just": 1,
  "a": 1
}
```
