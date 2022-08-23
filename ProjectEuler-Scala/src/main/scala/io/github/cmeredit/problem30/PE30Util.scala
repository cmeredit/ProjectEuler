package io.github.cmeredit.problem30

object PE30Util {

  // Returns the digits of n
  def getDigits(n: Int): Vector[Int] = {
    var digits: Vector[Int] = Vector()
    var remaining: Int = n

    // Peel off the digits, one by one, starting with the LSD
    while (remaining != 0) {
      // Prepend has better performance on most Scala collections, so let's get in
      // the habit of using it. Also, we're peeling off digits from least-to-most significant,
      // so prepending keeps digits in the usual order.
      digits = digits.prepended(remaining % 10)
      remaining = remaining / 10
    }

    digits
  }

  // Gets a vector of all numbers, represented as digit collections, with the specified
  // number of digits and maximum digit.
  def getDigitsWith(numDigits: Int, maxDigit: Int): Vector[Vector[Int]] = {
    val possibleDigits: Vector[Int] = (0 to maxDigit).toVector
    val maxDigitLocations: IndexedSeq[Int] = 0 until numDigits

    // Knowing the location of the max digit makes it a bit easier to construct satisfactory digit sequences
    maxDigitLocations.flatMap(maxLocation => {
      (0 until numDigits).foldLeft[Vector[Vector[Int]]](Vector(Vector()))({case (curDigits, nextLocation) =>
        // If we're at the location of the max digit, make sure to add the max digit to any previously constructed digit
        // sequences
        if (nextLocation == maxLocation) {
          curDigits.map(_.appended(maxDigit))
        } else {
          // If we're not at the location of the max digit, then just add any admissible digit to any
          // previously constructed digit sequence
          for (
            digitSequence <- curDigits;
            nextDigit <- possibleDigits
          ) yield digitSequence.appended(nextDigit)
        }
      })
    }).toVector.distinct // Some digit sequences can be generated multiple ways. E.g., Vector(9, 9) has maxLocation 0 or 1

  }

  // Returns the collection of all digit sequences that aren't prevented by our quick reductions.
  // We still need to test whether these numbers equal their power digit sums
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

  // Converts a (short enough) digit sequence back to an Int
  def digitsToInt(digits: Vector[Int]): Int = digits.reverse.foldLeft((0, 1))({case ((curSum, curPower), nextDigit) => (curSum + curPower * nextDigit, curPower * 10)})._1

}
