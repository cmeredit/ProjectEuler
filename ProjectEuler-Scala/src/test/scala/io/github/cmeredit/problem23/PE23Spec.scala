package io.github.cmeredit.problem23

import org.scalatest.funspec.AnyFunSpec

class PE23Spec extends AnyFunSpec {

  describe("The Problem 22 Utility Object") {
    it("should agree with the information given in the problem") {

      assert(PE23Util.getAbundantSumsBelow(25) == Vector(24))

    }
  }

}
