package io.github.cmeredit.problem30

import org.scalatest.funspec.AnyFunSpec

class PE30Spec extends AnyFunSpec {

  describe("The Problem 30 Utility Object") {

    it("should produce the correct digits of a few test numbers") {

      assert(PE30Util.getDigits(123456789) == Vector(1, 2, 3, 4, 5, 6, 7, 8, 9))
      assert(PE30Util.getDigits(100) == Vector(1, 0, 0))
      assert(PE30Util.getDigits(0) == Vector())

    }

    it("should (on nonzero inputs) invert getDigits with digitsToInt") {

      (1 to 1000).foreach(n => assert(n == PE30Util.digitsToInt(PE30Util.getDigits(n))))

    }

    it("should (on nonempty inputs) invert digitsToInt with getDigits") {

      for (
        a <- 1 to 9;
        b <- 0 to 9;
        c <- 0 to 9;
        d <- 0 to 9
      ) assert(PE30Util.getDigits(PE30Util.digitsToInt(Vector(a, b, c, d))) == Vector(a, b, c, d))

    }

  }

  // TODO: Write tests for the rest of PE30Util

}
