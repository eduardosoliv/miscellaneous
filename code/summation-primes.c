// ProjectEuler.net
// Summation of primes
// Problem 10
//
// The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
// Find the sum of all the primes below two million.
//
// Compilation: gcc -Wall -lm -std=c99 summation-primes.c

#include <stdio.h>
#include <math.h>

#define MAX 2000000

int isPrime(int num) {
    for (int i = (int) sqrt(num); i > 2; --i)
        if (num % i == 0)
            return 0;

    return 1;
}

int main(void) {
    long sum = 0;
    for (int i=MAX; i>1; --i)
        if (i % 2 != 0 && isPrime(i))
            sum += i;

    printf("%ld\n", sum + 2);
}
