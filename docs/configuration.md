# Configuration and Secrets

## Profiles
- Default profile is `dev` (`spring.profiles.default` in `web.xml`).
- Set `SPRING_PROFILES_ACTIVE` to switch profile at runtime.

## Property resolution order
`applicationContext.xml` loads DB settings in this order:
1. `classpath:config/database.properties`
2. `classpath:config/database-{profile}.properties`
3. `${user.home}/.kozynest/database-{profile}.properties` (optional machine-local override)

Later sources override earlier ones.

## Local development
1. Copy `.env.example` values into your shell/session environment.
2. Optionally create `${user.home}/.kozynest/database-dev.properties` from `src/main/resources/config/database.properties.example`.
3. Keep all real credentials out of git.

## Production
- Run with `SPRING_PROFILES_ACTIVE=prod`.
- Provide `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` through environment variables or deployment secret manager.
