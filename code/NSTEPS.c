/*
 * http://www.spoj.com/problems/NSTEPS/
 */

#include <stdio.h>

int main() {
    int tests;
    scanf("%d", &tests);

    int i, x, y;
    for (i = 1; i <= tests; ++i) {
        scanf("%d %d\n", &x, &y);

        // check diagonals
        if (x == y || x == y +2) {
            if (x % 2 == 0 && y % 2 == 0)
                printf("%d\n", x + y);
            else
                printf("%d\n", x + y -1);
        }
        else
            puts("No Number");
    }

    return 0;
}
