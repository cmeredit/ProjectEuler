# Pandigital Multiples

## Problem 38

> Take the number 192 and multiply it by each of 1, 2, and 3:
>
>    192 × 1 = 192
> 
>    192 × 2 = 384
> 
>    192 × 3 = 576
>
> By concatenating each product we get the 1 to 9 pandigital, 192384576. We will call 192384576 the concatenated product of 192 and (1,2,3)
> 
> The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5, giving the pandigital, 918273645, which is the concatenated product of 9 and (1,2,3,4,5).
> 
> What is the largest 1 to 9 pandigital 9-digit number that can be formed as the concatenated product of an integer with (1,2, ... , n) where n > 1?

---

### Observations

Let $\oprod$ denote the concatenated product.

- Since the problem gives an example of a 1 to 9 pandigital that starts with 9, any greater 1 to 9 pandigital also starts with 9.
- If $k \oprod (1, \dots, n)$ begins with a 9, then so too does $k$ (since this product begins with the digits of $k$).
- In order for $k \oprod (1, \dots, n)$ to be a 1 to 9 pandigital (with $n > 1$), we must have that $k$ has at most $4$ digits. If it has more, then the concatenated product will have at least 10 digits.
- In order for $k \oprod (1, \dots, n)$ to be a 1 to 9 pandigital, we must have $n \leq 9$ as the concatenated product has at least $n$ digits when $k > 0$.
  - In fact, we know that the desired $k$ is at least $9$, so $n$ is actually at most $5$.
- Moreover, suppose $k$ has $d$ digits. Then $k \oprod (1, \dots, n)$ has at least $nd$ digits.