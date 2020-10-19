import numpy as np
import math

def trianglePoints(d, sr, tr):
	# d = 400 # distance from point on sphere to center of triangle
	L = sr + d # distance from center of sphere to center of triangle
	D = 3 * L - 2600
	S = np.array([[0], [0], [-2600]]) # center of sphere
	V = np.array([[-math.sqrt(3)/3], [-math.sqrt(3)/3], [math.sqrt(3)/3]])
	T = S + (V * L)

	print('L: ', L)
	print('D: ', D)
	print('S: ', S)
	print('V: ', V)
	print('T: ', T)

	transformation = np.array([[1, 0, 0], [0, 1, 0], [1, 1, D]])

	for i in 0, 1, 2:
		vec = T + np.array([
			[tr * math.cos(i * 2 * math.pi / 3)], 
			[tr * math.sin(i * 2 * math.pi / 3)], 
			[1]])
		vec[2, 0] = 1

		#print('vec ' + str(i) + ': ', vec)
		print('point ' + str(i) + ': ', np.matmul(transformation, vec))
