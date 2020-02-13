package calculator;

class AddExpression extends BinaryExpression
{

   public AddExpression(final Expression lft, final Expression rht, String operator)
   {
      super(lft, rht, operator);
   }

   @Override
   protected double _applyOperator( double lft, double rht ) {
      return lft + rht;
   }
}
