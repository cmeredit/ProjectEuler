package io.github.cmeredit.problem38

import io.github.cmeredit.MathUtil

object PE38Util {

  // Checks whether or not digits is a permutation of (1 to 9)
  def isPandigital(digits: Vector[Int]): Boolean = digits.length == 9 && digits.toSet == (1 to 9).toSet

  def computeConcatenatedProduct(kDigits: Vector[Int], n: Int): Vector[Int] = {
    val k: Int = MathUtil.digitsToInt(kDigits)
    (1 to n).toVector
      .map(i => k * i)                                                  // Compute the concatenated product...
      .flatMap(n => if (n >= 10) MathUtil.getDigits(n) else Vector(n))  // ... As a digit sequence
  }

}
