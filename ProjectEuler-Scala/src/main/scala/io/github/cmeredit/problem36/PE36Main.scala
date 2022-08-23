package io.github.cmeredit.problem36

import io.github.cmeredit.MathUtil

object PE36Main extends App {

  val doubleBasePalindromes: Seq[Int] = (1 to 1_000_000).filter(x => {
    val digits = MathUtil.getDigits(x)
    digits == digits.reverse
  }).filter(x => {
    val binaryDigits = MathUtil.getDigits(x, 2)
    binaryDigits == binaryDigits.reverse
  })

  doubleBasePalindromes foreach println

  println(f"The sum of all double-base palindromes below one million is ${doubleBasePalindromes.sum}")

}
