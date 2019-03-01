-- Autor: Rubén Rodríguez Esteban
-- NIP: 737215

module TupleListPolynomial where

-- Función x
x :: [(Double, Int)]
x = [(1,1),(0,0)]

-- Función coef c
coef :: Double -> [(Double,Int)]
coef c = [(c,0)]

-- Función padd lp
sumarPolinomios :: [(Double,Int)] -> [(Double,Int)] -> [(Double,Int)]
sumarPolinomios xs [] = xs
sumarPolinomios [] ys = ys
sumarPolinomios ((a,b):xs) ((c,d):ys)
		| b == d = ((a+c,b):(sumarPolinomios xs ys))
		| b > d = ((a,b):(sumarPolinomios xs ((c,d):ys)))
		| b < d = ((c,d):(sumarPolinomios ((a,b):xs) ys))

padd :: [[(Double, Int)]] -> [(Double, Int)]
padd lp = foldr (sumarPolinomios) (coef 0) lp


-- Función pmul lp
multiplicarPolinomios :: [(Double,Int)] -> (Double,Int) -> [(Double,Int)]
multiplicarPolinomios [] (a,b) = []
multiplicarPolinomios ((a,b):xs) (0.0,0) = []
multiplicarPolinomios ((a,b):xs) (c,d) = ((a*c,b+d):(multiplicarPolinomios xs (c,d)))

mulPol :: [(Double,Int)] -> [(Double,Int)] -> [(Double,Int)]
mulPol [] p = []
mulPol p@(x:xs) r = sumarPolinomios (multiplicarPolinomios r x) (mulPol xs r)

pmul :: [[(Double,Int)]] -> [(Double,Int)]
pmul [] = []
pmul lp@(x:y:xs) = sumarPolinomios (mulPol x y) (pmul xs) 

-- Función peval 
peval :: [(Double,Int)] -> Double -> Double
peval ((a,b):ys) x 
		| b > 0 = ((x ^ b) * a) + peval ys x
		| b == 0 = a

-- Función pderv p
derivarPol :: [(Double,Int)] -> [(Double,Int)]
derivarPol [] = []
derivarPol ((a,b):xs) = ((a*(fromIntegral b),(pred b)):(derivarPol xs))


pderv :: [(Double,Int)] -> [(Double,Int)]
pderv [] = []
pderv p = filter ((>=0).snd) (derivarPol p)



