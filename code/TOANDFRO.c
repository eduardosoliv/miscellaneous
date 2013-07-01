/*
 * http://www.spoj.com/problems/TOANDFRO/
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LINE_MAX_SIZE 200

int main() {
    int cols;
    char line[LINE_MAX_SIZE + 2];
    while (1) {
        fgets(line, LINE_MAX_SIZE, stdin);
        sscanf(line, "%d", &cols);
        if (cols == 0) {
            break;
        }

        fgets(line, LINE_MAX_SIZE, stdin);

        // calculate the number of rows
        int rows = (strlen(line)-1)/cols;

        // allocate matrix
        int **mat = malloc(rows * sizeof(int *));
        int row;
	for(row = 0; row < rows; ++row)
            mat[row] = malloc(cols * sizeof(int));

        // "transform" line into matrix
        int matRow = 0, matCol = 0, i = 0, reverse = 0;
        while (line[i] != '\n') {
            if (i != 0 && i % cols == 0) {
                ++matRow;
                if (reverse) {
                    reverse = 0;
                    matCol = 0;
                }
                else {
                    reverse = 1;
                    matCol = cols - 1;
                }
            }

            mat[matRow][matCol] = line[i];

            if (reverse)
                --matCol;
            else
                ++matCol;
            ++i;
        }

        // print the matrix column by column
        int col;
        for (col = 0; col < cols; ++col) {
            for (row = 0; row < rows; ++row)
                printf("%c", mat[row][col]);
        }
        puts("");
    }
    return 0;
}
