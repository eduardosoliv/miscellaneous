<?php
// Project Euler.net
// Largest palindrome product
// Problem 4
// A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91  99.
// Find the largest palindrome made from the product of two 3-digit numbers.

$max = 0;
for ($a = 9999; $a > 999; --$a) {
    for ($b = 9999; $b > 999; --$b) {
        $val = $a * $b;

        // no need to proceed any further
        if ($val < $max) break;

        if (strrev((string) $val) == $val) {
            $max = $val;
        }
    }
}

echo $max . "\n";
