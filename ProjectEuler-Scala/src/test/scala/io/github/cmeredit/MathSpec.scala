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

          for (
            a <- 1 to 100;
            b <- 1 to 100 if a > 1 || b > 1
          ) assert(
            MathUtil.Primes.Factorization.getPrimeFactorization(a * b)
            ===
            MathUtil.Primes.Factorization.mergeFactorizations(
              MathUtil.Primes.Factorization.getPrimeFactorization(a),
              MathUtil.Primes.Factorization.getPrimeFactorization(b)
            )
          )


          // Records the number of (not necessarily proper) divisors of the given keys
          //
          // Taken from https://en.wikipedia.org/wiki/Divisor_function
          val numberOfDivisorsLimited: Map[Int, Int] = Map(
            2 -> 2,
            3 -> 2,
            4 -> 3,
            5 -> 2,
            6 -> 4,
            7 -> 2,
            8 -> 4,
            50 -> 6,
            49 -> 3,
            48 -> 10,
            47 -> 2,
            46 -> 4,
            45 -> 6
          )

          numberOfDivisorsLimited.foreach({case (n, s) =>
            assert(MathUtil.Primes.Factorization.getNumDivisors(n).toInt === s)
          })

          // Records the sum of proper divisors of the given keys
          //
          // Taken from https://en.wikipedia.org/wiki/Aliquot_sum
          val aliquotSumsLimited: Map[Int, Int] = Map(
            1 -> 0,
            2 -> 1,
            3 -> 1,
            4 -> 3,
            5 -> 1,
            6 -> 6,
            7 -> 1,
            8 -> 7,
            9 -> 4,
            10 -> 8
          )

          aliquotSumsLimited.foreach({case (n, s) =>
            assert(MathUtil.Primes.Factorization.computeAliquotSum(n).toInt === s)
          })


          val computedDeficientNums: Vector[Int] = MathUtil.Primes.Factorization.getDeficientNumbersBelow(100)
          val computedPerfectNums: Vector[Int] = MathUtil.Primes.Factorization.getPerfectNumbersBelow(100)
          val computedAbundantNums: Vector[Int] = MathUtil.Primes.Factorization.getAbundantNumbersBelow(100)

          // OEIS A005100, https://oeis.org/A005100
          val knownDeficientNums: Vector[Int] = Vector(1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 19, 21, 22,
            23, 25, 26, 27, 29, 31, 32, 33, 34, 35, 37, 38, 39, 41, 43, 44, 45, 46, 47, 49, 50, 51, 52, 53, 55, 57, 58,
            59, 61, 62, 63, 64, 65, 67, 68, 69, 71, 73, 74, 75, 76, 77, 79, 81, 82, 83, 85, 86)
          // OEIS A000396, https://oeis.org/A000396
          val knownPerfectNums: Vector[Int] = Vector(6, 28)
          // OEIS A005101, https://oeis.org/A005101
          val knownAbundantNums: Vector[Int] = Vector(	12, 18, 20, 24, 30, 36, 40, 42, 48, 54, 56, 60, 66, 70, 72, 78,
            80, 84, 88, 90, 96)

          knownDeficientNums.foreach(n => assert(computedDeficientNums.contains(n)))
          knownPerfectNums.foreach(n => assert(computedPerfectNums.contains(n)))
          knownAbundantNums.foreach(n => assert(computedAbundantNums.contains(n)))


        }

      }
    }
  }

}
