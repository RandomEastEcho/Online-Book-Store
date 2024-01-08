# Online Book Store

 This project aims to deliver best experience of book store, to passion book lovers and readers, right from your house! With usage of <code>JWT</code> tokens to ensure that your time will be not only fun but safe in same time. All users are divided in two categroies <code>ADMIN</code> and <code>USER</code> with different access and rights in this app. Made with use of general <code>REST</code> principles. 

 ## Features
<ul>
  <li> Authentication and registration service</li>
  <li> Get list of all books or certain book</li>
  <li> Get books by it`s categories</li>
  <li> Ability to create shopping cart that attached to specific user</li>
  <li> Ability to add books to orders</li>
  <li> Orders status updates</li>
  <li> Support roles with different access to resources (USER and ADMIN)</li>
  <ul>
    <i><small>List of specific actions avaible only as USER or ADMIN</small></i>
    <li><code>ADMIN</code> can create, delete, update books and categories, change status of order</li>
    <li><code>USER</code> can save orders, get orders history, get lists of items from specific order. Also have ability to add books to shopping cart, delete them or change quantity of specific books</li>
  </ul>
</ul>

## Getting Started
<ol>
  <li> Clone this reprositority</li>
  <li> Send various requests via <code>Postman</code> service</li>
  <li>In <code>.env</code> fill data with your variables.</li>
  <li>Run the following command to build and start the Docker containers <code>docker-compose up --build</code></li>
  <li>Launch your request via Postman service to <code>http://localhost:8081</code></li>
</ol>

## Structure
<ul>
  <li><code>config</code> - contains configuration for Spring (including application context and configuration to beans)</li>
  <li><code>controller</code> - contains endpoints that can resive and handle various HTTP requests</li>
  
  <i><small>Authentication controller</small></i>
  
  | **HTTP method** |      **Endpoint**      | **Role** | **Function**                               |
  |-----------------|------------------------|----------|--------------------------------------------|
  |      POST       | /api/auth/registration |   ALL    | Register a new user                        |
  |      POST       |    /api/auth/login     |   ALL    | Authenticates a user and returns JWT token |
  
  <i><small>Book controller</small></i>
  
  | **HTTP method** |      **Endpoint**      |    **Role**     | **Function**                  |
  |-----------------|------------------------|-----------------|-------------------------------|
  |       GET       |       /api/book/       |   ADMIN, USER   | Get list of all books         |
  |       GET       |     /api/book/{id}     |   ADMIN, USER   | Get specific book by it`s id  |
  |      POST       |       /api/books/      |       ADMIN     | Creating new book             |
  |      DELETE     |    /api/books/{id}     |       ADMIN     | Delete book by it`s id        |
  |       PUT       |    /api/books/{id}     |       ADMIN     | Update existing book          |

  <i><small>Category controller</small></i>

  | **HTTP method** |      **Endpoint**                |    **Role**     | **Function**                      |
  |-----------------|----------------------------------|-----------------|-----------------------------------|
  |       GET       |     /api/categories/             |   ADMIN, USER   | Get list of all categories        |
  |       GET       |     /api/categories/{id}         |   ADMIN, USER   | Get specific category by it`s id  |
  |      POST       |       /api/categories/           |       ADMIN     | Creating new category             |
  |      DELETE     |    /api/categories/{id}          |       ADMIN     | Delete category by it`s id        |
  |       PUT       |    /api/categories/{id}          |       ADMIN     | Update existing category          |
  |       GET       |    /api/categories/{id}/books    |  ADMIN, USER    | Get all books by category`s id    |

  <i><small>Order controller</small></i>

  | **HTTP method** |      **Endpoint**                            | **Role** | **Function**                              |
  |-----------------|----------------------------------------------|----------|-------------------------------------------|
  |      POST       |  /api/orders                                 |   USER   | Save and submit users order               |
  |      GET        |  /api/orders                                 |   USER   | Get users order history                   |
  |      GET        |  /api/orders/{orderId}/items                 |   USER   | Get list of all items from chosen order   |
  |      GET        |  /api/orders//{orderId}/items/{orderItemId}  |   USER   | Finds exact item from chosen order        |
  |      PATCH      |  /api/orders/{orderId}                       |   ADMIN  | Changes order status by order id          |

  <i><small>Shopping cart controller</small></i>

  | **HTTP method** |  **Endpoint**                       | **Role**         | **Function**                                    |
  |-----------------|-------------------------------------|------------------|-------------------------------------------------|
  |      GET        |  /api/cart/{id}                     |   ADMIN, USER    | Get shopping cart by it`s id                    |
  |      POST       |  /api/cart                          |   USER           | Adds books to users shopping cart               |
  |      PUT        |  /api/cart/cart-items/{cartItemId}  |   USER           | Change number of books in users shopping cart   |
  |      DELETE     |  /api/cart/cart-items/{cartItemId}  |   USER           | Delete book from shopping car                   |
  
  <li><code>dto</code> - contains Data Transfer Objects that helps to unify requests and responses in the controllers.</li>
  <ul>
    <li><code>request</code> - Data Transfer Objects for response</li>
    <li><code>response</code> - Data Transfer Objects for request</li>
  </ul>
  <li><code>exception</code> - contains custom exceptions for handling exceptions in registration and while working with entities.</li>
  <li><code>lib</code> - contains password validator.</li>
  <li><code>mapper</code> - contains mapper for entities.</li>
  <li><code>model</code> - contains a models of the objects that the application works with.</li>
  <li><code>repository</code> - contains a repositories of objects that the application works with.</li>
  <li><code>security</code> - contains authentication service, jwt tokens providers and custom user details.</li>
  <li><code>service</code> - contains services interface and implementations that are responsible for performing business logic and coordinating the interactions between the controllers and the DAO.</li>
</ul>

## Used technologies
<ul>
 <li>Java 17</li>
 <li>Maven</li>
 <li>Spring boot</li>
  <li>Spring JPA</li>
  <li>Spring Security</li>  
 <li>Lombok</li>
 <li>MapStruct</li>
 <li>MySql 8</li>
 <li>Hibernate</li>
 <li>Liquibase</li>
 <li>JUnit5</li>
  <li>Mockito</li>
 <li>Docker</a></li>
 <li>Swagger</li>
</ul>

## Authors
<a href="https://github.com/RandomEastEcho">Gleb Iaroshevych</a>
