import java.util.Arrays;

public class VendingMachineModel {

	private class Product{
		public String name;
		public int price;
		public int stockLeft;

		public Product(String name, int price, int stockLeft){
			this.name=name;this.price=price;this.stockLeft=stockLeft;
		}
	}


	public VendingMachineModel(){
		currentCredit = 0;
	}

	private Product[] products;
	private int[] productNumbers = {11, 12, 13, 14,
									21, 22, 23, 24,
									31, 32, 33, 34,
									41, 42, 43, 44};

	private int[] changeRemaining;
	private int[] change = {1, 2, 5, 10, 20, 50, 100, 200};


	private int getPositionOfCoin(int coin){
		//to find out how many 2p pieces we have left
		//we need to find which index of the array "changeRemaining"
		//refers to 2p pieces.

		//The bijection is set up using the array change.
		//This function returns the mapping from coin to array index.
		return Arrays.binarySearch(change, coin);
	}

	private int getPositionOfProduct(int product){
		return Arrays.binarySearch(productNumbers, product);
	}



//---------------------------------------------------------------------------------------------
// 							Calculating credit
//---------------------------------------------------------------------------------------------

	//Private so that it can't be increased without adding
	//the coins to the change pile also.
	private int currentCredit;

	public int currentCredit(){
		return currentCredit;
	}

	public void addCredit(int coin){
		currentCredit += coin;
		int index = getPositionOfCoin(coin);
		changeRemaining[index] += 1;
	}

	public int[] returnCredit(){
		int[] toReturn = New int[change.length];

		for(int i = change.length; currentCredit>0||i>=0;i--){
			while(changeRemaining[i]>0 && change[i]<=currentCredit){
				toReturn[i]++;
				currentCredit-=change[i];
			}
		}

		return toReturn;
	}



//---------------------------------------------------------------------------------------------
// 							Setting up THE MACHINE.
//---------------------------------------------------------------------------------------------
	public void initialMachineStock(int[] changeInserted, int[] productsInserted){
		changeRemaining = changeInserted;

		for(int i=0; i<productNumbers.length; i++){
			//initial prices are 0, prices set separately afterwards.
			products[i] = new Product(Integer.toString(productNumbers[i]), 0, productsInserted[i]);
		}

	}

	public void reloadMachineChange(int[] changeInserted){

	}

	public void reloadMachineProducts(int[] productsInserted){
		for(int i=0; i<productNumbers.length; i++){
			Product item = products[i];
			products[i] = new Product(item.name, item.prices, item.stockLeft + productsInserted[i]);
		}
	}


	public void setMachinePrices(int[] prices){
		for(int i=0; i<productNumbers.length; i++){
			Product item = products[i];
			products[i] = new Product(item.name, prices[i], item.stockLeft);
		}
	}

}
