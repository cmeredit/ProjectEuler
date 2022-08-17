package io.github.cmeredit.problem23

object PE23Main extends App {

  // All values greater than this can be written as a sum of two abundant numbers
  val highestCandidate: Int = 28123
  // Note: This is the highest candidate by direct analysis, but the problem promises that this candidate fails.
  val candidates: Vector[Int] = (1 until highestCandidate).toVector
  val failures: Vector[Int] = PE23Util.getAbundantSumsBelow(highestCandidate)

  val successes: Vector[Int] = candidates.diff(failures)

  println(f"The sum of all positive integers that cannot be written as a sum of two abundant numbers is ${successes.sum}")
}
