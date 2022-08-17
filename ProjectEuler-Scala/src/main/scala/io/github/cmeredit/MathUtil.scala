package io.github.cmeredit

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

  }

}
