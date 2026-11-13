[README.md](https://github.com/user-attachments/files/34797180/README.md)
# MyDB — gestiunea persoanelor dintr-o bază de date MySQL

Aplicație web construită cu **Spring Boot 3** și **Thymeleaf** care permite autentificarea utilizatorilor și operații CRUD (inserare, modificare, ștergere, căutare) asupra tabelei `Persoane` dintr-o bază de date MySQL. Proiect realizat în cadrul practicii.

## Funcționalități

- **Înregistrare și autentificare** — conturile sunt salvate în tabela `user_inregistrat`, cu parola criptată cu BCrypt.
- **Vizualizare** — afișarea tuturor persoanelor într-un tabel (randat server-side cu Thymeleaf).
- **Inserare / Modificare / Ștergere** — formulare care trimit cereri către API-ul REST.
- **Căutare cu filtre** — după ID, nume, prenume (căutare parțială, `LIKE`), an naștere, ID ocupație, ID oraș domiciliu; tabelul se actualizează fără reîncărcarea paginii.

## Tehnologii

| Componentă | Versiune / detalii |
|---|---|
| Java | 17 |
| Spring Boot | 3.3.2 (`web`, `thymeleaf`, `devtools`, `data-jpa`) |
| Spring Security Crypto | 5.8.0 (doar `BCryptPasswordEncoder`) |
| Bază de date | MySQL 8 (`mysql-connector-j`) |
| Acces la date | JDBC simplu (`DriverManager` + `PreparedStatement`) |
| Frontend | HTML + CSS + JavaScript vanilla (`fetch`) |
| Build | Maven (wrapper inclus: `mvnw` / `mvnw.cmd`) |

## Structura proiectului

```
ProiectPractica-main/
├── .idea/                          # configurație IntelliJ IDEA
└── DataBConn/                      # aplicația Spring Boot
    ├── pom.xml
    ├── mvnw, mvnw.cmd
    └── src/
        ├── main/
        │   ├── java/com/example/databconn/
        │   │   ├── DataBConnApplication.java     # punctul de intrare
        │   │   ├── config/
        │   │   │   ├── AppConfig.java            # bean BCryptPasswordEncoder
        │   │   │   └── DatabaseConfig.java       # conexiune JDBC la MySQL
        │   │   ├── controller/
        │   │   │   ├── Controller.java           # pagini HTML (/home, /users-view)
        │   │   │   └── UserController.java       # API REST (/api/...)
        │   │   ├── model/
        │   │   │   ├── User.java                 # o persoană din tabela Persoane
        │   │   │   └── UserLogin.java            # un cont din user_inregistrat
        │   │   ├── service/
        │   │   │   ├── UserService.java          # logica pentru persoane
        │   │   │   └── UserLoginService.java     # înregistrare / autentificare
        │   │   └── UserRepo/
        │   │       ├── UserRepository.java       # SQL pentru Persoane
        │   │       └── UserLoginRepository.java  # SQL pentru user_inregistrat
        │   └── resources/
        │       ├── application.properties
        │       ├── templates/
        │       │   ├── login.html                # pagina de login / register
        │       │   ├── index.html                # tabelul + formularele CRUD
        │       │   └── old/index.html            # versiune veche (nefolosită)
        │       └── static/
        │           ├── Main.js                   # logica din frontend
        │           ├── loginStyle.css, Style2.css
        │           ├── image.jpg, image2.jpg
        │           └── old/Style.css             # stil vechi (nefolosit)
        └── test/java/.../DataBConnApplicationTests.java
```

Arhitectura este pe straturi: **Controller → Service → Repository → MySQL (JDBC)**.

## Baza de date

Aplicația se conectează la baza `bazadate` pe `localhost:3306`. Repository-ul nu conține un script de creare a tabelelor; structura de mai jos e dedusă din interogările SQL din cod:

```sql
CREATE DATABASE IF NOT EXISTS bazadate;
USE bazadate;

CREATE TABLE Persoane (
    ID_Pers            INT AUTO_INCREMENT PRIMARY KEY,
    Nume               VARCHAR(100) NOT NULL,
    Prenume            VARCHAR(100) NOT NULL,
    An_nastere         INT,
    ID_Ocupatie        INT,
    ID_Oras_Domiciliu  INT
);

CREATE TABLE user_inregistrat (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,   -- hash BCrypt (60 caractere)
    status    VARCHAR(20)  DEFAULT 'user',
    email     VARCHAR(255)
);
```

> `ID_Ocupatie` și `ID_Oras_Domiciliu` sunt, cel mai probabil, chei străine spre alte tabele (ocupații, orașe) din baza originală, care nu sunt referite în cod.

## Configurare și rulare

### Cerințe
- JDK 17+
- MySQL 8 pornit local

### Pași

1. Creează baza de date și tabelele (scriptul de mai sus).
2. Setează datele de conectare. Atenție: ele apar în **două** locuri, ambele trebuie modificate:
   - `DataBConn/src/main/resources/application.properties` (`spring.datasource.*`)
   - `DataBConn/src/main/java/com/example/databconn/config/DatabaseConfig.java` (constantele `URL`, `USERNAME`, `PASSWORD`) — **acesta este cel folosit efectiv** de repository-uri.
3. Pornește aplicația:

   ```bash
   cd DataBConn
   ./mvnw spring-boot:run
   ```

   Pe Windows: `mvnw.cmd spring-boot:run`

4. Deschide în browser: <http://localhost:8080/home>

## Pagini și API

### Pagini (Thymeleaf)

| URL | Descriere |
|---|---|
| `GET /home` | Pagina de login / înregistrare |
| `GET /users-view` | Tabelul cu persoane și formularele Insert / Update / Delete / Search |

### API REST (`/api`)

| Metodă | Endpoint | Parametri / corp | Răspuns |
|---|---|---|---|
| `GET` | `/api/users` | `?id=` (opțional) | o persoană sau lista completă (JSON) |
| `GET` | `/api/filter` | `id`, `nume`, `prenume`, `anNastere`, `idOcupatie`, `idOrasDomiciliu` (toate opționale) | listă filtrată (JSON) |
| `POST` | `/api/save` | JSON `User` (fără `idPers`) | `Saved...` |
| `PUT` | `/api/update` | JSON `User` (cu `idPers`) | `Updated...` |
| `DELETE` | `/api/delete` | `?id=` | `Deleted...` |
| `POST` | `/api/register` | `{ "username", "password", "email" }` | 200 cu ID-ul / 400 dacă userul există |
| `POST` | `/api/login` | `{ "username", "password" }` | 200 `Login successful` / 401 |

Exemplu:

```bash
curl -X POST http://localhost:8080/api/save -H "Content-Type: application/json" -d "{\"nume\":\"Popescu\",\"prenume\":\"Ion\",\"anNastere\":1990,\"idOcupatie\":1,\"idOrasDomiciliu\":2}"
```

## Flux de utilizare

1. Utilizatorul accesează `/home`, își creează cont (**Register**) și apoi se autentifică (**Login**).
2. La login reușit, `Main.js` îl redirecționează la `/users-view`.
3. Din bara de navigare alege operația; formularele trimit cereri `fetch` către `/api/...`.
4. După Insert / Update / Delete se apasă **Home** pentru a reîncărca tabelul; **Search** actualizează tabelul direct.

## Limitări cunoscute și îmbunătățiri posibile

- **Securitate**
  - Parola bazei de date este scrisă direct în cod și în `application.properties`. Ar trebui mutată în variabile de mediu (ex. `${DB_PASSWORD}`) și scoasă din istoricul Git.
  - Login-ul nu creează sesiune sau token: `/users-view` și toate endpoint-urile `/api` sunt accesibile fără autentificare. Dependința `jjwt` este inclusă, dar nefolosită — se poate implementa autentificare cu JWT sau Spring Security complet.
  - Opțiunea „Remember Me” nu are funcționalitate.
- **Arhitectură**
  - `spring-boot-starter-data-jpa` este inclus, dar nu există entități JPA; accesul se face prin JDBC manual, cu o conexiune nouă la fiecare cerere. Se poate trece pe `JdbcTemplate` sau pe entități JPA + `JpaRepository`, folosind pool-ul HikariCP configurat deja.
  - `Controller` și `UserService` creează dependențele cu `new` în loc să le primească prin injecție (Spring DI).
  - Configurația bazei de date este duplicată (`DatabaseConfig` vs. `application.properties`).
  - `spring-security-crypto` 5.8.0 este dintr-o generație mai veche decât Spring Boot 3.3; versiunea ar putea fi lăsată pe seama BOM-ului Spring Boot.
- **Gestionarea erorilor**
  - Erorile SQL de la insert/update/delete sunt doar logate; API-ul răspunde totuși cu „Saved/Updated/Deleted...”, chiar dacă operația a eșuat sau ID-ul nu există.
  - `/api/filter` întoarce `null` la eroare SQL.
- **Frontend**
  - `Main.js` este comun ambelor pagini; pe `index.html` handler-ul `DOMContentLoaded` caută elementul `register-form`, care nu există acolo, și aruncă o eroare în consolă (fără efecte asupra restului funcțiilor).
  - Tabelul nu se reîmprospătează automat după Insert / Update / Delete.
- **Teste** — există doar testul implicit `contextLoads`, care necesită o bază MySQL pornită.
- Fișierele din `old/` și directorul `target/` (build) pot fi eliminate din repository / adăugate în `.gitignore`.
