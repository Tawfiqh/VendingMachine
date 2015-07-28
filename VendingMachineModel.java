public class VendingMachineModel {

	private class product{
		String name;
		int price;
		int stockLeft;

		public product(String name, int price, int stockLeft){
			this.name=name;this.price=price;this.stockLeft=stockLeft;
		}
	}

	private product[] products;

	private int[] changeRemaining;
	private int[] change = {1, 2, 5, 10, 20, 50, 100, 200};

	public Boolean initialMachineLoad(int[][] changeInserted, int[][] productsInserted){

		return true;
	}

	public Boolean reloadMachineChange(int[][] changeInserted){

		return true;
	}

	public Boolean reloadMachineProducts(int[][] productsInserted){

		return true;
	}


}
