package com.techreturners

object OrderStatus extends Enumeration {
  type OrderStatus = Value

  val cancel: Value = Value(-1)
  val idle: Value = Value(0)
  val placed: Value = Value(1)
  val making: Value = Value(2)
  val serving: Value = Value(3)
  val billed: Value = Value(4)
}
