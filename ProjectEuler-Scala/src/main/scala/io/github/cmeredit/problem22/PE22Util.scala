package io.github.cmeredit.problem22

import scala.io.Source

object PE22Util {

  def getNamesFromFile(filename: String, delim: String = ","): Array[String] = {

    val bufferedSource = Source.fromFile(filename)
    val megaLine: String = bufferedSource.getLines().next()
    bufferedSource.close()

    megaLine.split(delim).map(_.filter(_.toString != "\""))

  }

  def getAlphabetValue(str: String): Int = str.toVector.map(_.toInt - 64).sum

}
