/* http://www.spoj.com/problems/SAMER08F/
 *
 * Solution not optimized, with an input like this:
 *
 * 20
 * 10
 * 80
 * 0
 *
 * If 20, 10 and 80 are ordered into 10, 20, 80 a lot of calculation
 * can be easily saved.
 */

#include <stdio.h>

int main() {
    int squares;
    while (1) {
        scanf("%d", &squares);
        if (squares == 0) {
            break;
        }

        int i;
        int sum = 0;
        for (i = 1; i <= squares; ++i) {
            sum += i * i;
        }
        printf("%d\n", sum);
    }
    return 0;
}
