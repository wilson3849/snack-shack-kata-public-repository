package com.techreturners

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AppTest extends AnyFlatSpec with Matchers{

  "A string from the app" should "be Hi from Tech Returners" in {
    App.someString should be ("Hi from Tech Returners")
  }

  "Order Status Set Definition Test" should
    "return correct Status code." in {
    assert(OrderStatus(-1) == OrderStatus.cancel)
    assert(OrderStatus(0) == OrderStatus.idle)
    assert(OrderStatus(1) == OrderStatus.placed)
    assert(OrderStatus(2) == OrderStatus.making)
    assert(OrderStatus(3) == OrderStatus.serving)
    assert(OrderStatus(4) == OrderStatus.billed)
  }

  "Order Job Queue Operation Test" should
    "get the operation parameter " in {
    val Queue = new JobQueue
    assert(Queue.QueueItems.count(_.orderSeq > 0) == 0)
  }

  "Job item Operation Test" should
    "return correct status on each stage" in {
    val OrderItem1 = new Order
    val OrderItem2 = new Order
    // init order
    assert(OrderItem1.orderStatus == OrderStatus.idle)
    assert(OrderItem2.orderStatus == OrderStatus.idle)
    // place order1
    OrderItem1.place(1,0,1,true: Boolean)
    assert(OrderItem1.orderStatus == OrderStatus.making)
    assert(OrderItem1.NextTimeUnit == 60)
    // place order2
    OrderItem2.place(1,0,1,false: Boolean)
    assert(OrderItem2.orderStatus == OrderStatus.placed)
    assert(OrderItem2.NextTimeUnit == 0)
    // make order1
    OrderItem1.serve(1,60)
    assert(OrderItem1.orderStatus == OrderStatus.serving)
    assert(OrderItem1.NextTimeUnit == 90)
    // bill order 1
    OrderItem1.bill(1,90)
    assert(OrderItem1.orderStatus == OrderStatus.billed)
    assert(OrderItem1.NextTimeUnit == 90)
  }

  "Order Queue Operation Test" should
    "return correct status on each stage" in {
    val Orders = List[(Int, Int)]((0, 2),(0, 1),(0, 3),(0, 2))
    val Queue = new JobQueue
    assert(Queue.QueueItems.count(_.orderSeq > 0) == 0)
    assert(Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Add order 1 success
    Queue.Add(Orders.head._2,Orders.head._1)
    assert(Queue.QueueItems.head.orderStatus == OrderStatus.making)
    assert(Queue.QueueItems.head.NextTimeUnit == 120)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Add order 2 success
    Queue.Add(Orders(1)._2,Orders(1)._1)
    assert(Queue.QueueItems(1).orderStatus == OrderStatus.placed)
    assert(Queue.QueueItems(1).NextTimeUnit == 0)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Add order 3 rejected more than 5:00 wait time
    assert(Queue.Add(Orders(2)._2,Orders(2)._1) == OrderStatus.cancel)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Add order 4 success
    Queue.Add(Orders(3)._2,Orders(3)._1)
    assert(Queue.QueueItems(2).orderStatus == OrderStatus.placed)
    assert(Queue.QueueItems(2).NextTimeUnit == 0)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 1 ready for serve
    Queue.MakeOrder(120)
    assert(Queue.QueueItems.head.orderStatus == OrderStatus.serving)
    assert(Queue.QueueItems.head.NextTimeUnit == 150)
    assert(Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 2 start make
    Queue.ProcessOrder(120)
    assert(Queue.QueueItems(1).orderStatus == OrderStatus.making)
    assert(Queue.QueueItems(1).NextTimeUnit == 180)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 1 complete
    Queue.ServeOrder(150)
    assert(Queue.QueueItems.head.orderStatus == OrderStatus.billed)
    assert(Queue.QueueItems.head.NextTimeUnit == 150)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 2 serving
    Queue.MakeOrder(180)
    assert(Queue.QueueItems(1).orderStatus == OrderStatus.serving)
    assert(Queue.QueueItems(1).NextTimeUnit == 210)
    assert(Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 3 start make
    Queue.ProcessOrder(180)
    assert(Queue.QueueItems(2).orderStatus == OrderStatus.making)
    assert(Queue.QueueItems(2).NextTimeUnit == 300)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 2 completed
    Queue.ServeOrder(210)
    assert(Queue.QueueItems(1).orderStatus == OrderStatus.billed)
    assert(Queue.QueueItems(1).NextTimeUnit == 210)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // Order 3 start make
    Queue.ProcessOrder(210)
    assert(Queue.QueueItems(2).orderStatus == OrderStatus.making)
    assert(Queue.QueueItems(2).NextTimeUnit == 300)
    assert(!Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // order 3 serving
    Queue.MakeOrder(300)
    assert(Queue.QueueItems(2).orderStatus == OrderStatus.serving)
    assert(Queue.QueueItems(2).NextTimeUnit == 330)
    assert(Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
    // order 3 complete
    Queue.ServeOrder(330)
    assert(Queue.QueueItems(2).orderStatus == OrderStatus.billed)
    assert(Queue.QueueItems(2).NextTimeUnit == 330)
    assert(Queue.IsMakeIdle)
    assert(Queue.IsServeIdle)
  }
}
