# **GlowUp Tracker API (Java JDBC Project)**

## **A. Project Overview**

### **Purpose of the API**

The purpose of this project is to manage self-care routines and activities using **Java**, **JDBC**, and **PostgreSQL**.
The project demonstrates **CRUD operations**, **object-oriented programming**, and **database interaction**.

### **Entities and Their Relationships**

The project includes three main entities:

* **RoutineType** (e.g. Morning, Night)
* **Routine** (e.g. Glow Up Morning)
* **Activity** (Beauty or Wellness)

**Relationships:**

* One **RoutineType** → many **Routines**
* One **Routine** → many **Activities**

### **OOP Design Overview**

The project applies:

* Abstract classes
* Inheritance
* Interfaces
* Composition
* Polymorphism

The architecture is divided into **model**, **repository**, **service**, and **controller (Main)** layers.



## **B. OOP Design Documentation**

### **Abstract Class and Subclasses**

* **BaseEntity** is an abstract class with common fields (`id`, `name`)
* It defines abstract and concrete methods
* **BeautyCareActivity** and **WellnessActivity** extend the base class

### **Interfaces**

* **Validatable** – validates input data
* **Trackable** – provides activity duration in minutes

Interfaces are implemented in activity classes.

### **Composition / Aggregation**

* **Routine** contains a **RoutineType** object
  This demonstrates composition and reflects a foreign key relationship in the database.

### **Polymorphism**

Activities are processed through a base class reference.
The method `printInfo()` behaves differently for Beauty and Wellness activities.

### **UML Diagram**

The UML diagram is available here:
docs/uml.png

## **C. Database Description**

### **Schema**

Database system: **PostgreSQL**

Tables:

* **routine_types**
* **routines**
* **activities**

### **Constraints and Foreign Keys**

* `routines.type_id` → `routine_types.id`
* `activities.routine_id` → `routines.id`
* Unique constraints prevent duplicate records where required

### **Sample SQL Inserts**

Sample SQL insert statements are included in:

resources/schema.sql

## **D. Controller (CRUD Operations)**

CRUD operations are demonstrated in **Main.java** using the service layer.

### **CRUD Summary**

* **Create** – create new activities
* **Read** – retrieve activities by id or list all
* **Update** – update activity data
* **Delete** – delete activities by id

### **Error Handling**

* Duplicate data → `DuplicateResourceException`
* Invalid input → `InvalidInputException`
* Missing data → `ResourceNotFoundException`


## **E. Instructions to Compile and Run**

### **Steps to Run the Project**

1. Create a PostgreSQL database:

```
selfcare_db
```

2. Execute SQL script:

```
resources/schema.sql
```

3. Configure database credentials in:

```
DatabaseConnection.java
```

4. Run the application:

```
Main.java
```

---

## **F. Screenshots**

Screenshots are located in:

```
docs/screenshots/
```

They demonstrate:

* Successful CRUD operations
* Error handling (duplicate and validation errors)
* Database tables and stored data


## **G. Reflection**

### **What I Learned**

I learned how to build a Java application using JDBC and a multi-layer architecture.
I improved my understanding of OOP concepts in practice.

### **Challenges Faced**

* Configuring PostgreSQL and JDBC driver
* Managing foreign keys and validations
* Implementing custom exceptions correctly

### **Benefits of JDBC and Multi-layer Design**

* JDBC allows precise control over SQL queries
* Layered architecture improves code readability and maintenance
* Business logic is separated from database logic

