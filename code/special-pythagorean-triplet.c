// ProjectEuler.net
// Special Pythagorean triplet
// Problem 9
//
// A Pythagorean triplet is a set of three natural numbers, a  b  c, for which,
// a²+b²=c²
//
// For example, 3² + 4² = 9 + 16 = 25 = 52.
//
// There exists exactly one Pythagorean triplet for which a + b + c = 1000.
// Find the product abc.

// Compile: gcc -Wall -std=c99 special-pythagorean-triplet.c

#include <stdio.h>

int abc(void) {
    for (int c = 1; ; ++c)
        for (int b = 1; b < c; ++b)
            for (int a = 1; a < b; ++a)
                if (a*a+b*b == c*c && a+b+c == 1000)
                    return a*b*c;

   return 0;
}

int main(void) {
    printf("%d\n", abc());

    return 0;
}
