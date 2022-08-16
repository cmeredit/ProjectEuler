package io.github.cmeredit.problem19

import org.scalatest.funspec.AnyFunSpec

class PE19Spec extends AnyFunSpec {

  describe("The Problem 19 Utility Object") {
    it("should produce the correct length of various known months") {
      // Leap years
      assert(PE19Util.getMonthLength(1, 1912) === 29)
      assert(PE19Util.getMonthLength(1, 1600) === 29)
      // Weird leap year exception
      assert(PE19Util.getMonthLength(1, 1900) === 28)

      // All of 2022, manually typed out!
      assert(PE19Util.getMonthLength(0, 2022) === 31)
      assert(PE19Util.getMonthLength(1, 2022) === 28)
      assert(PE19Util.getMonthLength(2, 2022) === 31)
      assert(PE19Util.getMonthLength(3, 2022) === 30)
      assert(PE19Util.getMonthLength(4, 2022) === 31)
      assert(PE19Util.getMonthLength(5, 2022) === 30)
      assert(PE19Util.getMonthLength(6, 2022) === 31)
      assert(PE19Util.getMonthLength(7, 2022) === 31)
      assert(PE19Util.getMonthLength(8, 2022) === 30)
      assert(PE19Util.getMonthLength(9, 2022) === 31)
      assert(PE19Util.getMonthLength(10, 2022) === 30)
      assert(PE19Util.getMonthLength(11, 2022) === 31)
    }

    it("should produce the correct first day of January, 2022 after extending from 1900") {
      // January, 2022 started on a Saturday
      assert(PE19Util.getMonthStartInfo(1900, PE19Util.monthNumbers("January"), PE19Util.dayNumbers("Monday"), 2022, PE19Util.monthNumbers("January")).last === (2022, 0, 6))
    }
  }

}
