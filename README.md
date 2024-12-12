This project was built as part of a coding interview. The company in question requested I put this code up on GitHub. 

The brief was that there was an application that would allow customers to post projects with requirements including a description of the work, the number of hours expected, and the last day and time for accepting bids. I would not build the functionality to post project or bids, but would instead provide an interface that allowed the user to the winning projects and bids. I was allowed to create mock data.

The brief stated that there were 50K registered customers and on average, 100 projects are posted every day. On average, each project receives 50 bids. The tradesperson with the lowest bid for the project automatically wins the project when the deadline is reached.

Tradespeople then bid to work on these projects using either a fixed price or hourly basis. 

The project brief left it up to me to decide how to display the winning bids, but I chose to do this as a web page using web sockets to display the winning projects. I used a h2 DB to store information on the customers and the users.

This project was my first SpringBoot application and was impressive enough for me to eventually get the job.

## Run Instructions

./mvnw spring-boot:run 
Then go to http://localhost:8080


