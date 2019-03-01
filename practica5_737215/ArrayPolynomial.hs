-- Autor: Rubén Rodríguez Esteban
-- NIP: 737215

module ArrayPolynomial where

-- Función x
x :: [Double]
x = [1.0,0.0]

-- Función coef c
coef :: Double -> [Double]
coef c = [c]


-- Función padd lp

sumar :: [Double] -> [Double] -> [Double]
sumar x [] = x
sumar [] x = x
sumar (x:xs) (y:ys) = (x + y) : sumar xs ys

sumarPoly :: [Double] -> [Double] -> [Double]
sumarPoly [] [] = []
sumarPoly p@(x:xs) [] = x : sumarPoly xs []
sumarPoly [] r@(x:xs) = x : sumarPoly [] xs
sumarPoly p@(x:xs) r@(y:ys)
	| length p > length r = x : sumarPoly xs r
	| length r > length p = y : sumarPoly ys p
	| length p == length r && length p == 1 = (x+y) : []
	| otherwise = sumar p r

padd :: [[Double]] -> [Double]
padd [] = []
padd lp = foldr (sumarPoly) (coef 0) lp


-- Función pmul lp
anyadirCeritos :: [Double] -> [Double]
anyadirCeritos xs = 0:xs

multiplicarPolinomios :: [Double] -> Double -> [Double]
multiplicarPolinomios [] v = []
multiplicarPolinomios (xs) v = map (v *) xs 

mulPol :: [Double] -> [Double] -> [Double]
mulPol [] p = []
mulPol p@(x:xs) r = sumar (multiplicarPolinomios r x) (anyadirCeritos (mulPol xs r))

pmul :: [[Double]] -> [Double]
pmul [] = []
pmul lp@(x:y:xs) = sumarPoly (mulPol x y) (pmul xs)  



-- Función peval p
evaluarPolinomio :: Double -> [Double] -> Int -> Double
evaluarPolinomio q (x:xs) pos
		 | pos > 0 = ((q ^ pos) * x) + evaluarPolinomio q xs (pred pos)
		 | pos == 0 = last [x]

peval :: [Double] -> Double -> Double
peval [] x = 0.0
peval p x = evaluarPolinomio x p (pred (length p))


-- Función pderv 
pderv :: [Double] -> [Double]
pderv [] = []
pderv p = reverse (zipWith (*) [1..] (drop 1 (reverse p)))


