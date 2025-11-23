# 🚴 Spark SQL - Bike Sharing Analysis

## 📋 Table of Contents
- [Overview](#overview)
- [Dataset Description](#dataset-description)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
  - [Local Execution](#local-execution)
  - [Docker Execution](#docker-execution)
- [Running the Application](#running-the-application)
  - [Method 1: Local Mode](#method-1-local-mode)
  - [Method 2: Docker Cluster Mode](#method-2-docker-cluster-mode)
- [Analysis Overview](#analysis-overview)
- [Results & Screenshots](#results--screenshots)
- [Troubleshooting](#troubleshooting)
- [Technologies Used](#technologies-used)

---

## 🎯 Overview

This project analyzes a city's public bike-sharing system using **Apache Spark SQL**. The goal is to extract insights about:
- 📊 User behavior patterns
- 🚉 Station popularity
- ⏰ Peak usage hours
- 💰 System performance and revenue

The analysis is performed using Spark SQL queries on a dataset of bike rental transactions.

---

## 📁 Dataset Description

**File:** `bike_sharing.csv`

### Column Structure

| Column | Type | Description |
|--------|------|-------------|
| `rental_id` | Integer | Unique identifier for each rental |
| `user_id` | Integer | Unique identifier for the user |
| `age` | Integer | User's age |
| `gender` | String | User's gender (M/F) |
| `start_time` | Timestamp | Rental start timestamp |
| `end_time` | Timestamp | Rental end timestamp |
| `start_station` | String | Station where bike was picked up |
| `end_station` | String | Station where bike was returned |
| `duration_minutes` | Integer | Length of the trip in minutes |
| `price` | Double | Rental cost in dollars |

### Sample Data
<img width="1234" height="358" alt="image" src="https://github.com/user-attachments/assets/982e57a2-583c-4279-b4fa-016b81ed4b3f" />

## ✅ Prerequisites

### For Local Execution
- ☕ **Java JDK 11 or higher**
  ```bash
  java -version
  ```
- 📦 **Apache Maven 3.6+**
  ```bash
  mvn -version
  ```
- ⚡ **Apache Spark 4.0.1** (automatically managed by Maven)

### For Docker Execution
- 🐳 **Docker Desktop** (Windows/Mac) or Docker Engine (Linux)
  ```bash
  docker --version
  docker compose version
  ```
- 💾 **Minimum 8GB RAM** allocated to Docker
- 💿 **Minimum 20GB free disk space**

---

## 🔧 Setup & Installation

This project can be executed in two modes:
1. **Local Mode** - Running Spark directly on your machine
2. **Docker Cluster Mode** - Running on a distributed Hadoop/Spark cluster

---

## 🖥️ Method 1: Local Execution

### Step 1: Prerequisites Check

Ensure you have:
- ☕ Java JDK 11+ installed
- 📦 Maven 3.6+ installed

```bash
# Verify installations
java -version
mvn -version
```

### Step 2: Project Setup

Create your project structure:
```
spark-sql-tp/
├── src/
│   └── main/
│       └── java/
│           └── ma/
│               └── bigdata/
│                   └── BikeRentalAnalysis.java
├── data/
│   └── bike_sharing.csv
└── pom.xml
```

### Step 3: Configure pom.xml for Local Execution

```xml
 <dependencies>
        <!-- Spark Core -->
        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-core_${scala.binary.version}</artifactId>
            <version>${spark.version}</version>
        </dependency>      
        <!-- Spark SQL -->
        <dependency>
            <groupId>org.apache.spark</groupId>
            <artifactId>spark-sql_${scala.binary.version}</artifactId>
            <version>${spark.version}</version>
        </dependency>
    </dependencies>
```

**⚠️ Important:** For local execution, do NOT include `<scope>provided</scope>` in dependencies.

### Step 4: Java Code for Local Mode

<img width="1137" height="918" alt="Screenshot 2025-11-23 204154" src="https://github.com/user-attachments/assets/01412ff5-e002-46ba-8190-c54b4818024b" />

<img width="1356" height="857" alt="Screenshot 2025-11-23 204206" src="https://github.com/user-attachments/assets/d5c41935-663d-41b2-9d76-846cbdba35c7" />

<img width="1621" height="854" alt="Screenshot 2025-11-23 204225" src="https://github.com/user-attachments/assets/19998cbf-bafd-4446-9ae0-b975ff7f5d52" />


<img width="1257" height="925" alt="Screenshot 2025-11-23 204302" src="https://github.com/user-attachments/assets/daf9c138-d784-43ca-814d-62613b61e089" />

### Step 5: Compile and Run

```bash
# Compile the project
mvn clean package

# Run the application
java -cp target/spark-sql-tp-1.0-SNAPSHOT.jar ma.bigdata.BikeRentalAnalysis
```

**Or using Maven directly:**
```bash
mvn exec:java -Dexec.mainClass="ma.bigdata.BikeRentalAnalysis"
```

### Step 6: View Results


<img width="1602" height="667" alt="Screenshot 2025-11-23 203704" src="https://github.com/user-attachments/assets/d2867292-6f09-4d8d-b512-009daa4bda65" />
<img width="1708" height="454" alt="Screenshot 2025-11-23 203724" src="https://github.com/user-attachments/assets/172f129c-273b-4af8-b3e1-bdaeb3dae2b6" />
<img width="1726" height="661" alt="Screenshot 2025-11-23 203736" src="https://github.com/user-attachments/assets/066426d4-a243-4e69-a51f-4848e2f5fb66" />
<img width="1723" height="642" alt="Screenshot 2025-11-23 203750" src="https://github.com/user-attachments/assets/24e78f0f-80b3-4026-9af7-0ca0c7a8d80e" />
<img width="1731" height="734" alt="Screenshot 2025-11-23 203803" src="https://github.com/user-attachments/assets/6185e759-c854-4ade-93d8-e02b78206238" />
<img width="1713" height="300" alt="Screenshot 2025-11-23 203815" src="https://github.com/user-attachments/assets/1b3c55a4-d0d2-4d3a-a240-ca6474edec17" />
<img width="1720" height="805" alt="Screenshot 2025-11-23 203827" src="https://github.com/user-attachments/assets/db597652-a3b8-47a5-b0b7-b70c14175624" />
<img width="1719" height="844" alt="Screenshot 2025-11-23 203842" src="https://github.com/user-attachments/assets/d1d96d4b-a34f-41a6-a6af-efd5daf77cdc" />
<img width="1732" height="854" alt="Screenshot 2025-11-23 203854" src="https://github.com/user-attachments/assets/8d3c89b0-3991-47ab-8604-0d6c4072ec1e" />

---

## 🐳 Method 2: Docker Cluster Mode

### Step 1: Prerequisites Check

Ensure you have:
- 🐳 Docker Desktop (Windows/Mac) or Docker Engine (Linux)
- 💾 Minimum 8GB RAM allocated to Docker
- 💿 Minimum 20GB free disk space

```bash
# Verify Docker installation
docker --version
docker compose version
```
### Step 2: Project Structure for Docker

```
spark-sql-tp/
├── src/
│   └── main/
│       └── java/
│           └── ma/
│               └── bigdata/
│                   └── BikeRentalAnalysis.java
├── data/
│   └── bike_sharing.csv
├── volumes/
│   ├── namenode/
│   └── datanode/
├── target/
│   └── (generated by Maven)
├── pom.xml
├── docker-compose.yml
├── config
└── submit-job.bat (or submit-job.sh)
```
### Step 3: Configure pom.xml for Docker Cluster

**⚠️ Important:** For Docker cluster, use `<scope>provided</scope>` because Spark is already in the Docker images.

### Step 4: Create Docker Compose Configuration
- Check the docker compose code in the code section
  
### Step 5: Create Hadoop Configuration File

Create a file named `config` (no extension) in the project root:

### Step 6: Java Code for Docker Cluster Mode
### Step 7: Start the Docker Cluster

<img width="1806" height="633" alt="image" src="https://github.com/user-attachments/assets/d4a84fa6-ab97-44e0-8182-100b449fcb90" />

### Step 8: Compile the Application

```bash
mvn clean package
```

<img width="1146" height="318" alt="Screenshot 2025-11-23 211300" src="https://github.com/user-attachments/assets/545e13d5-ba23-493c-8a9f-360e901a5534" />


### Step 9: Upload Data to HDFS

<img width="1781" height="147" alt="image" src="https://github.com/user-attachments/assets/ab619920-9c75-4148-81fc-6e5b13b8644b" />

### Step 10: Submit the Spark Job


<img width="1783" height="879" alt="Screenshot 2025-11-23 224757" src="https://github.com/user-attachments/assets/6d0cb821-59ef-4fd7-8600-a9826c9ce356" />

### Step 12: Access Web Interfaces

Once the job is running, you can access:

- **Spark Master UI:** http://localhost:8086

  <img width="1914" height="1046" alt="Screenshot 2025-11-23 211844" src="https://github.com/user-attachments/assets/7cc7d215-42b2-4fed-9a55-766f9679c40e" />

- **Spark Worker 1 UI:** http://localhost:8082

  <img width="1919" height="987" alt="Screenshot 2025-11-23 213350" src="https://github.com/user-attachments/assets/2255fdd1-8c5d-480b-9ee2-291d583d1b95" />

- **Spark Worker 2 UI:** http://localhost:8084

  <img width="1896" height="903" alt="Screenshot 2025-11-23 214326" src="https://github.com/user-attachments/assets/e0a34c91-1555-4005-8217-9a27c20c65ff" />

- **Hadoop NameNode UI:** http://localhost:9870

<img width="1905" height="1105" alt="Screenshot 2025-11-23 214351" src="https://github.com/user-attachments/assets/c1bcc478-6a88-4c0e-aece-0b99f9cd079a" />

### Step 13 : View executions 


<img width="1804" height="922" alt="Screenshot 2025-11-23 224827" src="https://github.com/user-attachments/assets/27125120-311a-402c-afc4-60ab0eef7496" />


## 📊 Comparison: Local vs Docker Cluster

| Feature | Local Mode | Docker Cluster Mode |
|---------|------------|---------------------|
| **Setup Complexity** | Low | Medium |
| **Resource Usage** | Uses host machine directly | Containerized, isolated resources |
| **Data Storage** | Local filesystem | HDFS (distributed) |
| **Scalability** | Limited to single machine | Can scale with multiple workers |
| **Master URL** | `local[*]` | `spark://spark-master-bike:7077` |
| **Data Path** | `data/bike_sharing.csv` | `hdfs://namenode:8020/input-data/bike_sharing.csv` |
| **Dependencies Scope** | No `provided` scope | With `provided` scope |
| **Web UI Port** | http://localhost:4040 | http://localhost:4041 |
| **Best For** | Development, testing | Production-like environment, learning distributed systems |

---

## 📊 Analysis Overview

The application performs the following analyses:

### 1. Data Loading & Exploration
- ✅ Load CSV into Spark DataFrame
- ✅ Display schema
- ✅ Show first 5 rows
- ✅ Count total rentals

### 2. Create Temporary View
- ✅ Create SQL view: `bike_rentals_view`

### 3. Basic SQL Queries
- ✅ Rentals longer than 30 minutes
- ✅ Rentals starting at "Station A"
- ✅ Total revenue calculation

### 4. Aggregation Queries
- ✅ Rental count per start station
- ✅ Average duration per station
- ✅ Station with highest rentals
- 
### 5. Time-Based Analysis
- ✅ Extract hour from start_time
- ✅ Rentals per hour (peak hours)
- ✅ Most popular morning station (7-12h)

### 6. User Behavior Analysis
- ✅ Average user age
- ✅ User count by gender
- ✅ Rental count by age group (18-30, 31-40, 41-50, 51+)

## 🌐 Web Interfaces
### Local Mode
- **Spark UI:** http://localhost:4040 (active during job execution)
  
### Docker Cluster Mode
- **Spark Master UI:** http://localhost:8086
- **Spark Worker 1 UI:** http://localhost:8082
- **Spark Worker 2 UI:** http://localhost:8084
- **Hadoop NameNode UI:** http://localhost:9870
- **YARN ResourceManager UI:** http://localhost:8088
  
## 🐛 Troubleshooting

### Common Issues

#### 1. **Port Already in Use**
```bash
Error: bind: address already in use
```
**Solution:** Change ports in `docker-compose.yml` or stop conflicting services.

#### 2. **ClassNotFoundException: SparkSession**
```bash
java.lang.ClassNotFoundException: org.apache.spark.sql.SparkSession
```
**Solution:** Remove `<scope>provided</scope>` from dependencies for local execution.

#### 3. **Unable to Resolve Table**
```bash
Unable to resolve table 'bike_rentals_view'
```
**Solution:** Ensure `createOrReplaceTempView()` is called before SQL queries.

#### 4. **File Not Found in HDFS**
```bash
Path does not exist: hdfs://namenode:8020/input-data/bike_sharing.csv
```
**Solution:** 
```bash
docker exec spark-sql-tp-namenode-1 hdfs dfs -put -f /input-data/bike_sharing.csv /input-data/
```

#### 5. **Container Name Conflict**
```bash
Conflict. The container name "/spark-master" is already in use
```
**Solution:** Change container names in `docker-compose.yml` or remove existing containers:
```bash
docker rm -f spark-master spark-worker-1 spark-worker-2
```

#### 6. **PowerShell Multi-line Command Error**
```powershell
Missing expression after unary operator '--'
```
**Solution:** Use backticks (`) instead of backslashes (\), or put the entire command on one line.

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| Apache Spark | 4.0.1 | Distributed data processing |
| Spark SQL | 4.0.1 | SQL queries on DataFrames |
| Apache Hadoop | 3.3.6 | Distributed file system (HDFS) |
| Java | 21 | Programming language |
| Maven | 3.6+ | Build automation |
| Docker | Latest | Containerization |
| Docker Compose | V2 | Multi-container orchestration |

---

## 📝 Key Learnings

1. ✅ **Spark SQL basics:** Creating DataFrames, temporary views, and SQL queries
2. ✅ **Data aggregation:** GROUP BY, COUNT, AVG, SUM operations
3. ✅ **Time-based analysis:** Extracting time components and analyzing patterns
4. ✅ **Distributed computing:** Running Spark on a cluster vs local mode
5. ✅ **HDFS integration:** Storing and reading data from Hadoop filesystem
6. ✅ **Docker orchestration:** Managing multi-container Spark/Hadoop cluster

---

## 👨‍💻 Author

**Big Data Course 2025**  
Instructor: Mr. Abdelmajid BOUSSELHAM

---

## 📄 License

This project is created for educational purposes as part of the Big Data course.

---

## 🙏 Acknowledgments

- Apache Spark documentation
- Apache Hadoop documentation
- Spark Docker images
- Course materials and guidance

---

**Happy Analyzing! 🚴📊**
