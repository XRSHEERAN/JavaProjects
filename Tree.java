public boolean isBinary(Treenode n) {
			if(n==null || n.getChildren().size()==0) {
				return true;
			}
			boolean result=false;
			List<Treenode<T>> treelst=n.getChildren();
			if(treelst.size()>2)
				return false;
			for(Treenode<T> t : treelst) {
				if(!isBinary(t)) {
					return false;
				}
			}
			return true;
}
