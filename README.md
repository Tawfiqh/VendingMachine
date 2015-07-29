Vending Machine Notes
======================

VendingMachineView
------------------
Handles the view such as buttons and layout.

Receives user interaction, works out what the user is trying to achieve
and calls the relevant action from the controller.

The view is independent of the controller and the model and in this case
is implemented in javaSwing as a basic GUI. It must implement the following
methods:

public void UpdateScreen(String message);
public void DispenseProduct(String product);

VendingMachineController
-------------------------
Independent of the view, has methods for core functionality and updates the
model appropriately.




VendingMachineModel




Notes
=======
Unfortunately java uses a lot of boiler plate, i don't like the way buttons
handle actions. Being unfamiliar with JButtons before this project i read into
them and see that they can either be used with
