## Directory Structure

The project is divided into several packages, each serving a specific purpose:

- **config**: Contains configuration classes, such as Spring Security configurations, CORS policies, and any other application-wide settings.

- **controller**: Houses the REST controllers that define the endpoints and handle incoming HTTP requests. Each controller maps to specific business functions and interacts with service classes to fulfill requests.

- **filter (middleware)**: Contains custom filter classes, like authentication or logging filters, that intercept and process HTTP requests before they reach the controllers.

- **init**: Includes classes that initialize data or configurations at the startup of the application. For instance, this can be used to load default values, seed the database, or set up initial configuration.

- **model**: Contains the domain models or entity classes, representing the data structure within the application. Examples might include `Employee`, `Payroll`, and other essential business objects.

- **repository**: It has repository interfaces for data access. These interfaces provide CRUD operations over **models** for the database.

- **security**: Contains security-related classes, such as authentication providers, user details services, and custom security configurations (e.g., password encoders).

- **service**: Holds service classes that contain business logic. Services interact with **repositories** to retrieve and manipulate data and may be used by **controllers** to handle requests.

- **utility**: Contains helper or utility classes for reusable methods that assist in common tasks across the application, such as date formatting or error handling.

- **ServerApplication**: The main entry point for the application.