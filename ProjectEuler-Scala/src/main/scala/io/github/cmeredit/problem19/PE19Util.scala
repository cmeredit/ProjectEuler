package io.github.cmeredit.problem19

object PE19Util {
  
  val dayNames: Map[Int, String] = Map(
    0 -> "Sunday",
    1 -> "Monday",
    2 -> "Tuesday",
    3 -> "Wednesday",
    4 -> "Thursday",
    5 -> "Friday",
    6 -> "Saturday"
  )
  val dayNumbers: Map[String, Int] = dayNames.map({case (a, b) => (b, a)})
  
  val monthNames: Map[Int, String] = Map(
    0 -> "January",
    1 -> "February",
    2 -> "March",
    3 -> "April",
    4 -> "May",
    5 -> "June",
    6 -> "July",
    7 -> "August",
    8 -> "September",
    9 -> "October",
    10 -> "November",
    11 -> "December"
  )
  val monthNumbers: Map[String, Int] = monthNames.map({case (a, b) => (b, a)})

  def getMonthLength(month: Int, year: Int): Int = {
    if (Vector("September", "April", "June", "November").contains(monthNames(month))) 30
    else if (monthNumbers("February") != month) 31
    else {
      // A year is a leap year iff [the year is divisible by 4] and [if the year is a century, then it is divisible by 400]
      // Recall that [P implies Q] is equivalent to [Not(P) or Q]
      val isLeapYear: Boolean = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)

      if (isLeapYear) 29 else 28
    }
  }

  // Returns an indexed sequence of elements of the form (year, month number, day number). A triple (y, m, d) is included iff
  // y is between startYear and endYear (inclusive) and the first day of month m in year y is d.
  def getMonthStartInfo(startYear: Int, startMonth: Int, startDay: Int, endYear: Int, endMonth: Int): IndexedSeq[(Int, Int, Int)] = {
    val years: IndexedSeq[Int] = startYear to endYear

    val yearMonthPairs: IndexedSeq[(Int, Int)] = years.flatMap(y => {
      val months = if (y == startYear) {
        startMonth to 11
      } else if (y == endYear) {
        0 to endMonth
      } else {
        0 to 11
      }

      months.map(m => (y, m)).toVector
    })

    val days: IndexedSeq[Int] = yearMonthPairs.foldLeft(IndexedSeq(startDay))({case (currentStartDays, (curYear, curMonth)) =>
      currentStartDays.appended((currentStartDays.last + getMonthLength(curMonth, curYear)) % 7)
    })

    yearMonthPairs.zip(days).map({case ((a, b), c) => (a, b, c)})

  }

}
