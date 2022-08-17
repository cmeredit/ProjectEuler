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

  }

}
