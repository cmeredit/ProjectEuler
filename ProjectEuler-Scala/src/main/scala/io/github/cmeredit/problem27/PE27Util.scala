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
    
    val candidatePairs: Vector[(Int, Int)] = for (
      b <- MathUtil.Primes.primesLessThan(bMax + 1).reverse;
      a <- aMin to aMax
    ) yield (a, b)

    val (firstA, firstB): (Int, Int) = candidatePairs.head

    val (winningA, winningB, winningLength): (Int, Int, Int) = candidatePairs.tail.foldLeft((firstA, firstB, getPrimeSeqLength(firstA, firstB)))({case ((curWinnerA, curWinnerB, curWinnerLength), (nextA, nextB)) =>

//      println(f"Testing a=$nextA, p=$nextB")
      val maxSeqLength: Int = if (nextB > nextA) nextB - nextA else nextB

      if (maxSeqLength < curWinnerLength) {
        // This a/b pair is guaranteed not to produce enough primes
//        println(f"This pair is guaranteed to lose")
        (curWinnerA, curWinnerB, curWinnerLength)
      } else {
        // Need to check for victory...
        val nextPrimeSeqLength: Int = getPrimeSeqLength(nextA, nextB)

        if (nextPrimeSeqLength > curWinnerLength) {
//          println(f"Found a new winning length of $nextPrimeSeqLength!")
          (nextA, nextB, nextPrimeSeqLength)
        } else {
//          println(f"The old champion remains...")
          (curWinnerA, curWinnerB, curWinnerLength)
        }
      }

    })

    (winningA, winningB, winningLength)

  }

}
