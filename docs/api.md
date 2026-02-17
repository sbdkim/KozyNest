# Public API (Incremental)

This project now exposes incremental API-first endpoints in parallel with JSP pages.

## Endpoints
- `GET /api/health`
  - Returns service liveness metadata.
- `GET /api/accommodations?page=1&size=10&key=seoul`
  - Returns paged accommodation summaries.
- `GET /api/accommodations/{aseq}`
  - Returns one accommodation detail item.
- `GET /api/metrics`
  - Returns in-memory request metrics (uptime, request counts, error counts, latency stats).

## Error format
API endpoints return JSON error payloads:

```json
{
  "code": "INVALID_REQUEST",
  "message": "key length must be 100 characters or fewer",
  "path": "/api/accommodations"
}
```
