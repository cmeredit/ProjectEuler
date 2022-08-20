# Digit Fifth Powers

## Problem 30

> Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
> 
> $1634 = 1^4 + 6^4 + 3^4 + 4^4$
> $8208 = 8^4 + 2^4 + 0^4 + 8^4$
> $9474 = 9^4 + 4^4 + 7^4 + 4^4$
> 
> As $1 = 1^4$ is not a sum it is not included.
> 
> The sum of these numbers is $1634 + 8208 + 9474 = 19316$.
> 
> Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.

---

### Finiteness

Given a positive integer $p$, let $DS_p(n)$ be the sum of $p$-th powers of the digits of $n$. In order for problems 
of the given form to be solvable, we need to show that there are only finitely many numbers satisfying $DS_p(n) = n$. 
Before we begin, note that a negative number cannot be its own power digit sum (a sum of powers of positive bases
cannot be negative).

Consider the digit sum of an $N$-digit number with digits $d_0$, $\dots$, $d_{N-1}$:

$$
\begin{align}
DS_p\left(\sum_{k=0}^{N-1} d_k10^k\right) &= \sum_{k=0}^{N-1} d^p\\
&\leq \sum_{k=0}^{N-1} 9^p\\
&= 9^pN
\end{align}
$$

Hence, the power digit sum of a number is bounded above by a linear function of its number of digits. However, the
number itself is approximately exponential in its number of digits! Hence, there is some $M$ such that for all 
$n \geq M$, $DS_p(n) < n$, i.e., there are only finitely many $n$ satisfying $DS_p(n) = n$.

In the case of $p=5$, any number with at least $7$ digits will exceed its power digit sum.

### Reductions

Some numbers can be excluded outright. For example, if a two-digit number has one of its digits greater than 2, then it
cannot possibly equal its own power digit sum as $3^5$, $\dots$, $9^5$ all have at least $3$ digits. Hence, instead
of checking all $90$ two-digit numbers, we need only check $10$, $11$, $12$, $20$, $21$, and $22$. We can come up
with similar reductions for an $N$-digit number. Let the maxdigit of a number be its highest digit. The maximum maxdigit
of an $N$-digit number $n$, under the condition that $DS_5(n)=n$, can be bounded by the following observation. Suppose
the maxdigit of $n$ is $D$. Then

$$
DS_5(n) \geq D^5
$$

So if $D^5 > n$, then we can't possibly have $DS_5(n) = n$! E.g., in the case that a two-digit number has maxdigit 3,
we have $DS_5(n) \geq 3^5 = 243$, which is greater than any two-digit number (this eliminates $13$, $39$, etc.). This 
restricts the maximum maxdigit of our number. Similarly,

$$
DS_5(n) \leq ND^5
$$

So if $ND^5 \leq n$, then we can't possibly have $DS_5(n) = n$! E.g., in the case that a two-digit number has maxdigit 1,
we have $DS_5(n) \leq 2 \cdot 1^5 = 2$, which is less than any two-digit number (this eliminates $11$ and $10$). This
limits the minimum maxdigit of our number.

These two reductions limit rather heavily the maxdigit of our candidates. We can do something similar to limit the
mindigits as well!

Let $d$ denote the mindigit of $n$. Then

$$
DS_5(n) \geq Nd^5
$$

So if $ND^5 > n$, then we can't possibly have $DS_5(n) = n$! E.g., in the case that a two-digit number has mindigit 3,
we have $DS_5(n) \geq 2 \cdot 3^5 = 486$. This limits the maximum mindigit of our number.

Unfortunately, there are two problems with this type of reduction

1. For this particular problem, the mindigit restrictions don't actually restrict anything! E.g., all two-digit numbers with mindigit 3 have maxdigit at least 3, and are thereby eliminated in the maxdigit reduction. All such mindigit restrictions in _this_ problem are entailed by the maxdigit restrictions. However, this is not the case for other problems. For example, there are nontrivial mindigit restrictions for the problem that has "sum of fifth powers of digits" replaced with "sum of factorials of digits".
2. There is no way that is clear to me to restrict the minimum mindigit of a number purely based on its number of digits.

In any case, for this problem, we only restrict numbers based on their maxdigit with ranges determined as above.