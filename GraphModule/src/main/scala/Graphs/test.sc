// Basics Worksheet

// Setup links
// JDK compatibility: https://docs.scala-lang.org/overviews/jdk-compatibility/overview.html
// Installing Scala with Coursier: https://docs.scala-lang.org/getting-started/index.html
// Online Scala worksheet: https://scastie.scala-lang.org

// Primitive expressions: represent the simplest elements
1
"hello"
true

// Expressions: more complex expressions expressions can be created by combination using operators
// https://scala-lang.org/api/3.x/scala/Int.html
1 + 2
"Hello, " ++ "World"

// Evaluation: reducing an expression to a value
(1 + 2) * 3
3 * 3
9

// Method calls
// Another way to produce complex expressions, is to call methods on expression using the dot notation
"Hello, world".length
1.to(10)
1.toString

// Operators are methods, usually using symbols
42.+(1)
42.+(1) == 42 + 1
assert(42.+(1) == 42 + 1)

// The compiler statically checks that you don’t combine incompatible expressions (types)
// This is called static typing
// In practice: be explicit on types
val x: Int = 42
x

// Naming things, using meaningful names
val base: Int = 4
val height: Int = 6
val area = base * height / 2
s"area of triangle is equal to $area"

// Immutable
val z = 41
// z = 42 // does not work, cannot reassign a value to a `val`

// Mutable
var z2 = 41
z2
z2 = 42
z2

// Immutable collections
// https://www.scala-lang.org/api/2.12.7/scala/collection/immutable/List.html
val xs = List(1, 2, 3)

// The list `ys` is mutable even if bound to a `val`
import scala.collection.mutable.ListBuffer
val ys = ListBuffer(1, 2)
ys.length
ys.append(3)
ys.length

// Functions: parameters are listed with their types
// In practice: be explicit on returned type
def identity(x: Int): Int = x
assert(identity(x) == x)

// Sum of two integers
def sum(x: Int, y: Int): Int = x + y
assert(sum(1, 2) == 3)

// Possible to give a default value to a parameter
// In practice: use this carefully
def sumWithDefault(x:Int, y: Int = 0): Int = sum(x, y)
sumWithDefault(1, 1)
sumWithDefault(1)
// Can also be useful to explicitly name parameters
// For functions with a lot of parameters, default values and/or more readability
sumWithDefault(x=1, y=1)

// Sum of two integers, renamed
def sumOfInts(x: Int, y: Int): Int = x + y
sumOfInts(2, 3)

// Sum of the square of each parameter
def sumOfSquares(x: Int, y: Int): Int = x * x + y * y
sumOfSquares(2, 3)

// Sum of the predecessor of each parameter
def sumOfPredecessors(x: Int, y: Int): Int = x - 1 + y - 1
sumOfPredecessors(2, 3)

// High-order function: a function that takes another function as a parameter
def sumOf(x: Int, y: Int, f: Int => Int): Int =  f(x) + f(y)
sumOf(1, 2, identity)

// Functions: expression evaluation is called the substitution model
// This model can be applied to all expressions, as long as they have no side effects
// The λ-calculus is a foundation for functional programming and formalises this model
sumOf(1, 1 + 1, identity)
sumOf(1, 2, identity)
identity(1) + identity(2)
1 + identity(2)
1 + 2
3

def square(a: Int): Int = a * a

def predecessor(b: Int): Int = b -1

sumOf(2, 3, square)
sumOf(2, 3, predecessor)

// The function passed as an argument using a `val`
val identityVal: Int => Int = x => x
sumOf(2, 3, identityVal)

// Or even anonymous function
sumOf(2, 3, x => x)

// `val` evaluates when defined
// `def` evaluates on every call

// A function with no parameter that returns a function Int => Int
// Creates a new function every time the function is called
// The type if squareDef is Function0[Function1[Int, Int]
def squareDef(): Int => Int = {
  print(">>>def<<<")
  x => x * x
}

// A value, a single function Int => Int, evaluated at definition
val squareVal: Int => Int = {
  print(">>>val<<<")
  x => x * x
}

// A value, a single function Int => Int, evaluated when used for the first time
lazy val squareLazyVal: Int => Int = {
  print(">>>lazy val<<<")
  x => x * x
}

sumOf(2, 3, squareDef())
sumOf(2, 3, squareDef())

sumOf(2, 3, squareVal)
sumOf(2, 3, squareVal)

sumOf(2, 3, squareLazyVal)
sumOf(2, 3, squareLazyVal)

// Call-by-value and call-by-name
// Call-by-value and call-by-name are two evaluation strategies
// Both strategies reduce to the same final values as long as
// - the reduced expression consists of pure functions, and
// - both evaluations terminate.
// Call-by-value evaluates every function argument only once, even if not used in the function body.
// Call-by-name does not evaluate if the corresponding parameter is unused in the evaluation of the function body.
// Scala normally uses call-by-value.

def time(): Long = {
  println("Executing time()")
  System.nanoTime
}

// `t` is now defined as a by-value parameter
// `time()` is called once
def execByValue(t: Long): Long = {
  println("Entered exec, calling t...")
  println(s"t is $t")
  println("Calling t again...")
  t
}

execByValue(time())

// `t` is now defined as a by-name parameter
// `time()` is called twice
def execByName(t: => Long) = {
  println("Entered exec, calling t...")
  println(s"t is $t")
  println("Calling t again...")
  t
}

execByName(time())

// `t` is now defined as a by-value parameter
// `time()` is called once
def execConstantByValue(t: Long): Long = 35221213909789L

execConstantByValue(time())

// `t` is now defined as a by-name parameter
// `time()` is never called
def execConstantByName(t: => Long) = 35221213909789L

execConstantByName(time())
// Functions Worksheet

// Boolean and conditional expressions
// Boolean expressions: constants, negation, conjunction, disjunction, comparisons
// Reduction also to boolean expressions
true
!false
42 == 42
42 != 43
val x = 12
x > 0 && x != 42
x > 0 || x != 12

// Conditional expressions: `if-else` looks like Java but remember it's an expression not a statement
if (x < 0) -x else x

// Write a function `repeat` that concatenates the message `message` `iteration` times:
// 1) think about the function signature
// 2) think about the algorithm
// 3) implement the function body (you can use `var`s, for now...)
def repeat(iteration: Int, message: String): String = {
  var result = ""
  var i = iteration
  while (i > 0) {
    result += message
    i = i - 1
  }
  result
}

repeat(3, "bye")
repeat(1, "bye")
repeat(3, "bye")

(repeat _).isInstanceOf[Function2[Int, String, String]]

// Recursion:
// A recursive function is a function that calls itself
// Minimize `for` loops and mutable variables when reading code
// See: https://en.wikipedia.org/wiki/The_Magical_Number_Seven,_Plus_or_Minus_Two#

// Recursion, updated approach:
// 1) what is the function signature?
// 2) what is the end condition for this algorithm?
// 3) what is the actual algorithm?
def repeatRec(iteration: Int, message: String): String = {
  if (iteration == 1) message
  else message + repeatRec(iteration = iteration - 1, message)
}

repeatRec(2, "bye")

// (Optional) Recursion: A Conversation Between Two Developers (from "The Little Schemer")
// See: https://fpsimplified.com/scala-fp-Recursion-Conversation.html
def sum(xs: List[Int]): Int = {
  if (xs.isEmpty) 0
  else xs.head + sum(xs.tail)
}

sum(List(1, 2, 3))

// Exercise 1: write a function that computes the greatest common divisor of two numbers
// 1) apply the methodology above
// 2) show how `gcd(14, 21)` is evaluated
def gcd(a: Int, b: Int): Int =
  if (b == 0) a else gcd(b, a % b)

gcd(14, 21)
// gcd(14, 21)
// if (21 == 0) 14 else gcd(21, 14 % 21)
// if (false) 14 else gcd(21, 14 % 21)
// gcd(21, 14 % 21)
// gcd(21, 14)
// if (14 == 0) 21 else gcd(14, 21 % 14)
// if (false) 21 else gcd(14, 21 % 14)
// gcd(14, 7)
// gcd(7, 14 % 7)
// gcd(7, 0)
// if (0 == 0) 7 else gcd(0, 7 % 0)
// if (true) 7 else gcd(0, 7 % 0)
// 7

// Using multiple parameter lists
def anotherRepeat(iteration: Int)(message: String): String = {
  if (iteration == 1) message
  else message + anotherRepeat(iteration = iteration - 1)(message)
}

anotherRepeat(2)("bye")

// Benefits of multiple parameter lists:
// - when writing your own control structures or DSLs
// - to group parameters by proximity or meaning
// - to facilitate type inference
// - because you have non implicit and implicit parameters
// - to use partial application
// - a parameter in one group can use a parameter from a previous group as a default value

// A partially applied function is a function that you do not apply any or all the arguments, creating another function
// This partially applied function doesn't apply any arguments
// Partially applied functions can helps you to keep your code DRY
val repeatTwo: String => String = anotherRepeat(2)(_)

repeatTwo("bye")

// But you can replace any number of arguments
val anotherRepeatTwo: String => String = repeatRec(2, _)

anotherRepeatTwo("bye")

// About partially-applied and curried functions: though they are different at the definition site, the call site might nonetheless look identical
// See: https://docs.scala-lang.org/tour/multiple-parameter-lists.html
// We discourage the use of the word “curry” in reference to Scala’s multiple parameter lists, for two reasons:
// 1) In Scala, multiple parameters and multiple parameter lists are specified and implemented directly, as part of the language, rather being derived from single-parameter functions
// 2) There is danger of confusion with the Scala standard library’s curried and uncurried methods, which don’t involve multiple parameter lists at all

// Currying: a function that takes multiple arguments can be translated into a series of function calls that each take a single argument
// In Haskell, all functions are technically curried functions whereas in Scala it's good to know about but not a core properties need to write code

// See: https://github.com/scala/scala/blob/v2.13.10/src/library/scala/Function2.scala#L41-L47
// Creates a curried version of this function
// Returns: a function f such that f(x1)(x2) == apply(x1, x2)
val repeatCurried: Int => String => String = repeat.curried

repeatCurried(2)("bye")

val repeatCurriedTwo: String => String = repeatCurried(2)

repeatCurriedTwo("bye")

val repeatUncurried: (Int, String) => String = Function.uncurried(repeatCurried)

repeatUncurried(2, "bye")

// Do not confuse in Scala partially applied functions and partial functions (see pattern matching)
// a) Partially applied functions:
// - are functions that are created by fixing some of the parameters of an existing function
// - are useful for creating specialized versions of functions
// b) Partial functions:
// - are functions that are defined only for a subset of their input domain
// - are useful when you want to define different behavior based on patterns or conditions

// Implicits are a powerful feature that allows the compiler to automatically insert conversions, method calls, or variable assignments to satisfy certain requirements

// First example, we want to provide extra functionality to existing classes, eg. `isOdd` for `Int`
class KoanIntWrapper(val original: Int) {
  def isOdd = original % 2 != 0
}

KoanIntWrapper(42).isOdd
KoanIntWrapper(43).isOdd

// See: https://docs.scala-lang.org/scala3/reference/changed-features/implicit-conversions.html
import scala.language.implicitConversions
implicit def thisMethodNameIsIrrelevant(value: Int): KoanIntWrapper =
  new KoanIntWrapper(value)

19.isOdd
20.isOdd

// We want to define a method sum which computes the sum of a list of elements (using the monoid's `add` and `unit` operations)
// We can use implicits to provide the monoid
// The compiler will look for an implicit value of type Monoid[Int] when it encounters the `sum` method
// The compiler will look for an implicit value of type Monoid[String] when it encounters the `sum` method
// See: https://docs.scala-lang.org/scala3/reference/contextual/implicit-parameters.html
trait SemiGroup[A]:
  def add(x: A, y: A): A
trait Monoid[A] extends SemiGroup[A]:
  def unit: A

// A method can have contextual parameters, also called implicit parameters, or more concisely implicits
// Parameter lists starting with the keyword `using` (or `implicit` in Scala 2) mark contextual parameters
def sumImplicit[A](xs: List[A])(using m: Monoid[A]): A =
  if (xs.isEmpty) m.unit
  else m.add(xs.head, sumImplicit(xs.tail))

val sumImplicitVal: List[Int] => Int = sumImplicit[Int]

// Unless explicitly provided in the call, the compiler will look for implicitly available `given` (or `implicit` in Scala 2) values of the correct type
given StringMonoid: Monoid[String] with {
  def add(x: String, y: String): String = x concat y
  def unit: String = ""
}
given IntMonoid: Monoid[Int] with {
  def add(x: Int, y: Int): Int = x + y
  def unit: Int = 0
}

sumImplicit(List(1, 2, 3))
sumImplicit(List("a", "b", "c"))

// Implicits can be used to automatically convert a value's type to another (think about JSON...)
// Implicits can be used to declare a value to be provided as a default as long as an implicit value is set with in the scope (database connection...)
// When you need to refer to shared resources several times and you want to keep your code clean
// However, default arguments can be preferred to implicit function parameters (anti-pattern)

// The Scala language specification, about implicit parameters:
// A method or constructor can have only one implicit parameter list, and it must be the last parameter list given
// If there are several eligible arguments which match the implicit parameter’s type, a most specific one will be chosen using the rules of static overloading resolution
// For more information, see: https://docs.scala-lang.org/scala3/reference/contextual/

// The rules of static overloading resolution in Scala are as follows:
// 1) The compiler looks for methods with the exact number of parameters and the same names as the method being called. If a matching method is found, it is chosen as the most specific method.
// 2) If no exact match is found, the compiler looks for methods that can be accessed without a typecast or implicit conversion on the arguments. This means that methods whose parameter types are a subtype of the argument types are considered more specific. If there is only one such method, it is chosen.
// 3) If there are multiple methods that can be accessed without a typecast or implicit conversion, the compiler chooses the most specific method. This is determined based on the subtype relationship between the arguments' types and the parameter types of the available methods.
// 4) If none of the above rules can determine a single most specific method, a compilation error occurs, indicating an ambiguous overload resolution.
// It's important to note that the return type of a method is not considered during static overloading resolution. Only the method name and the types of its parameters are taken into account.
// These rules ensure that the appropriate method is selected based on the static types of the arguments at the call site, allowing for safe and unambiguous method dispatch during compilation.

// Lexical scopes
// Let's compute the approximation of the square root of a value using Newton's method:
// a) to compute `√x`, start with `y = 1` as an initial estimate
// b) repeatedly improve the estimate by taking the mean of `y` and `x/y`
// c) let's define a threshold of `0.001` by default for our estimate

// Exercise: square root
// a) define two functions `improve` and `isPreciseEnough` to translate the method above
// b) implement these functions
// c) define a third function `sqrtItr` to use the two implemented functions
// d) implement `sqrtItr`
// e) how to make `sqrtItr` easier to use?
// (it's good functional programming style to split up a task into many small functions)

def improve(guess: Double, x: Double): Double =
  (guess + x / guess) / 2

def isPreciseEnough(guess: Double, x: Double, threshold: Double = 0.001): Boolean =
  Math.abs(guess * guess - x) < threshold

def sqrtItr(guess: Double, x: Double): Double =
  if (isPreciseEnough(guess, x)) guess
  else sqrtItr(improve(guess, x), x)

def sqrt(x: Double): Double = sqrtItr(1.0, x)

sqrt(1.0)
sqrt(2.0)
sqrt(3.0)
sqrt(4.0)

// Nested functions: `sqrtItr`, `isPreciseEnough`, `improve` are part of the implementation and should not be exposed
// We can rewrite the main function by declaring its auxiliaries inside, nested
def sqrt2(x: Double): Double = {
  def improve(guess: Double, x: Double): Double =
    (guess + x / guess) / 2

  def isPreciseEnough(guess: Double, x: Double, threshold: Double = 0.001): Boolean =
    Math.abs(guess * guess - x) < threshold

  def sqrtItr(guess: Double, x: Double): Double =
    if (isPreciseEnough(guess, x)) guess
    else sqrtItr(improve(guess, x), x)

  sqrtItr(1.0, x)
}

sqrt2(1.0)
sqrt2(2.0)
sqrt2(3.0)
sqrt2(4.0)

// Blocks: are sequence of expressions, but expressions themselves
// The last element of a block is the expression that defines its value and type
// Definitions inside a block are only visible from within the block
// Definitions inside a block shadow definitions of the same names outside the block

// Therefore, we can simplify `sqrt2` by deleting unnecessary `x` parameters
def sqrt3(x: Double): Double = {
  def improve(guess: Double): Double =
    (guess + x / guess) / 2

  def isPreciseEnough(guess: Double, threshold: Double = 0.001): Boolean =
    Math.abs(guess * guess - x) < threshold

  def sqrtItr(guess: Double): Double =
    if (isPreciseEnough(guess)) guess
    else sqrtItr(improve(guess))

  sqrtItr(1.0)
}

sqrt3(1.0)
sqrt3(2.0)
sqrt3(3.0)
sqrt3(4.0)

// More about syntax:
// https://docs.scala-lang.org/scala3/reference/other-new-features/control-syntax.html
// https://docs.scala-lang.org/scala3/reference/other-new-features/indentation.html

// Tail recursion:
// JVM Stacks and Stack Frames (https://fpsimplified.com/scala-fp-JVM-Stacks-Frames.html)
// A Visual Look at JVM Stacks and Frames (https://fpsimplified.com/scala-fp-Jvm-Stacks-Frames-2.html)

// A tail-recursive function is just a function whose very last action is a call to itself
// The Scala compiler can optimize the resulting JVM bytecode so that the function requires only one stack frame
// Example: run `RepeatFunctions` example available in `src/main/scala`
import scala.annotation.tailrec
import scala.language.implicitConversions

// @tailrec
// private def sumMaybeTailRec(xs: List[Int]): Int = {
//     if (xs.isEmpty) 0
//     else xs.head + sumMaybeTailRec(xs.tail)
// }

// @tailrec
// private def anotherRepeatMaybeTailRec(iteration: Int, message: String): String = {
//   if (iteration == 1) message
//   else message + anotherRepeatMaybeTailRec(iteration = iteration - 1, message)
// }

def repeatTailRec(iteration: Int, message: String): String = ???

// Back to `def` and `val`
// Historically, methods have been a part of the definition of a class, although in Scala 3 you can now have methods outside of classes
// Unlike methods, functions are complete objects themselves, making them first-class entities
// η-expansion allows us to use methods just like functions
// See: https://docs.scala-lang.org/scala3/reference/changed-features/eta-expansion.html
def identityMethod(x: Int): Int = x
val identityFunction: Function1[Int, Int] = x => x

identityMethod(6)
identityFunction(6)

// Anonymous function
val identityAnonymous = (x: Int) => x
identityAnonymous(42)

// Is equivalent to the expanded form
val identityExpanded = {
  class AnonFun extends Function1[Int, Int] {
    def apply(x: Int) = x
  }
  new AnonFun
}
identityExpanded(42)

// Or anonymous class syntax
val identityClass = new Function1[Int, Int] {
  def apply(x: Int) = x
}
identityClass(42)

// That means to two calls below are equivalent
identityAnonymous(42)
identityClass.apply(42)

// Dependency injection: injecting your dependencies the functional way
// Partially applied functions and curring one of the ways of injecting dependencies in functional programming

// Functions Exercises Worksheet
import scala.annotation.tailrec

// Tail-recursive solution using an accumulator and a nested function:
def repeatTailRec2(iteration: Int, message: String): String = {

  @tailrec
  def repeatHelper(iteration: Int, accumulator: String): String = {
    if (iteration == 1) accumulator
    else
      repeatHelper(
        iteration = iteration - 1,
        accumulator = accumulator + message
      )
  }

  repeatHelper(iteration, message)
}

repeatTailRec2(1, "bye")
repeatTailRec2(3, "bye")

// Exercise: factorial
// 1) write a `factorial` function that computes the factorial of a non-negative integer `n`
// 2) what is the result for `factorial(1000)`? how to solve it?
// 3) what is the result for `factorial(50000)`? write a tail recursive `factorialTailRec` function

def factorial(n: Int): BigInt =
  if (n <= 0) 1
  else n * factorial(n - 1)

factorial(0)
factorial(1)
factorial(2)
factorial(8)
factorial(1000)
// factorial(50000)

def factorialTailRec(n: Int): BigInt = {

  @tailrec
  def factorialHelper(n: Int, accumulator: BigInt): BigInt =
    if (n <= 0) accumulator
    else factorialHelper(n = n - 1, accumulator = n * accumulator)

  factorialHelper(n, accumulator = 1)
}

factorialTailRec(0)
factorialTailRec(1)
factorialTailRec(2)
factorialTailRec(8)
factorialTailRec(1000)
factorialTailRec(50000)

// Exercise: same exercise with the Fibonacci sequence

def fibonacci(index: Int): BigInt = {
  if (index == 0) 0
  else if (index == 1) 1
  else fibonacci(index - 1) + fibonacci(index - 2)
}

fibonacci(0)
fibonacci(1)
fibonacci(2)
fibonacci(8)
// fibonacci(500)

def fibonacciTailRec(index: Int): BigInt = {

  @tailrec
  def fibonacciHelper(index: Int, current: BigInt, next: BigInt): BigInt =
    if (index <= 0) current
    else
      fibonacciHelper(
        index = index - 1,
        current = next,
        next = next + current
      )

  fibonacciHelper(index, current = 0, next = 1)
}

fibonacciTailRec(0)
fibonacciTailRec(1)
fibonacciTailRec(2)
fibonacciTailRec(8)
fibonacciTailRec(500)

import scala.annotation.tailrec

object RepeatFunctions {

  def repeat(iteration: Int, message: String): String = {
    var result = ""
    var i = iteration
    while (i > 0) {
      result += message
      i = i - 1
    }
    result
  }
  val repeatCurried: Int => String => String = repeat.curried
  repeatCurried(2)("bye")

  def repeatRec(iteration: Int, message: String): String = {
    if (iteration == 1) {
      val stackTrace = Thread.currentThread.getStackTrace
      stackTrace.foreach(println)
      message
    } else message + repeatRec(iteration = iteration - 1, message)
  }

  def repeatTailRec(iteration: Int, message: String): String = {

    @tailrec
    def repeatHelper(iteration: Int, accumulator: String): String = {
      if (iteration == 1) {
        val stackTrace = Thread.currentThread.getStackTrace
        stackTrace.foreach(println)
        accumulator
      } else
        repeatHelper(
          iteration = iteration - 1,
          accumulator = accumulator + message
        )
    }

    repeatHelper(iteration = iteration, accumulator = message)
  }
}

@main def main: Unit =
  RepeatFunctions.repeatRec(n, "bye")
  RepeatFunctions.repeatTailRec(n, "bye")

def n = 10



// Structuring Information Worksheet

// Structuring your project and your code:
// Packages, imports, objects and `main` method: https://docs.scala-lang.org/scala3/book/packaging-imports.html
// Automatic imports: `scala`, `java.lang`, all members of the singleton object `scala.Predef`
// See: https://scala-lang.org/api/3.2.2/scala/Predef$.html#

// SBT: the interactive build tool, to define your projects and tasks in Scala
// https://www.scala-sbt.org/1.x/docs/sbt-by-example.html
// https://www.scala-sbt.org/1.x/docs/Running.html

// Structuring information using case classes
// Case classes are regular classes, which are immutable by default and decomposable through pattern matching
case class TreeLeaf(value: Int)

// Keyword `new` is unnecessary
val l1 = TreeLeaf(value = 42)
l1.value

// Equality
// Case classes have automatic `equals` and `hashCode` methods
val l2 = TreeLeaf(value = 42)
val l3 = TreeLeaf(value = 1)

l1 == l2
l1.equals(l2)
l1 eq l2

l1 == l3

l1.hashCode
l2.hashCode
l3.hashCode

// Other features:
// Automatic `toString` method
l1.toString

// Are `Serializable`: instances to be stored, transmitted and shared (network communication, caching, or interoperability)
l1.isInstanceOf[Serializable]

// Default, named, or `var` parameters
case class SuperHero2(name: String, var power: String = "Flight")

val hero: SuperHero2 = SuperHero2("Mystique")
hero.power = "Shapeshifting"
hero.toString

// Method `copy` for safe updates
case class TrueSuperHero(name: String, power: String)

val standardHero = TrueSuperHero(name = "Mystique", power = "Flight")
val shapeShifter = standardHero.copy(power = "Shapeshifting")

// The code above uses a syntactic sugar of the `apply` method
val invisible =
  TrueSuperHero.apply(name = "John Constantine", power = "Invisibility")
invisible.name

// Can be converted to tuple
// The `unapply` method is critical to pattern matching, destructuring binding, equality and hashing, copy or extractor objects
val t: (String, String) = Tuple.fromProductTyped[TrueSuperHero](shapeShifter)

// About extractors: https://www.scala-exercises.org/std_lib/extractors

// Case classes can expand to a class and companion object definition
// Eg. below is the expansion of `case class Hero(name: String, level: Int)`

class Hero(_name: String, _level: Int) extends Serializable {

  // Constructor parameters are promoted to members
  val name = _name
  val level = _level

  // Equality redefinition
  override def equals(other: Any): Boolean = other match {
    case that: Hero =>
      (that canEqual this) &&
        name == that.name &&
        level == that.level
    case _ => false
  }

  private def canEqual(other: Any): Boolean = other.isInstanceOf[Hero]

  // Java hashCode redefinition according to equality
  override def hashCode(): Int = {
    val state = Seq(name, level)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  // toString redefinition to return the value of an instance instead of its memory address
  override def toString = s"Hero($name,$level)"

  // Create a copy of a case class, with potentially modified field values
  def copy(name: String = name, level: Int = level): Hero =
    new Hero(name, level)
}

object Hero {

  // Constructor that allows the omission of the `new` keyword
  def apply(name: String, level: Int): Hero =
    new Hero(name, level)

  // Extractor for pattern matching
  def unapply(hero: Hero): Option[(String, Int)] =
    if (hero eq null) None
    else Some((hero.name, hero.level))
}

val padawan: Hero = new Hero(_name = "Luke", _level = 1)

// Algebraic data types
// Data types defined with sealed trait and case classes are called algebraic data types
// An algebraic data type definition can be thought of as a set of possible values
// Algebraic data types are a powerful way to structure information
// If a concept of your domain can be expressed in terms of an is relationship, you will use a sealed trait
// If a concept of your domain can be expressed in terms of an has relationship, you will use a case class

// Defining alternatives
// You can't extend multiple `abstract class`es but you can extend multiple `trait`s
sealed trait Tree
case class Leaf(value: Int) extends Tree
case class Node(value: Int, left: Tree, right: Tree) extends Tree

// Pattern matching
val leaf: Tree = Leaf(42)
val tree: Tree = Node(1, Leaf(20), Leaf(22))

// Pattern matching is a mechanism for checking a value against a pattern
// It is a more powerful version of the switch statement in Java
// It also can be used in place of a series of if/else statements
val isFortyTwo: Boolean = leaf match
  case Leaf(42) => true
  case _        => false

// A successful match can also deconstruct a value into its constituent parts
val maybeLeafValue: Int = tree match
  case Leaf(value) => value
  case _           => -1

// Pattern matching statement is most useful for matching on algebraic types expressed via case classes
// Pattern matching is also using `unapply` methods in extractor objects
val allLeafChild: Boolean = tree match
  case Node(_, Leaf(_), Leaf(_)) => true
  case _                         => false

val onlyLeftLeaf: Boolean = tree match
  case Node(_, Leaf(_), n: Node) => true
  case _                         => false

// Base types that are `sealed` provide extra safety because the compiler checks that the `case`s of a `match` expression are exhaustive
// Pattern matching exhaustiveness
def nonExhaustiveValue(tree: Tree): Int =
  tree match {
    case Leaf(value) => value
  }

nonExhaustiveValue(leaf)
// nonExhaustiveValue(tree)

// Partial functions
// See: https://danielwestheide.com/blog/the-neophytes-guide-to-scala-part-4-pattern-matching-anonymous-functions/
// See: https://www.scala-exercises.org/std_lib/partial_functions

val nonExhaustiveValuePartial: PartialFunction[Tree, Int] =
  case Leaf(value) => value

nonExhaustiveValuePartial(leaf)
// nonExhaustiveValuePartial(tree)

// An alternative way of defining anonymous functions, namely as a sequence of cases, which opens up some nice destructuring possibilities in a rather concise way
// Partial function provide the means to be chained, allowing for a neat functional alternative to the chain of responsibility pattern known from object-oriented programming
// Partial functions are also a crucial element of many Scala libraries and APIs, eg. Akka

// Pattern guards are boolean expressions which are used to make cases more specific
val isTrueLeaf: Boolean = leaf match
  case Leaf(value) if value == 42 => true
  case _                          => false

// Pattern matching on type only
val isLeaf: Boolean = leaf match
  case l: Leaf => true
  case _       => false

// Enumerations
// Defines a type, consisting of a set of named values
// Useful to makes it impossible to reach invalid states
enum Structure:
  case Terrestrial, GasGiant, IceGiant

// Enums can be parametrized
enum Planet(val structure: Structure):
  def solarSystemRegion: String = structure match
    case Structure.Terrestrial => "Inner Solar System"
    case _                     => "Outer Solar System"

  case Mercury extends Planet(Structure.Terrestrial)
  case Venus extends Planet(Structure.Terrestrial)
  case Earth extends Planet(Structure.Terrestrial)
  case Mars extends Planet(Structure.Terrestrial)
  case Jupiter extends Planet(Structure.GasGiant)
  case Saturn extends Planet(Structure.GasGiant)
  case Uranus extends Planet(Structure.IceGiant)
  case Neptune extends Planet(Structure.IceGiant)

// Enums are indexed
Planet.Earth.ordinal

// Method `toString` is also available
Planet.Earth.toString

// Values
Planet.values
Planet.fromOrdinal(2)
Planet.valueOf("Earth")

// As `structure` parameter is set as a `val`, you can access its value
Planet.Earth.structure

// We can add our own definitions to enums
Planet.Earth.solarSystemRegion

// And also a companion object
object Planet:
  def regions(): Unit =
    for p <- values do println(s"$p is part of ${p.solarSystemRegion}")
end Planet

Planet.regions()

// We can also manage deprecations
enum Planet2(val structure: Structure):
  case Mercury extends Planet2(Structure.Terrestrial)
  case Venus extends Planet2(Structure.Terrestrial)
  case Earth extends Planet2(Structure.Terrestrial)
  case Mars extends Planet2(Structure.Terrestrial)
  case Jupiter extends Planet2(Structure.GasGiant)
  case Saturn extends Planet2(Structure.GasGiant)
  case Uranus extends Planet2(Structure.IceGiant)
  case Neptune extends Planet2(Structure.IceGiant)
  @deprecated("refer to IAU definition of planet") case Pluto
    extends Planet2(Structure.Terrestrial)

Planet2.Pluto

// Enums are represented as `sealed` classes that extend the `scala.reflect.Enum` trait

// Enum values with `extends` clauses get expanded to anonymous class instances
// val Venus: Planet = new Planet(Structure.Terrestrial):
//   def ordinal: Int = 1
//   override def productPrefix: String = "Venus"
//   override def toString: String = "Venus"

// Enum values without `extends` clauses all share a single implementation that can be instantiated using a private method that takes a tag and a name as arguments
// val Terrestrial: Structure = $new(0, "Terrestrial")

// Tuples: we already mentioned tuples for the `unapply` methods
// Tuples are also useful when you want to aggregate some information but don't need a complete class
// Tuples are lightweight data structures
// Tuples also allow you to return multiple values

def pairHeroPower(hero: String, power: String): (String, String) = (hero, power)

val p1: (String, String) = pairHeroPower("Mystique", "Shapeshifting")
val p2: (String, String) = pairHeroPower("John Constantine", "Invisibility")

p1 match {
  case (hero, power) => hero == "Mystique" && power == "Shapeshifting"
}

val (h, p) = p2

p2._1
p2._2

// Overall, tuples can make your code more expressive and concise
// But should be used only for simple structures

// String interpolation
// Is a mechanism to create strings from your data
// Using *processed* string literals (denoted by a set of characters preceding the first `"`)
// The compilers applies additional work to these literals for you

// Here are the three interpolators provided by the standard library: `s`, `f` and `raw`
val x2: Int = 1
val y: Int = 2
s"sum of $x and $y is ${x2 + y}"

val a: Double = 1.0
val b: Double = 2.0
f"sum of $a%2.2f and $b%2.2f is ${a + b}%2.2f"

println(s"sum of $x2 and $y is:\n${x2 + y}")
println(raw"sum of $x2 and $y is:\n${x2 + y}")

// When the compiler detect a processed string literal, it transforms it to a method call
// The name is defined by the characters preceding the first `"` and the call made against the `StringContext` class
// See: https://scala-lang.org/api/3.2.2/scala/StringContext.html#

// It's possible to define your custom string interpolator, very useful for libraries (eg. sql"SELECT * FROM table t")
extension (sc: StringContext) {
  def superhero(args: Any*): TrueSuperHero = {
    val tokens = sc.s(args: _*).split(",")
    TrueSuperHero(name = tokens(0), power = tokens(1))
  }
}

val name = "Mystique"
val power = "Shapeshifting"
val trueSuperHero: TrueSuperHero = superhero"$name,$power"
trueSuperHero.toString

// Or in Scala 2:
// implicit class TrueSuperHeroInterpolator(sc: StringContext) {
//     def hero(args: Any*): TrueSuperHero = {
//         val tokens = sc.s(args: _*).split(",")
//         TrueSuperHero(name = tokens(0), power = tokens(1))
//     }
// }

// For Comprehensions Worksheet
import scala.util.{Failure, Success, Try}
import scala.annotation.tailrec

// A quick tour of the standard library (collections and errors handling)
case class SuperHero(name: String, power: String)

// List data structure is fundamental in functional programming
// Lists in Scala are: immutable, recursive and homogeneous
val fantasticFour: List[SuperHero] = List(
  SuperHero("Mr Fantastic", "Elasticity"),
  SuperHero("The Invisible Girl", "Invisibility"),
  SuperHero("The Human Torch", "Pyrokinesis"),
  SuperHero("The Thing", "Strength")
)
val fruits: List[String] = List("apple", "orange", "banana")
val numbers: List[Int] = List(1, 2, 3, 4)
val empty: List[Nothing] = List()
val heterogeneous: List[Any] = List(fantasticFour.head, "pear", 42)

// Constructors of lists
// A convention in Scala states that operators ending in `:` are right-associative
1 :: (2 :: (3 :: Nil))
1 :: 2 :: 3 :: Nil
Nil.::(3).::(2).::(1)

// Pattern matching
numbers match {
  case 1 :: 2 :: xs => "starts with 1 and 2"
  case x :: Nil     => "is of length one"
  case List(x)      => "is of length one"
  case Nil          => "is empty"
  case List()       => "is empty"
  case _            => "is something else"
}

// Optional: insertion sort
def insertionSort(xs: List[Int]): List[Int] = {
  val isSmallerThan: (Int, Int) => Boolean = (x, y) => x <= y

  def insert(x: Int, xs: List[Int]): List[Int] =
    xs match {
      case List() => x :: Nil
      case y :: ys =>
        if (isSmallerThan(x, y)) x :: y :: ys
        else y :: insert(x, ys)
    }

  xs match {
    case List()  => List()
    case y :: ys => insert(y, insertionSort(ys))
  }
}

insertionSort(List(5, 17, 2, 1, 5, 8)) == List(1, 2, 5, 5, 8, 17)

// Operations on list:
// - map
// - filter
// - flatMap
// - foreach
numbers.map(x => x * x)
numbers.filter(_ % 2 == 1)
numbers.map(x => if x > 0 then List(-x, x) else List(x, -x))
numbers.flatMap(x => if x > 0 then List(-x, x) else List(x, -x))
numbers.foreach(println)

// Operations can be chained:
numbers
  .map(x => x * x)
  .filter(_ % 2 == 1)
  .flatMap(x => if x > 0 then List(-x, x) else List(x, -x))

// This expression above is equivalent to:
// (for {
//   y <- numbers
//   n = y * y
//   if n % 2 == 1
//   x = if n > 0 then List(-n, n) else List(n, -n)
// } yield x).flatten

// For comprehensions:
// - generators
// - filters
// - definitions
val xs2 = List(1, 2, 3, 4, 5, 6, 7, 8)
val ys2 = List(8, 6, 4, 2)

// for expression
val forExpression = for {
  x <- xs2 if x % 2 == 0
  y <- ys2
} yield (x, y)

// is equivalent to
val filterThenFlatMap = xs2
  .filter { x =>
    x % 2 == 0
  }
  .flatMap { x =>
    ys.map { y =>
      (x, y)
    }
  }

assert(forExpression == filterThenFlatMap)

// In Scala, the term "comprehension" refers to the ability to combine iterations, transformations, and filters in a concise and declarative manner using the for construct.
// A for comprehension allows you to express complex looping operations over collections in a more readable and functional style.
// In mathematics, the term "comprehension" is commonly used in the context of set theory and logic.
// It refers to a notation or construction that allows you to define a set by specifying the properties or conditions that its elements must satisfy.
// Georg Cantor (1845-1918) introduced the concept of set comprehension as a fundamental idea in his development of set theory.
// He used the term "Begriffsbildung" in German, which can be translated as "concept formation" or "comprehension".
// The term emphasizes the idea that a set is defined by comprehending or grasping the elements that satisfy certain conditions or properties.

// Remember our own implementation of a list (of integers):
enum MyList:
  case Empty
  case Cons(head: Int, tail: MyList)

  // `reverse` is going to be used as an helper in the implementation below
  final def reverse(): MyList = {
    @tailrec
    def reverseHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        reverseHelper(tail, Cons(head, accumulator))

    reverseHelper(this, Empty)
  }

  // `append` is going to be used as an helper in the implementation below
  final def append(ys: MyList): MyList = {
    @tailrec
    def appendHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        appendHelper(tail, Cons(head, accumulator))

    appendHelper(this.reverse(), ys)
  }

  final def isEmpty: Boolean = this match
    case Empty => true
    case _     => false

  // `foreach` should be f: A => U, this is a simple case as MyList is not generic
  // Note: `[U]` parameter needed to help scalac's type inference.
  @tailrec
  final def foreach(f: Int => Unit): Unit = this match
    case Empty => ()
    case Cons(head: Int, tail: MyList) =>
      f(head)
      tail.foreach(f)

  // `map` should be f: A => B, this is a simple case as MyList is not generic
  final def map(f: Int => Int): MyList = {
    @tailrec
    def mapHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        mapHelper(tail, accumulator = Cons(f(head), accumulator))

    mapHelper(this, Empty).reverse()
  }

  // `p` should be A => Boolean, this is a simple case as MyList is not generic
  final def withFilter(p: Int => Boolean): MyList = {
    @tailrec
    def withFilterHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        withFilterHelper(
          tail,
          if p(head) then Cons(head, accumulator) else accumulator
        )

    withFilterHelper(this, Empty).reverse()
  }

  // `flatMap` should be f: A => MyList[B], this is a simple case as MyList is not generic
  final def flatMap(f: Int => MyList): MyList = {
    @tailrec
    def flatMapHelper(xs: MyList, accumulator: MyList): MyList = xs match
      case Empty => accumulator
      case Cons(head: Int, tail: MyList) =>
        val ys = f(head)
        flatMapHelper(tail, accumulator.append(ys))

    flatMapHelper(this, Empty)
  }

end MyList

import MyList.*
val myEmptyList: MyList = Empty
val myNumbers: MyList = Cons(1, Cons(2, Cons(3, Cons(4, Empty))))

myEmptyList.isEmpty
myNumbers.isEmpty

// Let's talk about `foreach`, `map`, `withFilter` and `flatMap`

// For loop: `foreach`
// Applying a for *loop* to the `numbers` list:
for { n <- numbers } println(n)
// What about `myNumbers`? `value foreach is not a member of MyList`
for { n <- myNumbers } println(n)

// This for comprehension (loop is okay in this specific case):
for { n <- numbers } println(n)
// is equivalent to:
numbers.foreach(println)

// We need to implement `foreach` for `MyList`
for { n <- myNumbers } println(n)

// For comprehension with a single generator: `map`
// We can apply a transformation to the `numbers` list:
for { n <- numbers } yield n * n
// What about `myNumbers`? `value map is not a member of MyList`
for { n <- myNumbers } yield n * n

// This for comprehension:
for { n <- numbers } yield n * n
// is equivalent to:
numbers.map(x => x * x)

// We need to implement `map` for `MyList`
for { n <- myNumbers } yield n * n
// To solve this problem, we add the `reverse` function
myNumbers.reverse()

// For comprehension with filtering enabled: `withFilter`
// We can filter the `numbers` list:
for {
  n <- numbers
  if n % 2 == 1
} yield n * n
// What about `myNumbers`? `value withFilter is not a member of MyList`
for {
  n <- myNumbers
  if n % 2 == 1
} yield n * n

// This for comprehension:
for {
  n <- numbers
  if n % 2 == 1
} yield n * n
// is equivalent to:
numbers.filter(_ % 2 == 1).map(x => x * x)

// We need to implement `withFilter` for `MyList`
for {
  n <- myNumbers
  if n % 2 == 1
} yield n * n

// For comprehension with a multiple generators: `flatMap`
val otherNumbers = List(4, 33, 9, 71)
for {
  n <- numbers
  o <- otherNumbers
  if n * n == o
} yield o

// What about `myNumbers`? `value flatMap is not a member of MyList`
val myOtherNumbers: MyList = Cons(4, Cons(33, Cons(9, Cons(71, Empty))))
for {
  n <- myNumbers
  o <- myOtherNumbers
  if n * n == o
} yield o

// We need to implement `flatMap` for `MyList`
for {
  n <- myNumbers
  o <- myOtherNumbers
  if n * n == o
} yield o
// To solve this problem, we add the `append` function
myNumbers.append(Cons(5, Cons(6, Empty)))

// Remember what we said during the introduction course: pure functions (and their signature) tell no lies

// This function is lying:
@throws(classOf[NumberFormatException])
def toFloat(s: String): Float = s.toFloat

// And this one if even worse:
def toInt(s: String): Int = s.toInt

// Throwing exception (or null) is not expected from pure functions, it make composition of functions weak
// Option type to the rescue: `Option[A]` is either `Some[A]` or None
// A good way to implement partially defined functions and deal with exceptions

val someValue: Option[Int] = Some(42)
val noValue: Option[Int] = None

def maybeInt(s: String): Option[Int] = s.trim.toIntOption
// try {
//   Some(s.trim.toInt)
// } catch {
//   case e: Exception => None
// }

// Another good thing is that `Option` works well with pattern matching, `map`, ... and for comprehensions
Some(42).map(x => x + 1)
(None: Option[Int]).map(x => x + 1)

Some(1).filter(_ % 2 == 1)
Some(2).filter(_ % 2 == 1)
(None: Option[Int]).filter(_ % 2 == 1)

Some(1).map(x => Some(x + 1))
Some(1).flatMap(x => Some(x + 1))

val maybeSum1: Option[Int] = for {
  x <- maybeInt("1")
  y <- maybeInt("2")
  z <- maybeInt("3")
} yield x + y + z

val maybeSum2: Option[Int] = for {
  x <- maybeInt("1")
  y <- maybeInt("oops")
  z <- maybeInt("3")
} yield x + y + z

val f: Option[Int] => String = {
  case Some(value) => s"value is $value"
  case None        => "something went wrong"
}
//val f: Option[Int] => String = x =>
//  x match {
//    case Some(value) => s"value is $value"
//    case None        => "something went wrong"
//  }

f(maybeSum1)
f(maybeSum2)

// In a functional programming style, we will prefer `Option` over `null` or exceptions
// A pure function signature is a contract with its users, need to be as explicit as possible

// `Try[A]` is either `Success[A]` or `Failure` (where any exception thrown during execution is converted to `Failure`)
// `Either[A, B]` is either a success with `Right[B]` or an error with `Left[A]` (by convention)
// `Try`s and `Either`s also work well with pattern matching, `map`, ... and for comprehensions

def div(x: Int, y: Int): Try[Double] =
  if y == 0 then Failure(new IllegalArgumentException("division by zero"))
  else Success(x / y)

div(1, 2)
div(1, 0)

def even(x: Either[String, Int]): Either[String, Int] =
  x.filterOrElse(_ % 2 == 0, "odd value")

even(Right(2))
even(Right(1))

// Difference between `filter` and `withFilter`:
// `def filter(p: A => Boolean): Repr` where `Repr` is the current collection, eg. `List`
// `def withFilter(p: A => Boolean): WithFilter[A, Repr]`, with the same meanings for `Repr` and `A`
// The `withFilter` method iterates only when we call the `foreach`, `map`, or `flatMap` method on `WithFilter`
// When we need to provide more than one filtering operation, `withFilter`provides better performances
// When we need to apply a single predicate without any mapping on the result, the `filter` method should be used
// Unless using a `map` with the identity function with `WithFilter`

// Accumulating errors, a Scalactic example (https://www.scalactic.org)
// Scalactic provides an “`Either` with attitude” named `Or`, designed for functional error handling
// `Every` enables you to accumulate errors, with `Every` is either `One` or `Many`
// `Or` is either `Good` or `Bad`
import org.scalactic._
import Accumulation._

case class Person(name: String, age: Int)

def parseName(input: String): String Or One[ErrorMessage] = {
  val trimmed = input.trim
  if trimmed.nonEmpty then Good(trimmed)
  else Bad(One(s""""$input" is not a valid name"""))
}

def parseAge(input: String): Int Or One[ErrorMessage] = {
  try {
    val age = input.trim.toInt
    if age >= 0 then Good(age) else Bad(One(s""""$age" is not a valid age"""))
  } catch {
    case _: NumberFormatException =>
      Bad(One(s""""$input" is not a valid integer"""))
  }
}

def parsePerson(
                 inputName: String,
                 inputAge: String
               ): Person Or Every[ErrorMessage] = {
  val name = parseName(inputName)
  val age = parseAge(inputAge)
  withGood(name, age) { Person.apply } // withGood(name, age) { Person(_, _) }
}

parsePerson("Bridget Jones", "29")
parsePerson("Bridget Jones", "")
parsePerson("Bridget Jones", "-29")
parsePerson("", "")

// The Null Object Pattern or Void Value
// A null object is an object with no referenced value or with defined neutral (null) behavior (eg. `Nil` for `List`)
// Instead of using a null reference to convey absence of an object (for instance, a non-existent customer), one uses an object which implements the expected interface, but whose method body is empty
// The advantage of this approach over a working default implementation is that a null object is very predictable and has no side effects: it does nothing
def cond(xs: List[Int]): Boolean = ???

def go(xs: List[Int]): List[Int] =
  if cond(xs) then
    // do something
    xs
  else
    // return the Null object for `List`
    Nil

// We still sometimes need to handle `null` values, eg. when working with Java libraries
// if (null == obj) None else { // do something... }

// Biasing: two alternatives treated differently, with one favored over the other
// - `Option` is biased to `Some` result
// - `Try` to `Success`
// - `Either` is right-biased, which means that `Right` is assumed to be the default case to operate on
// - `Or` to `Good`
// Note: up until Scala 2.12, `Either` was unbiased, which means the `map` and `flatMap` function don't know which value to apply
// Use `Option` if you don't need the error message, `Try` to wrap exceptions, or `Or` as an alternative (and errors accumulation)

// Empty values
// - null: the same as in Java. Any reference type can be `null`, like `String`s, `Object`s, or your own classes. Also just like Java, value types like `Int`s can't be `null`.
// - Null: is a trait whose only instance is `null`. It is a subtype of all reference types, but not of value types. Its purpose in existing is to make it so reference types can be assigned `null` and value types can't.
// - Nothing: is a trait that is guaranteed to have zero instances. It is a subtype of all other types. It has two main reasons for existing: to provide a return type for methods that never return normally (i.e. a method that always throws an exception) and to provide a type for `Nil`.
// - Unit: is the equivalent of `void` in Java. It's used in a function's signature when that function doesn't return a value.
// - Nil: is the empty list, exactly like the result of `List()`. It is of type `List[Nothing]`. Since we know there are no instances of `Nothing`, we now have a list that is statically verifiable as empty.

List() == Nil
None == None
None eq None
None.toString == "None"
None.toList == Nil
None.isEmpty == true
None.getOrElse("No Value")
None.asInstanceOf[Any] == None
None.asInstanceOf[AnyRef] == None

// See Scala type hierarchy at https://docs.scala-lang.org/scala3/book/first-look-at-types.html
// `AnyVal` represents value types, which have value semantics and efficient memory representation.
// `AnyRef` represents reference types, which have reference semantics and follow the object-oriented programming model.

// `Future`s provide a way to reason about performing many operations in parallel – in an efficient and non-blocking way.
// A `Future` is a placeholder object for a value that may not yet exist.
// Generally, the value of the `Future` is supplied concurrently and can subsequently be used.
// Composing concurrent tasks in this way tends to result in faster, asynchronous, non-blocking parallel code.
// By default, futures and promises are non-blocking, making use of callbacks instead of typical blocking operations.
// To simplify the use of callbacks both syntactically and conceptually, Scala provides combinators such as `flatMap`, `foreach`, and `filter` used to compose futures in a non-blocking way.
// Blocking is still possible - for cases where it is absolutely necessary, futures can be blocked on (although this is discouraged).
// (eg. interoperability with blocking APIs, synchronous APIs, rate limiting, or legacy codebases)

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
// Equivalent to `given ExecutionContext = ExecutionContext.global`
// Backed by a ForkJoinPool, see: https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html
// The fork/join framework distributes tasks to worker threads in a thread pool by using a work-stealing algorithm.
// Worker threads that run out of things to do can steal tasks from other threads that are still busy.
//import scala.jdk.DurationConverters._

// A `Future` is an object holding a value which may become available at some point.
val eventualInt: Future[Int] = Future {
  Thread.sleep(10.millis.toMillis)
  42
}
eventualInt.isCompleted

// Functional composition using for comprehensions
val result: Future[Int] = for {
  x <- eventualInt
} yield x * x

result.isCompleted
result

// Callbacks: he `onComplete` method allows us to handle the result of both failed and successful future computations.
result.onComplete {
  case Success(value) => println(s"result is $value")
  case Failure(t) => println("An error has occurred: " + t.getMessage)
}

// Callbacks: in the case where only successful results need to be handled, the `foreach` callback can be used.
for value <- result do println(s"result is $value")

// For more about futures (and promises), see: https://docs.scala-lang.org/overviews/core/futures.html

// Functor, Applicative Functor and Monads:

// Functor lifts or upgrades a function, allowing it to operate on a single effect, leaving the effect intact after it's done.
// It requires a lawful `map` definition.
// First law: If you pass identity to map, map's output must equal its input
// Second law: If you compose two or more functions and pass that to map, map's output must equal the output of composing multiple calls of map together

// Applicative Functor builds on or generalizes functor, allowing you to sequence multiple independent effects.
// It requires a lawful `pure` and `apply` definition.
// The apply function:
// - takes a function inside an effect effect(input -> output),
// - takes input for that function inside an effect effect(input),
// - applies the function to the input,
// - and returns the function's output inside the effect effect(output) .

// Monad builds on or generalizes applicative functor, allowing you to sequence independent and/or dependent effects.
// It requires a lawful `flatten` definition (`flatMap` is an helper thant composes `flatten` and `map`).
// The three monad laws need to be satisfied by a proper monad: left identity, right identity, and associativity.
// Together, left identity and right identity are know as simply identity.

// In summary:
// map      ::  (i ->   o ) -> e(i) -> e(o) -- Functor
// apply    :: e(i ->   o ) -> e(i) -> e(o) -- Applicative
// flatMap  ::  (i -> e(o)) -> e(i) -> e(o) -- Monad
//             ^      ^
// (inspired by: https://medium.com/@lettier/your-easy-guide-to-monads-applicatives-functors-862048d61610)

// Another famous articles that explains functors, applicatives and monads for Haskell:
// https://www.adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html
// Types Worksheet
import scala.annotation.{tailrec, targetName}

// In Scala, operators can be used as identifiers.
val xs3: List[Int] = 1 :: 2 :: 3 :: Nil
val ys3: List[Int] = 1 :: List(2, 3)

class Rational(x: Int, y: Int):
  @tailrec
  private def gcd(a: Int, b: Int): Int = if b == 0 then a else gcd(b, a % b)
  private val g = gcd(x, y)

  private def numerator = x / g
  private def denominator = y / g

  @targetName("add")
  def +(r: Rational) =
    new Rational(
      numerator * r.denominator + r.numerator * denominator,
      denominator * r.denominator
    )

  override def toString: String = s"$numerator/$denominator"
end Rational

val r1: Rational = Rational(1, 2)
val r2: Rational = Rational(1, 3)
val r3: Rational = Rational(3, 9)
r3.toString

r1 + r2
r2 == r3 // false because `equals` is not implemented

// The precedence of an operator is determined by its first character.
// See: https://docs.scala-lang.org/tour/operators.html
// See also: https://docs.scala-lang.org/scala3/reference/other-new-features/targetName.html

// We can distinguish two principal forms of polymorphism:
// - subtyping: instances of a subclass can be passed to a base class
// - generics (aka parametric): instances of a function or class are created by type parameterization

// But also, ad-hoc polymorphism with function overloading, operator overloading, and implicits

// Side note: SOLID
// - Single Responsibility principle
// - Open-Closed principle
// - Liskov Substitution principle
// - Interface Segregation principle
// - Dependency Inversion principle
// Liskov Substitution principle states that a subclass can be used in place of its parent class.
// Benefits to prevent: misleading code, less readable code, error-prone code

// How subtyping and generics interact?

// Type bounds:
// - upper bounds `A <: B` means: `A` is a subtype of `B`
// - lower bounds `A >: B` means: `A` is a supertype of `B` (or `B` is a subtype of `A`)

trait Animal:
  def name: String

trait Reptile extends Animal

trait Mammal extends Animal

case class Bear(name: String) extends Mammal

case class Python(name: String) extends Reptile

// `A` can be instantiated only to types that conform to `Animal`
def f[A <: Animal](a: A): A = ???

// `A` could be one of `Reptile`, `Animal`, `AnyRef`, or `Any`
def g[A >: Reptile](a: A): A = ???

// `A` could ba any type on the interval between `Bear` and `Animal`
def h[A >: Bear <: Animal](a: A): A = ???

// Invariance
// By default, type parameters in Scala are invariant: subtyping relationships between the type parameters aren't reflected in the parameterized type.

class Box[A](var content: A)

val kaa = Python("Kaa")
val baloo = Bear("Baloo")

val pythonBox = Box[Python](kaa)
//val animalBox: Box[Animal] = pythonBox // this doesn't compile
//val animal: Animal = myAnimalBox.content

val anotherAnimalBox: Box[Animal] = Box(kaa)
anotherAnimalBox.content = baloo
anotherAnimalBox.content

// We can put a `Python` or a `Bear` in an `Animal` box
// But a `Box[Python]` cannot be a `Box[Animal]`
// Covariance (can be replaced by a subtype, denoted by the `+` symbol) can solve this issue.

class ImmutableBox[+A](val content: A)
val kaaBox: ImmutableBox[Python] = ImmutableBox[Python](kaa)
val jungleBox: ImmutableBox[Animal] = kaaBox
jungleBox.content

// Covariance is useful in scenarios where you want to create collections or data structures that can safely accept subtypes.
// It provides flexibility and allows you to write more generic and reusable code.
// It's worth noting that covariance is only allowed for immutable types.
// If a covariant type appears as a method parameter, it can only be used in a read-only context, not in a mutable context.
// This restriction ensures type safety and prevents potential runtime errors.

// Contravariance means can be replaced by a supertype and is denoted by the `-` symbol.
abstract class Serializer[-A]:
  def serialize(a: A): String

val animalSerializer: Serializer[Animal] = new Serializer[Animal]():
  def serialize(animal: Animal): String = s"""{ "name": "${animal.name}" }"""

val pythonSerializer: Serializer[Python] = animalSerializer
pythonSerializer.serialize(kaa)

// Contravariance is useful when you have situations where you want to use a more general type as an argument where a more specific type is expected.
// It provides flexibility and allows you to write code that can accept a broader range of types.
// Similar to covariance, contravariance is only allowed for immutable types.
// If a contravariant type appears as a method parameter, it can only be used in a read-only context, not in a mutable context.
// This constraint ensures type safety and prevents potential runtime errors.

// Let's make `MyList` generic!

// Covariant to `A`
enum MyList2[+A]:
  case Empty // extends MyList[Nothing]
  case Cons(head: A, tail: MyList2[A])

  private final def reverse(): MyList2[A] = {
    @tailrec
    def reverseHelper(xs: MyList2[A], accumulator: MyList2[A]): MyList2[A] =
      xs match
        case Empty => accumulator
        case Cons(head: A, tail: MyList2[A]) =>
          reverseHelper(tail, Cons(head, accumulator))

    reverseHelper(this, Empty)
  }

  // `MyList` does not compile if the parameter `ys` in `append` is of type `A`, which we declared covariant.
  // This doesn't work because functions are contravariant in their parameter types and covariant in their result types.
  // To fix this, we need to flip the variance of the type of the parameter `ys` in `append`.
  // We do this by introducing a new type parameter `B` that has `A` as a lower type bound.
  private final def append[B >: A](ys: MyList2[B]): MyList2[B] = {
    @tailrec
    def appendHelper(xs: MyList2[A], accumulator: MyList2[B]): MyList2[B] =
      xs match
        case Empty            => accumulator
        case Cons(head, tail) => appendHelper(tail, Cons(head, accumulator))

    appendHelper(this.reverse(), ys)
  }

  final def isEmpty: Boolean = this match
    case Empty => true
    case _     => false

  @tailrec
  final def foreach(f: A => Unit): Unit = this match
    case Empty => ()
    case Cons(head: A, tail) =>
      f(head)
      tail.foreach(f)

  final def map[B](f: A => B): MyList2[B] = {
    @tailrec
    def mapHelper(xs: MyList2[A], accumulator: MyList2[B]): MyList2[B] = xs match
      case Empty => accumulator
      case Cons(head: A, tail) =>
        mapHelper(tail, Cons(f(head), accumulator))

    mapHelper(this, Empty).reverse()
  }

  final def withFilter(p: A => Boolean): MyList2[A] = {
    @tailrec
    def withFilterHelper(xs: MyList2[A], accumulator: MyList2[A]): MyList2[A] =
      xs match
        case Empty => accumulator
        case Cons(head: A, tail) =>
          withFilterHelper(
            tail,
            if p(head) then Cons(head, accumulator) else accumulator
          )

    withFilterHelper(this, Empty).reverse()
  }

  final def flatMap[B](f: A => MyList2[B]): MyList2[B] = {
    @tailrec
    def flatMapHelper(xs: MyList2[A], accumulator: MyList2[B]): MyList2[B] =
      xs match
        case Empty => accumulator
        case Cons(head: A, tail) =>
          val ys = f(head)
          flatMapHelper(tail, accumulator.append(ys))

    flatMapHelper(this, Empty)
  }
end MyList2

import MyList.{Cons, Empty}

val empty3 = Empty

val numbers2: MyList[Int] = Cons(1, Cons(2, Cons(3, Cons(4, Empty))))
numbers.isEmpty
for { n <- numbers } println(n)

val asStrings: MyList[String] = for { n <- numbers2 } yield s"'$n'"

// Newtypes Worksheet
import scala.annotation.targetName
import zio.prelude.Validation

// Pure function signature tells no lie:
// `def findPersonById(person: MutablePerson): Boolean` is bad because of nutation and unrelated return type
// `def findPersonById(personId: Int): Person` is bad because Person can be `null` (or with empty fields!) if not found
// `def findPersonById(personId: Int): Option[Person]` is better because it's explicit that a person may not exist
// `def findPersonById(id: PersonId): Option[Person]` is even better because person identifier is no more an `Int`

// Why? In Domain-Driven Design (DDD), your types should match the names used in the business domain)
// You also always want to make your code safer and easier to read, right?
// How to give values that have simple types like `String` and `Int` more meaningful type names?

// Example 1: Logarithm
object MyMath:
  opaque type Logarithm = Double

  object Logarithm:
    // These are the two ways to lift to the Logarithm type
    def apply(d: Double): Logarithm =
      math.log(d)

    def safe(d: Double): Option[Logarithm] =
      Option.when(d > 0.0)(math.log(d))
  end Logarithm

  // Extension methods define opaque types' public APIs
  extension (x: Logarithm)
    def toDouble: Double = math.exp(x)

    @targetName("sum")
    def +(y: Logarithm): Logarithm = Logarithm(math.exp(x) + math.exp(y))

    @targetName("product")
    def *(y: Logarithm): Logarithm = x + y
end MyMath

import MyMath.*

val log2: Logarithm = Logarithm(2.0)
val log3: Logarithm = Logarithm(3.0)
log3.toDouble

log2 + log3
log2 * log3

val log2safe = Logarithm.safe(2.0)
val log3safe = Logarithm.safe(3.0)
val error = Logarithm.safe(-2.0)

for {
  log2 <- log2safe
  log3 <- log3safe
} yield log2 + log3

for {
  log2 <- log2safe
  e <- error
} yield log2 + e

// Example 2: Choices
object Choices:
  opaque type Choice <: Int = Int

  object Choice:
    def safe(value: Int): Option[Choice] =
      Option.when(value >= 1 && value <= 9)(value)
  end Choice
end Choices

import Choices.*

val goodChoice: Option[Choice] = Choice.safe(1)
val badChoice: Option[Choice] = Choice.safe(-1)
val anotherBadChoice: Option[Choice] = Choice.safe(42)
// val doNotCompile: Option[Choice] = 9

val anInput: List[Int] = List(2, 4, 7, 67, -245, 56, 9)
anInput.flatMap(Choice.safe)

// Example 3: Customers & Products
object Customers:
  opaque type CustomerId = Int

  object CustomerId:
    def apply(i: Int): CustomerId = i
  end CustomerId

  given CanEqual[CustomerId, CustomerId] = CanEqual.derived
end Customers

object Products:
  opaque type ProductId = Int

  object ProductId:
    def apply(i: Int): ProductId = i
  end ProductId

  given CanEqual[ProductId, ProductId] = CanEqual.derived
end Products

import Customers.*
import Products.*

val customerId1 = CustomerId(42)
val customerId2 = CustomerId(1)
val productId = ProductId(42)

customerId1 == customerId2
// customerId1 == productId // does not compile

// Opaque types benefits:
// a) The `opaque type` declaration creates a new type named `CustomerId`. (Behind the scenes, a `CustomerId` is an `Int`.)
// b) The object with the `apply` method creates a factory method (constructor) for new `CustomerId` instances.
// c) The `given CanEqual` declaration states that a `CustomerId` can only be compared to another `CustomerId`.
// Attempting to compare a `CustomerId` to a `ProductId` or `Int` will create a compiler error; it’s impossible to compare them.

// Others implementations:
// - estatico/scala-newtype for Scala 2 (https://github.com/estatico/scala-newtype/)
// - monix/newtypes (https://github.com/monix/newtypes), see https://newtypes.monix.io/docs/motivation.html

// Iltotore/iron (https://github.com/Iltotore/iron)
// Iron is a type constraints, or "refined types", library for Scala.
// It enables binding constraints to a specific type.
// This processus is called "type refinement".

import io.github.iltotore.iron._
import io.github.iltotore.iron.constraint.numeric._
import io.github.iltotore.iron.constraint.string._

// Refined types are represented by `IronType[A, C]` where:
// - `A` is the base type
// - `C` is the constraint (or "refinement") attached to `A`.

val x: Int :| (GreaterEqual[1] & LessEqual[9]) = 9
val y: Int = x //Compiles
// val doesNotCompile: Int :| (GreaterEqual[1] & LessEqual[9]) = -12

// You can make this expression simple
type Between[Min, Max] = GreaterEqual[Min] & LessEqual[Max]
val z: Int :| Between[0, 9] = 9

// Sometimes, you need to refine a value that is not available at compile time
val runtimeGoodInt = 9
val runtimeBadValue = -12

val refinedInt: Int :| Between[0, 9] = runtimeGoodInt.refine
// val exception: Int :| Between[0, 9] = runtimeBadValue.refine // throws an `IllegalArgumentException`

// A more functional way is available, to return `Option` or `Either`
runtimeGoodInt.refineOption[Between[0, 9]]
runtimeBadValue.refineEither[Between[0, 9]]

// You can accumulate refine errors, for example using ZIO Prelude:
import io.github.iltotore.iron.zio._

type Username = Alphanumeric DescribedAs "Username should be alphanumeric"

type Age = Positive DescribedAs "Age should be positive"

case class User(name: String :| Username, age: Int :| Age)

def createUser(name: String, age: Int): Validation[String, User] =
  Validation.validateWith(
    name.refineValidation[Username],
    age.refineValidation[Age]
  )(User.apply)

createUser("Iltotore", 18) //Success(Chunk(),User(Iltotore,18))
createUser("Il_totore", 18) //Failure(Chunk(),NonEmptyChunk(Username should be alphanumeric))
createUser("Il_totore", -18) //Failure(Chunk(),NonEmptyChunk(Username should be alphanumeric, Age should be positive))

// Finally, Iron works well with newtypes :)
object Measure:
  opaque type Temperature = Double :| Positive
  // object Temperature extends RefinedTypeOps[Temperature]
  object Temperature:
    def apply(d: Double): Temperature = d.refine[Positive]
  end Temperature

  opaque type Moisture = Double :| Positive
  // object Moisture extends RefinedTypeOps[Moisture]
  object Moisture:
    def apply(d: Double): Moisture = d.refine[Positive]
  end Moisture

  val t: Double :| Positive = 5
  // val temperature: Temperature = x // does not compile

  case class Info(temperature: Temperature, moisture: Moisture)
end Measure

import Measure.*
Info(temperature = Temperature(29.0), moisture = Moisture(100.0))
// Info(temperature = Moisture(100.0), moisture = Temperature(29.0)) // does not compile-time error

// For Scala 2, see also fthomas/refined (https://github.com/fthomas/refined)

