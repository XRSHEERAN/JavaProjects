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
