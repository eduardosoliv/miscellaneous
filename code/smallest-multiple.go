// ProjectEuler.net
// Smallest multiple
// Problem 5
// 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
// What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?

package main

func main() {
    for i := 1; ; i++ {
        isDiv := true
        for div := 20; div > 1; div-- {
            if (i % div != 0) {
                isDiv = false
                break
            }
        }

        if (isDiv) {
            println(i)
            break
        }
    }
}
