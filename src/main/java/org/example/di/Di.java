package org.example.di;

class A {
  public A(B b){
  System.out.println("A created with B");
  }
}

class B {
  public B(C c){
    System.out.println("B created with C");
  }
}

class C {
  public C(){
    System.out.println("C created");
  }
}

public class Di {
  public static void main(String[] args) throws Exception {
    DiContainer dc = new DiContainer();
    A a = dc.getInstance(A.class);
    System.out.println(a);
  }
}