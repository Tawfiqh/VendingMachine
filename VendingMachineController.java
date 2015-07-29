import java.text.DecimalFormat;

public class VendingMachineController {

	private VendingMachineViewInterface view;
	private VendingMachineModel model;
	public VendingMachineController(VendingMachineViewInterface view){
		this.view = view;
		model = new VendingMachineModel();
	}

	// public  Boolean coinInserted(Coin coin);
	public Boolean coinInserted(int coinVal){
		model.addCredit(coinVal);
		int credit = model.currentCredit();

		double value = credit / 100.0;
	    DecimalFormat df = new DecimalFormat("#.00");
		this.view.UpdateScreen("\u00A3"+df.format(value));
		return true;
	}


	//returns the amount of change that should be returned.
	public Boolean madeChoiceOfItem(int choice){

		return true;
	}

	public int[] coinReturnPressed(){
		return model.returnCredit();
	}
}
