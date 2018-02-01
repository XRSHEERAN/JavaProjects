### 1.
**A.** "washington", "jefferson", "madison", "j.q. adams", "jackson"

**B.** No; It removes the second element and then removes every third item after that. (2nd and 2+3n th item) 

**C.** First, ‘i’ should starts from 0; Then, ‘i’ should be less than words.size(), to avoid out of index exception; Finally, it should be i++ instead of i+=2.
Here is how the correct code should looks like:
```JAVA
for (int i = 0; i < words.size(); i ++) {
  words.remove(i); 
}
```

### 2.
**A.** Resized three times; 70 more elements can be added.

**B.** i*3^d (i times, 3 to the exponent of d)

**C.** The number of tripling would be the smallest integer greater than log_3(c/i) (The logarithm of quotient from c divided by i, to base 3)

### 3.
**A.** "main caught Exc1"

**B.** Run fails. Exception thrown in main and not handled.

**C.** "c caught Exc3"

**D.** "c caught Exc3"

**E.** "main caught Exc1"

**F.** function b needs to have a handler or declare the exception
```JAVA
//handler
void b( ) {
    println("b enter");
    try{
      if (v2 == true) throw new Exc2();
    }
    catch(Exc2 ex){
      println("b caught Exc2");
    }
    println("b exit");
}
//declare
void b( ) throws Exc2 {
    println("b enter");
    if (v2 == true) throw new Exc2();
    println("b exit");
}
```

**G.** function d should have Exc 5 handled or declared
```JAVA
//handle
void d( ) {
    println("d enter");
    if (v1 == true) throw new Exc1();
    if (v3 == true) throw new Exc3();
    try{
      if (v5 == true) throw new Exc5();
    }
    catch(Exc5 ex){
      println("d caught Exc5");
    }
    println("d exit");
}
//declare
void d( ) throws Exc5 {
    println("d enter");
    if (v1 == true) throw new Exc1();
    if (v3 == true) throw new Exc3();
    if (v5 == true) throw new Exc5();
    println("d exit");
}
```
