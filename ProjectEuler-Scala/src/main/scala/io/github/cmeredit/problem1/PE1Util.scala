package io.github.cmeredit.problem1

object PE1Util {

  def PE1BruteForce(n: Int): Int = {
    (1 until n).filter(x => x % 3 == 0 || x % 5 == 0).sum
  }

  def PE1(n: Int): Int = {

    // Calculates the k-th triangular number
    def triangleNum(k: Int): Int = k * (k+1) / 2

    3*triangleNum((n-1)/3) + 5*triangleNum((n-1)/5) - 15*triangleNum((n-1)/15)

  }

}
