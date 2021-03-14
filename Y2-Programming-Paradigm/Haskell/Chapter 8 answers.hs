-- :cd C:\Users\z2019079\teaching\comp1039\FP practical
-- :load practical8.hs

-- In-class exercises
data Shape = Circle Float | Rect Float Float | EqTriangle Float
area :: Shape -> Float
area (Circle r) = pi * r^2 
area (Rect x y) = x * y
area (EqTriangle x) = x^2 * sqrt(3)/4
totalArea :: [Shape] -> Float
totalArea = sum . map area

data TreeV a = NodeV a [TreeV a] deriving Show
tv1 :: TreeV Int
tv1 = NodeV 3 [NodeV 2 [], NodeV 5 [NodeV 6 [], NodeV 5 []], NodeV 8 []]
tv2 :: TreeV Int
tv2 = NodeV 3 [
  NodeV 2 [], 
  NodeV 5 [
    NodeV 6 [
      NodeV 7 [NodeV 8 []], 
      NodeV 5 []
    ], 
    NodeV 8 [
      NodeV 3 [],
      NodeV 7 [NodeV 5 []]
      ]
    ]  
  ]

treePath :: Eq a => a -> TreeV a -> [[a]]
treePath y (NodeV x ts) |  x==y     = [x]:path
                        | otherwise =     path
  where
    path = map (x:) (concat (map (treePath y) ts))

-- Chapter 8  Exercises
data Nat = Zero | Succ Nat deriving Show
--  Succ (Succ Zero)

add Zero n = n
add (Succ m) n = Succ (add m n)
-- add (Succ (Succ Zero)) (Succ Zero)

mult Zero n = Zero
mult (Succ m) n = add n (mult m n)
-- mult (Succ (Succ Zero)) (Succ (Succ (Succ Zero)))

-- EVAL
data Expr = Val Int
          | Add Expr Expr
          | Mul Expr Expr deriving Show
a1 :: Expr
a1 = Add (Val 1) (Mul (Val 2) (Val 3))

folde :: (Int -> a) -> (a -> a -> a) -> (a -> a -> a) -> Expr -> a
folde id _ _ (Val x) = id x
folde id add mul (Add x y) = add (folde id add mul x) (folde id add mul y)
folde id add mul (Mul x y) = mule (folde id add mul x) (folde id add mul y)

eval = folde id (+) (*)

-- TREES
data Tree a = Leaf a
            | Node (Tree a) a (Tree a) deriving Show

t :: Tree Int
t = Node (Node (Leaf 1) 3 (Leaf 4)) 5
         (Node (Leaf 6) 7 (Leaf 9))
occurs :: Ord a =>   a ->  Tree a -> Bool 
occurs x (Leaf y)    = x == y
occurs x (Node l y r) = x == y
  || occurs x l
  || occurs x r

complete :: Tree a -> Bool
complete t = fst (depthcomplete t)
  where
    -- depthcomplete returns the pair (complete, depth)
    -- indicating if tree is complete along with its depth
    depthcomplete :: Tree a -> (Bool, Int)
    depthcomplete (Leaf _) = (True, 1)
    depthcomplete (Node l _ r) = (cl && cr && (dl==dr), (max dl dr)+1)
      where (cl, dl) = depthcomplete l
            (cr, dr) = depthcomplete r

t2 = Node (Node (Leaf 1) 3 (Leaf 4)) 5
         (Node (Node (Leaf 4) 6 (Leaf 0)) 7 (Leaf 9))

iscomplete :: Tree a ->Bool
iscomplete (Node l x r)  = count l == count r

count :: Tree a -> Int
count (Leaf x) = 1
count (Node l _ r) = count l + 1 + count r

