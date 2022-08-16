package io.github.cmeredit.problem1

import org.scalatest.funspec.AnyFunSpec

class PE1Spec extends AnyFunSpec {

  describe("The Problem 1 Utility Object") {
    describe("when used in a brute-force style") {
      it("should agree with the problem statement on an example input") {
        assert(PE1Util.PE1BruteForce(10) === 23)
      }
    }

    describe("when used in a clever style") {
      it("should agree with the problem statement on an example input") {
        assert(PE1Util.PE1(10) === 23)
      }
    }

    describe("when used in either style") {
      it("should produce the same output") {
        (1 until 100).foreach(n => assert(PE1Util.PE1(n) === PE1Util.PE1BruteForce(n)))
      }
    }
  }

}
