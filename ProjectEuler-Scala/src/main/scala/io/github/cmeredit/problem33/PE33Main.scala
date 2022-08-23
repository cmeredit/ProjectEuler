package io.github.cmeredit.problem33

import scala.annotation.tailrec

object PE33Main extends App {

  val candidates: IndexedSeq[((Int, Int), (Int, Int))] = for (
    n1 <- 1 to 9;
    n0 <- 0 to 9;
    d1 <- 1 to 9;
    d0 <- 0 to 9 if 10*n1 + n0 < 10*d1 + d0 && (n1 == d0 || n0 == d1) && !(n0 == 0 && d0 == 0)
  ) yield ((n1, n0), (d1, d0))


//  candidates foreach println



  val funnyFractions = candidates.filter({case ((n1, n0), (d1, d0)) =>
    if (n1 == d0) {
      (10*n1 + n0).toDouble / (10*d1 + d0).toDouble == n0.toDouble / d1.toDouble
    } else {
      (10*n1 + n0).toDouble / (10*d1 + d0).toDouble == n1.toDouble / d0.toDouble
    }
  })


  funnyFractions.foreach({case ((n1, n0), (d1, d0)) => println(f"$n1$n0 / $d1$d0 = ${if (n1 == d0) f"$n0/$d1" else f"$n1/$d0"}")})

}
