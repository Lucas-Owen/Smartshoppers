# Smartshoppers
Smartshoppers is a web application that makes it possible for retail chain stores to allow customers to virtually browse their in-store products as a way to improve customers' shopping experience. This system uses a postgre sql database containing information regarding stores, each store has managers who can track and update their stores inventory. It is a maven project. The front end is built by vaadin. The spring framework is used for dependency injection and security. JUnit is used for unit testing.  
  
The project is still a work in progress. 

### Customer
Customers are individuals part of the general public. They require the ability to view the website, 
login/logout, select a store, browse store inventory, interact with shopping lists, add/remove items 
to/from their shopping list and to view their shopping list.  
  
### Manager
Manager’s have the ability to update item information in inventory as well as update items’ stock 
count for the stores they manage. They also require the ability to put items on sale for a chosen 
amount of time.  
  
### Administrator
The administrators role is to manage the overall system including managing the managers, stores 
and items in stores. This means they need the ability to add/remove and edit the aforementioned 
groups.  
  
### Logging in
There are three authenticated users for each fresh run since the app uses an in memory h2 database;  
Username    Password  
admin       admin  
manager     manager  
customer    customer  
