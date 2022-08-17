package io.github.cmeredit.problem23

import io.github.cmeredit.MathUtil

object PE23Util {

  // Finds all numbers below n that _can_ be written as the sum of two abundant numbers
  def getAbundantSumsBelow(n: Int): Vector[Int] = {

    val abundantNums: Vector[Int] = MathUtil.Primes.Factorization.getAbundantNumbersBelow(n)

    {
      for (
        a <- abundantNums;
        b <- abundantNums if a + b < n
      ) yield a+b
    }.distinct

  }

}
