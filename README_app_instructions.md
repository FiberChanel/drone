## Drones

:scroll: **START**

### DB

1. Install postgresSQL on your local machine and run
2. Create new DB with name 'drone' and owner 'postgres'. For connect into db use login 'postgres'
   and password 'postgres'

### Build and run

You have two variants for start

1. Simpy run application (migration scripts will be executed automatically)
2. Execute sequentially script from db.migration directory and then run application and run app from
   IntelliJ or Eclipse

### API

1. After application started copy url (http://localhost:8080/swagger-ui/index.html) into browser
2. You can use this page for work with application. For convenience, you can import Postman
   collection, specially created for this. Simply import
   drone-service.postman_collection.json from resources/postman-collections into your Postman app.

### How it works main points

1. Create medications (you can choose medication image from resource directory or something else)
2. Create drones
3. Loading drone
4. Start delivery process for droneId. Use drone-simulation-controller for this.
5. After start the app automatic enabled schedule task with 10 sec period for check battery level
   for each drone.
   For change schedule period modify 'schedule.battery_audit.periodInSecond' application.yaml
   properties. Of course the best way is cron, @Scheduled(cron = ...)

I think that the battery audit mechanism can be implemented through events.
When the battery charge level changes every 5%, for example, send an event and log it to a table.
But I haven't time for this.

You can see all changes into DB. Simply get select from tables if you needed.

### Conditions

- Medications can be created regardless from drone and conversely
- Drones are created without relation with medications. It's just creating an empty drone with
  parameters like a car in a factory
- All created process do with request object and boundary conditions for entities are checked at the
  time of creation
- All drones spent 10% of battery capacity for delivering and 10% when return back
- We do not take into account the distance to the delivery point and return back
- Drone model is simply param and condition for - how many weight they can take on board
- Any delivery ends with the shipment of medications and the drone returns empty
- Lightweight model drone delivers faster then heavy
- The start of delivery involves the internal return of the drone back
- Delivery and return speed depends on the drone model (LIGHTWEIGHT = 5 sec; MIDDLEWEIGHT = 10 sec;
  CRUISERWEIGHT = 15 sec; HEAVYWEIGHT = 20 sec)

### Tests

For start all test right click on test directory and click 'Run Tests in drone.test'

:scroll: **END** 

