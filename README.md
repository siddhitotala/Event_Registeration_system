# Cloud-Based Event Registration System — Backend

Spring Boot REST API for EC2 + ALB + Auto Scaling Group + RDS MySQL.

## Requirements
- JDK 21
- Maven 3.9+ (or Maven support from your IDE)
- MySQL locally for development, then Amazon RDS MySQL on AWS

## Run locally
1. Create database: `CREATE DATABASE eventdb;`
2. Set environment variables:
   - `DB_URL=jdbc:mysql://localhost:3306/eventdb`
   - `DB_USERNAME=your_local_user`
   - `DB_PASSWORD=your_local_password`
3. Run `mvn spring-boot:run`
4. Health check: `http://localhost:8080/actuator/health`

Never commit real DB credentials to GitHub. On AWS, set `DB_URL` to the RDS endpoint URL.

## API endpoints
### Events
- `GET /api/events` — list events
- `GET /api/events/{id}` — event details
- `POST /api/events` — create event (admin endpoint; no authentication in this starter)
- `PUT /api/events/{id}` — update event
- `PATCH /api/events/{id}/registration?open=false` — close registration
- `DELETE /api/events/{id}` — delete event if no registrations exist

Example event JSON:
```json
{
  "name": "Cloud Tech Day",
  "description": "A student technology event",
  "eventDate": "2026-11-10",
  "eventTime": "10:00:00",
  "venue": "Seminar Hall",
  "capacity": 100,
  "registrationOpen": true
}
```

### Registrations
- `POST /api/events/{eventId}/registrations` — register
- `GET /api/events/{eventId}/registrations` — list participants (admin endpoint; no authentication in this starter)

Example registration JSON:
```json
{
  "studentName": "Aarav Sharma",
  "email": "aarav@example.com",
  "collegeId": "CSE2026-01"
}
```

## Before public deployment
- Educational starter only: admin endpoints are not protected. Add Spring Security before exposing admin operations publicly.
- EC2 security group should allow app traffic only from the ALB security group.
- RDS security group should allow TCP 3306 only from the EC2 security group.
- Keep RDS private.
- Configure ALB target-group health check path as `/actuator/health`.
- Auto Scaling instances must all use the same RDS database and be reproducibly configured from an AMI or Launch Template.
- Capacity checks are suitable for a class demo but strict production capacity guarantees require atomic allocation/locking under concurrent requests.
