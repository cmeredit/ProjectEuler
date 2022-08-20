package io.github.cmeredit.problem30

object PE30Util {

  def getDigits(n: Int): Vector[Int] = {
    var digits: Vector[Int] = Vector()
    var remaining: Int = n

    while (remaining != 0) {
      digits = digits.appended(remaining % 10)
      remaining = remaining / 10
    }

    digits.reverse
  }

  def getDigitsWith(numDigits: Int, maxDigit: Int): Vector[Vector[Int]] = {
    val possibleDigits: Vector[Int] = (0 to maxDigit).toVector
    val minDigitLocations: IndexedSeq[Int] = 0 until numDigits

    minDigitLocations.flatMap(maxLocation => {
      (0 until numDigits).foldLeft[Vector[Vector[Int]]](Vector(Vector()))({case (curDigits, nextLocation) =>
        if (nextLocation == maxLocation) {
          curDigits.map(_.appended(maxDigit))
        } else {
          for (
            digitSequence <- curDigits;
            nextDigit <- possibleDigits
          ) yield digitSequence.appended(nextDigit)
        }
      })
    }).toVector.distinct

  }

  def getCandidates: Vector[Vector[Int]] = {

    val digitPowers: Vector[Int] = (0 to 9).toVector.map(n => math.pow(n, 5).toInt)

    // The problem does not consider one-digit sums to be "real" sums
    val minNumDigits: Int = 2

    // Any number with more than 6 digits has more digits than its power digit sum
    val maxNumDigits: Int = 6

    // In an N-digit number, how small can the maxdigit be?
    val minimum_maxdigit: Map[Int, Int] = (minNumDigits to maxNumDigits).map(N => {
      // Records how large the power digit sum can be given the value of the maxdigit
      val maxDSFromMaxdigit: Vector[Int] = digitPowers.map(_ * N)
      N -> maxDSFromMaxdigit.zipWithIndex.find({case (maxDigitSum, _) => getDigits(maxDigitSum).length >= N}).get._2
    }).toMap

    // In an N-digit number, how large can the maxdigit be?
    val maximum_maxdigit: Map[Int, Int] = (minNumDigits to maxNumDigits).map(N => {
      N -> digitPowers.zipWithIndex.findLast({case (minDigitSum, _) => getDigits(minDigitSum).length <= N}).get._2
    }).toMap

    (minNumDigits to maxNumDigits).toVector.flatMap(N => {
      val nDigitCandidates: Vector[Vector[Int]] = (minimum_maxdigit(N) to maximum_maxdigit(N)).toVector.flatMap(maxDigit => getDigitsWith(N, maxDigit))
      nDigitCandidates
    })
  }

  def powerDigitSum(digits: Vector[Int]): Int = digits.map(d => math.pow(d, 5).toInt).sum

  def digitsToInt(digits: Vector[Int]): Int = digits.reverse.foldLeft((0, 1))({case ((curSum, curPower), nextDigit) => (curSum + curPower * nextDigit, curPower * 10)})._1

}
