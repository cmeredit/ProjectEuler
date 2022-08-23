package io.github.cmeredit.problem31

object PE31Util {

  private var psTable: Map[(Int, Int), Int] = Map()

  def PS(S: Vector[Int], n: Int, k: Int): Int = {
    if (psTable.contains((n, k))) {
      psTable((n, k))
    } else {
      if (!S.contains(k)) {
        0
      } else {
        val result: Int = if (k == 1) {
          // Include the special case of k=1. If we don't include this, then we'll
          // travel n steps down the recurrence
          1
        } else if (k > n) {
          0
        } else if (k == n) {
          1
        } else {
          S.filter(j => j <= math.min(k, n-k)).map(j => PS(S, n-k, j)).sum
        }

        psTable = psTable.updated((n, k), result)
        result
      }
    }
  }

  // Computes P_S(n), the number of S-partitions of n
  def PS(S: Vector[Int], n: Int): Int = {
    S.map(k => PS(S, n, k)).sum
  }
}
