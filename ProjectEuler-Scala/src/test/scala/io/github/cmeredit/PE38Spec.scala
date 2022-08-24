package io.github.cmeredit

import io.github.cmeredit.problem38.PE38Util
import org.scalatest.funspec.AnyFunSpec

class PE38Spec extends AnyFunSpec{

  describe("The Problem 38 Utility Object") {

    it("should detect pandigitality of known examples") {

      (1 to 9).permutations.take(100).foreach(digits => assert(PE38Util.isPandigital(digits.toVector)))
      (1 to 4).permutations.foreach(digits => assert(!PE38Util.isPandigital(digits.toVector)))

    }

    it("should compute example concatenated products correctly") {

      assert(PE38Util.computeConcatenatedProduct(Vector(1, 9, 2), 3) == Vector(1, 9, 2, 3, 8, 4, 5, 7, 6))
      assert(PE38Util.computeConcatenatedProduct(Vector(9), 5) == Vector(9, 1, 8, 2, 7, 3, 6, 4, 5))
      
    }

  }

}
