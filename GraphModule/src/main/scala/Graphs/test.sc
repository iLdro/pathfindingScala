Some(1).map(x => x + 1)
None.map((x : Int) => x+1)
Some(1).filter(x => x % 2 == 0)
Some(2).filter(x => x % 2 == 0)
Some(1).flatMap(x => Some(x + 1))
None.flatMap((x : Int) => Some(x+1))


val result1 = for {
  a <- List(1, 2, 3)
  b <- List("a", "b", "c")
  t = (a, b)
} yield t

val result2 = for{
  a <- Some(3)
  b <- Some(5) if b % 2 == 0
}yield a*b