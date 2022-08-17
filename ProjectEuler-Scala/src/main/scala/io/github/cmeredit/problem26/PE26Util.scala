package io.github.cmeredit.problem26

object PE26Util {

  // Computes the remainders produced by computing 1/d through long division
  def getRemainders(d: Int): Vector[Int] = {

    lazy val allRemainders: LazyList[Int] = 1 #:: allRemainders.map(r => 10*r % d)
    val indexedRemainders: LazyList[(Int, Int)] = allRemainders.zipWithIndex

    // Find the first zero or repeated remainder. Note that if zero occurs as a remainder, then it will be repeated.
    // Every rational number has a terminating or repeating decimal expansion, so the "get" at the end is safe.
    val earliestRepeatedRemainderIndex: Int = indexedRemainders.find(
      {case (r, index) =>
        // r is a repeated remainder if it has occurred at an earlier index.
        indexedRemainders.find({case (s, _) => s == r}).get._2 < index}
    ).get._2

    // Get all remainders up to and including the second instance of that which was repeated
    allRemainders.take(earliestRepeatedRemainderIndex + 1).toVector
  }

  // Gets the length of the repeated part of the decimal expansion of 1/d
  def getCycleLength(d: Int): Int = {

    val remainders: Vector[Int] = getRemainders(d)

    // We don't count a trailing sequence of zeroes as a proper "cycle"
    if (remainders.last == 0) {
      0
    } else {

      // The cycle length is just the distance between the first and second instance of the repeated remainder

      val firstInstanceIndex: Int = remainders.zipWithIndex.find(_._1 == remainders.last).get._2
      val lastInstanceIndex: Int = remainders.length - 1

      lastInstanceIndex - firstInstanceIndex
    }

  }




}
