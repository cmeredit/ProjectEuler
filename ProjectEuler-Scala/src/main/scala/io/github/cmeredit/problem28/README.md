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
2. Top Left: $k^2-(k-1)$
3. Bottom Left: $k^2-2(k-1)$
4. Bottom Right: $k^2 - 3(k-1)$

The diagonal entries of a number spiral are the corner entries of all sub-spirals. A $2k+1$ by $2k+1$ spiral has a sub-spiral of
dimension $s$ by $s$ for each odd number $s \leq 2k+1$. This yields a formula for the diagonal sum of a $2n+1$ by $2n+1$ 
number spiral (with a correction term for the center):

$$
\begin{align}
\text{[Diagonal Sum]}(2n+1) &= \sum_{k=0}^{n}[(2k+1)^2] + \sum_{k=0}^{n}[(2k+1)^2-((2k+1)-1)] \\
&+ \sum_{k=0}^{n}[(2k+1)^2-2((2k+1)-1)] + \sum_{k=0}^{n}[(2k+1)^2-3((2k+1)-1)] + 1\\
&= \sum_{k=0}^n [4k^2+4k+1] + \sum_{k=0}^n [4k^2+2k+1] + \sum_{k=0}^n [4k^2+1] + \sum_{k=0}^n [4k^2-2k+1] + 1\\
&= (4+4+4+4) \sum_{k=0}^n [k^2] + (4 + 2 + 0 - 2) \sum_{k=0}^n [k] + (1 + 1 + 1 + 1) \sum_{k=0}^n [1] + 1\\
&= 16 \frac{n(n+1)(2n+1)}{6} + 4 \frac{n(n+1)}{2} + 4n + 1
\end{align}
$$

In our particular problem, we have

$$
\begin{align}
\text{[Diagonal Sum]}(1001) &= \text{[Diagonal Sum]}(2\cdot 500+1) \\
&=16 \frac{500(500+1)(2\cdot 500+1)}{6} + 4 \frac{500(500+1)}{2} + 4\cdot 500 + 1\\
&= 669,171,001
\end{align}
$$

which is accepted by Project Euler.