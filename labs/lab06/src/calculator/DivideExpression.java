package calculator;

class DivideExpression extends BinaryExpression
{

   public DivideExpression(final Expression lft, final Expression rht, String operator)
   {
      super(lft, rht, operator);
   }

   @Override
   protected double _applyOperator(double leftExpression, double rightExpression) {
      return leftExpression / rightExpression;
   }

}

