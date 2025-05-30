# Quarkus vs Spring Boot Performance Benchmark

This project compares the performance of a **Quarkus Native** application versus a **Spring Boot JVM** application using a CPU-intensive benchmark: computing the _n-th prime number_.

## 🔧 Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven (or use Maven Wrapper)
- At least 4 GB of RAM available for native build

---

## 🚀 Getting Started

### 1. 🔄 Stop Any Previous Containers (optional)

```bash
docker-compose down --remove-orphans
```

---

### 2. ⚙️ Build the Quarkus Native Application

```bash
cd quarkus-graalvm-vs-jvm

# Generate Maven Wrapper if missing
mvn -N io.takari:maven:wrapper

# Build the native executable using GraalVM with Docker
./mvnw clean package -Pnative \
  -Dquarkus.native.container-build=true \
  -Dquarkus.native.native-image-xmx=4g
```

---

### 3. 📦 Start All Services

From the **root folder**:

```bash
docker-compose up --build --remove-orphans
```

This will:
- Start **PostgreSQL**
- Start the **Quarkus Native** app (port `8080`)
- Start the **Quarkus JVM** app (port `8081`)
- Start the **Spring Boot** app (port `8083`)

---

## 📡 Example Endpoint

Test the `/prime` endpoint:

```bash
curl "http://localhost:8080/prime?n=10000"    # Quarkus Native
curl "http://localhost:8081/prime?n=10000"    # Quarkus JVM
curl "http://localhost:8083/prime?n=10000"    # Spring Boot JVM
```

---

## 📊 Benchmark Idea

Each request returns:

```json
{
  "nthPrime": 104729,
  "position": 10000,
  "durationInMillis": 145
}
```

Use the duration to compare response speed across runtimes.

---

## 📁 Folder Structure

```plaintext
quarkus-vs-springboot/
├── docker-compose.yml
├── README.md
├── quarkus-graalvm-vs-jvm/     # Quarkus project (native & JVM)
├── springboot-jvm/             # Spring Boot JVM project
```

---

## 🧹 Clean Up

```bash
docker-compose down --remove-orphans
```

---

Enjoy benchmarking! 💻⚡

