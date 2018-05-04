# 1.
```java
public static boolean hasSelfCycle( Graphnode<T> node ){
    n.setVisitMark(true);
    for (Graphnode<T> m : n.getSuccessors()) {
        if (m.getVisitMark()) {
            return true;;
        }
        else
          hasSelfCycle(m);
    }
    return false;
}
```

# 2.

| visited nodes and their shortest distances from start  | dist values for nodes in U (only finite values, listed in increasing order) |
| ------------- | ------------- |
| -  | 	(0,S)  |
| S:0  | (4,G), (11,H), (33,P)  |
| S:0, G:4 | (10,R), (11,H), (11,P)  |
| S:0 G:4 R:10 | (11,H), (11,P), (30,A) |
| S:0 G:4 R:10 H:11 | (11,P), (30,A) |
| S:0 G:4 R:10 H:11 P:11 | (13,A) |
| S:0 G:4 R:10 H:11 P:11 A:13 |
