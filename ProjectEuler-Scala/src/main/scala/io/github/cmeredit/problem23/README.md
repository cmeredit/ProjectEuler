# Non-abundant Sums

## Problem 23



> A perfect number is a number for which the sum of its proper divisors is exactly equal to the number. For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.
> 
> A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.
> 
> As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of two abundant numbers is 24. By mathematical analysis, it can be shown that all integers greater than 28123 can be written as the sum of two abundant numbers. However, this upper limit cannot be reduced any further by analysis even though it is known that the greatest number that cannot be expressed as the sum of two abundant numbers is less than this limit.
> 
> Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.

---

### A Slow Solution to Avoid

One solution to this problem is to loop through all numbers below 28,124, look at all ways of splitting those numbers into 
a sum of two lesser numbers, then testing if those two lesser numbers are abundant. E.g., something like:

```scala
import io.github.cmeredit.MathUtil
(1 to 28123).filterNot(n => {
  (1 to n/2).exists(a => {
    a < MathUtil.Primes.Factorization.computeAliquotSum(a) &&
      (n - a) < MathUtil.Primes.Factorization.computeAliquotSum(n-a)
  })
}).sum
```

However, this solution performs a _lot_ of repeat work! The aliquot sum of a number $a$ is computed, for example, for each 
$n$ greater than $2a$.

This solution is a sort of "top-down" solution: We start with candidate numbers and try to split them into sums of 
abundant numbers. A better solution is...

---

### A Bottom-Up Solution

We can compute the [aliquot sum](https://en.wikipedia.org/wiki/Aliquot_sum) of each positive integer below 28,124 exactly 
once, thereby determining which of these numbers are abundant. Then, we can look at all sums of pairs of abundant numbers 
and exclude them.