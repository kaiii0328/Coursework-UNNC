factors :: Int ->   [Int]
factors n = [x | x <-   [1..n], n `mod` x == 0]
prime :: Int ->  Bool
prime n = factors n == [1,n]
primes :: Int ->   [Int]
primes n = [x | x <-   [2..n], prime x]

-- In-class exercise
sumsOfTwoPrimes :: Int -> [(Int,Int)]
sumsOfTwoPrimes n = [(x,n-x) | x <- primes (div n 2), 
                               prime (n-x)]
getAtIndexes :: [a] -> [Int] -> [a]
getAtIndexes xs indexes = [x | (x,i) <- zip xs [0..], 
                                j <- indexes, i==j ]

-- Chapter 5 exercises
pyths :: Int -> [(Int,Int,Int)]
pyths n = [(x,y,z) | x <- [1..n], y <- [1..n], z <- [y..n], x^2+y^2==z^2]
pyths2 :: Int -> [(Int,Int,Int)]
pyths2 n = [(x,y,z) |  z <- [1..n], x <- [1..z], y <- [1..z],x^2+y^2==z^2]

perfects :: Int -> [Int]
perfects n = [x | x <- [1..n], x == sum [y | y <- [1..(x-1)], x `mod` y == 0]]

scalarProd :: [Int] -> [Int] -> Int
scalarProd xs ys = sum [x*y | (x,y) <- zip xs ys] 