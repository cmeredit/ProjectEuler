package io.github.cmeredit.problem27

import io.github.cmeredit.MathUtil

import scala.collection.Searching.Found

object PE27Util {



  // Returns the pair (a, b) such that n^2 + an + b produces the longest sequence of primes for consecutive
  // values of n, starting from n=0.
  def getWinningCoefficients(aMin: Int = -999, aMax: Int = 999, bMax: Int = 999): (Int, Int, Int) = {

    // Primes up to the highest polynomial value we might need to compute
    val primes: Vector[Int] = MathUtil.Primes.primesLessThan(bMax * bMax + math.max(math.abs(aMin), aMax) * bMax + bMax)

    val nat: LazyList[Int] = LazyList.from(0)

    def getPrimeSeqLength(a: Int, b: Int): Int = {
      nat.takeWhile(n =>
        primes.search(n * n + a * n + b) match {
          case Found(_) => true
          case _ => false
        }
      ).length
    }



    val candidatePrimes: Vector[Int] = MathUtil.Primes.primesLessThan(bMax + 1).reverse


    // Using a couple of nested for-loops and an updating winningLength, we can skip quite a lot of work. It's a pain
    // to avoid this work in a purely functional style.
    var (winningA, winningB): (Int, Int) = (aMin, candidatePrimes.head)
    var winningLength: Int = getPrimeSeqLength(winningA, winningB)

    for (b <- candidatePrimes) {

      // A pair (_, b) can only win if b is above the current winning length
      if (b > winningLength) {
        // b > a
        for (a <- aMin until b) {

          val seqLengthUpperBound: Int = b-a
          if (seqLengthUpperBound >= winningLength) {
            // Need to check for victory...
            val nextPrimeSeqLength: Int = getPrimeSeqLength(a, b)

            if (nextPrimeSeqLength > winningLength) {
              //          println(f"Found a new winning length of $nextPrimeSeqLength!")
              winningA = a
              winningB = b
              winningLength = nextPrimeSeqLength
            }
          }

        }

        // b <= a
        for (a <- b until aMax) {

          val seqLengthUpperBound: Int = b
          if (seqLengthUpperBound >= winningLength) {
            // Need to check for victory...
            val nextPrimeSeqLength: Int = getPrimeSeqLength(a, b)

            if (nextPrimeSeqLength > winningLength) {
              //          println(f"Found a new winning length of $nextPrimeSeqLength!")
              winningA = a
              winningB = b
              winningLength = nextPrimeSeqLength
            }
          }

        }
      }
    }

    (winningA, winningB, winningLength)

  }

}
