package calculator;
public abstract class BinaryExpression implements Expression {

    private Expression lft;
    private Expression rht;
    private String operator;

    public BinaryExpression( Expression leftSideExpression, Expression rightSideExpression, String binaryOperator ) {
        this.lft = leftSideExpression;
        this.rht = rightSideExpression;
        this.operator = binaryOperator;
    }

    public String toString()
    {
        return "(" + lft + operator + rht + ")";
    }

    public double evaluate(final Bindings bindings)
    {
            return _applyOperator( lft.evaluate(bindings), rht.evaluate(bindings) );
    }

    protected abstract double _applyOperator(double leftExpression, double rightExpression );

}
