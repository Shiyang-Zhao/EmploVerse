# Employee Management System

This project is an Employee Management System built using Spring Boot for the backend and Next.js with the App Router for the frontend. The system includes functionality for managing users, roles, employees, departments, and projects. It also integrates JWT and OAuth2 for authentication and authorization.

## Features

- User, Role, Employee, Department, and Project models
- JWT and OAuth2 authentication
- Basic CRUD operations
- Attendance tracking
- Project management
- Payroll management
- Performance rating

## Technologies Used

### Backend

- **Spring Boot**: Backend framework for building Java applications
- **Spring Security**: For implementing security features like JWT and OAuth2
- **Hibernate**: For ORM (Object-Relational Mapping)
- **MySQL**: Database for storing data

### Frontend

- **Next.js**: React framework with the latest App Router features
- **React**: JavaScript library for building user interfaces
- **Tailwind CSS**: Utility-first CSS framework for styling

## Usage

- **API Documentation:** The backend provides a set of RESTful APIs for interacting with the system. You can access the API documentation at `http://localhost:8080/swagger-ui.html`.
- **Frontend Interface:** Access the frontend application at `http://localhost:3000`.

### Authentication

The system uses JWT and OAuth2 for authentication and authorization. You can configure the OAuth2 settings in the backend's `application.properties` file.

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgements

- Spring Boot and Spring Security teams for the robust frameworks
- Next.js and React teams for the modern frontend tools
- Tailwind CSS for the sleek UI components
