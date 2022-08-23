# Pandigital Products

## Problem 32

> We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once; for example, the 5-digit number, 15234, is 1 through 5 pandigital.
> 
> The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing multiplicand, multiplier, and product is 1 through 9 pandigital.
> 
> Find the sum of all products whose multiplicand/multiplier/product identity can be written as a 1 through 9 pandigital.
> 
> HINT: Some products can be obtained in more than one way so be sure to only include it once in your sum.

---

### Reductions

Let $m, n \in \mathbb{N}$ and for each $x \in \mathbb{N}$, let $D(x)$ be the number of digits of $x$. We will call $(m, n, mn)$ a 
pandigital identity if $m \times n = mn$ is a pandigital identity.  An exact formula
for $D(x)$ is given by $D(x) = \lfloor \log_{10}(x) \rfloor + 1$. Recall that $\forall x, y \in \mathbb{R}$, we have the
inequalities

$$\lfloor x \rfloor + \lfloor y \rfloor \leq \lfloor x + y \rfloor \leq \lfloor x \rfloor + \lfloor y \rfloor + 1$$

These inequalities and the definition of $D$ can be used to show that $D(mn)$ is either $D(m)+D(n)$ or $D(m)+D(n)-1$.

In order for $(m, n, mn)$ to be a pandigital identity, we need $D(m)+D(n)+D(mn)=9$ (in order for the identity to be
1-9 pandigital, it needs 9 digits!) So, we either need $2D(m)+2D(n) = 9$ or $2D(m) + 2D(n) - 1 = 9$. Note that in the
former equation, the left-hand expression is even, yet 9 is odd. So, in fact, we must have $2D(m)+2D(n)-1=9$.
Cleaning this up a bit, we obtain $2D(m)+2D(n)=10$ or simply $D(m)+D(n)=5$.

We may assume without loss of generality that $m \leq n$ (if $(m, n, mn)$ is a pandigital identity, then so too is 
$(n, m, mn)$). Moreover, we may assume that $m$ and $n$ each have at least one digit (otherwise, $m$ or $n$ is zero, and
so too is their product).

Only a few partitions of $5$ are left over from these assumptions: $5 = 1 + 4$ and $5 = 2+3$. Ultimately, this yields:

**Lemma.** Every 1 through 9 pandigital identity is equivalent to one of the form $(m, n, mn)$ where ($D(m)=1$ and 
$D(n) = 4$) or ($D(m) = 2$ and $D(n) = 3$).

This lemma severely limits the form of our pandigital identities. A pure brute-force approach might consider:

$$
\begin{align}
9! \cdot \sum_{i=1}^{9-2} \sum_{j=i+1}^{9-1} [1] &= 9! \cdot \sum_{i=1}^7[8 - (i+1) + 1]\\
&= 9! \cdot \sum_{i=1}^7 [8 - i]\\
&= 9! \cdot (7 \cdot 8 - 7 \cdot 8 / 2)\\
&= 9! \cdot 28\\
&= 10,160,640
\end{align}
$$

different possibilities for a pandigital identity ($9!$ permutations of our digits and $28$ choices for the 
position of "$\times$" and "$=$"). After our reductions, our candidates are reduced in number to:

$$
\begin{align}
9 \cdot (8 \cdot 7 \cdot 6 \cdot 5) + (9 \cdot 8) \cdot (7 \cdot 6 \cdot 5) &= 30,240
\end{align}
$$

So we have much less work to do!

### Silly Reductions

The reduction above yields the greatest benefit, but there's a bit more we can squeeze out of this problem. Assume
$(m, n, mn)$ forms a pandigital identity. Then...

- Neither $m$ nor $n$ can end in a "5". If they did, then $mn$ would end either in a $5$ or a $0$. In the former case, "5" appears twice in $(m, n, mn)$. In the latter case, "0" is a forbidden digit for 1-9 pandigital expressions.
- Neither $m$ nor $n$ can end in a "1". If $m$ ended in a $1$, then $mn$ would end in the last digit of $n$, so the last digit of $n$ would appear twice. A similar argument holds for $n$ ending in a "1". To justify this, take a look at the following computation:

$$
mn\ (\text{mod}\ 10) = (m (\text{mod}\ 10))(n (\text{mod}\ 10)) = 1(n (\text{mod}\ 10)) = n (\text{mod}\ 10)
$$

Through similar arguments, we can restrict, given the last digit of $m$, feasible last digits of $n$. The entire list
is given below:

| Last digit of m      | Possible last digits of n |
| -------------------- | ----------- |
|   1    |       None                |
|   2    |       3, 4, 7, 8, 9       |
|   3    |       2, 4, 6, 7, 8, 9    |
|   4    |       2, 3, 7, 8, 9       |
|   5    |       None                |
|   6    |       3, 7, 9             |
|   7    |       2, 3, 4, 6, 8, 9    |
|   8    |       2, 3, 4, 7, 9       |
|   9    |       2, 3, 4, 6, 7, 8    |

For example, 6 and 8 are incompatible since the product of any two numbers that end in 6 and 8 will end in 8. In our actual solution, we 
compute these automatically.

Remark: Ultimately, this digit compatibility reduction leaves us with 15,120 possible pandigital identities to check. Much better than the
10 million (or even 30 thousand) we started with!