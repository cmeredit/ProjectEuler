package io.github.cmeredit

import org.scalatest.funspec.AnyFunSpec

class MathSpec extends AnyFunSpec {

  describe("The Math Utility Object") {
    describe("When used to compute a triangular number") {
      it("should agree with a brute-force computation") {
        (1 to 100).foreach(n => assert((1 to n).sum === MathUtil.triangleNum(n)))
      }
    }

    describe("And its Primes subobject") {

      it("should produce the correct first few primes") {

        assert(MathUtil.Primes.primesLessThan(10) === Vector(2, 3, 5, 7))

      }

      it("should produce the correct number of primes below certain limits") {
        // Values of the prime counting function taken from https://en.wikipedia.org/wiki/Prime-counting_function

        assert(MathUtil.Primes.primesLessThan(100).length === 25)
        assert(MathUtil.Primes.primesLessThan(1_000).length === 168)
        assert(MathUtil.Primes.primesLessThan(10_000).length === 1_229)
        assert(MathUtil.Primes.primesLessThan(100_000).length === 9_592)
        assert(MathUtil.Primes.primesLessThan(1_000_000).length === 78_498)

      }

      it("should not produce any composite numbers") {

        val upper = 100

        val primes = MathUtil.Primes.primesLessThan(upper * upper + 1)

        for (
          i <- 2 to upper;
          j <- 2 to i
        ) assert(!primes.contains(i * j))

      }

      describe("And its Factorization subobject") {

        it("should perform correctly on limited, known, examples") {

          val primes: Vector[Int] = MathUtil.Primes.primesLessThan(100)

          for (
            p <- primes;
            q <- primes;
            r <- primes if p < q && q < r
          ) {
//            println(f"p: $p q: $q r: $r")
//            println(f"pqr: ${p*q*r}")
//            println(f"Computed factorization: ${MathUtil.Primes.Factorization.getFactorizationRepresentation(MathUtil.Primes.Factorization.getPrimeFactorization(p*q*r))}")
            assert(
              MathUtil.Primes.Factorization.getPrimeFactorization(p * q * r) ===
                Vector((p, 1), (q, 1), (r, 1))
            )
          }


          for (
            e1 <- 0 to 5;
            e2 <- 0 to 5;
            e3 <- 0 to 5 if !(e1 == 0 && e2 == 0 && e3 == 0)
          ) assert(
            MathUtil.Primes.Factorization.getPrimeFactorization(
              (math.pow(2, e1) * math.pow(3, e2) * math.pow(5, e3)).toInt
            ) ===
              Vector((2, e1), (3, e2), (5, e3)).filter(_._2 != 0)
          )

        }

      }
    }
  }

}
