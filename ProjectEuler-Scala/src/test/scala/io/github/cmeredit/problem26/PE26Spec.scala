package io.github.cmeredit.problem26

import io.github.cmeredit.MathUtil
import org.scalatest.funspec.AnyFunSpec

class PE26Spec extends AnyFunSpec {

  describe("The Problem 26 Utility Object") {

    it("should find a cycle length of zero for numbers of the form 1/(2^p * 5^q)") {

      for (
        p <- 1 to 5;
        q <- 1 to 5
      ) assert(
        PE26Util.getCycleLength((math.pow(2, p) * math.pow(5, q)).toInt) == 0
      )

    }

    it("should find a nonzero cycle length for reciprocals of primes other than 2 and 5") {
      MathUtil.Primes.primesLessThan(1_000).filter(p => p != 2 && p != 5).foreach(p => {
        assert(PE26Util.getCycleLength(p) > 0)
      })
    }

    it("should compute the same cycle lengths as given in the problem") {
      assert(PE26Util.getCycleLength(2) == 0)
      assert(PE26Util.getCycleLength(3) == 1)
      assert(PE26Util.getCycleLength(4) == 0)
      assert(PE26Util.getCycleLength(5) == 0)
      assert(PE26Util.getCycleLength(6) == 1)
      assert(PE26Util.getCycleLength(7) == 6)
      assert(PE26Util.getCycleLength(8) == 0)
      assert(PE26Util.getCycleLength(9) == 1)
      assert(PE26Util.getCycleLength(10) == 0)
    }

  }

}
