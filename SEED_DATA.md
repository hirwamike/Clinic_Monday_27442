# Seed data – enter through your save endpoints

Run the application first. Then use Postman in this order.

## 1. Specializations
POST `http://localhost:8080/api/specializations/save`

Create four records:
```json
{"name":"Cardiology"}
{"name":"Pediatrics"}
{"name":"Dermatology"}
{"name":"Neurology"}
```
Expected IDs: 1, 2, 3, 4. Nobody should be assigned to Neurology.

## 2. Offices
POST `http://localhost:8080/api/offices/save`

```json
{"name":"Main Cardiology Office","officeNumber":101}
{"name":"Pediatrics Office","officeNumber":102}
{"name":"Dermatology Office","officeNumber":103}
```
Expected IDs: 1, 2, 3.

## 3. Doctors
POST `http://localhost:8080/api/doctors/save`

Doctor 1 – two specializations and office 101:
```json
{
  "firstName":"Alice",
  "lastName":"Mukamana",
  "phone":"0788000001",
  "office":{"id":1},
  "specializations":[{"id":1},{"id":2}]
}
```

Doctor 2 – office 102:
```json
{
  "firstName":"Jean",
  "lastName":"Habimana",
  "phone":"0788000002",
  "office":{"id":2},
  "specializations":[{"id":3}]
}
```

Doctor 3 – no office:
```json
{
  "firstName":"Claude",
  "lastName":"Niyonsenga",
  "phone":"0788000003",
  "office":null,
  "specializations":[{"id":1}]
}
```

## 4. Patients
POST `http://localhost:8080/api/patients/save`

```json
{"firstName":"Aline","lastName":"Uwase","phone":"0788111111","email":"aline@example.com"}
{"firstName":"Beata","lastName":"Uwase","phone":"0788111112","email":"beata@example.com"}
{"firstName":"Charles","lastName":"Mugisha","phone":"0788111113","email":"charles@example.com"}
{"firstName":"Diane","lastName":"Iradukunda","phone":"0788111114","email":"diane@example.com"}
{"firstName":"Eric","lastName":"Nkurunziza","phone":"0788111115","email":"eric@example.com"}
```
Expected IDs: 1–5.

## 5. Appointments
POST `http://localhost:8080/api/appointments/save`

Use these one at a time. The nested `patient` and `doctor` objects only need their IDs.

1.
```json
{"appointmentDate":"2026-10-10","status":"SCHEDULED","notes":"Cardiology review","patient":{"id":1},"doctor":{"id":1}}
```
2. Same doctor/date but CANCELLED, proving the seed contains 2 appointments for one doctor on the same date without violating A4:
```json
{"appointmentDate":"2026-10-10","status":"CANCELLED","notes":"Cancelled follow-up","patient":{"id":2},"doctor":{"id":1}}
```
3.
```json
{"appointmentDate":"2026-10-12","status":"CONFIRMED","notes":"Pediatric consultation","patient":{"id":1},"doctor":{"id":1}}
```
4.
```json
{"appointmentDate":"2026-10-15","status":"COMPLETED","notes":"Dermatology check","patient":{"id":1},"doctor":{"id":2}}
```
5.
```json
{"appointmentDate":"2026-10-20","status":"SCHEDULED","notes":"Dermatology follow-up","patient":{"id":3},"doctor":{"id":2}}
```
6.
```json
{"appointmentDate":"2026-10-25","status":"CONFIRMED","notes":"Cardiology consultation","patient":{"id":1},"doctor":{"id":3}}
```
7.
```json
{"appointmentDate":"2026-11-02","status":"COMPLETED","notes":"Cardiology review","patient":{"id":4},"doctor":{"id":1}}
```
8.
```json
{"appointmentDate":"2026-11-08","status":"SCHEDULED","notes":"Dermatology consultation","patient":{"id":5},"doctor":{"id":2}}
```
9.
```json
{"appointmentDate":"2026-11-15","status":"CONFIRMED","notes":"Cardiology review","patient":{"id":1},"doctor":{"id":3}}
```
10.
```json
{"appointmentDate":"2026-11-20","status":"COMPLETED","notes":"Pediatric review","patient":{"id":2},"doctor":{"id":1}}
```

This gives:
- all four statuses;
- October and November dates;
- Aline Uwase has 5 appointments, so C2 with `min=3` returns her;
- Doctor 1 has two appointments on 2026-10-10, one SCHEDULED and one CANCELLED;
- Doctor 3 has no office;
- Neurology is unused;
- Doctors 1 and 3 hold Cardiology;
- Doctor 1 has the busiest office (101).

## Important A4 test
After seeding, try to save another active appointment for Doctor 1 on 2026-10-10:
```json
{"appointmentDate":"2026-10-10","status":"CONFIRMED","notes":"Should be rejected","patient":{"id":3},"doctor":{"id":1}}
```
Expected HTTP status: **409 CONFLICT**
Expected body: **Doctor is already booked on that date**

A CANCELLED appointment on that same date is allowed by the required derived query.
