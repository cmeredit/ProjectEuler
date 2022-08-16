package io.github.cmeredit.problem19

object Main19 extends App {
  val monthStartInfo: IndexedSeq[(Int, Int, Int)] = PE19Util
    .getMonthStartInfo(1900, PE19Util.monthNumbers("January"), PE19Util.dayNumbers("Monday"), 2000, PE19Util.monthNumbers("December"))
    .filter({case (y, _, _) => y > 1900})

  println(f"${monthStartInfo.count({case (_, _, d) => d == PE19Util.dayNumbers("Sunday")})} months during the twentieth century started with Sunday")
}
