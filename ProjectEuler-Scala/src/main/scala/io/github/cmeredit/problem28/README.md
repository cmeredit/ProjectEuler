# Number Spiral Diagonals

## Problem 28

> Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is formed as follows:
> 
> $$
\begin{matrix}
21 & 22 & 23 & 24 & 25\\
10 & 7 & 8 & 9 & 10\\
19 & 6 & 1 & 2 & 11\\
18 & 5 & 4 & 3 & 12\\
17 & 16 & 15 & 14 & 13
\end{matrix}
$$
> 
> It can be verified that the sum of the numbers on the diagonals is 101.
> 
> What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the same way?

---

This problem can be solved directly on a calculator. It can be shown by induction on $k$ that in a $k$ by $k$ number spiral, 
the corners of the spiral are as follows:

1. Top Right: $k^2$
2. Top Left: $k^2-2(k-2)$
3. Bottom Left: $k^2-4(k-2)$
4. Bottom Right: $k^2 - 6(k-2)$

The diagonal entries of a number spiral are the corner entries of all sub-spirals. A $2k+1$ by $2k+1$ spiral has a sub-spiral of
dimension $s$ by $s$ for each odd number $s \leq 2k+1$. This yields a formula for the diagonal sum of a $2n+1$ by $2n+1$ 
number spiral (with a correction term for the quadruple-counting of the center):

$$
\begin{align}
\text{[Diagonal Sum]}(2n+1) &= \sum_{k=1}^{n}[(2k+1)^2] + \sum_{k=1}^{n}[(2k+1)^2-2((2k+1)-2)] + \sum_{k=1}^{n}[(2k+1)^2-4((2k+1)-2)] + \sum_{k=1}^{n}[(2k+1)^2-6((2k+1)-2)] - 3\\
&= \sum_{k=1}^n [4k^2+4k+1] + \sum_{k=1}^n [4k^2+3] + \sum_{k=1}^n [4k^2-4k+5] + \sum_{k=1}^n [4k^2-8k+7] - 3\\
&= 16 \sum_{k=1}^n [k^2] + (4 + 0 - 4 - 8) \sum_{k=1}^n [k] + (1 + 3 + 5 + 7) \sum_{k=1}^n [1] - 3\\
&= 16 \frac{n(n+1)(2n+1)}{6} - 8 \frac{n(n+1)}{2} + 16n - 3
\end{align}
$$