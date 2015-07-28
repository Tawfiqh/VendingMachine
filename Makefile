all: model view controller


model:
	javac VendingMachineModel.java

controller:
	javac VendingMachineController.java

view:
	javac VendingMachineView.java
	java VendingMachineView
