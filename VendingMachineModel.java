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
	public int[] change = {1, 2, 5, 10, 20, 50, 100, 200};


	public int getPositionOfCoin(int coin){
		//to find out how many 2p pieces we have left
		//we need to find which index of the array "changeRemaining"
		//refers to 2p pieces.

		//The bijection is set up using the array change.
		//This function returns the mapping from coin to array index.
		return Arrays.binarySearch(change, coin);
	}

	public int getPositionOfProduct(int product){
		return Arrays.binarySearch(productNumbers, product);
	}

	public Product getProductWithChoice(int choice){
		int index = getPositionOfProduct(choice);
		return products[index];
	}

	public String getNameOfProductWithChoice(int choice){
		return getProductWithChoice(choice).name;
	}
//---------------------------------------------------------------------------------------------
// 							Setting up THE MACHINE.
//---------------------------------------------------------------------------------------------
	public void initialMachineStock(int[] changeInserted, int[] productsInserted){
		changeRemaining = changeInserted;
		products = new Product[productsInserted.length];

		for(int i=0; i<productsInserted.length; i++){
			//initial prices are 0, prices set separately afterwards.
			products[i] = new Product(Integer.toString(productNumbers[i]), 0, productsInserted[i]);
		}
	}

	public void setMachinePrices(int[] prices){
		for(int i=0; i<productNumbers.length; i++){
			Product item = products[i];
			products[i] = new Product(item.name, prices[i], item.stockLeft);
		}
	}

	public void reloadMachineChange(int[] changeInserted){
		for(int i=0; i<change.length; i++){
			changeRemaining[i]+=changeInserted[i];
		}
	}

	public void reloadMachineProducts(int[] productsInserted){
		for(int i=0; i<productNumbers.length; i++){
			Product item = products[i];
			products[i] = new Product(item.name, item.price, item.stockLeft + productsInserted[i]);
		}
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
		int[] toReturn = new int[change.length];

		for(int i = change.length-1; currentCredit>0 && i>=0;i--){
			while(changeRemaining[i]>0 && change[i]<=currentCredit){
				toReturn[i]++;
				changeRemaining[i]--;
				currentCredit-=change[i];
			}
		}

		return toReturn;
	}


//---------------------------------------------------------------------------------------------
// 							Purchasing an item
//---------------------------------------------------------------------------------------------
public int userBuys(int choice){
	//returns: 2 = notEnough change
	//returns: 1 = succesful
	//returns: 0 = out of stock
	//returns: <0 = insufficient funds

	Product toBuy = getProductWithChoice(choice);



	if(currentCredit>= toBuy.price && toBuy.stockLeft>0){
		if(couldGiveChangeAfterBuying(choice)){
			currentCredit-=toBuy.price;
			toBuy.stockLeft--;
			return 1;
		}
		else{
			return 2;
		}
	}

	else{
		if(toBuy.stockLeft==0) return 0;
		else{//in stock but insufficient credit.
			return -1 * toBuy.price;
		}
	}
}

public Boolean couldGiveChangeAfterBuying(int choice){
	Product toBuy = getProductWithChoice(choice);

	int newCredit = currentCredit - toBuy.price;
	int[] tempChangeRemaining = new int[change.length];
	System.arraycopy(changeRemaining, 0, tempChangeRemaining, 0, changeRemaining.length);

	for(int i = change.length-1; newCredit>0 && i>=0; i--){
		while(tempChangeRemaining[i]>0 && change[i]<=newCredit){
			tempChangeRemaining[i]--;
			newCredit-=change[i];
		}
	}

	return newCredit == 0;

	}


}
