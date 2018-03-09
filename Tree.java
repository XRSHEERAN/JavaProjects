public boolean isBinary( Treenode<T> n ){
  if(n==null || n.getChildren().size()==0){
    return ture;
  }
  List<TreeNode<T>> tbl=n.getChildren();
  if(tbl.length==1 || tbl.size()>2){
    return flase;
  }
  return (isBinary(tbl.get(0)) && isBinary(tbl.get(1)));
}
