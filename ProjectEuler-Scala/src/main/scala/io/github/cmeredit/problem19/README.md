# Counting Sundays

## Problem 19

> You are given the following information, but you may prefer to do some research for yourself.
> - 1 Jan 1900 was a Monday.
> - Thirty days has September,
    April, June and November.
    All the rest have thirty-one,
    Saving February alone,
    Which has twenty-eight, rain or shine.
    And on leap years, twenty-nine.
> - A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
> 
> How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?

---

### A Brute Force Solution

Let the days of the week be indexed by $0, \dots, 7$ in their usual order. 
Given a month $m$ belonging to year $y$, let $SD(m, y)$ denote the index of the first day of $m$ in year $y$ 
and let $L(m, y)$ denote the length of month $m$ in year $y$. Let $y'$ denote the year of the month directly following month $m$.

The $SD$ function satisfies the following recurrence:

- $SD(0, 1900) = 1$
- $SD(m+1, y') \equiv SD(m, y) + L(m, y)$ ($mod\ 7$)

And the $L$ function can be determined by a fixed set of conditions on the month and year (e.g., through consideration of leap years).

Hence, to solve this problem, it is possible to simply enumerate all month/year combinations in the desired range, then use the above recurrence to compute the start day of each month.
Once this has been completed, we need only count how many months start with a Sunday.

### Month Lengths

The poem in the problem statement resolves to the following code:
```scala
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
```

---

### A Better Solution

This problem can be solved without enumerating and filtering all year/month/day triples in the desired range:
The pattern of month-start-days repeats every few years (within a given century), so it's actually only necessary to count Sunday-starts within a single cycle of this pattern (and then extend to the whole century).