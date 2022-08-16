package io.github.cmeredit.problem22

import org.scalatest.funspec.AnyFunSpec

class PE22Spec extends AnyFunSpec {

  describe("The Problem 22 Utility Object") {
    it("should compute the same alphabet value as in the problem example") {
      assert(PE22Util.getAlphabetValue("COLIN") === 53)
    }

    it("should load the correct number of names") {
      assert(PE22Util.getNamesFromFile("src/main/scala/io/github/cmeredit/problem22/p022_names.txt").length > 5000)
    }

    it("should find the 938-th name (in alphabetical order) to be \"COLIN\"") {
      val names = PE22Util.getNamesFromFile("src/main/scala/io/github/cmeredit/problem22/p022_names.txt")

      assert(names.sorted.toVector(937) == "COLIN")
    }
  }

}
