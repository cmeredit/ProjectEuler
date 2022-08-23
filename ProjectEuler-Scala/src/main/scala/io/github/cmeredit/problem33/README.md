# Digit Cancelling Fractions

## Problem 33

> The fraction $\frac{49}{98}$ is a curious fraction, as an inexperienced mathematician in attempting to 
> simplify it may incorrectly believe that $\frac{49}{98} = \frac{4}{8}$, which is correct, is obtained 
> by cancelling the $9$s.
> 
> We shall consider fractions like, $\frac{30}{50} = \frac{3}{5}$, to be trivial examples.
> 
> There are exactly four non-trivial examples of this type of fraction, less than one in value, and 
> containing two digits in the numerator and denominator.
> 
> If the product of these four fractions is given in its lowest common terms, find the 
> value of the denominator.

---

### Reductions

This problem is quite small ($10^4$ candidates with the most naive solution), and it's quite tempting to just write a brute force solution.
Regardless, let's see if there's anything that can be done.

Suppose $\frac{n}{d}$ is a fraction such as described in the problem. Let $n$ have digits $n_1n_0$ and 
let $d$ have digits $d_1d_0$. Observations:

- $\{n_1, n_0\} \cap \{d_1, d_0\}$ only has one element
  - If it had two, then $n$ would equal $d$, or $n/d$ would be of the form $ab/ba$. In the latter case, the naive cancellation yields $a/a$ or $b/b$, but we've assumed $ab/ba < 1$ in the problem statement.
  - If it had zero, then $d$ and $n$ would have no digits in common).
- $n_1 \neq 0 \neq d_1$ since $n$ and $d$ are (nontrivially) two-digit numbers.

From now on, let $c$ denote the common digit of $n$ and $d$. Let $t$ and $b$ denote the other digit of $n$ and $d$ respectively. Let's look
at two cases we can eliminate:

- Suppose $n/d$ is of the form $ct/cb$. Then $ct/cb = t/b \Rightarrow 10cb+tb = 10ct + tb \Rightarrow ct=cb$. Since the first digit of our numbers is nonzero, we gain $t=b$, contradicting $ct < cb$.
- Suppose $n/d$ is of the form $tc/bc$. Then $tc/tb = t/b \Rightarrow 10tb+cb = 10tb+ct \Rightarrow cb=ct$. Since we've assumed the last digits of our numbers are not both zero, we gain $b=t$, contradicting $tc < bc$.

Hence, all such fractions are of the form $ct/bc$ or $tc/cb$.

It's unclear to me how to reduce the problem any further. Given that the reduction isn't that extreme, and given that the problem is 
small, I'll just write a solution and move on to a problem that's more fun.