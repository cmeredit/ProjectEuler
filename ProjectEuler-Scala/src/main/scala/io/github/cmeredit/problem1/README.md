# Multiples of 3 or 5

## Problem 1



>If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
>
>Find the sum of all the multiples of 3 or 5 below 1000.

---

### A Brute Force Solution

A brute force solution to this problem exists:
```scala
def PE1BruteForce(n: Int): Int = {
  (1 until n).filter(x => x % 3 == 0 || x % 5 == 0).sum
}
```
That is, we need only take all numbers below the given maximum, filter them by divisibility, then take the sum.

---

### A Solution using Triangular Numbers

We can make use of [triangular numbers](https://en.wikipedia.org/wiki/Triangular_number) and the [inclusion/exclusion principle](https://en.wikipedia.org/wiki/Inclusion%E2%80%93exclusion_principle) to solve this problem 
while avoiding the creation of any lists / loops / etc:
```scala
def PE1(n: Int): Int = {
  
  // Calculates the k-th triangular number
  def triangleNum(k: Int): Int = k * (k+1) / 2

  3*triangleNum((n-1)/3) + 5*triangleNum((n-1)/5) - 15*triangleNum((n-1)/15)
  
}
```

### A Long Explanation

For every $k, n \in \mathbb{N}$, let $D(n, k)$ be the set of all natural numbers less than $n$ that are evenly divisible by $k$. The problem asks for sum of values in $D(n, 3) \cup D(n, 5)$ with $n=1000$, i.e., the set of all natural numbers less than $n$ that are evenly divisible by $3$ or $5$. One's first thought might suggest that $\sum\left[D(n, 3) \cup D(n, 5)\right] = \sum\left[D(n, 3)\right] + \sum\left[D(n,5)\right]$, but this formula double-counts any value divisible both by $3$ and $5$. E.g., $15$ appears only once in the left-hand side of this equation, but appears in both of the sums on the right-hand side. Instead, by the [inclusion/exclusion principle](https://en.wikipedia.org/wiki/Inclusion%E2%80%93exclusion_principle), the actual formula is:
$$\sum\left[D(n, 3) \cup D(n, 5)\right] = \sum\left[D(n, 3)\right] + \sum\left[D(n, 5)\right] - \sum\left[D(n, 3) \cap D(n, 5)\right]$$
The intersection under the last sum can be expressed as a single $D$-set. In general,

$$
\begin{align}
D(n, k_1) \cap D(n, k_2) &= \lbrace x \in \mathbb{N} : (x < n) \wedge (k_1 | x) \rbrace \cap \lbrace x \in \mathbb{N} : (x < n) \wedge (k_2 | x) \rbrace \\
&= \lbrace x \in \mathbb{N} : (x < n) \wedge (k_1 | x) \wedge (k_2 | x)\rbrace \\
&= \lbrace x \in \mathbb{N} : (x < n) \wedge (lcm(k_1, k_2) | x) \rbrace \\
&= D(n, lcm(k_1, k_2))
\end{align}
$$

So we now obtain:
$$\sum\left[D(n, 3) \cup D(n, 5)\right] = \sum\left[D(n, 3)\right] + \sum\left[D(n, 5)\right] - \sum\left[D(n, 15)\right]$$
Hence, if we were to obtain an expression for the sum of values in a $D$-set, then we would obtain an expression for the desired sum. This motivates the following lemma:


**Lemma.** Let $n, k \in \mathbb{N}$. Then
$$\sum\left[D(n, k)\right] = k\frac{\lfloor (n-1)/k \rfloor (\lfloor (n-1)/k \rfloor + 1)}{2}$$
where $\lfloor x \rfloor$ denotes the floor of $x$, i.e., the greatest natural number less than or equal to $x$ (alternatively, $\lfloor x \rfloor$ is $x$ "rounded down").

> _Proof._ Recall that $k | x$ if and only if $x$ is a multiple of $k$, so $D(n, k)$ can be expressed as the set of multiples of $k$ that are less than $n$. If $i \in \mathbb{N}$, then $ki < n$ if and only if $i \leq (n-1) / k$, or, since $i$ is natural, $i \leq \lfloor (n-1) / k \rfloor$. Hence,
> 
> $$
> \begin{align}
> \sum\left[D(n, k)\right] &= \sum_{i=1}^{\lfloor (n-1)/k \rfloor} (ki)\\
> &= k \left(\sum_{i=1}^{\lfloor (n-1)/k \rfloor} i\right)\\
> &= k\frac{\lfloor (n-1)/k \rfloor (\lfloor (n-1)/k \rfloor + 1)}{2}
> \end{align}
> $$
> 
> The last step relies on the following triangular number formula: For any $N \in \mathbb{N}$,
> $$\sum_{i = 1}^N i = \frac{N(N+1)}{2}$$
> Taking $N = \lfloor (n-1) / k \rfloor$ yields the last step.


This lemma now yields the formula used in the code above:

$$
\begin{align}
\sum\left[D(n, 3) \cup D(n, 5)\right] &= \sum\left[D(n, 3)\right] + \sum\left[D(n, 5)\right] - \sum\left[D(n, 15)\right]\\
&= 3 \frac{\lfloor (n-1) / 3 \rfloor (\lfloor (n-1) / 3 \rfloor + 1)}{2} + 5 \frac{\lfloor (n-1) / 5 \rfloor (\lfloor (n-1) / 5 \rfloor + 1)}{2} \\
&\ \ \ \ - 15 \frac{\lfloor (n-1) / 15 \rfloor (\lfloor (n-1) / 15 \rfloor + 1)}{2}
\end{align}
$$

In the special case of $n=1000$, this becomes

$$
\begin{align}
\sum\left[D(1000, 3) \cup D(1000, 5)\right] &= 3 \frac{333\cdot 334}{2} + 5 \frac{199 \cdot 200}{2} - 15 \frac{66 \cdot 67}{2}\\
&= 166,833 + 99,500 - 33,165\\
&= 233,168
\end{align}
$$
