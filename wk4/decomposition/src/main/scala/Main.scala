@main def hello: Unit = 
  println("Hello world!")


trait Expr:
  def isNumber: Boolean
  def isSum: Boolean
  def numValue: Int
  def leftOp: Expr
  def rightOp: Expr

class Number(n: Int) extends Expr:
  def isNumber = true
  def isSum = false
  def numValue = n
  def leftOp = throw Error("Number.leftOp")
  def rightOp = throw Error("Number.rightOp")

class Sum(e1: Expr, e2: Expr) extends Expr:
  def isNumber = false
  def isSum = true
  def numValue = throw Error("Sum.numValue")
  def leftOp = e1
  def rightOp = e2

def eval(e: Expr): Int = 
  if e.isNumber then e.numValue
  else if e.isSum then eval(e.leftOp) + eval(e.rightOp)
  else throw Error("Unknown expression " + e)

/*
  If we consider the eval method using type tests and casts
  it gets ugly and unsafe
*/

def eval2(e: Expr): Int = 
  if e.isInstanceOf[Number] then e.asInstanceOf[Number].numValue
  else if e.isInstanceOf[Sum] then eval(e.asInstanceOf[Sum].leftOp) + eval(e.asInstanceOf[Sum].rightOp)
  else throw Error("Unknown expression" + e)