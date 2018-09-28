# Bank Web Aplication

**UNDER CONSTRUCTION**

This is an example of a web application using Spring Boot 2.0 that aims to be a very simple bank application.
This use case was chosen because of the constraints it enforces, principally when it comes to an account balance.
Some of the constraints it tries to demonstrate is as follows:

+ There are three types of accounts: COMMON, SILVER and GOLD.
+ An account of type COMMON cannot have a negative balance. 
+ SILVER and GOLD accounts can have negative balance at some point.
+ An account must be associate with one agency and one client. 
+ An client must be associate with an agency.

The main goal of the project is not to build a bank application, which is much complex, 
but to be an example with a bit of challenge, using some features Spring Framework offers in validation,
 security, persistence and testing. 

**Disclaimer**: The client model has a document property that uses a brazilian government issue document called CPF in the register of the client.
A CPF must be validated, a valid random CPF can be generate [here](https://www.4devs.com.br/gerador_de_cpf) for test purposes.
Just click on "Gerar CPF" to generate one.
