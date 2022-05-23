package com.techreturners

class Order {
  var orderSeq = 0
  var orderStatus: OrderStatus.Value = OrderStatus.idle
  var sandwich = 1 // no of sandwich ordered
  var orderTimeUnit = 0 // reference of order time
  var startTimeUnit = 0 // reference of start making order time
  var BillTimeUnit = 0 // reference of start Billing order time
  var NextTimeUnit = 0 // reference of estimated availiable time for Next process
  var billAmount = 0 // store bill amount
  var DurationMake = 60 // require time in second for making a sandwich
  var DurationServe = 30 // require time in second for serve a customer
  var MaxWaitTime = 300 // maximum customer wait time in second for single order

  def place(QueueSeq: Int, CurTimeUnit: Int, noOfSandwich: Int, IsIdle: Boolean): OrderStatus.Value = {
    if (!QueueSeq.isNaN) {
      orderSeq = QueueSeq
      sandwich = noOfSandwich
      orderTimeUnit = CurTimeUnit
      billAmount = orderSeq * 5
      if (IsIdle) {
        make(QueueSeq, CurTimeUnit)
      }
      else {
        orderStatus = OrderStatus.placed
        NextTimeUnit = CurTimeUnit
      }
      orderStatus }
    else {
      OrderStatus.idle
    }
  }

  def make(QueueSeq: Int, CurTimeUnit: Int): OrderStatus.Value = {
    if (QueueSeq == orderSeq && (orderStatus == OrderStatus.placed || orderStatus == OrderStatus.idle)) {
      startTimeUnit = CurTimeUnit
      NextTimeUnit = CurTimeUnit + DurationMake * sandwich
      orderStatus = OrderStatus.making
      orderStatus
    }
    else {
      orderStatus
    }
  }

  def serve(QueueSeq: Int, CurTimeUnit: Int): OrderStatus.Value = {
    if (QueueSeq == orderSeq && orderStatus == OrderStatus.making) {
      startTimeUnit = CurTimeUnit
      NextTimeUnit = CurTimeUnit + DurationServe
      if (CurTimeUnit - orderTimeUnit <= MaxWaitTime )
        orderStatus = OrderStatus.serving
      else
        orderStatus = OrderStatus.cancel
      orderStatus
    }
    else {
      orderStatus
    }
  }

  def bill(QueueSeq: Int, CurTimeUnit: Int): OrderStatus.Value = {
    if (QueueSeq == orderSeq && orderStatus == OrderStatus.serving) {
      startTimeUnit = CurTimeUnit
      NextTimeUnit = CurTimeUnit
      orderStatus = OrderStatus.billed
      orderStatus
    }
    else {
      orderStatus
    }
  }
}
