package com.techreturners

class JobQueue {
  var QueueItems: List[Order] = List()
  var ServiceStatus: OrderStatus.Value = OrderStatus.idle
  var OrderSeq: Int = 0
  var CurTimeUnit: Int = 0
  var NewOrderStartTimeUnit: Int = 0
  var PendingOrder: Int = 0
  var IsMakeIdle: Boolean = true
  var IsServeIdle: Boolean = true
  var MakeTime: Int = 60
  var ServeTime: Int = 30
  var MaxWaitTime: Int = 300

  def PrintLog(InTimeUnit: Int, Message: String): Boolean = {
    Console.println(s"${FormatTimeString(InTimeUnit)}: $Message >> total Queue time:${FormatTimeString(NewOrderStartTimeUnit)}")
    true
  }

  def TwoDigit(NumberDigits: Int): String = {
    if(NumberDigits > 9) { NumberDigits.toString } else { "0" + NumberDigits.toString }
  }

  def FormatTimeString(timeUnit: Int): String = s"${TwoDigit(timeUnit / 60)}:${TwoDigit(timeUnit % 60)}"

  def ServeOrder(InTimeUnit: Int): OrderStatus.Value = {
    QueueItems
      .filter(order => order.orderStatus == OrderStatus.serving)
      .filter(order => order.NextTimeUnit == InTimeUnit)
      .sortBy(_.orderSeq)
      .foreach(order =>
        if(IsServeIdle) {
        order.orderStatus match {
          case OrderStatus.serving =>
            IsServeIdle = order.bill(order.orderSeq, InTimeUnit) == OrderStatus.billed
            ServiceStatus = order.orderStatus
            PrintLog(InTimeUnit, s"start bill ${order.sandwich} sandwich (${order.orderSeq})")
          case _ =>
            PrintLog(InTimeUnit, s"No Action is taken.")
        }
      })
    ServiceStatus
  }

  def MakeOrder(InTimeUnit: Int): OrderStatus.Value = {
    QueueItems
      .filter(order => order.orderStatus == OrderStatus.making)
      .filter(order => order.NextTimeUnit == InTimeUnit)
      .sortBy(_.orderSeq)
      .foreach(order => {
          order.orderStatus match {
            case OrderStatus.making =>
              order.serve(order.orderSeq, InTimeUnit)
              ServiceStatus = order.orderStatus
              IsMakeIdle = (order.orderStatus == OrderStatus.serving)
              PrintLog(InTimeUnit, s"start serve ${order.sandwich} sandwich (${order.orderSeq},${order.orderStatus},${FormatTimeString(order.startTimeUnit)},${FormatTimeString(order.orderTimeUnit)},${FormatTimeString(order.NextTimeUnit)})")
            case _ =>
              PrintLog(InTimeUnit, s"No Action is taken.")
          }
      })
    ServiceStatus
  }

  def ProcessOrder(InTimeUnit: Int): OrderStatus.Value = {
    QueueItems
      .filter(order => order.orderStatus == OrderStatus.placed)
      .sortBy(_.orderSeq)
      .foreach(order =>
        if(IsMakeIdle) {
            order.orderStatus match {
              case OrderStatus.placed =>
                IsMakeIdle = order.make(order.orderSeq, InTimeUnit) != OrderStatus.making
                ServiceStatus = order.orderStatus
                if (order.orderStatus == OrderStatus.making)
                  PrintLog(InTimeUnit, s"start make ${order.sandwich} sandwich (${order.orderSeq}) at ${FormatTimeString(order.NextTimeUnit)}")
                else
                  PrintLog(InTimeUnit, s"accept ${order.sandwich} sandwich order: (${order.orderSeq})")
              case _ =>
                PrintLog(InTimeUnit, s"No Action is taken.")
            }
        })
    ServiceStatus
  }

  def Add(noOfSandwich: Int, InTimeUnit: Int) : OrderStatus.Value = {
      if (NewOrderStartTimeUnit + (noOfSandwich * MakeTime) <= (InTimeUnit + MaxWaitTime)) {
        OrderSeq += 1
        CurTimeUnit = InTimeUnit
        val order = new Order
        ServiceStatus = order.place(OrderSeq, CurTimeUnit, noOfSandwich, IsMakeIdle)
        IsMakeIdle = IsMakeIdle && (ServiceStatus == OrderStatus.idle || ServiceStatus == OrderStatus.placed || ServiceStatus == OrderStatus.billed)
        QueueItems = QueueItems :+ order
        PendingOrder += 1
        NewOrderStartTimeUnit += (noOfSandwich * MakeTime)
        if (order.orderStatus == OrderStatus.making)
          PrintLog(InTimeUnit, s"start make ${order.sandwich} sandwich (${order.orderSeq}), estimate ready at ${FormatTimeString(order.NextTimeUnit)} ")
        else
          PrintLog(InTimeUnit, s"start take ${order.sandwich} sandwich order (${order.orderSeq})")
      } else {
        PrintLog(InTimeUnit, s"waitress reject $noOfSandwich sandwich order")
        ServiceStatus = OrderStatus.cancel
      }
      ServiceStatus
  }
}
