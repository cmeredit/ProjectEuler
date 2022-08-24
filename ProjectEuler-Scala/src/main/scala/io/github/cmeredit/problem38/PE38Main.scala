package io.github.cmeredit.problem38

import io.github.cmeredit.MathUtil
import io.github.cmeredit.problem38.PE38Util._

object PE38Main extends App {

  // See the README for discussion on candidate ranges and first digits
  val digitSuffixes: Vector[Vector[Int]] = (0 to 999).toVector.map(n => MathUtil.getDigits(n))
  val kCandidates: Vector[Vector[Int]] = digitSuffixes.map(_.prepended(9))

  // Collection of all (k, n) such that the concatenated product of k with (1, ..., n) is 1 to 9 pandigital
  val pandigitalProducts: Vector[(Int, Int)] = kCandidates
    .filter(digitSeq => {
      // The concatenated product starts with this digit sequence, so make sure this portion doesn't violate pandigitality
      digitSeq.length == digitSeq.distinct.length
    })
    .filterNot(_.contains(0))
    .flatMap(digitSeq => {
      // The number of digits in a product is at most the sum of the number of digits of the two factors
      // We're only multiplying our number by 1, 2, ..., n < 10, so the most digits in one of the
      // concatenation components is digitSeq.length + 1. The number of digits in a component is at least
      // digitSeq.length.
      val candidateNs: Vector[Int] = (math.max(2, 9 / (digitSeq.length + 1)) to 9 / digitSeq.length).toVector

      candidateNs
        .find(n => {
          isPandigital(computeConcatenatedProduct(digitSeq, n))
        })
        .map(n => (MathUtil.digitsToInt(digitSeq), n))
    })

  // Let's take a peek at all pandigital concatenated products
  pandigitalProducts.foreach({case (k, n) => println(f"$k (*) $n = ${MathUtil.digitsToInt(computeConcatenatedProduct(MathUtil.getDigits(k), n))}")})

}
