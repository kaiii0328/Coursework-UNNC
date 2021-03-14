-- In-class exercises
triangle :: Int -> Int
triangle n | n==0      = 0
           | otherwise = n + triangle (n-1)

splitString :: [Char] -> Char -> [[Char]]
splitString "" _ = [""]
splitString (s:ss) split 
  | s==split = ("":ss2)
  | otherwise = ((s:head ss2) : tail ss2)
  where
    ss2 = splitString ss split

-- Chapter 6 exercises
myAnd :: [Bool] -> Bool
myAnd [] = True
myAnd (b:bs) = b && myAnd bs

myAnd' :: [Bool] -> Bool
myAnd' xs | null xs = True
          | otherwise = head xs && myAnd' (tail xs)

myConcat :: [[a]] -> [a]
myConcat [] = []
myConcat (xs:xss) = xs ++ myConcat xss

myReplicate :: Int -> a -> [a]
myReplicate 0 _ = []
myReplicate n x = x: myReplicate (n-1) x

-- (!!) function...
select :: [a] -> Int -> a
select (x:xs) 0 = x
select (x:xs) n = select xs (n-1)
-- eg [12,3,4445,3] `select` 3

myElem :: Eq a => a -> [a] -> Bool
myElem x [] = False
myElem x (y:xs) | x==y = True
                 | otherwise = myElem x xs

myMerge :: Ord a => [a] -> [a] -> [a]
myMerge xs [] = xs
myMerge [] ys = ys
myMerge (x:xs) (y:ys)| x < y       = x:(myMerge xs (y:ys))
 | otherwise = y:(myMerge (x:xs) ys)

myMsort :: Ord a => [a] -> [a]
myMsort [] = []
myMsort [x] = [x]
myMsort xs = myMerge (myMsort (take len xs)) (myMsort (drop len xs))
  where len = length(xs) `div` 2
  


