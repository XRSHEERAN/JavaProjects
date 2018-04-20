# 1.
## PartA
The best-case complexity is O(N). Assume that the list is already sorted in order. Than the algorithm will only execute the first for loop. Assume the size of this list is N, then the for loop will run N-1 times and compare the A[i] and A[i+1], which is a simple comparison and constant in time. After loop, no swaping happened and breaks from for loop; this step is also constant. So the total time should be O(N). Thus the complexity of best case is O(N).

if the values in the array are __sorted in order__.
## PartB

The worst case complexity is O(N^2). This complexity occured when the whole list is in complete opposite order. For example, if we want to sort from small to large, and the list is N,N-1,N-2,...,3,2,1. In this way, the first loop will execute N times and move the first element to the end and then, N-1 for the next loop, plus 1 for the if statement. Thus the total time should be N+(N-1)+...+3+2+1=1/2* N* (N+1), plus N/2 if statements. The total time complexity should be N^2/2+N which is O(N^2) in the worst case.

if the values in the array are __in the reverse order of sorting__.

# 2.
## PartA
The most efficient sorting should be selection sort. Since it always finds the smallest elements in order.
## PartB
The only thing need to be done is change N into K so that there will only be K smallest elements collected
## PartC
Since insertion sort go through the original list and sort them each at a time, following the original order. So we still need to go over the entire list in order to make sure the list is in order. So it's not so efficient.

# 3
__80__|90|50|10|80|70|__30__|40|70|50|40|20|__60__

30|->90|50|10|80|70|20|40|70|50|40<-|__60__|80

30|->40|50|10|80|70|20|40|70|50|90<-|__60__|80

30|40|50|10|->80|70|20|40|70|50<-|90|__60__|80

30|40|50|10|->50|70|20|40|70|80<-|90|__60__|80

30|40|50|10|50|->70|20|40<-|70|80|90|__60__|80

30|40|50|10|50|->40|20|70<-|70|80|90|__60__|80

30|40|50|10|50|40|20<-|->70|70|80|90|__60__|80

30|40|50|10|50|40|20|__60__|70|80|90|70|80


