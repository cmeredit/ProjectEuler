package io.github.cmeredit.problem29

import io.github.cmeredit.MathUtil

object PE29Util {

  def countUniquePowers(minA: Int = 2, maxA: Int = 100, minB: Int = 2, maxB: Int = 100): Int = {

    val aFactorizations: IndexedSeq[Vector[(Int, Int)]] = (minA to maxA).map(MathUtil.Primes.Factorization.getPrimeFactorization)

    val candidates: IndexedSeq[(Vector[(Int, Int)], Int)] = for(
      aFact <- aFactorizations;
      b <- minB to maxB
    ) yield (aFact, b)

    def computePower(aFact: Vector[(Int, Int)], b: Int): Vector[(Int, Int)] = aFact.map({case (p, e) => (p, e*b)})

    candidates.groupBy({case (aFact, b) => computePower(aFact, b)}).count(_ => true)

  }

}
