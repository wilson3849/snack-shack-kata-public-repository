# Operation Instruction
This manual is the operation procedure for use snack-shack-kata project.
## Project Aim
The scenrio is designed for:
- simulate 1 shop/1 kitchen/1 cashier operation with a limited opening time to serve customer with 1 or more sandwich on single order
- maximum wait time for customer is assigned to 300 second
- use assigned customer order queue with element in List(timeslot Int, NoOfSandwich Int): List[tuple]

## Setup the environment
1. clone the source repository from main branch of GITHub project.
2. Open with Intellij and open directory from the download location of the repository
3. wait for the automatic setup from Intellij autoload

## Modules in package
1. App - Main application of project
2. OrderStatus - Custom type of Order Status
3. JobQueue - Queue navigation on handling elements for order making/serving/taking from concurrent order list
4. Order - Order process for individual order element

## Custom the order environment
1. Order Queue: 
   In App.scala file line 6,7
   - line 6: val Orders = List[(Int, Int)]((0, 2),(0, 1),(0, 3),(0, 2)) //customer order record in format (Timeslot: Int, NoOfSandwich: Int)
     - Orders can be update in order element pairs in format  (timeslot Int, NoOfSandwich Int): List[tuple] 
     - i.e. (10, 3) = 10 second from start of open time slot and order for 3 sandwich
   - line 7: val openTimeUnit = 3600 // open from 0:00 to 60:00 = 60*60 = 3600second // Duration to test the working logic
     - No of Second for duration of shop open   
2. Order handling time definition
   - In Order.scala file line 12,13,14
     - line 12: var DurationMake = 60 // require time in second for making a sandwich
     - line 13: var DurationServe = 30 // require time in second for serve a customer
     - line 14: var MaxWaitTime = 300 // maximum customer wait time in second for single order
3. JobQueue waittime overlimit calculation definition
   - In JobQueue.scala file line 12,13,14
     - line 12: var MakeTime: Int = 60
     - line 13: var ServeTime: Int = 30
     - line 14: var MaxWaitTime: Int = 300

##Run the applicaion
- run "sbt run" in console terminal under the root directory of project 

##Test the application
- run "sbt test" in console terminal under the root directory of project