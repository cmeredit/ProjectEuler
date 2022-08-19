package io.github.cmeredit.problem27

object PE27Main extends App {
  val (a, b, l) = PE27Util.getWinningCoefficients()

  println(f"Final winner: a=$a, p=$b with a length of $l primes!")
}
