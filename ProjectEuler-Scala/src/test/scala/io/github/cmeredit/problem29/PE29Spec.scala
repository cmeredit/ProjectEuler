package io.github.cmeredit.problem29

import org.scalatest.funspec.AnyFunSpec

class PE29Spec extends AnyFunSpec {

  describe("The Problem 29 Utility Object") {
    it("should agree with the example in the problem statement") {
      assert(PE29Util.countUniquePowers(2, 5, 2, 5) == 15)
    }
  }

}
