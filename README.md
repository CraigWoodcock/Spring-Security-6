- [Spring Security 6 Documentation.](#spring-security-6-documentation)
  - [Project Overview](#project-overview)
  - [Starting the Application.](#starting-the-application)
  - [Adding Basic Security to Our Application.](#adding-basic-security-to-our-application)
  - [Configuring Static Credentials Using application.properties.](#configuring-static-credentials-using-applicationproperties)
  - [Adding SecurityFilterChain](#adding-securityfilterchain)
  - [Configuring Users using InMemoryUserDetailsManager](#configuring-users-using-inmemoryuserdetailsmanager)
  - [Configuring InMemoryUsers without DefaultPasswordEncoder](#configuring-inmemoryusers-without-defaultpasswordencoder)
  - [Creating MySQL database to store User Credentials.](#creating-mysql-database-to-store-user-credentials)
  - [Configuring SecurityConfig to use MySQL database.](#configuring-securityconfig-to-use-mysql-database)
  


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

## Adding SecurityFilterChain

we can add a securityfilterChain to define which endpoints we want to secure. we can do that by creating a config package and a class inside called Securityconfig. this should contain the following code:

- This code snippet will secure all endpoints behind the login screen until the user enters the correct credentials.

```
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

}

```

We can add filters to dictate the endpoints that we want to secure by changing the code to match this:

- This code snippet will allow all access to /notices, /contact and /welcome pages on our webapp. It will only allow authorised users to access all of the remaining endpoints. 

```
@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount","/myCards", "/myLoans", "/myBalance").authenticated()
                .requestMatchers("/notices", "/contact", "/welcome").permitAll());
                http.formLogin(Customizer.withDefaults());
                http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

```

We can Deny all traffic to all endpoints, this might be for testing purposes or when pushing a large update:
We simply replace `.requestMatchers().auntheniticated` and `.requestMatchers.permitAll()` with `anyRequest().denyAll()`

```
@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .anyRequest().denyAll());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
```

Or we can permit all traffic to all endpoints, replace `.denyAll()` with `.permitAll()`:

```
 @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
```

## Configuring Users using InMemoryUserDetailsManager

we can configure our own users directly within our application. This is not recommended for production purposes but can be ideal for testing purposes where more than one developer needs access to the application.

 - This code snippet will create a user called 'admin' with admin authorisation and a user called 'user' with access to 'read' only.
 - The password will be encoded by the DefaultPasswordEncoder but this could still be retrieved in plain text and so this is not a production recommended approach.

```
 @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
```
Since we now have several users being created via the UserDetailsManager, we no longer need the static credentials we hardcoded into our application.properties file. I will comment them out for now, but we can remove them completely:

```
#spring.security.user.name=admin
#spring.security.user.password=password

```

## Configuring InMemoryUsers without DefaultPasswordEncoder

Sometimes we may want/need our passwords to be plain text. We can achieve this by creating users without the password encoder and creating a password encoder bean to create p;ain text passwords;

```
  @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin = User.withUsername("admin")
                .password("password")
                .authorities("admin")
                .build();
        UserDetails user = User.withUsername("user")
                .password("password123")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

     @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

```

## Creating MySQL database to store User Credentials.

A more secure approach to creating and storing users would be to use a database. A good example would be a 'Relational Database' on AWS RDS using MySQL. I chose to use MySQL WorkBench, I can create a script of this database and deploy to aws in a 2 tier architecture later on.

- This MySQL script will create a database, create tables and populate the tables with required data.
- It auto-increments the 'id' for both tables and starts the auto-increment at 101.
- Users table requires a unique 'username'.
- Authorities table uses a combination of username and authority(username+authority) to ensure unique entries.
  - This means we could still have multiple entries with the same username as long as the authority is different for each. 

```
DROP DATABASE IF EXISTS `SpringSecurity`;  

CREATE DATABASE `SpringSecurity`;

USE `SpringSecurity`;

drop table if exists users;

create table users(
 id int auto_increment primary key,
 username varchar(50) unique not null,
 password varchar(50) not null,
 enabled boolean not null
)auto_increment=101;

drop table if exists authorities;

create table authorities (
	id int auto_increment primary key not null,
	username varchar(50) not null,
	authority varchar(50) not null
)auto_increment=101;

create unique index ix_auth_username on authorities (username,authority);


insert into users (username, password, enabled) values ("craig", "password", true);
insert into users (username, password, enabled) values ("john", "john123", true);

insert into authorities(username, authority) values("craig", "admin");
insert into authorities(username, authority) values("john", "read");

select * from users;
select * from authorities;


```

## Configuring SecurityConfig to use MySQL database.
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
```
