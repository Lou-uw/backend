# Backend — Proyecto de testing (Spring Boot + Kotlin)

El proyecto Maven vive en `testing/`. Esta guía explica cómo configurar la base de datos
y cambiar de perfil.

## Perfiles

El perfil activo se define en `testing/src/main/resources/application.properties`:

```properties
spring.profiles.active=${SPRING_PROFILES_ACTIVE:lab}
```

- `local`: base MariaDB/MySQL en tu máquina (`127.0.0.1:3306`).
- `lab`: base del laboratorio.

### Cambiar de perfil

Se puede hacer de dos maneras:

1. **Variable de entorno** (recomendado para la terminal):

   ```powershell
   $env:SPRING_PROFILES_ACTIVE = "local"
   .\mvnw.cmd spring-boot:run
   ```

2. **Run Configuration de IntelliJ IDEA**:
   - `Run` → `Edit Configurations…` → seleccioná la configuración de la aplicación.
   - En **Environment variables** agregá `SPRING_PROFILES_ACTIVE=local` (o `lab`).

Si no se define la variable, se usa el perfil `lab`.

## Credenciales

El perfil `lab` trae las credenciales del docente directamente en
`testing/src/main/resources/application-lab.properties`, así que no hay que configurar
nada para levantar en la escuela.

Si en algún momento preferís no tenerlas en el repositorio, se pueden reemplazar por
variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`) y definirlas en la terminal o en
la Run Configuration de IntelliJ.

### Perfil `lab`

- URL, usuario y contraseña: en `application-lab.properties`.

### Perfil `local`

`application-local.properties` ya trae valores por defecto para una base local:

- URL: `jdbc:mariadb://127.0.0.1:3306/dcabanas`
- Usuario: `root`
- Contraseña: `dc123456`

Se pueden pisar con las mismas variables `DB_URL`, `DB_USER` y `DB_PASSWORD`, por ejemplo
si tu MariaDB local usa otro puerto, base o usuario.

## Tests

Los tests usan H2 en memoria (`testing/src/test/resources/application.properties`), así que
no necesitan una base de datos real:

```powershell
cd testing
.\mvnw.cmd test
```

## Migraciones

Las migraciones SQL están en `testing/src/main/resources/db/migration` (Flyway está
deshabilitado con `spring.flyway.enabled=false`).
