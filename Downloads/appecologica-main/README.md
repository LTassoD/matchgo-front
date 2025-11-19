# App Ecológica – Arquitectura Fullstack

Este repositorio transforma la antigua app móvil en una solución fullstack moderna compuesta por un backend Spring Boot + API REST segura con JWT y un frontend React + Vite con Bootstrap y pruebas en Vitest.

## Estructura general

```
backend/   → API REST con Spring Boot, JPA, JWT y Swagger
frontend/  → SPA en React + Vite + Bootstrap + React Router + Vitest
app/       → Código móvil original (referencia de dominio)
```

### Backend (`backend/`)

Paquetes principales:

- `controller` – Endpoints REST (`/api/auth`, `/api/clientes`, `/api/materiales`, `/api/vehiculos`, `/api/rutas`, `/api/ordenes`, `/api/usuarios`, `/api/historial`, `/api/puntos`).
- `service` – Reglas de negocio, validaciones y composición de repositorios.
- `repository` – Interfaces JPA (`JpaRepository`) para usuarios, clientes, materiales, rutas, etc.
- `model` – Entidades JPA (Usuario, Cliente, Ruta, OrdenServicio, Material, Vehículo…).
- `security` – Configuración JWT (filters, `JwtService`, `SecurityConfig`, `CustomUserDetailsService`).
- `config` – `DataSeeder` para datos demo y configuración de Swagger/OpenAPI automática.

### Frontend (`frontend/`)

Carpetas destacadas:

- `src/components` – Componentes reutilizables (Navbar, tablas, formularios, rutas protegidas).
- `src/pages` – Pantallas principales (Login, Registro, Inicio, Productos, Pedidos, Clientes, Vehículos, Perfil).
- `src/routes` – Configuración de React Router con rutas protegidas y guardas por rol.
- `src/services` – Cliente Axios con inyección automática del token y llamadas a la API REST.
- `src/context` & `src/hooks` – `AuthContext` y `useAuth` con persistencia de sesión en `localStorage`.
- `src/styles` – CSS global + módulos por vista.
- `src/__tests__` – Pruebas con Vitest + React Testing Library + helpers de autorización.

## Levantar el backend

Requisitos: Java 17 y Maven.

```bash
cd backend
mvn spring-boot:run
```

El backend expone `http://localhost:8080/api` y usa H2 en memoria (`jdbc:h2:mem:appecologica`). Swagger UI queda en `http://localhost:8080/swagger-ui.html`.

Variables importantes (`backend/src/main/resources/application.properties`):

- `app.jwt.secret` y `app.jwt.expiration-minutes` – Configuración del token.
- `spring.datasource.*` – Fuente de datos (se puede apuntar a PostgreSQL/MySQL cambiando la URL, driver y credenciales).

Usuarios demo creados automáticamente:

- `admin@appecologica.com` / `admin123` – Rol ADMIN.
- `chofer@appecologica.com` / `chofer123` – Rol CHOFER.

## Levantar el frontend

Requisitos: Node 18+.

```bash
cd frontend
npm install
npm run dev
```

La aplicación queda en `http://localhost:5173`. Configura la URL base del backend creando un archivo `.env` en `frontend/` con:

```
VITE_API_URL=http://localhost:8080/api
```

## Pruebas de frontend

```bash
npm test
```

Ejecuta Vitest en modo watch (pruebas para formularios, páginas con API mockeada y utilidades de roles).

## Flujo de autenticación y roles

1. **Login/Registro** (`/api/auth/login`, `/api/auth/register`):
   - El backend valida credenciales, genera un JWT firmado y responde con `{ token, nombre, role }`.
   - El frontend almacena el token en `localStorage` (`app-token`) y mantiene la sesión con `AuthContext`.
2. **Sesión**:
   - `AuthContext` expone `login`, `register`, `logout`, `isAuthenticated`, `hasRole`, `user`.
   - Al recargar la página, el contexto toma el token guardado, consulta `/api/usuarios/me` y restaura el usuario.
   - Axios (`src/services/apiClient.js`) agrega `Authorization: Bearer <token>` automáticamente.
3. **Protección de rutas**:
   - `ProtectedRoute` bloquea cualquier ruta privada si no hay token.
   - `RoleGuard` restringe secciones sensibles (ej. administración de clientes, vehículos) a `ADMIN`.
   - `NavigationBar` y componentes usan `hasRole` para mostrar/ocultar acciones como “Nuevo material” o botones para finalizar órdenes.

## Flujo de datos Frontend ↔ Backend

1. El usuario inicia sesión o se registra → obtiene token → `AuthContext` lo guarda y React Router habilita rutas privadas.
2. Las páginas consumen servicios (`src/services`) que llaman a los controladores REST:
   - `LoginPage` → `POST /api/auth/login`.
   - `ProductosPage` → `GET /api/materiales`, `POST /api/materiales` (solo ADMIN).
   - `PedidosPage` → `GET /api/rutas`, `GET /api/ordenes/ruta/{id}`, `PUT /api/ordenes/{id}`.
   - `ClientesPage` → `GET/POST /api/clientes`.
   - `HomePage` → `GET /api/rutas`.
3. El backend aplica filtros JWT (`JwtAuthenticationFilter`), valida roles con `@PreAuthorize` y accede a la capa de servicio/repositorio.
4. Los cambios se persisten mediante JPA; Swagger documenta todos los endpoints para facilitar pruebas manuales.

## Próximos pasos sugeridos

- Reemplazar H2 por PostgreSQL/MySQL en producción (ajustando `application.properties`).
- Añadir CI/CD que ejecute `mvn test` y `npm test`.
- Integrar subida de evidencias/fotos en las órdenes de servicio.

Con esta estructura puedes enseñar arquitectura fullstack moderna manteniendo el dominio original de la app de gestión de residuos. Cada capa está fuertemente comentada y separada para que estudiantes de 3er semestre sigan el flujo completo desde React hasta Spring Boot.  
