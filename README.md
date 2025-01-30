# Manulife Test

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0.0-blue?style=for-the-badge&logo=apachemaven)
![JasperReports](https://img.shields.io/badge/JasperReports-7.0.1-orange?style=for-the-badge)

## Project Description

This project is a Spring Boot application that uses JasperReports for generating reports. It is built with Maven and uses Java 17.

## Versions

- **Spring Boot:** 3.4.2
- **Java:** 17
- **JasperReports:** 7.0.1
- **Project Version:** 0.0.1-SNAPSHOT

## Dependencies

This project uses the following key dependencies:

- **Spring Boot Starter**
- **Spring Boot DevTools** (for development)
- **Lombok** (to reduce boilerplate code)
- **JasperReports** (for report generation)

## How to Run the Project

1. **Clone this repository:**

   ```bash
   git clone https://github.com/dvlwj/manulife_test.git
   cd manulife_test
   ```
2. **Build the project using Maven:**

   ```bash
   mvn clean install
   ```
3. **Run the project:**

   ```bash
   mvn spring-boot:run
   ```
The application will automatically generate report files in the `output` directory.

## Project Structure

```
manulife_test/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/davidvalentino/
│       │       └── manulife_test/
│       │           ├── controller/
│       │           ├── service/
│       │           └── ManulifeTestApplication.java
│       └── resources/
│           ├── templates/
│           └── application.properties
├── pom.xml
└── README.md
```

