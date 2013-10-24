/*
 * ProjectEuler.net
 *
 * Sum square difference
 * Problem 6
 *
 * The sum of the squares of the first ten natural numbers is,
 * 1^2 + 2^2 + ... + 10^2 = 385
 *
 * The square of the sum of the first ten natural numbers is,
 * (1 + 2 + ... + 10)^2 = 55^2 = 3025
 *
 * Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025  385 = 2640.
 * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
 *
 * Compile with: gcc -Wall sum-square-difference.c -std=c99
 */

#include <stdio.h>

#define MAX 100

int main(void) {
    int sumofs = 0;
    for (int i = 0; i <= MAX; ++i) {
        sumofs += i * i;
    }

    int sum = 0;
    for (int i = 0; i <= MAX; ++i) {
        sum += i;
    }
    int ssums = sum * sum;
    printf("%d\n", ssums - sumofs);

    return 0;
}
