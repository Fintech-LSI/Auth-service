<div align="center">

# 🔐 Auth Service

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=your-jenkins-url)](https://jenkins-url)
[![Quality Gate Status](https://img.shields.io/sonar/quality_gate/auth-service?server=http://54.86.47.1:9000)](http://54.86.47.1:9000)
[![Docker Pulls](https://img.shields.io/docker/pulls/your-repo/auth-service)](https://hub.docker.com/r/your-repo/auth-service)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
</div>

## 📋 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Pipeline](#-pipeline)
- [Getting Started](#-getting-started)
- [API Documentation](#-api-documentation)
- [Configuration](#-configuration)
- [Deployment](#-deployment)
- [Contributing](#-contributing)
- [Team](#-team)
- [License](#-license)

## 🎯 Overview

A robust authentication microservice built with Spring Boot that provides secure user authentication and authorization services for the FinTechPro platform. This service handles user registration, authentication, token management, and role-based access control.

## ✨ Features

- 🔒 **Secure JWT Authentication**
- 👥 **User Management**
- 🔑 **Role-Based Access Control**
- 🔄 **Token Refresh Mechanism**
- 📝 **User Registration**
- 🔍 **Account Validation**
- 🛡️ **Password Encryption**
- 📊 **Authentication Metrics**
- 🚫 **Rate Limiting**
- 🔐 **Password Reset Flow**

## 🏗 Architecture

The auth service follows a clean, layered architecture:

```
Controller Layer → Service Layer → Repository Layer → Database
        ↓              ↓               ↓
    DTOs/Models     Business      Data Access
                     Logic
```

## 🛠 Tech Stack

<div align="center">
  <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white" alt="JWT" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/kubernetes-326ce5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white" alt="Kubernetes" />
  <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white" alt="AWS" />
</div>

## 📂 Project Structure

```
src/
├── 📱 main/java/com/fintech/auth/
│   ├── 📊 config/
│   │   ├── SecurityConfig.java
│   │   └── JwtUtil.java
│   ├── 🎮 controller/
│   │   └── AuthController.java
│   ├── 📦 dto/
│   │   ├── request/
│   │   └── response/
│   ├── 🏢 entity/
│   ├── 📚 repository/
│   └── 🔧 service/
├── 📝 main/resources/
│   └── application.yml
└── 🧪 test/
```

## 🔄 Pipeline

Our CI/CD pipeline ensures secure and reliable deployments:

![CI/CD Pipeline](/images/pipeline-diagram.png)

1. 📥 **Code Checkout**: Source code retrieval
2. 🔍 **SonarQube Analysis**: Code quality and security checks
3. 🏗️ **Maven Build**: Compilation and package creation
4. 🐳 **Docker Build & Push**: Container image creation and registry upload
5. ☸️ **EKS Deployment**: Kubernetes deployment with proper configurations

## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/your-org/auth-service.git

# Navigate to the project directory
cd auth-service

# Build the project
mvn clean install

# Run locally
mvn spring-boot:run

# Run tests
mvn test

# Build Docker image
docker build -t auth-service .
```

## 👥 Team

| Avatar                                                                                                  | Name | Role | GitHub |
|---------------------------------------------------------------------------------------------------------|------|------|--------|
| <img src="https://github.com/zachary013.png" width="50" height="50" style="border-radius: 50%"/>        | Zakariae Azarkan | DevOps Engineer | [@zachary013](https://github.com/zachary013) |
| <img src="https://github.com/goalaphx.png" width="50" height="50" style="border-radius: 50%"/>          | El Mahdi Id Lahcen | Frontend Developer | [@goalaphx](https://github.com/goalaphx) |
| <img src="https://github.com/hodaifa-ech.png" width="50" height="50" style="border-radius: 50%"/>       | Hodaifa | Cloud Architect | [@hodaifa-ech](https://github.com/hodaifa-ech) |
| <img src="https://github.com/khalilh2002.png" width="50" height="50" style="border-radius: 50%"/>       | Khalil El Houssine | Backend Developer | [@khalilh2002](https://github.com/khalilh2002) |
| <img src="https://github.com/Medamine-Bahassou.png" width="50" height="50" style="border-radius: 50%"/> | Mohamed Amine BAHASSOU | ML Engineer | [@Medamine-Bahassou](https://github.com/Medamine-Bahassou) |

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
<div align="center">
  <p>Built with ❤️ by the Fintech Team</p>
</div>
