package io.github.cmeredit.problem32

object PE32Main extends App {

  val allPandigitalIdentities: Vector[(Int, Int, Int)] = PE32Util.findPandigitalIdentities

  allPandigitalIdentities.foreach({case (m, n, mn) => println(f"$m * $n = $mn")})

  println(f"Sum of distinct pandigital identity products: ${allPandigitalIdentities.map(_._3).distinct.sum}")

}
