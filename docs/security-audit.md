# Dependency Security Audit

This project provides an opt-in Maven profile to run OWASP Dependency-Check.

## Run locally

```bash
mvn -Psecurity-audit verify
```

## CI behavior

The CI workflow runs with `-Psecurity-audit` and uploads:
- `target/dependency-check-report.html`
- `target/dependency-check-report.json`

Build fails when a dependency vulnerability has CVSS score `>= 7.0`.
