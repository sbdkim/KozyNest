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

## Architecture Snapshot
- **Presentation layer**: Spring MVC controllers + JSP views for guest/host/admin experiences.
- **Business layer**: Service interfaces and implementations for booking, accommodation, room, member, host, review, and Q&A domains.
- **Persistence layer**: MyBatis mappers/DAO classes backed by Oracle.
- **Operational support**: Global exception handling, request logging filter, and metrics endpoint.

## Functional Sitemap (Text Version)
- **Public/Guest**
  - Home, location-based search, accommodation list, room list/detail
  - Guest signup/login, account recovery/reset
  - Booking flow and guest mypage
- **Host**
  - Host signup/login
  - Accommodation and room management
  - Booking status and host mypage
- **Admin**
  - Admin login
  - Host approval and user management
  - Q&A moderation
  - Revenue reporting

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
Enhancements were delivered as a focused modernization series across:

- **Dependency and vulnerability posture**
  - Updated vulnerable dependencies and added automated OWASP dependency scanning in CI.
- **Authentication and account security**
  - BCrypt-based password handling, safer reset-token flow, and login hardening.
- **Configuration and secret management**
  - Profile-based configuration, environment variable support, and machine-local secret override patterns.
- **Input and request hardening**
  - Validation improvements and standardized safe parsing/sanitization for paging and query inputs.
- **File upload security**
  - Size limits, content checks, extension/MIME validation, and safer upload handling.
- **Error handling and observability**
  - Unified error handling with proper HTTP status codes, request correlation IDs, structured Log4j2 logging, and runtime request metrics.
- **Architecture evolution**
  - Introduced initial `/api` endpoints while preserving existing JSP flows.
- **Quality engineering**
  - Expanded test coverage for core controller and API behaviors.
- **UX and accessibility improvements**
  - UI consistency updates and safer login form defaults/attributes.

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
