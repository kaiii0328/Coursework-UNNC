-- :cd C:\Users\z2019079\teaching\comp1039\FP practical
-- :load practical7.hs

-- Chapter 7 exercises
twice :: (a -> a) -> a -> a
twice f x = f (f x)

double :: Num a => a -> a
double x = 2*x

-- twice double 5
-- twice (+ 6) 14
-- twice (["a", "b"] ++) ["c","d"]

mySum :: Num a => [a] -> a
mySum = foldr (+) 0

myLength :: [a] -> Int
myLength = foldr (\_ n -> 1+n) 0

-- (not . even) 3

-- Exercises
mf1 f p xs = [f x | x <- xs, p x]
-- mf1 (+1) even [0,1,1,2,4,4,5,6]
mf2 f p xs = map f (filter p xs)
-- mf2 (+1) even [0,1,1,2,4,4,5,6]

myMap :: (a -> b) -> [a] -> [b] 
myMap f xs = foldr (\y ys -> (f y) : ys) [] xs
-- myMap (*3) [5,3,9,4]

myFilter :: (a -> Bool) -> [a] -> [a]
myFilter p xs = foldr (\y ys -> if p y then y:ys else ys) [] xs
-- myFilter even [2,2,1,3,4,6,7]