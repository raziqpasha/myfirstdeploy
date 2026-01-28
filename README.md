# Selenium Automation Framework - Simple Guide

## 🎯 What This Framework Does

This is a **beginner-friendly** Selenium automation framework that:
- Runs tests on **Selenium Grid** using Docker
- Uses **RemoteWebDriver** to connect to browsers
- Follows **Page Object Model** design pattern
- Runs in **Docker containers**
- Uses **Jenkins** for test execution on AWS EC2
- Uses **GitHub Actions** for build validation

---

## 📁 Project Structure

```
selenium-framework/
├── src/
│   ├── main/java/com/automation/
│   │   ├── driver/DriverManager.java      # Creates WebDriver
│   │   └── pages/GoogleHomePage.java      # Page Object
│   └── test/java/com/automation/
│       ├── base/BaseTest.java             # Setup & Teardown
│       ├── tests/GoogleSearchTest.java    # Test Cases
│       └── resources/data.properties      # Configuration
├── pom.xml                                # Maven dependencies
├── testng.xml                             # TestNG configuration
├── Dockerfile                             # Test container
├── docker-compose.yml                     # Selenium Grid
├── Jenkinsfile                            # Jenkins pipeline
└── .github/workflows/ci.yml               # GitHub Actions
```

---

## 🧩 How Everything Connects

### 1. **DriverManager** (Creates the Browser)
- Creates a **RemoteWebDriver**
- Connects to **Selenium Grid** (running in Docker)
- Uses the `SELENIUM_HUB_URL` environment variable

### 2. **BaseTest** (Setup & Cleanup)
- Runs **before each test**:
    - Reads `data.properties`
    - Creates WebDriver
    - Opens the website
- Runs **after each test**:
    - Closes browser

### 3. **GoogleHomePage** (Page Object)
- Represents the Google homepage
- Contains elements (search box)
- Contains actions (search method)

### 4. **GoogleSearchTest** (Actual Test)
- Extends `BaseTest`
- Uses `GoogleHomePage` to interact with the page
- Verifies results using TestNG assertions

### 5. **Selenium Grid** (Browser Infrastructure)
- **Hub**: Central point that receives test requests
- **Chrome Node**: Actual browser where tests run
- Started using `docker-compose.yml`

### 6. **Docker** (Containerization)
- Tests run inside a Docker container
- Container connects to Selenium Grid
- Isolated and portable environment

### 7. **Jenkins** (Test Execution)
- Pulls code from GitHub
- Starts Selenium Grid
- Builds test Docker image
- Runs tests
- Stops Selenium Grid

### 8. **GitHub Actions** (Build Validation)
- Runs on every push/PR
- Only compiles code (doesn't run tests)
- Ensures code has no syntax errors

---

## 🚀 How to Run

### Option 1: Run Locally

**Step 1: Start Selenium Grid**
```bash
docker-compose up -d
```

**Step 2: Set Environment Variable**
```bash
export SELENIUM_HUB_URL=http://localhost:4444/wd/hub
```

**Step 3: Run Tests**
```bash
mvn clean test
```

**Step 4: Stop Selenium Grid**
```bash
docker-compose down
```

---

### Option 2: Run in Docker

**Step 1: Start Selenium Grid**
```bash
docker-compose up -d
```

**Step 2: Build Test Image**
```bash
docker build -t selenium-tests .
```

**Step 3: Run Tests**
```bash
docker run --rm \
  --network selenium-framework_selenium-grid \
  -e SELENIUM_HUB_URL=http://selenium-hub:4444/wd/hub \
  selenium-tests
```

**Step 4: Cleanup**
```bash
docker-compose down
```

---

### Option 3: Run via Jenkins (on AWS EC2)

**Prerequisites:**
- Jenkins installed on AWS EC2
- Docker installed on EC2
- GitHub repository configured

**Steps:**
1. Create a **Pipeline Job** in Jenkins
2. Point to your GitHub repository
3. Jenkins will use the `Jenkinsfile` automatically
4. Click **Build Now**

**What Jenkins Does:**
```
1. Pulls code from GitHub
   ↓
2. Starts Selenium Grid (docker-compose up)
   ↓
3. Builds Docker image for tests
   ↓
4. Runs tests inside Docker container
   ↓
5. Stops Selenium Grid (docker-compose down)
   ↓
6. Shows results
```

---

## 🔍 Understanding Key Concepts

### What is RemoteWebDriver?
- Normal WebDriver: Opens browser on **your machine**
- RemoteWebDriver: Opens browser on a **remote machine** (Selenium Grid)

### What is Selenium Grid?
- **Hub**: Acts like a "manager" - receives test requests
- **Nodes**: Actual browsers (Chrome, Firefox, etc.)
- Your test talks to Hub → Hub sends to Node → Node runs test

### What is Page Object Model?
Instead of:
```java
driver.findElement(By.name("q")).sendKeys("test");
```

You do:
```java
GoogleHomePage page = new GoogleHomePage(driver);
page.searchFor("test");
```

**Benefits:**
- Code is cleaner
- Easy to maintain
- Reusable

### What is the Flow?

```
GitHub (Code Storage)
    ↓
GitHub Actions (CI - Build Check Only)
    ↓
Jenkins on AWS EC2 (CD - Runs Tests)
    ↓
Starts Selenium Grid in Docker
    ↓
Builds Test Docker Image
    ↓
Runs Tests (RemoteWebDriver connects to Grid)
    ↓
Reports Results
```

---

## 📝 Configuration Files Explained

### 1. `data.properties`
```properties
base.url=https://www.google.com
browser=chrome
```
- Stores configuration
- Easy to change without modifying code

### 2. `pom.xml`
- Lists dependencies (Selenium, TestNG)
- Maven uses this to download libraries

### 3. `testng.xml`
- Tells TestNG which tests to run
- Can configure parallel execution

### 4. `docker-compose.yml`
- Defines Selenium Grid services
- Hub on port 4444
- Chrome node connects to hub

### 5. `Dockerfile`
- Packages tests in a container
- Uses Maven to run tests
- Portable (runs anywhere)

---

## 🎓 Learning Path

**Beginner:**
1. Understand DriverManager
2. Understand BaseTest
3. Run one simple test locally

**Intermediate:**
4. Add more Page Objects
5. Run tests in Docker locally
6. Understand docker-compose

**Advanced:**
7. Set up Jenkins on AWS EC2
8. Configure GitHub webhook
9. Run full CI/CD pipeline

---

## 🛠️ Common Issues & Solutions

### Issue 1: Tests fail with "Connection refused"
**Solution:** Selenium Grid is not running
```bash
docker-compose up -d
docker ps  # Verify containers are running
```

### Issue 2: "SELENIUM_HUB_URL not set"
**Solution:** Set environment variable
```bash
export SELENIUM_HUB_URL=http://localhost:4444/wd/hub
```

### Issue 3: Maven build fails
**Solution:** Check Java version
```bash
java -version  # Should be Java 11+
```

---

## 📊 What Each Component Does

| Component | Purpose | When it Runs |
|-----------|---------|--------------|
| GitHub Actions | Build validation | On every push/PR |
| Jenkins | Test execution | Manual or scheduled |
| Selenium Grid | Browser infrastructure | During test execution |
| Docker | Containerization | Test runtime |
| Maven | Dependency management | Build & test |
| TestNG | Test execution | Test runtime |

---

## 🎯 Next Steps

1. **Add more tests** in `src/test/java/com/automation/tests/`
2. **Add more pages** in `src/main/java/com/automation/pages/`
3. **Configure parallel execution** in `testng.xml`
4. **Add reporting** (Extent Reports, Allure)
5. **Add screenshots** on test failure

---

## 📌 Key Takeaways

✅ Tests run on **RemoteWebDriver** connected to **Selenium Grid**  
✅ Everything runs in **Docker** for portability  
✅ **Jenkins** executes tests, **GitHub Actions** validates builds  
✅ **Page Object Model** keeps code clean and maintainable  
✅ Configuration is external (`data.properties`)

---

## 💡 Tips for Beginners

1. **Start simple**: Run one test locally first
2. **Understand the flow**: Code → Build → Test → Report
3. **Use logs**: Add `System.out.println()` to debug
4. **Read errors**: Error messages tell you what's wrong
5. **Ask questions**: Don't hesitate to ask when stuck

---

**Happy Testing! 🚀**