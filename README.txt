This folder contains instances of the
Shortest Path Problem

An instance consists of a directed graph G with n vertices (labelled from 1 to n),
m arcs, a source vertex s and a destination vertex t. Each arc ij has a non-negative real weight w(i,j).

The input format of the instances is as follows

instanceName
n
m
s
t
i1 j1 w(i1,j1)
i2 j2 w(i2,j2)
.
.
.
im jm w(im,jm)

Thus every instance file will consitst of m+5 lines.
Example of an instance called "instance1" with 10 items

instance1
6
9
2
5
1 2 1.5
1 4 2.5
1 5 3
2 3 2
2 4 1.5
3 4 2
3 6 2.5
4 5 3
5 6 2
