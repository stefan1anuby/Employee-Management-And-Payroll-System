CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Users (
    ID UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    Username VARCHAR(255) NOT NULL UNIQUE,
    Email VARCHAR(255) NOT NULL UNIQUE,
    HashedPassword TEXT,
    CreatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Departments (
    department_id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    location VARCHAR(100)
);

CREATE TYPE employee_role AS ENUM ('Manager', 'HR');

CREATE TABLE Businesses (
    business_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(100),
    industry VARCHAR(100)
);

CREATE TABLE Employees (
    employee_id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    phone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    role employee_role NOT NULL,
    salary DECIMAL(10, 2),
    team VARCHAR(255),
    managed_employee_ids INT[] DEFAULT ARRAY[]::INT[],
    department_id INT REFERENCES Departments(department_id) ON DELETE SET NULL,
    business_id INT REFERENCES Businesses(business_id) ON DELETE SET NULL
);
