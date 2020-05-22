# NinjaManagement

This is a simple CRUD web application with the subject of "Ninja". Meaning, in this web app the user is able to add, read, delete and update a "Ninja" object. Moreover, this web-app consumes the services of the "ninja-rest-api" web service, which is considered the back-end in this case. As such, the resource of the back-end's REST API is the "Ninja" object and the provided HTTP methods are the GET, POST, PUT and DELETE.

It is comprised of front-end and back-end parts. The front-end is a single web-page built with HTML5, CSS3 and JQuery. It also uses the technologies of Ajax and Moustache.js (logicless template language). The back-end on the other hand is a RESTful web service (API), which is built with JavaEE and also based on the JAX-RS specifications. Its database part is based on JDBC and MySQL. Finally, the communication between these two parts is based exlusively on the exchange of JSON messages, according to the REST rules.

This web application is deployed on Heroku PaaS and thus live demo is available: [Live Demo at Heroku](https://ninja-management.herokuapp.com/Add domain)
