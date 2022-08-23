package io.github.cmeredit.problem32

import org.scalatest.funspec.AnyFunSpec

class PE32Spec extends AnyFunSpec {

  describe("The Problem 32 Utility Object") {

    it("should detect the example pandigital identity") {
      assert(PE32Util.isIdentityPandigital(Vector(3, 9), Vector(1, 8, 6), Vector(7, 2, 5, 4)))
    }

  }

}
