# Kigali Clinic — Quiz 1 + Quiz 2 backend

Spring Boot 3.5 / Java 21 REST backend, organized into the requested **Controller → Service → Repository → Domain** architecture.

## Project layout

```text
src/main/java/kigali/clinic/rw/
├── ClinicQuizApplication.java
├── controller/      # HTTP routes, request parameters, HTTP status mapping
├── service/         # business rules and orchestration
├── repository/      # Spring Data JPA derived methods and JPQL
└── domain/          # JPA entities and AppointmentStatus enum
```

`Prescription` is the Quiz 2 entity. Its ID is a UUID, and it belongs to one appointment. An appointment is not given a back-reference list of prescriptions, avoiding recursive JSON serialization.

## Run

Requirements: JDK 21 and Maven 3.9+.

```bash
mvn spring-boot:run
```

The default database is file-based H2 at `./data/clinicdb`; it persists between restarts. The first launch creates/updates tables automatically. API base URL: `http://localhost:8080`. H2 console: `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:file:./data/clinicdb`, user `sa`, blank password).

## Postman

Import `clinic-quiz2.postman_collection.json` in Postman. It includes a **00 - Seed data** folder and named requests for each Quiz 2 requirement plus both bonus requirements and error demos.

1. Start the app.
2. Run the seed requests in order, once against an empty database. The requests save generated IDs into collection variables.
3. Run the requirement requests in order. B2 has two requests: confirm then complete. Prescription C1 requires a completed appointment.
4. C2 stores a returned prescription UUID into `prescriptionId` when the appointment has at least one prescription. Run C2 before the C4 update/delete requests. Run C4 update and delete separately because deletion removes that record.
5. Run A3 before A4 if you want to see office 104 as free before moving a doctor into it.

The seed folder creates offices 101–104, four specializations, three doctors, five patients and ten future appointments. It starts all new appointments as SCHEDULED by design; use the status endpoint to mark a consultation COMPLETED before creating its prescription.

## Quiz 2 endpoints implemented

- **A1–A2:** add/remove a doctor's specialization
- **A3–A4:** free offices; move doctor to an office
- **B1–B3:** safe booking, status transition rules, rescheduling
- **C1–C4:** prescription save/list/history/update/delete and appointment deletion protection
- **D1–D4:** doctor agenda, patient upcoming appointments, doctor name search, dashboard summary
- **Bonus:** top prescribed medicines and completed appointment workload by doctor

Existing Quiz 1 CRUD and query endpoints remain in place. Request/response bodies use entities directly, as required by the quiz; no DTOs or try/catch blocks are used.

## Notes

- Patient now has a unique `ssNumber` field required by Quiz 2. Include it when creating patients.
- The exact messages and status codes for Quiz 2 business-rule endpoints are mapped in their controllers.
- Keep a copy of your own database and make your own Git commits while completing and demonstrating each quiz part.
