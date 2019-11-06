# Albanez-MathSolver
Math-solving program, made specific to solve equations

## Description
Solve almost any kind of equations that contains one unknown variable and get step-by-step how program obtain the result.

The solver are still in development, so not all kinds of operations are working.

See the list below of example equations to know what kind of expressions the solver is be able to do:

* 7*x^2-9*x = 373
* z^2 = 36-z^2
* 9*z^2+56^(1/2)-99 = 16*(2+4*(2-4))+42^3+9^(1/2)*0.1
* 27^(1/3)+1 = x+(x+x+(x+x+(x*3+(x)+1*x-(-3*x))))
* (3*x-1*(5*x * 2))-3 = 9^(1/2)
* 75*4-11+2^6*4 = 5*2+x*8+105*9
* 3*3^x*4*2^x = 12
* 2*y^2*1*2=2+2	
* 0*x=-0*2*x*0*x+1*0*x	
* 0*x=-2*x+2*x	
* 2/x=5E10 + 4E10
* x+2+2*5=9^0.5*5+1
* 2*3^x + 4*3^x=-1
* .8=2.*x^2 + 2.*x^2
* 2*x=99+1
* x=2.0155 + 2.4155 + 1 + 2. - 02.11 - .1
* 10+5-10+3*6=-x+x+2+3+x+x+9-x-x+x+5-1*9
* x=-3-3/2/4+1/2*11
* -2+5-3+x-1+9-2+2=-0+9-3-1-1+1-1+0-0

## How to use
the solver was made to be simple and easy to use.

First declare a variable:

    //Create a Variable
    Variable x = new Variable('x');
    
Then, create new expression for both sides.
    
    //Create Expressions for both sides;
    Expression left = new Expression("7*x^2-9*x");
    Expression right = new Expression("373");
    
Once have expressions and variable, the equation is can be instantiate:

    //Create a Equation with expressions
    Equation e = new Equation(left, right, x);

And for last, the solver:

    //Create a Solver with Equation
    Solver solver = new Solver(e);
    
Now running method solve, a Solution object will be receive as answer:
    
    //Solve
    Solution s = solver.solve();
    
If everything works fine, the answers will be in a Double array, this codes checks if solution is valid and prints result:

    if(s.isRealAndFinitySolution) {
      for(double solution : s.solutions) {
        System.out.println("Answer: " + String.valueOf(solution));
      }
    }else {
      System.out.println(s.cannotSolve.error);
    }

For more examples of use, see [src/albanez/test/TestEnvironment.java](https://github.com/AlbanezFelipe/Albanez-MathSolver/blob/master/src/albanez/test/TestEnvironment.java)
