# Coin Sums

## Problem 31

> In the United Kingdom the currency is made up of pound (£) and pence (p). There are eight coins in general circulation:
> 
> 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p), and £2 (200p).
> 
> It is possible to make £2 in the following way:
> 
> 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
> 
> How many different ways can £2 be made using any number of coins?

---

### Avoiding Partition Enumeration

This problem is somewhat related to the [Partition Function](https://en.wikipedia.org/wiki/Partition_function_(number_theory)),
which grows exponentially. Brute force algorithms for computing values of the partition function (such as "generate all 
partitions and see how many were generated") are therefore nonviable for large inputs. Instead, it's better to count 
partitions without computing them directly.

This problem requests the number of partitions of 200 with heavy restrictions on those partitions. 200 is small enough (and 
these partitions are restricted enough) that it is actually possible to brute-force generate all satisfactory partitions,
but in the spirit of generality, we take a different approach. Before tackling the problem as stated, let's consider 
general partitions for a moment.

### General Partitions

**Convention:** For any natural number $n$, a partition of $n$ is a vector of natural numbers $\langle a_1, \dots, a_m \rangle$
satisfying (1) $a_1 + \cdots a_m = n$ and (2) For all $i$ with $1 \leq i < m$, $a_i \geq a_{i+1}$ (i.e., partitions are 
nonincreasing). This convention helps us avoid double-counting, for example, $1+2$ and $2+1$ as different partitions of $3$: Of these two, 
only $\langle 2, 1 \rangle$ will be considered a partition. The convention also allows us to shift our focus to the first 
entry of partitions while counting.

**Definition:** Given $n, k \in \mathbb{N}, let $P(n, k)$ be the number of partitions of $n$ that have first entry $k$.

Several useful facts fall out of this definition and our convention! For all $n, k \in \mathbb{N}$,
1. If $k > n$, then $P(n, k) = 0$ (no partition of $n$ has a sum greater than $n$, but every partition sums to at least its first entry!),
2. If $k = n$, then $P(n, k) = P(n, n) = 1$ (the only partition of $n$ that begins with $n$ is $\langle n \rangle$), and
3. If $k < n$, then 

$$P(n, k) = \sum_{j = 0}^{min(k, n - k)} P(n - k, j)$$

The idea behind fact 3 is as follows: If $\langle k, a_2, \dots, a_m \rangle$
is a partition of $n$, then $\langle a_2, \dots, a_m$ is a partition of $n-k$ since (1) 
$a_2 + \cdots + a_m = (k + a_2 + \cdots + a_m) - k = n - k$ and since (2) $\langle k, a_2, \dots, a_m \rangle$ is nonincreasing,
so the subsequence $\langle a_2, \dots, a_m \rangle$ is also nonincreasing. Hence, to count the partitions of $n$ with first
entry $k$, we need only count the partitions of $n-k$ whose first entry exceeds neither $n-k$ nor $k$.

The first two facts give some base values of $P$, while the third fact lets us compute values of $P$ from smaller inputs.

Overall, the three facts allow us to compute $P$ recursively. This is not the best way to compute general partition counts 
(see, for example, [these recurrence relations](https://en.wikipedia.org/wiki/Partition_function_(number_theory)#Recurrence_relations)),
but this method restricts nicely to the given problem (unlike the faster recurrence relations for the general partition
function).

### Coin Partitions

Let $S$ be a set of natural numbers (e.g., in the given problem, we'll take $S := \{1, 2, 5, 10, 50, 100, 200\}$ ).

**Convention:** For any natural number $n$, an $S$-partition of $n$ is a vector of elements of $S$ $\langle a_1, \dots, a_m \rangle$
satisfying (1) $a_1 + \cdots a_m = n$ and (2) For all $i$ with $1 \leq i < m$, $a_i \geq a_{i+1}$ (i.e., partitions are
nonincreasing).

**Definition:** Given $n, k \in \mathbb{N}, let $P_S(n, k)$ be the number of $S$-partitions of $n$ that have first entry $k$.

As before, several useful facts arise:
1. If $k \not \in S$, then $P_S(n, k) = 0$.
2. If $k > n$, then $P(n, k) = 0$ (no partition of $n$ has a sum greater than $n$, but every partition sums to at least its first entry!),
3. If $k = n$ and $k \in S$, then $P(n, k) = P(n, n) = 1$ (the only partition of $n$ that begins with $n$ is $\langle n \rangle$), and
4. If $k < n$ and $k \in S$, then 

$$P(n, k) = \sum_{j = 0}^{min(k, n - k)} P(n - k, j) = \sum_{j \in S, j \leq min(k, n-k)} P(n - k, j)$$

Just as before, we can count $S$-partitions recursively. Let $P_S(n)$ be the number of $S$-partitions of $n$. Then 
$P_S(n) = \sum_{k \in S} P_S(n, k)$ (each $S$-partition of $n$ has to start with _something_). Ultimately, this yields
a solution to the given problem:

```scala
// Computes P_S(n, k), the number of S-partitions of n that begin with k
def PS(S: Vector[Int], n: Int, k: Int): Int = {
  if (!S.contains(k)) {
    0
  } else {
    if (k == 1) {
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
  }
}

// Computes P_S(n), the number of S-partitions of n
def PS(S: Vector[Int], n: Int): Int = {
  S.map(k => PS(S, n, k)).sum
}
```

So, for example, `PS(Vector(1, 2, 5, 10, 50, 100, 200), 200)` is the value requested by this problem.

The above solution can be further improved by memoizing values of $P_S(n, k)$. Many partitions will have the same tail
even if they begin differently. By memoizing $P_S(n, k)$, we only compute these tails once.
