package io.github.cmeredit.problem26

object PE26Main extends App {

  val (d, cycleLength): (Int, Int) = (2 until 1_000).map(d => (d, PE26Util.getCycleLength(d))).maxBy(_._2)

  println(f"$d has the longest reciprocal cycle length among the numbers less than 1,000, with a cycle length of $cycleLength")

}
