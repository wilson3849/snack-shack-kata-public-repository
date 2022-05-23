package com.techreturners

object App {

  def main(args: Array[String]): Unit = {
    val Orders = List[(Int, Int)]((0, 2),(0, 1),(0, 3),(0, 2)) //customer order record in format (Timeslot: Int, NoOfSandwich: Int)
    val openTimeUnit = 3600 // open from 0:00 to 60:00 = 60*60 = 3600second // Duration to test the working logic
    var curTimeUnit = 0
    var InProcess = false
    val restaurant = new JobQueue // define new Job Queue for order  handling
    while (curTimeUnit <= openTimeUnit) {
      Orders.foreach(order => if (order._1 == curTimeUnit) InProcess = restaurant.Add(order._2, order._1) > OrderStatus.placed)
      restaurant.MakeOrder(curTimeUnit) // handle order when the sandwich ready for serve
      restaurant.ServeOrder(curTimeUnit) // handle order when the order are ready for bill
      restaurant.ProcessOrder(curTimeUnit) // accept order to make on the condition that kitchen can commit the order in time.
      curTimeUnit += 1
    }
  }

  def someString = "Hi from Tech Returners"
}
