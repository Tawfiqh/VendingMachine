import java.text.DecimalFormat;

public class VendingMachineController {

	private VendingMachineViewInterface view;
	private VendingMachineModel model;

	public VendingMachineController(VendingMachineViewInterface view){
		this.view = view;
		model = new VendingMachineModel();
	}



//---------------------------------------------------------------------------------------------
// 							Loading Machine Stock
//---------------------------------------------------------------------------------------------


    public void populateMachine(){
		//Initially 10pounds worth of each coin
		int[] coins = {1000, 500, 200, 100, 50, 20, 10, 5};

		//Initially 10 of each item. 20 of the cheap itmes
		int[] products = {20, 20, 20, 20,
						10, 10, 10, 10,
						10, 10, 10, 10,
						10, 10, 10, 10};

		model.initialMachineStock(coins, products);


		//Pricing is done by row in the machine
		int[] productPrices = {50, 50, 50, 50,
							75, 75, 75, 75,
							100, 100, 100, 100,
							150, 150, 150, 150};

		model.setMachinePrices(productPrices);
    }





//---------------------------------------------------------------------------------------------
// 							Choosing an item
//---------------------------------------------------------------------------------------------

	// public  Boolean coinInserted(Coin coin);
	public Boolean coinInserted(int coinVal){
		model.addCredit(coinVal);
		updateScreenWithCurrentCredit();
		return true;
	}

	private void updateScreenWithCurrentCredit(){
		int credit = model.currentCredit();

		double value = credit / 100.0;
		DecimalFormat df = new DecimalFormat("#0.00");
		this.view.UpdateScreen("\u00A3"+df.format(value));
	}


	//returns the amount of change that should be returned.
	public Boolean madeChoiceOfItem(int choice){

		return true;
	}


//---------------------------------------------------------------------------------------------
// 							Returning Change
//---------------------------------------------------------------------------------------------

	public String coinReturnPressed(){

		int[] change = model.returnCredit();

		String changeStr = "";
		int totalReturn = 0;
		for(int i = 0; i<model.change.length; i++ ){
			int noOfThisCoin = change[i];
			if(noOfThisCoin>0){
				int thisCoin = model.change[i];
				int val = noOfThisCoin * thisCoin;

				totalReturn += val;
				if(thisCoin<100){
					changeStr += Integer.toString(noOfThisCoin) + " x " + thisCoin+ "p.\n";
				}

				else{
					changeStr += Integer.toString(noOfThisCoin) + " x \u00A3" + thisCoin/100+ "\n";
				}
			}
		}


		double value = totalReturn / 100.0;
	    DecimalFormat df = new DecimalFormat("#0.00");
		changeStr+="total = " + "\u00A3" + df.format(value);

		updateScreenWithCurrentCredit();
		return changeStr;
	}

}
