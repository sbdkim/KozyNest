# KozyNest LodgingService

KozyNest is a Spring MVC-based lodging reservation platform where:
- Hosts register accommodations and rooms.
- Guests search accommodations and make bookings.
- Admins manage users, host approvals, inquiries, and revenue views.

## Project Timeline
- Initial project build: **March 27, 2023 - April 12, 2023**
- Enhancement program (current modernization branch): **February 17, 2026**

## Original Project Scope
- Guest and host account flows (signup/login)
- Accommodation and room listing
- Booking and booking management
- My page (guest/host account pages)
- Q&A and review features
- Admin pages for member/host management and revenue dashboard
- Oracle DB + MyBatis persistence layer

## Tech Stack
- Java 11
- Spring MVC / Spring Context / Spring JDBC
- MyBatis
- Oracle Database
- JSP / JSTL / HTML / CSS / JavaScript / jQuery
- Apache Tomcat 9
- Maven

## Run Requirements
- JDK 11+
- Maven 3.8.6+
- Oracle Database (with schema/data import from `SQL_Kozynest`)
- Apache Tomcat 9

## Local Setup (Summary)
1. Clone repository.
2. Prepare Oracle schema/data using files under `SQL_Kozynest`.
3. Configure DB settings (see `docs/configuration.md`).
4. Build and run on Tomcat.
5. Open app at `http://localhost:8080/biz`.

## Configuration and Secrets
This project supports profile-based config and external secret overrides.

- Default profile: `dev`
- DB property resolution order:
1. `src/main/resources/config/database.properties`
2. `src/main/resources/config/database-{profile}.properties`
3. `${user.home}/.kozynest/database-{profile}.properties` (optional local override)

Use:
- `.env.example`
- `src/main/resources/config/database.properties.example`
- `docs/configuration.md`

## API-First Additions
Incremental REST endpoints are now available:
- `GET /api/health`
- `GET /api/accommodations?page=1&size=10&key=...`
- `GET /api/accommodations/{aseq}`
- `GET /api/metrics`

Reference: `docs/api.md`

## Security and Quality Enhancements (Feb 17, 2026)
The following enhancement commits were completed during the latest upgrade cycle:

1. `67201ca` Add request correlation logging and structured Log4j2 config  
2. `2411e39` Add environment config templates and secret handling docs  
3. `27650e7` Introduce initial /api endpoints and JSON API error handling  
4. `3253fb9` Add unit tests for new public API controller contracts  
5. `898a688` Harden accommodation search inputs and add controller unit tests  
6. `ccd14df` Add runtime request metrics registry and /api/metrics endpoint  
7. `07e95a4` Standardize safe request param parsing across admin qna and room flows  
8. `1982005` Harden login forms by removing default creds and adding safe autofill hints  
9. `c2ffc07` Return proper HTTP status codes in MVC error handler with request id  
10. `9bee740` Add CI dependency vulnerability audit with OWASP profile  

Additional foundational enhancement commits in this modernization program:
- `67e84f6` Add environment profiles and externalized DB config
- `702f919` Add global exception handling and unified error page
- `d15e90d` Harden input validation for auth and signup flows
- `4a15076` Standardize service boundaries and transaction scope
- `ca3385b` Fix DAO mapper correctness and safe SQL bindings
- `ba9e700` Refresh shared UI shell and homepage layout
- `8d3c07a` Improve accessibility semantics and keyboard focus UX
- `34c446f` Add CI workflow and baseline unit tests
- `63786d7` Harden auth with BCrypt, reset tokens, and lockout
- `5e61871` Harden file uploads with validation and external storage

## CI and Security Audit
- CI workflow: `.github/workflows/ci.yml`
- Dependency vulnerability scanning is enforced with OWASP Dependency-Check profile:
  - Run locally: `mvn -Psecurity-audit verify`
  - Details: `docs/security-audit.md`

## Testing
- Unit tests are available under `src/test/java`.
- Utility tests and controller-focused tests were added as part of the enhancement cycle.

## Notes
- This repository contains legacy JSP + Spring MVC architecture with incremental modernization.
- Existing web flows remain functional while API-first endpoints are being introduced in parallel.
