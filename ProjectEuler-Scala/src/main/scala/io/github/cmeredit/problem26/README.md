# Reciprocal Cycles

## Problem 26

> A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions with denominators 2 to 10 are given:
>
> $\frac{1}{2} = 0.5$
> 
> $\frac{1}{3} = 0.(3)$
> 
> $\frac{1}{4} = 0.25$
> 
> $\frac{1}{5} = 0.2$
> 
> $\frac{1}{6} = 0.1(6)$
> 
> $\frac{1}{7} = 0.(142857)$
> 
> $\frac{1}{8} = 0.125$
> 
> $\frac{1}{9} = 0.(1)$
> 
> $\frac{1}{10} = 0.1$
> 
> Where $0.1(6)$ means $0.166666\dots$, and has a $1$-digit
> recurring cycle. It can be seen that $\frac{1}{7}$ has a $6$-digit recurring cycle.
> 
> Find the value of $d < 1000$ for which $\frac{1}{d}$ contains the longest recurring cycle in its decimal fraction part.


---

### Brute Force - Long Division

A recurring cycle in the decimal representation of a number $\frac{a}{b}$ can be detected during the process of "long division" of $a$ by $b$.
Specifically, the presence of a repeated, nonzero, remainder indicates the presence of a repeated cycle. For example, consider
the computation of the decimal representation of $\frac{1}{7}$:

$$
\begin{align}
1 &= 7 \cdot 0 + 1\\
10 &= 7 \cdot 1 + 3\\
30 &= 7 \cdot 4 + 2\\
20 &= 7 \cdot 2 + 6\\
60 &= 7 \cdot 8 + 4\\
40 &= 7 \cdot 5 + 5\\
50 &= 7 \cdot 7 + 1
\emd{align}
$$

The remainder "$1$" has already appeared, so the division process is now entirely determined and will cycle.

For the purposes of this problem, we need only record the remainders, not the digits. A rather small recurrence
encodes these remainders: Given a number $d$, the sequence of remainders $r_n$ associated to $\frac{1}{d}$ is given by:

$$
\begin{align}
r_0 &:= 1 \\
r_{n+1} &:= 10 \cdot r_n \% d
\end{align}
$$