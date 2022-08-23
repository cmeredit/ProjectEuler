package io.github.cmeredit.problem32

import io.github.cmeredit.MathUtil

object PE32Util {

  val compatibleDigits: Map[Int, Vector[Int]] = (1 to 9).map(mLastDigit => mLastDigit -> {
    (1 to 9).toVector.filter(nLastDigit => {
      (mLastDigit != nLastDigit) && (mLastDigit * nLastDigit % 10 != mLastDigit) && (mLastDigit * nLastDigit % 10 != nLastDigit) && (mLastDigit * nLastDigit % 10 != 0)
    })
  }).toMap

  def isIdentityPandigital(m: Vector[Int], n: Vector[Int], mn: Vector[Int]): Boolean = (m ++ n ++ mn).length == 9 && (m ++ n ++ mn).toSet == (1 to 9).toSet

  // Find all pandigital identities - not the same as finding all products that belong to a pandigital identity!
  def findPandigitalIdentities: Vector[(Int, Int, Int)] = {

    def without(input: IndexedSeq[Int], remove: Vector[Int]): Vector[Int] = input.toVector.filterNot(n => remove.contains(n))

    // All pandigital identities with numDigits(m)=1 and numDigits(n)=4
    val oneFourCandidates = for (
      m1 <- 1 to 9;
      n1 <- compatibleDigits(m1);
      n2 <- without(1 to 9, Vector(m1, n1));
      n3 <- without(1 to 9, Vector(m1, n1, n2));
      n4 <- without(1 to 9, Vector(m1, n1, n2, n3))
    ) yield (Vector(m1), Vector(n4, n3, n2, n1))

    // All pandigital identities with numDigits(m)=2 and numDigits(n)=3
    val twoThreeCandidates = for (
      m1 <- 1 to 9;
      n1 <- compatibleDigits(m1);
      m2 <- without(1 to 9, Vector(m1, n1));
      n2 <- without(1 to 9, Vector(m1, m2, n1));
      n3 <- without(1 to 9, Vector(m1, m2, n1, n2))
    ) yield (Vector(m2, m1), Vector(n3, n2, n1))

//    oneFourCandidates foreach println
//    twoThreeCandidates foreach println
//
//    println(oneFourCandidates.length)
//    println(twoThreeCandidates.length)

    val allCandidates: Vector[(Vector[Int], Vector[Int])] = oneFourCandidates.toVector ++ twoThreeCandidates.toVector

    allCandidates
      .map({case (m, n) => (m, n, MathUtil.getDigits(MathUtil.digitsToInt(m) * MathUtil.digitsToInt(n)))})
      .filter({case (m, n, mn) => isIdentityPandigital(m, n, mn)})
      .map({case (m, n, mn) => (MathUtil.digitsToInt(m), MathUtil.digitsToInt(n), MathUtil.digitsToInt(mn))})

  }

}