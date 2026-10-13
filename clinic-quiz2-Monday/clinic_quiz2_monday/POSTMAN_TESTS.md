# Postman quick guide

Import `clinic-quiz2.postman_collection.json`. Set `baseUrl` to `http://localhost:8080` if needed.

## Run order

1. Run every request in **00 - Seed data (run these first, in order)** once against a fresh database.
2. Run **A1**, **A2**, **A3**, then **A4**.
3. Run **B1** (expected 400), then **B2 - Status flow (confirm)**, **B2 - Status flow (complete)**, and **B3**.
4. Run **C1** to save a prescription for the now-completed appointment. Then run **C2**; it saves the prescription UUID into `prescriptionId`. Run C3, then C4 update, and finally C4 delete.
5. Run D1–D4 and the bonus requests.
6. Error demo requests intentionally return errors.

## Important request examples

### Create a patient
```http
POST http://localhost:8080/api/patients/save
Content-Type: application/json
```
```json
{
  "firstName": "Martha",
  "lastName": "Uwase",
  "ssNumber": "SS-1001",
  "phone": "0781111001",
  "email": "martha@example.com"
}
```

### Create an appointment
```http
POST http://localhost:8080/api/appointments/save
Content-Type: application/json
```
```json
{
  "appointmentDate": "2026-10-20",
  "status": "COMPLETED",
  "notes": "Consultation",
  "patient": { "id": 1 },
  "doctor": { "id": 1 }
}
```
The server intentionally overrides the submitted status to `SCHEDULED`. Use `PATCH /api/appointments/{id}/status?status=CONFIRMED`, then `PATCH /api/appointments/{id}/status?status=COMPLETED` to finish the status flow.

### Save a prescription
```http
POST http://localhost:8080/api/prescriptions/save?appointmentId=1
Content-Type: application/json
```
```json
{
  "medicineName": "Amoxicillin",
  "dosage": "1 tablet, 3 times a day",
  "durationDays": 7
}
```
Expected response: HTTP 201, `Prescription saved successfully`. Use `GET /api/prescriptions/appointment/1` to retrieve the generated UUID before updating or deleting it.
