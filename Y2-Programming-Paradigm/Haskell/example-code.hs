import System.IO
--haskell boot up
oneOfTriple :: (Int,Int, Prelude.String) -> Int
oneOfTriple (x,_,_) =x 
add x y = x+y
howmany :: Eq a =>a->[a]->Int
howmany y []     = 0
howmany y (x:xs)  |x==y      = 1+ howmany y xs
                  |otherwise = howmany y xs 

sumless :: Int->[Int]->Int
sumless y []     = 0
sumless y (x:xs) | x<y       = x + sumless y xs
                 | otherwise = sumless y xs

doAll :: (Int->Int->Int)->[Int]->Int  -- can be transformed into folder funciton
doAll f [x]    =x 
doAll f (x:xs) = f x (doAll f xs)

transifSumGT :: Int ->([Int]->[Int])->[Int]->[Int]
transifSumGT n f xs | doAll add xs > n = f xs
                    | otherwise        = xs
mul :: Int ->Int -> Int
mul x y = x*y
add3 :: Int ->Int
--add3 x = add 3 x
add3 = add 3
quadruple x = double(double(x))

factorial n = product [1..n] 
average ns = sum ns `div` length ns 

n = a `div` length xs     
    where
        a = 10       
        xs = [1,2,3,4,5]
multThree :: (Num a) => a -> a -> a -> a
multThree x y z = x * y * z

add' :: Int->(Int->Int)
add' x y = x + y
--lambda expression
add1 = \x -> (\y-> x + y)

second xs = head (tail xs) 
 
swap (x,y) = (y,x) 
 
pair x y = (x,y) 
 
double x = x*2 
 
palindrome xs = reverse xs == xs 
 
twice f x = f (f x)

length' []=0
length' xs = 1 + length' xs

--lec4 exercise
safetail:: [a]->[a]
safetail xs | null xs   = []        --guarded equation
            | otherwise = tail xs 
safetail2 []  = []      --pattern matching
safetail2 xs  =  tail xs

safetail3 xs = if null xs then [] else tail xs  --conditional expression

(||!) ::Bool ->Bool ->Bool
False ||! False = False
True ||! False  = True
False ||! True  = True
True  ||! True  = True


--haskell lec5
concat :: [[a]] -> [a]
concat xss = [x|xs <- xss,x<-xs]

factors :: Int ->[Int]
factors n = [x|x<-[1..n],n `mod` x ==0]

prime :: Int ->Bool
prime n = factors n ==[1,n]

primes :: Int->[Int]
primes n = [x|x<-[2..n],prime x ==True]

pairs :: [a] -> [(a,a)]
pairs xs = zip xs (tail xs)

sorted :: Ord a => [a] -> Bool
sorted xs = and[x<=y|(x,y)<- pairs xs]

positions :: Eq a=>a->[a]->[Int]
positions x xs = [i|(x',i)<-zip xs [0..],x==x']
--lec5 exercise
pyths :: Int->[(Int,Int,Int)]
pyths x =[(m,n,p)|m<-[1..x],n<-[1..x],p<-[1..x],m*m+n*n==p*p||m*m+p*p==n*n||p*p+n*n==m*m]

perfects :: Int ->[Int]
perfects x = [m|m<-[1..x],sum (factors m)==m*2]
--perfects n = [x | x <- [1..n], x == sum [y | y <- [1..(x-1)], x `mod` y == 0]]

scalarproduct :: [Int]->[Int]->Int
scalarproduct xs ys = sum[x*y|(x,y)<-(zip xs ys)]

--lec6
fac :: Int ->Int
fac n = product [1..n] -- fac(-1) =1

fac1 :: Int ->Int
fac1 0 =1
fac1 n =n*fac1(n-1)   -- fac1 (-1) exception

product1 ::Num a => [a]->a
product1 [] =1
product1 (n:ns) = n * product1 ns

length1 :: [a]->Int
length1 []     = 0
length1 (_:ns) = 1+length1 ns

reverse1 :: [a]->[a]
reverse1 []    = []
reverse1 (n:ns)= reverse1 ns ++ [n]

fastreverse :: [a] -> [a]
fastreverse xs = fastreverse1 xs []
    where
    fastreverse1 :: [a]->[a]->[a]
    fastreverse1 [] acc = acc
    fastreverse1 (x:xs) acc = fastreverse1 xs (x:acc)

zip1 :: [a]->[b]->[(a,b)]
zip1 []  _ = []
zip1 _  [] = []
zip1 (x:xs) (y:ys) = (x,y) : zip1 xs ys

drop1 :: Int ->[a]->[a]
drop1 0 xs = xs
drop1 _ [] = []
drop1 n (_:xs) = drop1 (n-1) xs

quicksort :: Ord a=>[a]->[a]
quicksort []     = []
quicksort (x:xs) = quicksort smaller ++ [x] ++ quicksort bigger
    where 
        smaller = [a|a<-xs,a<=x]
        bigger  = [b|b<-xs,b>x]

--lec6 exercise

and1 :: [Bool] -> Bool
and1 []     = True
and1 (x:xs) = x && and1 xs

concat1 :: [[a]] -> [a]
concat1 [] = []
concat1 (x:xs) = x ++ concat1 xs

replicate1 :: Int ->a ->[a]
replicate1 0 _ = []
replicate1 n x = x:replicate1 (n-1) x

select :: [a] ->Int->a --和 !! 一样，说的是选 nth 实际上选的是 n+1 个
select (x:xs) 0 = x
select (x:xs) n = select xs (n-1)

elem1 :: Eq a=>a->[a]->Bool
elem1  x [] = False
elem1 x (n:ns) = x==n || elem1 x ns

merge :: Ord a=>[a]->[a]->[a]
merge a [] = a
merge [] b = b
merge (x:xs) (y:ys) = if x>y then [y]++merge (x:xs) ys
                             else [x]++ merge xs (y:ys)

msort ::Ord a =>[a]->[a]
msort ns = merge left right
     where 
     left = quicksort(take (length ns `div` 2) ns)
     right = quicksort(drop (length ns `div` 2) ns)

--lect7
map_1 f xs = [f x | x<-xs]
map_2 f []     = []
map_2 f (x:xs) = f x : map_2 f xs

filter1 :: (a->Bool)->[a]->[a]
filter1  p xs = [x | x<-xs,p x]

filter2 p [] = []
filter2 p (x:xs) 
    | p x       = x: filter2 p xs
    | otherwise = filter2 p xs

foldr_ :: (a->b->b)->b->[a]->b
foldr_ f v []     = v
foldr_ f v (x:xs) = f x ( foldr_ f v xs )

length_foldr = foldr_ (\_ n->1+n) 0


--map_folder :: (a->b)->[a]->[b]
--map_folder f xs  = foldr (And f) [] xs 
--f []      = v
-- f (x:xs) = x ⭕ f xs
trim :: (a->Bool) -> [a] ->[a]
trim  p [] = []
trim  p (x:xs)
    | p x       = x : trim p xs
    | otherwise = trim p xs

addUncurried :: (Int,Int) ->Int
addUncurried (x,y) = x+y
--lec8
type String = [Char]
type Pos = (Int,Int)

origin :: Pos
origin = (0,0)

left :: Pos -> Pos
left (x,y) = (x-1,y)

type Pair a = (a,a)
type Trans = Pos ->Pos -- nested type declaration
mult :: Pair Int -> Int  -- ?Pair->Int->Int Pair Int = (Int,Int)
mult  (m,n) = m*n

copy :: a-> Pair a
copy x = (x,x)

data Answer = Yes | No | Unknown  --string??

answers :: [Answer]
answers = [Yes, No , Unknown]

flip :: Answer ->Answer
flip Yes = No
flip No  = Yes
flip Unknown = Unknown



data Shape = Circle Float | Rect Float Float
--Circle :: Float ->  Shape  -- definition of Float?
--Rect :: Float -> Float -> Float
square :: Float -> Shape
square n = Rect n n

area :: Shape -> Float
area (Circle r) = pi * r^2
area (Rect x y) = x * y

data Maybe1 a = Nothing1 | Just1 a   --how to avoid ambiguous?
safediv :: Int ->Int ->Maybe1 Int
safediv _ 0 = Nothing1
safediv m n = Just1 (m `div` n )

safehead :: [a] -> Maybe1 a 
safehead [] = Nothing1
safehead xs = Just1 (head xs)

data Nat = Zero | Succ Nat deriving Show      --type Succ:: Nat->Nat,autodefined?
nat2int :: Nat -> Int
nat2int Zero = 0
nat2int (Succ n) = 1 + nat2int n

int2nat :: Int -> Nat
int2nat  0 = Zero
int2nat  n = Succ (int2nat (n-1))       

add_nat Zero     n = n               
add_nat (Succ m) n = Succ(add_nat m n)


data Expr = Val Int | Add Expr Expr| Mul Expr Expr 
--Val :: Int -> Expr
--Add :: Expr -> Expr ->Expr
--Mul :: Expr -> Expr -> Expr
size :: Expr -> Int
size (Val n) = 1
size (Add x y) = size x + size y 
size (Mul x y) = size x + size y


--Countdown case study 
takeV :: Int -> [a] -> [[a]]
takeV n xs = [take i xs |i<-[n..(length xs -n)]]

--dropV :: Int -> [a] ->[a]



fibs :: [Int]
fibs = fib 0 1

fib :: Int->Int -> [Int]
fib x1 x2 = x1:fib x2 (x2+x1)

--Lec 10 I/O
act :: IO (Char, Char)
act = do x<- getChar     
         getChar    
         y<- getChar
         return (x,y)

strlen :: IO ()
strlen  =  do putStr "Enter a word: "
              xs<-getLine
              putStr "The word has "
              putStr (show(length xs))
              putStrLn " characters"

hangman :: IO()
hangman = do putStrLn "Think of a word: "
             word <- sgetLine
             putStrLn "Try to guess it: "
             play word

sgetLine :: IO Prelude.String
sgetLine = do x<- getCh
              if x== '\n' then 
                 do putChar x
                    return []
              else
                  do putChar '-'
                     xs <- sgetLine
                     return (x:xs)


getCh :: IO Char
getCh = do hSetEcho stdin False
           x <-getChar
           hSetEcho stdin True
           return x

play :: Prelude.String -> IO ()
play word = 
     do putStr "? "
        guess <- getLine
        if guess == word then
              putStrLn "You got it!"
        else 
           do putStrLn (match word guess)
              play word

match :: Prelude.String -> Prelude.String -> Prelude.String
match xs ys = [if elem x ys then x else '-'|x<-xs]
