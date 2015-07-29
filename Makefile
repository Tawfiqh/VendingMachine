all: model view controller


model: VendingMachineModel.java
	javac VendingMachineModel.java

controller: VendingMachineController.java
	javac VendingMachineController.java

view: VendingMachineView.java
	javac VendingMachineView.java
	java VendingMachineView
