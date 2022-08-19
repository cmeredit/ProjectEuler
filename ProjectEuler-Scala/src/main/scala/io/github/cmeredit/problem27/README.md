# Quadratic Primes

## Problem 27

> Euler discovered the remarkable quadratic formula:
> 
> $$
> n^2+n+41
> $$
> 
> It turns out that the formula will produce 40 primes for the consecutive integer values $0 \leq n \leq 39$. 
> However, when $n=40$, $40^2+40+41$ is divisible by 41, and certainly when $n=41$, $41^2+41+41$ is clearly 
> divisible by 41.
> 
> The incredible formula $n^2-79n+1601$ was discovered, which produces 80 primes for the consecutive values 
> $0 \leq n \leq 79$. The product of the coefficients, −79 and 1601, is −126,479.
> 
> Considering quadratics of the form:
> 
> $$
> n^2 + an + b \text{, where } |a| < 1000 \text{ and } |b| \leq 1000
> $$
> 
> where $|n|$ is the modulus/absolute value of $n$, e.g., $|11|=11$ and $|-4|=4$.
> 
> Find the product of the coefficients, $a$ and $b$, for the quadratic expression that produces the maximum number 
> of primes for consecutive values of $n$, starting with $n=0$.

---

### First Thoughts

Given a primality test, the straightforward solution to this problem is to iterate over all $a < 1000$ and $b \leq 1000$,
compute values of $n^2+an+b$, and keep a record of which $a$ and $b$ have yielded the most primes. The value of $b$
gives an upper bound on the number of consecutive primes $n^2+an+b$ may generate, since $b \, | \, b^2 + ab + b$. The maximum value
of any polynomial of the given form (with the given restrictions on $a$ and $b$) is 
$1000^2 + 999 \cdot 1000 + 1000 = 2,000,000$, below which there are approximately 
$2\cdot 10^6 / log(2\cdot 10^6) \approx 137,848$ primes. I've generated and saved all primes (in order) below $2\cdot 10^6$ in other 
problems. Taking our primality test to just be "perform binary search on the pregenerated primes", a brute force solution
to this problem will require, very very loosely, at most about 
$(2 \cdot 999 + 1) \cdot (2 \cdot 1000 + 1) \cdot 1000 \cdot log(137,848) \approx 2 \cdot 10^{10}$ operations.

Let's try to do better!

---

### Reductions

For $n^2 + an + b$ to generate a nonempty sequence of primes for consecutive inputs starting at $n=0$, we must have 
that $0^2+a\cdot 0 + b = b$ is prime. There are only 168 primes less than 1000, so instead of having 2,001
candidates for $b$, we actually only have 168. We will now use "$p$" instead of "$b$".

There are also a few upper bounds on the lengths of sequences of primes that $n^2+an+p$ may generate that are quickly obtained:
First, $p^2 + ap + p = p(p + a + 1)$ is divisible by $p$, so $n^2+an+p$ can only possibly generate primes up to $p-1$.
Also, $(p-a)^2 + a(p-a) + p = (p-a)(p-a+a) + p = (p-a)p+p = (p-a+1)p$ is also divisible by $p$. If $p > a$, then $p-a$ is
positive and yields another upper bound on the prime sequence length. Note that if $p \leq a$, then no such bound is
obtained. In any case, discovering a pair $(a, p)$ that generates $k$ primes thereby revokes the candidacy of any
pair of the form $(a', p')$ where $p' \leq k$ or $p' - a' \leq k$ in the case where $p' > a'$. For example, the problem 
gives that $(a, p) = (1, 41)$ yields $40$ primes, so we know outright that $(a', p') = (57, 7)$ and 
$(a', p') = (996, 997)$ are unable to generate more than $40$ primes.

Iterating over prime values $p$ in descending order, we hope that a long sequence is found early, thereby eliminating 
much future work.