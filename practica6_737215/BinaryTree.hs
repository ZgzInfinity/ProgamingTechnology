-- Autor : Rubén Rodríguez Esteban
-- NIP: 737215


module BinaryTree where

-- Constructor del tipo de dato arbolBinario
data Arbol a = Empty 
		| Hoja a
		| Rama a (Arbol a) (Arbol a) 


-- Instancia SHOW 
instance (Show a, Ord a) => Show (Arbol a) where
    show Empty        = " -"
    show (Hoja raiz)  = " (" ++ show raiz ++ ")" 
    show (Rama x i d) = " (" ++ show x ++ show i ++ show d ++ ")"



-- Función empty
empty :: Arbol a
empty = Empty

-- Fubción Leaf
leaf :: a -> Arbol a
leaf x = Hoja x

-- Función tree
tree :: a -> Arbol a -> Arbol a -> Arbol a
tree x Empty Empty = leaf x
tree x lb Empty = Rama x lb Empty
tree x Empty rb = Rama x Empty rb
tree x lb rb = Rama x lb rb

-- Función Size
size :: Arbol a -> Int
size (Empty) = 0
size (Hoja x) = 1
size (Rama raiz izq dch) = 1 + (size izq) + (size dch)

-- FUNCIONES DE RECORRIDO 

-- Función preorder
preorder :: Arbol a -> [a]
preorder Empty = []
preorder (Hoja x) = [x]
preorder (Rama x izq dch) = x : (preorder izq ++ preorder dch)

-- Función inorder
inorder :: Arbol a -> [a]
inorder Empty = []
inorder (Hoja x) = [x]
inorder (Rama x izq dch) = (inorder izq) ++ [x] ++ (inorder dch)

-- Función postorden
postorder :: Arbol a -> [a]
postorder Empty = []
postorder (Hoja x) = [x]
postorder (Rama x izq dch) = (postorder izq) ++ (postorder dch) ++ [x]


-- Función add
add :: Ord a => Arbol a -> a -> Arbol a
add Empty x = leaf x
add (Hoja raiz) x
	| x == raiz = Hoja raiz 
	| x < raiz  = Rama raiz (leaf x) Empty
	| otherwise = Rama raiz Empty (leaf x)
add (Rama raiz izq dch) x  
	| x == raiz = Rama raiz izq dch
	| x < raiz  = Rama raiz (add izq x) dch
	| otherwise = Rama raiz izq (add dch x)



-- Función betwen 
between :: Ord a => Arbol a -> a -> a -> [a]
between Empty xmin xmax = []
between (Hoja raiz) xmin xmax
	| xmin == raiz || xmax == raiz = [raiz]
	| xmin < raiz && raiz < xmax = [raiz]
	| otherwise = []
between (Rama raiz izq dch) xmin xmax
	| xmin < raiz && raiz < xmax = [raiz] ++ (between izq xmin xmax) ++ (between dch xmin xmax)
	| raiz > xmax = between izq xmin xmax
	| raiz < xmin = between dch xmin xmax
	| raiz == xmin = [raiz] ++ between dch xmin xmax
	| raiz == xmax = [raiz] ++ between izq xmin xmax
	| otherwise = between izq xmin xmax
	





 
	
	



