- [Spring Security 6 Documentation.](#spring-security-6-documentation)
  - [Project Overview](#project-overview)
  - [Starting the Application.](#starting-the-application)
  - [Adding Basic Security to Our Application.](#adding-basic-security-to-our-application)
  - [Configuring Static Credentials Using application.properties.](#configuring-static-credentials-using-applicationproperties)
  


# Spring Security 6 Documentation.

## Project Overview

In this project, I will be creating a Banking Web App using Java, Spring and Spring Security 6.

I will start by creating a very basic Spring application with the most basic level of security using Spring's Security starter dependency. This uses a pre defined username of 'user' and an auto-generated password which can be found in the console and renews each time the program is run.

I will then move onto implementing more secure levels of security and build gradually.

I have mapped the application to a WelcomeController for now so that we can see an end product when testing in the browser:

![Welcome-Page](<imgs/Screenshot 2024-02-26 123447.png>)

## Starting the Application.
The application can be started at local level using an IDE (IntellIJ Idea is my preferred choice), and selecting the 'Run' button, the app will then launch on a Tomcat server on port 8080. We can view the application by going to `http://localhost:8080/welcome`.

Alternatively, the application can be started from the CLI. ensuring your terminal is open in the same directory as the `mvnw` and `pom.xml` files, we can run the following command:
`mvn spring-boot:start`.


## Adding Basic Security to Our Application.

- This approach is not recommended for production purposes!

To add the most basic layer of security to our application, we need to add a dependency to the pom.xml file. This security dependency will require the user to enter a username and password when trying access our application. The pre-defined username will be 'user' and the password will be generated in the console when the application is run. To enable this level of security, we simply need to add the following lines to our pom.xml file:

```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>

```
The issue is that the password is reset every time the application is run and there is only 1 user. This may not be practical even for testing purposes as the password that is generated is long and complex and so we may look to use more secure methods even in testing situations.


## Configuring Static Credentials Using application.properties.

- This approach is not recommended for production purposes!

Using the above configuration, we can set a custom username and password using the `application.properties` file. This is very important file for our spring boot applications and should not be pushed to VCS systems such as github.

we can set the required credentials by adding these lines to the file:

```
spring.security.user.name=<username>
spring.security.user.password=<password>

```

We can now log into the web app using the credentials that we set. We don't need to make any other changes to our code.


