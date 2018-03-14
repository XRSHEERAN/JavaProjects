## 1.
```
public boolean isBinary(Treenode n) {
			if(n==null || n.getChildren().size()==0) {
				return true;
			}
			boolean result=false;
			List<Treenode<T>> treelst=n.getChildren();
			if(treelst.size()>2)
				return false;
			Iterator<Treenode<T>> itr=treelst.iterator();
			while(itr.hasNext()) {
				Treenode<T> t=itr.next();
				if(!isBinary(t)) {
					return false;
				}
			}
			return true;
}
```
## 2.
* Pa
```
* The list of negative values in an empty tree is an empty list
* The list of negative values in a tree with one node is a list determined by this node's data (i.e. if the data of that node is negative, then return a list contains this node's value; else return an empty list)
* The list of negative values in a tree with more than one node is a list contains all negative values of its 1 or 2 subtree(s).

```
* Pb
```
public static List<Integer> findNegatives(BinaryTreenode<Integer> n){
			if(n==null)
				return new ArrayList<Integer>();
			List<Integer> tbl=new ArrayList<Integer>();
			if(n.getData()<0)
				tbl.add(n.getData());
			tbl.addAll(findNegatives(n.getLeft()));
			tbl.addAll(findNegatives(n.getRight()));
			return tbl;
		}
```
## 3.
```
public K secondSmallest(BSTnode<K> n) {
			if(n==null)
				return null;
			BSTnode<K> lft=n.getLeft(),rt=n.getRight();
			if(lft==null && rt==null)
				return null;
			if(lft!=null) {
				if(lft.getLeft()==null && lft.getRight()==null) {
					return n.getKey();
				}
				else
					return secondSmallest(n.getLeft());
			}
			else {
				if(rt.getLeft()==null) {
					return rt.getKey();
				}
				else {
					lft=rt.getLeft();
					while(lft.getLeft()!=null)
						lft=lft.getLeft();
					return lft.getKey();
				}
			}
		}
```
