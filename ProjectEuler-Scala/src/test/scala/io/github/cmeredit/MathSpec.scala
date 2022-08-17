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

    }
  }

}
