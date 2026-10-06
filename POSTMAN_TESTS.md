# Postman demo order

1. Start the application.
2. Run all requests in `SEED_DATA.md`.
3. Demonstrate A1–A4.
4. Demonstrate B1–B4.
5. Demonstrate C1–C4.
6. Demonstrate both bonus endpoints.

Suggested URLs:

| ID | Method | URL |
|---|---|---|
| A1 | GET | http://localhost:8080/api/patients/by-last-name?lastName=uwase |
| A2 | GET | http://localhost:8080/api/appointments/by-status?status=SCHEDULED |
| A3 | GET | http://localhost:8080/api/appointments/between?start=2026-10-01&end=2026-10-31 |
| A4 | POST | http://localhost:8080/api/appointments/save |
| B1 | GET | http://localhost:8080/api/doctors/by-specialization?name=cardiology |
| B2 | GET | http://localhost:8080/api/doctors/without-office |
| B3 | GET | http://localhost:8080/api/specializations/unused |
| B4 | GET | http://localhost:8080/api/patients/of-doctor/1 |
| C1 | GET | http://localhost:8080/api/appointments/stats/by-status |
| C2 | GET | http://localhost:8080/api/patients/frequent?min=3 |
| C3 | GET | http://localhost:8080/api/offices/busiest |
| C4 | PATCH | http://localhost:8080/api/appointments/cancel-day?doctorId=1&date=2026-10-10 |
| Bonus 1 | GET | http://localhost:8080/api/appointments/page?page=0&size=5&sort=appointmentDate,desc |
| Bonus 2 | DELETE | http://localhost:8080/api/appointments/cancelled-before?date=2026-10-15 |
