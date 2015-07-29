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
		// int[] coins = {1, 1, 0, 0, 0, 0, 0, 0}; less coins useful for testing of change giving
		//Initially 10 of each item. 20 of the cheap itmes, 1 of each expensive one.
		int[] products = {20, 20, 20, 20,
						10, 10, 10, 10,
						10, 10, 10, 10,
						1, 1, 1, 1};



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
		view.UpdateScreen("\u00A3"+df.format(value));
	}



	//returns the amount of change that should be returned.
	public void madeChoiceOfItem(int choice){

		int credit = model.currentCredit();

		double value = credit / 100.0;
		DecimalFormat df = new DecimalFormat("#0.00");
		String creditString = "\u00A3"+df.format(value);

		if(model.getPositionOfProduct(choice) <0){
			view.UpdateScreen("Invalid product selection. Credit" + creditString);
			return;
		}

		int result = model.userBuys(choice);
		if(result == 2){
			view.UpdateScreen("Please insert exact change!");
			returnCoins();
			return;
		}

		if (result==1){
			view.DispenseProduct(choice);
			returnCoins();
		    view.UpdateScreen("Vending machine is ready.");

		}
		else if(result == 0) view.UpdateScreen("Out of Stock. Credit:"+creditString);

		else if(result <= -100 ){
 			view.UpdateScreen("insufficient money. Cost:" + "\u00A3" + df.format(value)
			+ ".Credit:" + creditString);
		}
		//else =  if(-100< result < 0)
		else{ view.UpdateScreen("insufficient money. Cost:" + Integer.toString(-1 * result) + "p"
		+ ".Credit:" + creditString);
		}


	}


//---------------------------------------------------------------------------------------------
// 							Returning Change
//---------------------------------------------------------------------------------------------

	public void returnCoins(){
		view.GiveChange(model.returnCredit());
	}

	public String nameOfProductWithChoice(int choice){
		return model.getNameOfProductWithChoice(choice);
	}

	public int[] change(){
		return model.change;

	}

}
