package io.github.cmeredit

import scala.io.{BufferedSource, Source}

object MathUtil {

  // Calculates the k-th triangular number
  def triangleNum(k: Int): Int = k * (k+1) / 2

  object Primes {

    // Lazy List of all triples (P_n, a_n, S_n), indexed from 0, where P_n is the vector of the first n+2 primes,
    // a_n is the product of the elements of P_n, and S_n is a vector of all numbers less than a_n relatively prime to
    // every prime in P_n.
    //
    // Can compute the first entry manually - Just take the first couple of primes.
    //
    // See https://en.wikipedia.org/wiki/Wheel_factorization for more details.
    private lazy val wheels: LazyList[(Vector[Int], Long, Vector[Long])] = (Vector(2, 3), 6L, Vector(1L, 5L)) #::
      wheels.map({case (p, a, s) =>
        // The first entry of each s is 1. The next entry (the lowest nontrivial value relatively prime to all lower
        // primes) is the next lowest prime.
        val nextPrime: Int = s(1).toInt

        // Prepare the next wheel according to the rules above...
        // ... Add the next prime...
        val nextP: Vector[Int] = p.appended(nextPrime)
        // ... Update our product of primes...
        val nextA: Long = a * nextPrime
        // ... And use wheel math to get the next s! We need not perform any trial division or similar. See the
        // linked wheel factorization page for more info about why this process works.
        val nextS: Vector[Long] = {
          (0 until nextPrime)
            .flatMap(k => {
              s.map(x => x + k*a)
            })
            .toVector
            .diff(s.map(x => x * nextPrime))
        }
        (nextP, nextA, nextS)
      })



    // Returns the vector of all primes strictly less than n
    //
    // Attempts to make use of saved primes, if possible. If not enough primes have been saved,
    // then compute those that are needed manually and update the saved primes.
    //
    // TODO: Avoid completely recomputing the list of primes when we need just a few more. Instead, use the deficient
    // TODO: but saved primes to start a new wheel?
    def primesLessThan(n: Int): Vector[Int] = {


      // Check if we've already solved this problem...
      // This data file begins with the greatest n for which all primes less than n have been saved.
      val filename: String = "Data/primes.txt"
      val bufferedSource: BufferedSource = Source.fromFile(filename)
      val lines: Iterator[String] = bufferedSource.getLines()

      val highestLimitComputed: Int = lines.next().toInt

      // If we've already computed all the requested primes, then just return them.
      if (highestLimitComputed >= n) {
        val primes: Vector[Int] = lines.takeWhile(_.toInt < n).map(_.toInt).toVector
        bufferedSource.close()
        primes
      } else {

        // Otherwise, we need to actually do some work.

        bufferedSource.close()

        // We'll use a wheel factorization method. We only need a wheel whose prime-product is above the upper limit
        // (paring this wheel down is guaranteed to produce all primes less than n)
        var (partialPrimes, prod, remainingCandidates): (Vector[Int], Long, Vector[Long]) = wheels.find({case (_, a, _) => a > n}).get

        remainingCandidates = remainingCandidates.filter(_ < n)

        // Did some manual "profiling" - i.e., manually timed the total work on various parts of the while loop below.
//        var appendStart: Long = 0L
//        var appendEnd: Long = 0L
//        var appendTime: Long = 0L
//        var diffStart: Long = 0L
//        var diffEnd: Long = 0L
//        var diffTime: Long = 0L

        val sqrtn: Int = scala.math.sqrt(n.toDouble).ceil.toInt

        while (remainingCandidates.length > 1 && remainingCandidates(1).toInt < sqrtn) {
          //      println(remainingCandidates)
          val nextPrime: Int = remainingCandidates(1).toInt


//          println(f"Next prime: $nextPrime")
//          println(f"Number of remaining candidates: ${remainingCandidates.length}")

//          appendStart = System.nanoTime()
          partialPrimes = partialPrimes.appended(nextPrime)
//          appendEnd = System.nanoTime()
//          appendTime = appendTime + appendEnd - appendStart

          val highestToRemove: Int = (prod / nextPrime).toInt
//          println(f"Highest num that can be removed from candidates: $highestToRemove")
//          println(f"Remaining candidates = ${remainingCandidates.take(10)} + ... + ${remainingCandidates.takeRight(10)}")

//          diffStart = System.nanoTime()
          remainingCandidates = {
            remainingCandidates.diff(remainingCandidates.filter(_ <= highestToRemove).map(_ * nextPrime))
          }
//          diffEnd = System.nanoTime()
//          diffTime = diffTime + diffEnd - diffStart

//          println(f"Time spent on appending: ${appendTime.toDouble / 1_000_000_000.0} s")
//          println(f"Time spent on taking vector difference & filter: ${diffTime.toDouble / 1_000_000_000.0} s")
        }

        val primes: Vector[Int] = partialPrimes ++ remainingCandidates.tail.map(_.toInt)


        // Before returning, let's save these primes for later.

        import java.io._
        val file = new File(filename)
        val bw = new BufferedWriter(new FileWriter(file))
        bw.write(n.toString + "\n")
        // Way too big! Just write each line on its own instead.
        //      primes.map(_.toString).reduce(_ + "\n" + _).foreach(line => bw.write(line))
        primes.foreach(prime => bw.write(prime.toString + "\n"))
        bw.close()

        primes
      }
    }


    object Factorization {

      // Gets the prime factorization of the input.
      // A prime factorization is encoded as a vector of pairs of the form (p, e). A pair (p, e) is included iff
      // p^e is the largest power of p that divides n and e>0.
      def getPrimeFactorization(n: Int): Vector[(Int, Int)] = {

        // If n has a proper prime factor, then it is at most the square root of n.
        val primes: Vector[Int] = primesLessThan(scala.math.sqrt(n.toDouble).ceil.toInt+1)

        // To factor n: For each prime p, if p divides n, divide the highest power of p by which n is divisible.
        // Record the highest power of p that divides n and continue the process with remaining := n / p^e
        val (smallFactors, remaining): (Vector[(Int, Int)], Int) = primes
          .foldLeft[(Vector[(Int, Int)], Int)]((Vector(), n))({

            case ((currentFactors, currentRemaining), nextPrime) =>
              if (currentRemaining % nextPrime == 0) {
                var nextRemaining: Int = currentRemaining
                var degree: Int = 0
                while (nextRemaining % nextPrime == 0) {
                  nextRemaining = nextRemaining / nextPrime
                  degree = degree + 1
                }

                (currentFactors.appended((nextPrime, degree)), nextRemaining)
              } else {
                (currentFactors, currentRemaining)
              }

          })

        // We found almost all primes that divide n, but whatever is left over might be nontrivial
        smallFactors ++ {if (remaining == 1) Vector() else Vector((remaining, 1))}
      }

      // Gets the prime factors of n, but not the associated powers
      def getPrimeFactors(n: Int): Vector[Int] = getPrimeFactorization(n).map(_._1)

      // If we have the prime factorizations of x and y, then the prime factorization of xy need not be computed
      // directly. Instead, we can merge the prime factorizations of x and y by summing matching prime powers.
      // E.g., (2^3*5^4) * (5^2*7^1) = 2^3*5^(4+2)*7^1
      def mergeFactorizations(a: Vector[(Int, Int)], b: Vector[(Int, Int)]): Vector[(Int, Int)] = (a ++ b)
        .groupBy(_._1)
        .map({case (prime, appearances) => (prime, appearances.map(_._2).sum)})
        .toVector
        .sortBy(_._1)

      // Faster to compute the factorizations of the factors and then merge them than compute the factorization
      // of the product directly. The intuition here is that m and n are already factors of mn, so it seems fishy to
      // ignore that knowledge and just multiply m and n.
      def getPrimeFactorizationOfProduct(m: Int, n: Int): Vector[(Int, Int)] = {
        mergeFactorizations(getPrimeFactorization(m), getPrimeFactorization(n))
      }

      // Creates a "nice" string representation of a prime factorization
      def getFactorizationRepresentation(primeFactorization: Vector[(Int, Int)]): String = primeFactorization
        .map({case (prime, degree) =>
          if (degree > 1) {
            prime.toString + "^" + degree.toString
          } else {
            prime.toString
          }
        }).reduce(_ + " * " + _)

      // Returns the number of divisors of a number given its prime factorization
      // We need not compute all of these factors. Instead, the prime factorization of a divisor of a number
      // is a "sub-factorization" of the original: The only primes that may appear are those that appear as
      // divisors of the original number and their associated powers must not exceed those found in the original number.
      // Conversely, any number with such a prime factorization will be a divisor of the number of interest.
      //
      // Finding divisors of a number from its prime factorization amounts to choosing how to reduce each prime exponent.
      //
      // For example, the divisors of 2^3*5^1 are:
      //
      // 2^3 * 5^1
      // 2^2 * 5^1
      // 2^1 * 5^1
      // 2^0 * 5^1
      // 2^3 * 5^0
      // 2^2 * 5^0
      // 2^1 * 5^0
      // 2^0 * 5^0
      //
      // Ultimately, the number of choices is the product of the number of choices in each prime exponent,
      // i.e., the exponent in the original number plus one.
      def getNumDivisorsFromFact(primeFactorization: Vector[(Int, Int)]): Long = {
        primeFactorization.map(_._2 + 1).product
      }

      def getNumDivisors(n: Int): Long = getNumDivisorsFromFact(getPrimeFactorization(n))
      def getNumProperDivisors(n: Int): Long = getNumDivisors(n) - 1L


      // Aliquot sums can be computed directly from prime factorizations with a little cleverness!
      // If n=p1^e1 * p2^e2 * ... * pk^ek, then its sum of divisors factors as:
      // (1 + p1 + p1^2 + ... + p1^e1) * ... * (1 + pk + pk^2 + ... + pk^ek)
      // To see this, note that expanding the above product amounts to choosing a summand from each factor and multiplying
      // them. This is the same as choosing a divisor of n.
      // Note that each factor above is geometric, so we can do even better. The above product simplifies to:
      // [(p1^(e1+1)-1) / (p1 - 1)] * ... * [(pk^(ek+1)-1) / (pk - 1)]
      def computeAliquotSumFromFact(primeFactorization: Vector[(Int, Int)]): Long = {
        {
          primeFactorization
            .map({case (prime, degree) => (scala.math.pow(prime, degree + 1) - 1.0) / (prime.toDouble - 1.0)})
            .product
            .toLong
        } - {
          primeFactorization
            .map({case (prime, degree) => scala.math.pow(prime, degree)})
            .product
            .toLong
        }
      }
      def computeAliquotSum(n: Int): Long = {
        if (n < 2) 0 else computeAliquotSumFromFact(getPrimeFactorization(n))
      }

      def getDeficientNumbersBelow(n: Int): Vector[Int] = (1 until n).filter(k => k > computeAliquotSum(k)).toVector
      def getPerfectNumbersBelow(n: Int): Vector[Int] = (1 until n).filter(k => k == computeAliquotSum(k)).toVector
      def getAbundantNumbersBelow(n: Int): Vector[Int] = (1 until n).filter(k => k < computeAliquotSum(k)).toVector

    }

  }

}
