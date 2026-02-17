# 🏙️ KozyNest - 숙소예약 (Lodging Service)

프로젝트명: KozyNest - Korean Comfort Lodging

프로젝트 제작 기간: 2023.03.27 ~ 20223.04.12



### 🧳 프로젝트 개요 (Project Overview)

KozyNest 프로젝트의 목적은 백엔드 측면에 초점을 맞춘 숙박 웹 프로그램을 구축하는 것이었습니다. 프로젝트의 목표는 숙박 업주가 자신의 숙소를 등록하고 손님들이 이 숙소를 예약할 수 있는 완전히 기능이 구현된 웹사이트를 만드는 것이었습니다.

# 🏙️ 프로젝트 구성 및 설계

### 🧳 Database ERD

![ERD.jpg](kozynest/Picture1.jpg)

###  🧳 Site Map

![Sitemap.jpg](kozynest/Picture2.jpg)


### 🧳 사용된 기술 (Technologies Used)

**개발환경**

- Windows 10
- Eclipse IDE
- Oracle SQL Developer
- Github

**DB & WAS**
- Oracle
- Apache Tomcat 9.0

**백앤드**

- Spring MVC
- Spring AOP
- Java 11
- Oracle Database
- Mybatis

**프론트앤드**

- HTML/CSS
- JavaScript
- Bootstrap
- JQuery
- Ajax

# 🏙️ KOZYNEST 설치 가이드

### 🧳 요구 사항 (Requirements)
- JDK 11 이상
- Eclipse IDE for Java EE Developers
- Apache Tomcat 9.0 
- Oracle SQL Developer

### 🧳 설치 및 실행 방법
1. SQL Developer에 system개정에서 kozynest 사용자를 생성하고 권한을 부여합니다.
 
```
CREATE USER kozynest IDENTIFIED BY 1234;
GRANT dba TO kozynest;
```

2. 새로운 데이터베이스를 접속합니다
  - 접속 이름: KOZYNEST
  - 사용자 이름: kozynest
  - 비밀번호: 1234

3. Kozynest Repository를 클론 합니다
 
```
git clone https://github.com/sbdkim/LodgingService.git
```

4.  클론 한 LodgingService 안에 SQL_Kozynest파일을 여시고 Kozynest.sql 에 있는 SQL을 새로 접속된 데이터베이스에 순서대로 수행합니다.

5. 임포트 된 테이블에 SQL_Kozynest에 담겨있는 .xlxs 파일들을 "임포트행 설정.txt" 순서별로 하나씩 임포트 해줍니다. 

6. Eclipse를 실행하고 File > Open Projects from File System을 선택합니다.

7. 다운로드한 프로젝트 폴더를 선택하고 Finish를 클릭합니다.

8. Project Explorer에서 프로젝트를 선택하고 Run As > Run on Server를 클릭합니다.

9. Tomcat 서버가 설치되어 있지 않은 경우, Manually define a new server를 선택하고 설치된 Tomcat 버전을 선택합니다.

10. 브라우저에서 http://localhost:8080/biz 에 접속하여 웹 애플리케이션을 확인합니다.

## Configuration Profiles

- Default profile is `dev` (set in `src/main/webapp/WEB-INF/web.xml`).
- Database properties are loaded in this order:
  1. `src/main/resources/config/database.properties`
  2. `src/main/resources/config/database-{profile}.properties`
  3. `${user.home}/.kozynest/database-{profile}.properties` (optional, not in git)

### Example local override (recommended)

Create `${user.home}/.kozynest/database-dev.properties`:

```properties
jdbc.driver=oracle.jdbc.driver.OracleDriver
jdbc.url=jdbc:oracle:thin:@localhost:1521:XE
jdbc.username=your_user
jdbc.password=your_password
```

### Production profile

Use `database-prod.properties` with environment variables:

```properties
jdbc.url=${DB_URL}
jdbc.username=${DB_USERNAME}
jdbc.password=${DB_PASSWORD}
```
