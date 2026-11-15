# QuickChat POE - Authentication System

## Project Overview
QuickChat is a registration and login authentication system developed as part of PROG5121 (Programming 1 - POE).

## Features
- **User Registration** - Validates user information during registration
- **Login Authentication** - Securely verifies user credentials
- **Password Validation** - Ensures strong password requirements
- **Username Validation** - Enforces proper username formatting
- **Phone Number Validation** - Validates South African cell phone numbers

## Requirements

### Username Requirements
- Must contain an underscore (_)
- Maximum 5 characters in length

### Password Requirements
- Minimum 8 characters
- At least one uppercase letter
- At least one digit
- At least one special character

### Cell Phone Number Requirements
- Must start with South Africa's international code: +27
- Followed by exactly 9 digits
- Format: +27XXXXXXXXX

## Technology Stack
- **Language**: Java
- **Build Tool**: Maven
- **Testing Framework**: JUnit 4

## Project Structure
```
.
├── .github/workflows/
│   └── testfava.yml                         - Runs Maven tests on pushes and pull requests to main
├── pom.xml                                  - Maven configuration and project dependencies
├── src/
│   ├── main/java/programming1apoe/
│   │   ├── Login.java                       - Registration and login validation
│   │   ├── Main.java                        - Console application entry point
│   │   ├── Message.java                     - QuickChat message model
│   │   ├── MessageStorage.java              - Message persistence
│   │   └── QuickchatManager.java            - Message management and reporting
│   └── test/java/programming1apoe/
│       ├── LoginTest.java                   - Login and registration tests
│       ├── MessageStorageTest.java           - Message storage tests
│       ├── MessageTest.java                 - Message model tests
│       └── QuickchatManagerTest.java        - Message manager tests
└── README.md
```

## Running the Application

### Build
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java -Dexec.mainClass="programming1apoe.Main"
```

### Test
```bash
mvn test
```

## Author
ST10522809

## License
Default NetBeans License
