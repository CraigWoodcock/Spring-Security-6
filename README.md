- [Spring Security 6 Documentation.](#spring-security-6-documentation)
  - [Project Overview](#project-overview)
  - [Starting the Application.](#starting-the-application)
  - [Adding Basic Security to Our Application.](#adding-basic-security-to-our-application)
  - [Configuring Static Credentials Using application.properties.](#configuring-static-credentials-using-applicationproperties)
  - [Adding SecurityFilterChain](#adding-securityfilterchain)
  - [Configuring Users using InMemoryUserDetailsManager](#configuring-users-using-inmemoryuserdetailsmanager)
  - [Configuring InMemoryUsers without DefaultPasswordEncoder](#configuring-inmemoryusers-without-defaultpasswordencoder)
  - [Creating MySQL database to store User Credentials.](#creating-mysql-database-to-store-user-credentials)
  - [Using AWS RDS to Create a MySQL Database.](#using-aws-rds-to-create-a-mysql-database)
    - [How to Create an AWS RDS MySQL Database.](#how-to-create-an-aws-rds-mysql-database)
  - [Configuring the Application to use MySQL database.](#configuring-the-application-to-use-mysql-database)
  - [Configuring Custom Tables and custom UserDetailsService.](#configuring-custom-tables-and-custom-userdetailsservice)
  


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

Sometimes we may want/need our passwords to be plain text. We can achieve this by creating users without the password encoder and creating a password encoder bean to create plain text passwords. The NoOpPasswordEncoder doesn't encode passwords and so the IDE will usually warn us that this is an unsafe approach. It is fine for testing purposes. 

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

## Using AWS RDS to Create a MySQL Database.

Using the previous approach could pose certain risks such as a loss of data due to disaster etc, as the database would be stored locally and then migrated to an aws ec2 instance. Another approach could be to create an AWS RDS Database using MySQL, we can then connect MySQL workbench to the AWS RDS instance to create the required fields. We can achieve this by executing the same script as in the previous method.

 ### How to Create an AWS RDS MySQL Database.
 1. Sign into AWS Console, head to 'RDS' and Select 'Create Database'.
 2. Select 'Standard Create' and then Select 'MySQL' under 'Engine options'.
 3. We are only using a small database and so to save costs, be sure to select 'Free Tier' under 'Templates'
 4. Now set the database name(this must be unique across all instances owned by your AWS account in the current region), username, password.
 5. Under 'Connectivity', We need to change the 'Public Access' setting, We need to choose 'Yes'.
 6. If you have a Security Group already then you can select this now. If not, create one with inbound rules to allow all traffic on port 3306(MySQL).
 7. Now select 'Create Database'
 8. Now go to the database settings an copy the 'Endpoint',
 9. Head to MySQL workbench, Create a new Connection and paste the endpoint in as the hostname, Ensure the port is 3306, enter the database username, select 'Store in Vault...' and enter the database password. Now 'test connection'. If successful, press 'OK'.
 10. Now we can run the sql script from earlier to create the database and tables.      

## Configuring the Application to use MySQL database.
 
First we need to add the following dependencies to the pom.xml file:
  - jdbc allows spring to communicate with the database.
  - jpa allows spring to create entities from the database and includes Hibernate for ORM(Object-Relational Mapping).
  - mysql driver ensures that the application can connect to the MySQL database.

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


As we are no longer using InMemoryUserDetailsManager, we can comment out or delete this method:

```
    // create users without defaultPassowrdEncoder
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//        UserDetails admin = User.withUsername("admin")
//                .password("password")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("password123")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

```

Instead, we will be calling UserDetailsService and passing in Datasource:
 - This will allow our spring app to use the datasource to retrieve user details from the database.
 - This methof uses jdbcUserDetailsManager.

```
 @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

```

Now we need to add the database details to our application.properties file so that our application can communicate with the database. Add the following lines:

 - Important Note! - We only add these credentials to the application.properties file for testing purposes. For a production ready deployment, we would give these values to our DevOps team who will inject them as Environment variables!

```
spring.datasource.url=jdbc:mysql://<aws-endpoint>:3306/<DatabaseName>  - This points to the AWS database.
spring.datasource.username=<db username>
spring.datasource.password=<db password>

```
If using a local database then we can modify the url as follows:

```
spring.datasource.url=jdbc:mysql://localhost:3306/SpringSecurity
```

## Configuring Custom Tables and custom UserDetailsService.

We can use our own custom tables to perform authentication instead of the jdbc pre defined tables.
We add the following table to the database:

```
CREATE TABLE `customer` (
  `id` int auto_increment primary key,
  `username` varchar(45) unique not null, 
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL
);

INSERT INTO `customer` (`username`, `email`, `pwd`, `role`) VALUES ('john', 'johndoe@example.com', '12345', 'admin');


```

Then in IntellIJ we create an entity of the table (Customer) and a repository (CustomerRepository).
 
We then create a custom SecurityConfig file as follows:

- This file resides in the 'config' package. As the class performs com plex operations, it acts as a 'Service Layer' and so we annotate the class with @Service. Without this annotation, we cannot use the '@Autowired' annotation.
- The method checks that the entered username is stored in the database, if it is then the 'customer' object is returned and authenticated.

```
@Service
public class SimpleBankUserDetails implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName,password = null;
        List<GrantedAuthority> authorities = null;
        List<Customer> customer = customerRepository.findByUsername(username);
        if(customer.size() ==0){
            throw new UsernameNotFoundException("Username Not Found for user: "+username);
        }else{
            userName = customer.get(0).getUsername();
            password = customer.get(0).getPwd();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customer.get(0).getRole()));
        }

        return new User(username,password,authorities);
    }
}

```

As it stands, we now have two authentication providers, Our custom config class(SimpleBankUserDetails) is overriding the loadUserByUsername method from the UserDetailsService class, we have a conflict in our code, as the jdbcUserDetailsManager also uses the same class. 

To correct this, we need to remove or comment out the following from our SecurityConfig class:

```
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

```

The SecurityConfig class should now look like this:

```
@Configuration
    public class SecurityConfig {

        // Custom security filter allows authenticated traffic to secured endpoints and allows all traffic to
        // unsecured endpoints.
        @Bean
        SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/myAccount","/myCards", "/myLoans", "/myBalance").authenticated()
                    .requestMatchers("/notices", "/contact", "/welcome").permitAll());
                    http.formLogin(Customizer.withDefaults());
                    http.httpBasic(Customizer.withDefaults());
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return NoOpPasswordEncoder.getInstance();
        }
    }

```