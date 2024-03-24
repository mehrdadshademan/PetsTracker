What is the Project
================================================
This is simple application that calculates the number of pets (dogs and cats) outside the power saving zone based on data from different types of trackers.
this application is responsible for inserting data and querying and tracking the pets.
this application provides 3 different Rest methods.

registerTrackerData: This method handles the HTTP POST request to register tracker data for a pet. It takes a PetDto object as the request body, which contains the pet's information. It returns a ResponseEntity containing an optional PetDto object, indicating the success of the registration process.
searchPets: This method handles the HTTP POST request to search for pets based on input criteria. It takes a PetDto object as the request body, which contains the search criteria. It returns a ResponseEntity containing a list of PetDto objects, representing the found pets.
countGroupByPetTypeAndTrack: This method handles the HTTP GET request to retrieve the count of pets grouped by track and pet type. It returns a ResponseEntity containing a list of PetCountResult objects, representing the count of pets

In this application,Cat entity has additional attribute lostTracker and There are two types of trackers for cats (small
and big) and three for dogs (small, medium, big).



How Could Run the project And interact with Application
================================================
The Application is dockerized then you can do the following command and run application
1- create image by below comment:
docker build -t pets .

2- create container with below command:
docker run -d -p 8093:8093 pets

There are 3 endpoints to communicate with application, moreover you can find documentions by swagger in this address
http://localhost:8093/swagger-ui/index.html
you can change port and domain based on docker port and domain 

There are Curl of Rest APIs,
This service count pet which is not in zone and group them by trackType and petType 
curl -X GET "http://localhost:8093/api/v1/pet/tracker/count-pet-trackType"

This service provide inserts data into db , the content is sample  like ownerId is "12" (you can change your input)
curl -X POST http://localhost:8093/api/v1/pet/tracker/register-data \
-H "Content-Type: application/json" \
-d '{
"petType": "CAT",
"trackerType": "BIG",
"ownerId": 12,
"inZone": false,
"lostTracker": false
}'

This Services provides search based on input, the input should be PetDto object and you can fill all or some part of it
this service return all inserted pets with trackerType BIG

curl -X POST http://localhost:8093/api/v1/pet/tracker/search \
-H "Content-Type: application/json" \
-d '{
"trackerType": "BIG"
}'

**The PetDto object is like below :**   

"petType": "CAT",
"trackerType": "BIG",
"ownerId": 12,
"inZone": false,
"lostTracker": false

lostTracker can be Null


How can run the tests:
================================================
There are 2 ways to use test cases of the application,
First, with running the tests on test parts of the application and run each test individually. 

Second, run the tests by below command: 
mvn test -Dfail-fast