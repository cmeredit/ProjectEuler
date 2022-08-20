package io.github.cmeredit.problem30

object PE30Main extends App {

  // TODO: Figure out why the heck we need the .distinct!
  val curiousNumbers: Vector[Int] = PE30Util.getCandidates.filter(digits => PE30Util.powerDigitSum(digits) == PE30Util.digitsToInt(digits)).map(PE30Util.digitsToInt).distinct

  println("All of the following numbers equal their own fifth power digit sum!")
  curiousNumbers.foreach(num => println(f"$num == ${PE30Util.powerDigitSum(PE30Util.getDigits(num))}"))

  println(f"Their sum is ${curiousNumbers.sum}")

}
