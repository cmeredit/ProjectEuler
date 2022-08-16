package io.github.cmeredit

import org.scalatest.funspec.AnyFunSpec

class MathSpec extends AnyFunSpec {

  describe("The Math Utility Object") {
    describe("When used to compute a triangular number") {
      it("should agree with a brute-force computation") {
        (1 to 100).foreach(n => assert((1 to n).sum === MathUtil.triangleNum(n)))
      }
    }
  }

}
