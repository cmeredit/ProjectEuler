package io.github.cmeredit.problem30

import io.github.cmeredit.MathUtil

object PE30Main extends App {

  // TODO: Figure out why the heck we need the .distinct!
  val curiousNumbers: Vector[Int] = PE30Util.getCandidates.filter(digits => PE30Util.powerDigitSum(digits) == MathUtil.digitsToInt(digits)).map(MathUtil.digitsToInt).distinct

  println("All of the following numbers equal their own fifth power digit sum!")
  curiousNumbers.foreach(num => println(f"$num == ${PE30Util.powerDigitSum(MathUtil.getDigits(num))}"))

  println(f"Their sum is ${curiousNumbers.sum}")

}
