package io.github.cmeredit.problem22

object PE22Main extends App {

  val sortedNames: Array[String] = PE22Util.getNamesFromFile("src/main/scala/io/github/cmeredit/problem22/p022_names.txt").sorted

  val namesScores: Array[Long] = sortedNames.zipWithIndex.map({case (name, index) => PE22Util.getAlphabetValue(name).toLong * (index + 1).toLong})

  println(f"Total of all names scores is: ${namesScores.sum}")
}
