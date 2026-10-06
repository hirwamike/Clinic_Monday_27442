# Clinic Quiz – Spring Boot + Spring Data JPA

This project implements the clinic CRUD foundation plus all 12 required quiz features and both bonus features.

## Requirements
- Java 21+
- Maven 3.9+
- VS Code / IntelliJ / Eclipse

The default configuration uses an H2 file database, so the project can run without MySQL. If your lecturer requires MySQL, replace the datasource properties in `src/main/resources/application.properties`.

## Run
```bash
mvn spring-boot:run
```

Base URL: `http://localhost:8080`

H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/clinicdb`
- User: `sa`
- Password: blank

## CRUD save endpoints
- POST `/api/specializations/save`
- POST `/api/offices/save`
- POST `/api/doctors/save`
- POST `/api/patients/save`
- POST `/api/appointments/save`

Other CRUD endpoints are also provided with GET, PUT `/update`, and DELETE by id.

## Quiz endpoints
### Part A – derived
- A1 GET `/api/patients/by-last-name?lastName=uwase`
- A2 GET `/api/appointments/by-status?status=SCHEDULED`
- A3 GET `/api/appointments/between?start=2026-10-01&end=2026-10-31`
- A4 POST `/api/appointments/save`

### Part B – JPQL
- B1 GET `/api/doctors/by-specialization?name=cardiology`
- B2 GET `/api/doctors/without-office`
- B3 GET `/api/specializations/unused`
- B4 GET `/api/patients/of-doctor/1`

### Part C – JPQL aggregates / bulk updates
- C1 GET `/api/appointments/stats/by-status`
- C2 GET `/api/patients/frequent?min=3`
- C3 GET `/api/offices/busiest`
- C4 PATCH `/api/appointments/cancel-day?doctorId=1&date=2026-10-10`

### Bonus
- GET `/api/appointments/page?page=0&size=5&sort=appointmentDate,desc`
- DELETE `/api/appointments/cancelled-before?date=2026-10-15`

## Seed data
The quiz specifically says to enter seed data through your own save endpoints. Use `SEED_DATA.md` and create the records in the listed order. Do not use an automatic `CommandLineRunner` seed so your work demonstrates the endpoints.

## Query-type checklist
- A1: derived `findByLastNameIgnoreCaseOrderByFirstNameAsc`
- A2: derived `findByStatusOrderByAppointmentDateAsc`
- A3: derived `findByAppointmentDateBetweenOrderByAppointmentDateAsc`
- A4: derived `existsByDoctorIdAndAppointmentDateAndStatusNot`
- B1: JPQL JOIN + LOWER
- B2: JPQL IS NULL
- B3: JPQL IS EMPTY
- B4: JPQL DISTINCT + path navigation
- C1: JPQL GROUP BY + COUNT
- C2: JPQL GROUP BY + HAVING + ORDER BY COUNT
- C3: JPQL 3-way JOIN + ORDER BY COUNT DESC
- C4: JPQL UPDATE + `@Modifying` + `@Transactional`
- Bonus delete: JPQL DELETE + `@Modifying`
