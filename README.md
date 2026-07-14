# Selenium Java Automation Framework

**Java** "21.0.11" 2026-04-21 LTS | **Selenium** 4.27.0 | **TestNG** 7.10.2 | **Maven** 3.9.16 | **REST Assured** 5.5.0 | **License** MIT

A robust, scalable, and maintainable test automation framework built using **Selenium WebDriver**, **TestNG**, and **Java**.
Implements the **Page Object Model (POM) / Page Chaining** design pattern with a data-driven approach.
Supports both **UI** and **API** testing with environment-driven configuration, parallel execution, and Docker integration.

---

## Table of Contents

- [Framework Architecture](#framework-architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
  - [Clone the Repository](#clone-the-repository)
  - [Install JDK](#install-jdk)
  - [Install Maven](#install-maven)
  - [Install TestNG Plugin for Eclipse](#install-testng-plugin-for-eclipse)
  - [Configure Dependencies](#configure-dependencies)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Environment Configuration](#environment-configuration)
- [Adding a New Application](#adding-a-new-application)
- [Running Tests](#running-tests)
  - [Run a Single Test in Eclipse](#run-a-single-test-in-eclipse)
  - [Run a TestNG Suite in Eclipse](#run-a-testng-suite-in-eclipse)
  - [Run Tests via Maven](#run-tests-via-maven)
  - [Run Tests via Docker](#run-tests-via-docker)
- [Test Features](#test-features)
  - [Retry Mechanism](#retry-mechanism)
  - [Screenshot on Failure](#screenshot-on-failure)
  - [Disabling Test Cases](#disabling-test-cases)
- [Reporting](#reporting)
- [IDE Tips](#ide-tips)

---

## Framework Architecture

The framework is organized into the following logical layers:

### 1. Base Layer (shared across all applications)

**Location:** `src/main/java/com/base`

Contains three shared, application-agnostic classes:

- `TestBase` — parent for all **UI** page and test classes. Initializes WebDriver, manages browser
  configuration, timeouts, cookies, and ExtentReports. Resolves `-Dapp` and `-Denv` at startup
  to load the correct UI config file.

- `ApiTestBase` — parent for all **API** test classes. Reads `-Dapp` and `-Denv`, loads the
  matching API config file, and automatically sets `RestAssured.baseURI`.

- `Environment` — enum (`DEV`, `QA`, `STAGE`, `PROD`) used by both base classes to resolve the
  active environment from the `-Denv` system property. Defaults to `QA` when unset.

---

### 2. Application Layers (one namespace per application)

Each onboarded application lives under its own `com.<app>` namespace and is fully self-contained:

**Package structure per application:**

```
com/<app>/config/     - Per-environment .properties files
com/<app>/pages/      - Page Object classes (UI apps only)
com/<app>/testdata/   - Excel test data (if applicable)
```

**Currently onboarded applications:**

**Practice Test Automation (PTA)** — UI
- Package  : `com.pta`
- Base URL : `https://practicetestautomation.com`
- Suite    : `testng_pta_regression.xml`

**The Internet (Heroku)** — UI
- Package  : `com.herokuapp`
- Base URL : `https://the-internet.herokuapp.com`
- Suite    : `testng_herokuapp_regression.xml`

**JSONPlaceholder** — API
- Package  : `com.jsonplaceholder`
- Base URI : `https://jsonplaceholder.typicode.com`
- Suite    : `testng_jsonplaceholder.xml`

---

### 3. Test Layer — UI

**Location:** `src/test/java/com/<app>/ui/testcases`

TestNG test classes for each application's UI scenarios. All classes extend `com.base.TestBase`.

**PTA test coverage (6 classes):**
- `LoginPageTest`, `HomePageTest`, `PracticePageTest`, `CoursesPageTest`, `BlogPageTest`, `ContactPageTest`

**Heroku test coverage (5 classes):**
- `LoginPageTest`, `DropdownPageTest`, `CheckboxesPageTest`, `DynamicLoadingPageTest`, `JavaScriptAlertsPageTest`

---

### 3b. Test Layer — API

**Location:** `src/test/java/com/<app>/api/testcases`

TestNG test classes for each API service. All classes extend `com.base.ApiTestBase`, which
automatically sets `RestAssured.baseURI` from the resolved config file.

**JSONPlaceholder test coverage (3 classes):**
- `PostsGetAndPostTest`     — GET all posts, GET post by ID, POST create a post
- `PostsPutPatchDeleteTest` — PUT full update, PATCH partial update, DELETE
- `NestedAndFilterTest`     — Nested routes, query param filters (`?postId=`, `?userId=`), GET user by ID, filter todos

---

### 4. Utilities Layer (shared, app-agnostic)

**Location:** `src/main/java/com/util`

Shared helpers: screenshot capture, Excel reader (`TestUtil`), WebDriver event logger
(`WebDriverEventLogger`), event listeners (`CustomListener`, `MyTransformer`), and retry logic (`RetryAnalyzer`).

---

### 5. Reporting Layer

**Location:** `src/main/java/com/util`

Test result reporting via ExtentReports (rich HTML) and default TestNG HTML/XML reports.
The report title automatically includes the active environment name.

---

## Tech Stack

### Core
- **Java (JDK)** `21"21.0.11" 2026-04-21 LTS` — Core programming language
- **Maven** `3.9.9` — Build automation and dependency management

### UI Automation
- **Selenium WebDriver** `4.27.0` — Browser automation
- **WebDriverManager** `5.9.2` — Automatic driver binary management

### Test Framework
- **TestNG** `7.10.2` — Test execution, assertions, and reporting

### API Testing
- **REST Assured** `5.5.0` — REST API test automation
- **JSON Simple** `1.1.1` — JSON parsing

### Test Data
- **Apache POI** `5.3.0` — Excel-based data-driven testing (`poi`, `poi-ooxml`, `poi-scratchpad`)

### Reporting and Logging
- **ExtentReports** `5.1.2` — Rich interactive HTML test reports
- **Log4J** `2.24.3` — Application and test logging

### Infrastructure and CI
- **Docker / Selenium Grid** — Parallel and remote cross-browser execution
- **Jenkins** — Continuous Integration and pipeline automation

---

## Prerequisites

Before setting up the project, ensure the following are installed and configured on your machine:

- Java JDK 21
- Apache Maven 3.6 or higher
- Git
- Eclipse IDE (with TestNG plugin) or IntelliJ IDEA
- Docker (optional, for Grid-based execution)

---

## Project Setup

### Clone the Repository

```bash
git clone <repository-url>
cd Selenium-Java-Automation-Framework
```

### Install JDK

1. Download and install the JDK from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [Adoptium](https://adoptium.net/).

2. Locate the JDK installation path:

```bash
where javac
# Example output: C:\Program Files\Java\jdk-21.0.11\bin\javac.exe
```

3. Set the `JAVA_HOME` environment variable:

   - Variable name  : `JAVA_HOME`
   - Variable value : `C:\Program Files\Java\jdk-21.0.11`

4. Add `%JAVA_HOME%\bin` to the `Path` system variable.

5. Verify the installation:

```bash
java -version
javac -version
echo %JAVA_HOME%
```

### Install Maven

1. Download the binary archive from [maven.apache.org](https://maven.apache.org/download.cgi)
   (e.g., `apache-maven-3.9.16-bin.zip`).

2. Extract to a directory such as `C:\Program Files\Apache\maven-3.9.16`.

3. Set the following environment variables:

   - `MAVEN_HOME`    = `C:\Program Files\Apache\maven-3.9.16`
   - `Path` (append) = `C:\Program Files\Apache\maven-3.9.16\bin`

4. Verify the installation:

   ```bash
   mvn -version
   ```

### Install TestNG Plugin for Eclipse

1. Open Eclipse IDE.
2. Navigate to **Help > Eclipse Marketplace**.
3. Search for `TestNG`.
4. Install **TestNG for Eclipse** (by beust.com).
5. Accept the license agreement and restart Eclipse when prompted.

### Configure Dependencies

All dependencies are managed via `pom.xml` and are automatically downloaded by Maven on the first build.

Key dependencies:

- `selenium-java`
- `webdrivermanager`
- `testng`
- `poi`, `poi-ooxml`, `poi-scratchpad`
- `extentreports`
- `log4j-core`, `log4j-api`
- `rest-assured`
- `json-simple`

To download all dependencies, run:

```bash
mvn dependency:resolve
```

---

## Project Structure

```
Selenium-Java-Automation-Framework/
|-- src/
|   |-- main/
|   |   |-- java/
|   |   |   |-- com/base/                      (Shared - app-agnostic)
|   |   |   |   |-- TestBase.java              (UI base: WebDriver + ExtentReports)
|   |   |   |   |-- ApiTestBase.java           (API base: REST Assured)
|   |   |   |   `-- Environment.java           (Enum: DEV, QA, STAGE, PROD)
|   |   |   |-- com/util/                      (Shared utilities - app-agnostic)
|   |   |   |   |-- TestUtil.java              (Screenshot capture + Apache POI Excel reader)
|   |   |   |   |-- CustomListener.java        (ITestListener: screenshot on failure)
|   |   |   |   |-- MyTransformer.java         (IAnnotationTransformer: apply retry to all tests)
|   |   |   |   |-- RetryAnalyzer.java         (IRetryAnalyzer: test-level retry logic)
|   |   |   |   `-- WebDriverEventLogger.java  (WebDriver event listener for action logging)
|   |   |   |-- com/pta/                       (UI Application: Practice Test Automation)
|   |   |   |   |-- config/                    (config.dev/qa/stage/prod.properties)
|   |   |   |   |-- pages/                     (LoginPage, HomePage, PracticePage, ...)
|   |   |   |   `-- testdata/                  (TestData.xlsx)
|   |   |   |-- com/herokuapp/                 (UI Application: The Internet - Heroku)
|   |   |   |   |-- config/                    (config.dev/qa/stage/prod.properties)
|   |   |   |   `-- pages/                     (LoginPage, DropdownPage, CheckboxesPage, ...)
|   |   |   `-- com/jsonplaceholder/            (API Service: JSONPlaceholder)
|   |   |       `-- config/                    (config.dev/qa/stage/prod.properties)
|   |   `-- resources/
|   |       |-- log4j.properties               (Log4J logging configuration)
|   |       |-- testng.xml                     (PTA - targeted suite)
|   |       |-- testng_pta_regression.xml      (PTA - regression suite)
|   |       |-- testng_sanity.xml              (PTA - sanity suite)
|   |       |-- testng_herokuapp_regression.xml (Heroku - UI regression suite)
|   |       `-- testng_jsonplaceholder.xml     (JSONPlaceholder - API suite)
|   `-- test/
|       `-- java/com/
|           |-- pta/ui/testcases/              (PTA UI test classes)
|           |-- herokuapp/ui/testcases/        (Heroku UI test classes)
|           `-- jsonplaceholder/api/testcases/ (JSONPlaceholder API test classes)
|-- docker-compose-standalone.yml              (Selenium Grid: Chrome, Firefox, Edge containers)
|-- pom.xml                                    (Maven build, dependencies, and profiles)
`-- README.md                                  (Project documentation)
```

---

## Configuration

Each application manages its own environment-specific config files under `com/<app>/config/`.

**UI applications** (`url` + credentials + `browser`):

```
src/main/java/com/pta/config/
    config.dev/qa/stage/prod.properties

src/main/java/com/herokuapp/config/
    config.dev/qa/stage/prod.properties
```

**API services** (`baseUri` + optional `apiKey`):

```
src/main/java/com/jsonplaceholder/config/
    config.dev/qa/stage/prod.properties
```

**UI config structure:**

```properties
env      = qa
url      = https://your-app-base-url.com
username = your_username
password = your_password
browser  = chrome
```

**API config structure:**

```properties
env     = qa
baseUri = https://your-api-base-url.com
apiKey  = your_api_key          (optional - omit if no auth required)
```

---

## Environment Configuration

The active application and environment are selected via Maven profiles or system properties.

Both `TestBase` and `ApiTestBase` resolve the config file path at runtime as:

```
src/main/java/com/<app>/config/config.<env>.properties
```

The `Environment` enum (`com.base.Environment`) supports: `DEV`, `QA` (default), `STAGE`, `PROD`.

### Supported Maven Profiles

Each profile sets the target application (`-Dapp`), environment (`-Denv`), and the TestNG suite file.

**PTA — UI** (suite: `testng_pta_regression.xml`)

<table>
  <thead><tr><th>Profile</th><th>Environment</th></tr></thead>
  <tbody>
    <tr><td><code>pta-qa</code></td><td>QA (default)</td></tr>
    <tr><td><code>pta-dev</code></td><td>Dev</td></tr>
    <tr><td><code>pta-stage</code></td><td>Stage</td></tr>
    <tr><td><code>pta-prod</code></td><td>Prod</td></tr>
  </tbody>
</table>

**Heroku — UI** (suite: `testng_herokuapp_regression.xml`)

<table>
  <thead><tr><th>Profile</th><th>Environment</th></tr></thead>
  <tbody>
    <tr><td><code>heroku-qa</code></td><td>QA</td></tr>
    <tr><td><code>heroku-dev</code></td><td>Dev</td></tr>
    <tr><td><code>heroku-stage</code></td><td>Stage</td></tr>
    <tr><td><code>heroku-prod</code></td><td>Prod</td></tr>
  </tbody>
</table>

**JSONPlaceholder — API** (suite: `testng_jsonplaceholder.xml`)

<table>
  <thead><tr><th>Profile</th><th>Environment</th></tr></thead>
  <tbody>
    <tr><td><code>jsonplaceholder-qa</code></td><td>QA</td></tr>
    <tr><td><code>jsonplaceholder-dev</code></td><td>Dev</td></tr>
    <tr><td><code>jsonplaceholder-stage</code></td><td>Stage</td></tr>
    <tr><td><code>jsonplaceholder-prod</code></td><td>Prod</td></tr>
  </tbody>
</table>

### Selecting an Application and Environment

**Via Maven profile (recommended)**

```bash
# PTA UI tests
mvn clean test -Ppta-qa
mvn clean test -Ppta-dev
mvn clean test -Ppta-stage
mvn clean test -Ppta-prod

# Heroku UI tests
mvn clean test -Pheroku-qa
mvn clean test -Pheroku-dev
mvn clean test -Pheroku-stage
mvn clean test -Pheroku-prod

# JSONPlaceholder API tests
mvn clean test -Pjsonplaceholder-qa
mvn clean test -Pjsonplaceholder-dev
mvn clean test -Pjsonplaceholder-stage
mvn clean test -Pjsonplaceholder-prod
```

**Override browser independently (UI tests only)**

```bash
mvn clean test -Ppta-qa     -Dbrowser=firefox
mvn clean test -Pheroku-qa  -Dbrowser=edge
```

**Via system properties directly**

```bash
# UI tests
mvn clean test -Dapp=pta        -Denv=stage
mvn clean test -Dapp=herokuapp  -Denv=qa

# API tests
mvn clean test -Dapp=jsonplaceholder -Denv=qa
```

**Via Eclipse Run Configurations**

1. Open **Run > Run Configurations > TestNG**.
2. Select the **Arguments** tab.
3. In the **VM arguments** field, add:

```
-Dapp=herokuapp -Denv=qa -Dbrowser=chrome
```

---

## Adding a New Application

The framework is **plug-and-play** — onboarding a new UI app or API service requires no changes
to any shared code.

### Adding a New UI Application

**Step 1 — Create config files** under `src/main/java/com/<app>/config/`.
Each file contains: `env`, `url`, `username`, `password`, `browser`.

**Step 2 — Create Page Object classes** under `src/main/java/com/<app>/pages/`.
Each page class extends `com.base.TestBase` and includes a `navigate()` method.

**Step 3 — Create test classes** under `src/test/java/com/<app>/ui/testcases/`.
Each test class extends `com.base.TestBase`.

**Step 4 — Create a TestNG suite XML** under `src/main/resources/testng_<app>_regression.xml`.

**Step 5 — Add Maven profiles** in `pom.xml` for `<app>-dev/qa/stage/prod`.

**Step 6 — Run:**

```bash
mvn clean test -P<app>-qa
```

### Adding a New API Service

**Step 1 — Create config files** under `src/main/java/com/<app>/config/`.
Each file contains: `env`, `baseUri` and optionally `apiKey`.

**Step 2 — Create test classes** under `src/test/java/com/<app>/api/testcases/`.
Each test class extends `com.base.ApiTestBase`. The `baseURI` is set automatically.

**Step 3 — Create a TestNG suite XML** under `src/main/resources/testng_<app>.xml`.

**Step 4 — Add Maven profiles** in `pom.xml` for `<app>-dev/qa/stage/prod`.

**Step 5 — Run:**

```bash
mvn clean test -P<app>-qa
```

---

## Running Tests

### Run a Single Test in Eclipse

1. Open the desired test class under `src/test/java`.
2. Right-click the file and select **Run As > TestNG Test**.

### Run a TestNG Suite in Eclipse

1. Ensure TestNG is on the build path:
   - Right-click the project and select **Build Path > Configure Build Path > Libraries**
   - Confirm TestNG is listed.
2. Open the desired suite XML under `src/main/resources/`.
3. Right-click the file and select **Run As > TestNG Suite**.

### Run Tests via Maven

Execute the full test suite from the project root:

```bash
mvn clean install
```

**Maven build phases:**

- `clean`   — Deletes the `target/` directory (removes prior build artifacts)
- `compile` — Compiles all source code
- `test`    — Executes tests defined in the Surefire plugin configuration
- `package` — Packages compiled code into a `.jar`
- `install` — Installs the artifact to the local Maven repository (`~/.m2`)

> **Note:** The `maven-surefire-plugin` runs the suite file defined by the active Maven profile.
> If no `<suiteXmlFiles>` is specified, Surefire auto-discovers all test classes matching
> `*Test.java`, `*Tests.java`, or `*TestCase.java` under `src/test/java`.

To run only the test phase without packaging:

```bash
mvn clean test
```

### Run Tests via Docker

The framework supports parallel execution via **Selenium Grid** using Docker.

---

**1. Start the containers**

```bash
docker compose -f docker-compose-standalone.yml up -d
```

On first run, Docker pulls the Selenium Standalone images for Chrome, Firefox, and Edge automatically.

---

**2. WebDriver endpoints**

<table>
  <thead><tr><th>Browser</th><th>Selenium Grid URL</th></tr></thead>
  <tbody>
    <tr><td>Chrome</td><td>http://localhost:4441/</td></tr>
    <tr><td>Firefox</td><td>http://localhost:4442/</td></tr>
    <tr><td>Edge</td><td>http://localhost:4443/</td></tr>
  </tbody>
</table>

---

**3. Remote desktop access via noVNC**

Open in your browser and enter password `secret` when prompted.

<table>
  <thead><tr><th>Browser</th><th>noVNC URL</th></tr></thead>
  <tbody>
    <tr><td>Chrome</td><td>http://localhost:7901/</td></tr>
    <tr><td>Firefox</td><td>http://localhost:7902/</td></tr>
    <tr><td>Edge</td><td>http://localhost:7903/</td></tr>
  </tbody>
</table>

---

**4. Stop the containers**

```bash
docker compose -f docker-compose-standalone.yml down
```

---

## Test Features

### Retry Mechanism

Failed tests can be automatically retried using two approaches:

**Test-level retry**

1. Implement the `retry()` method from the `IRetryAnalyzer` interface in `RetryAnalyzer.java`.
2. Reference it in the test annotation:

   ```java
   @Test(retryAnalyzer = RetryAnalyzer.class)
   public void myTest() { ... }
   ```

**Runtime retry (applies to all tests)**

1. Implement the `transform()` method from the `IAnnotationTransformer` interface in `MyTransformer.java`.
2. Register the listener in the TestNG suite XML:

   ```xml
   <listeners>
       <listener class-name="com.util.MyTransformer"/>
   </listeners>
   ```

### Screenshot on Failure

Automatic screenshots are captured on test failure:

1. Override `onTestFailure()` (and optionally `onTestSuccess()`) from the `ITestListener`
   interface in `CustomListener.java`.
2. Register the listener in the TestNG suite XML:

   ```xml
   <listeners>
       <listener class-name="com.util.CustomListener"/>
   </listeners>
   ```

### Disabling Test Cases

To skip a test case without deleting it:

```java
@Test(enabled = false)
public void skippedTest() { ... }
```

---

## Reporting

The framework generates the following reports after each test run:

<table>
  <thead><tr><th>Report Type</th><th>Location</th></tr></thead>
  <tbody>
    <tr><td>TestNG HTML Report</td><td><code>target/surefire-reports/index.html</code></td></tr>
    <tr><td>TestNG XML Report</td><td><code>target/surefire-reports/testng-results.xml</code></td></tr>
    <tr><td>ExtentReport (HTML)</td><td><code>reports/extentSparkReport.html</code></td></tr>
  </tbody>
</table>

---

## IDE Tips

**Show hidden dot-files in Eclipse Package Explorer**

1. Click the **View Menu** (three dots) in the top-right corner of Package Explorer.
2. Select **Filters**.
3. Uncheck **.* resources**.
4. Click **OK**.