package ui;

import controller.Controller;
import domain.ClothingItem;
import domain.Client;
import domain.Transaction;
import domain.validators.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Class for facilitating operations involved in user interaction.
 * @author ale
 */
public class Console {

    private Controller ctrl;

    /**
     * Constructor.
     *
     * @param c(Controller)
     *
     */
    public Console(Controller c) {
        this.ctrl = c;
    }

    /**
     * Allows the user interaction to take place.
     *
     * @throws IOException
     *                  in case inputs are not valid.
     *
     */
    public void runConsole()throws IOException
    {
        while(true) {


            System.out.println("Menu : \n 1.Add an item \n 2.See all items \n " +
                    "3.Filter items \n 4.Add a client \n 5.See all clients \n " +
                    "6.Filter clients \n 7.Sort clients \n 8.Get most popular designer \n " +
                    "9.Add a transaction \n 10.See all transactions \n 11.Delete an item \n " +
                    "12.Delete a client \n 13.Delete a transaction \n " +
                    "14.Update a clothing item \n 15.Update a client \n 16.Update a transaction \n " +
                    "17.Sort the clothing items in a cool order \n 0.Exit \n");

            System.out.println("Enter an option: ");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

            String option = bufferRead.readLine();

            switch (option) {
                case "1":
                    addItems();
                    break;
                case "2":
                    printAllItems();
                    break;
                case "3":
                    filterItems();
                    break;
                case "4":
                    addClients();
                    break;
                case "5":
                    printAllClients();
                    break;
                case "6":
                    filterClients();
                    break;
                case "7":
                    sortClients();
                    break;
                case "8":
                    getMostPopularDesigners();
                    break;
                case "9":
                    addTransaction();
                    break;
                case "10":
                    printAllTransactions();
                    break;
                case "11":
                    deleteItem();
                    break;
                case "12":
                    deleteClient();
                    break;
                case "13":
                    deleteTransaction();
                    break;
                case "14":
                    updateItem();
                    break;
                case "15":
                  updateClient();
                    break;
                case "16":
                  updateTransaction();
                    break;
                case "17":
                    coolOrder();
                    break;
                default:
                    break;
            }
            if(option.equals("0")) break;
        }
    }

    /**
     * Returns the result of the sort operation for items in a readable form.
     */
    private void sortClients()
    {
        System.out.println("clients items (alphabetically):");
        Set<Client> clients = ctrl.sortClientsAlphabetically();
        clients.stream().forEach(System.out::println);
    }

    /**
     * Returns the result of the filter operation for items in a readable form.
     */
    private void filterItems()
    {
        System.out.println("filtered items (name containing 's'):");
        Set<ClothingItem> items = ctrl.filterItemsByName("s");
        items.stream().forEach(System.out::println);
    }

    /**
     * Returns the result of the retrieval operation for the items in a readable form.
     */
    private void printAllItems() {
        Set<ClothingItem> items = ctrl.getAllItems();
        items.stream().forEach(System.out::println);
    }

    /**
     * Allows the interaction specific to adding more items to take place.
     */
    private void addItems() {
        while (true) {
            ClothingItem item = readItem();
            if (item == null || item.getId() < 0) {
                break;
            }
            try {
                ctrl.addItem(item);
            } catch (ValidatorException  e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes a given item using the ID
     */
    private void deleteItem()
    {
        System.out.println("Delete item with the ID: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            ctrl.deleteItem(id);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }


    /**
     * Allows an item to be requested an its info to be given as input.
     *
     * @return ClothingItem - the resulted created item.
     *
     */
    private ClothingItem readItem() {
        System.out.println("Read item {id,name,designer,price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String name = bufferRead.readLine();
            String designer = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());
            ClothingItem item = new ClothingItem(name, designer, price);
            item.setId(id);

            return item;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an item by its ID
     *
     */
    public void updateItem(){

        System.out.println("Update item with ID: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            System.out.println("Enter new characteristics: ");
            System.out.println("Name: ");
            String newName = bufferRead.readLine();
            System.out.println("Designer: ");
            String newDesigner = bufferRead.readLine();
            System.out.println("Price: ");
            int newPrice = Integer.parseInt(bufferRead.readLine());
            ClothingItem newCI = new ClothingItem(newName, newDesigner, newPrice);
            newCI.setId(id);
            this.ctrl.updateItem(newCI);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the result of the filter operation for clients in a readable form.
     */
    private void filterClients()
    {
        System.out.println("filtered Clients (membership of type 'Gold''):");
        Set<Client> clients = ctrl.filterClientsByMembershipType("Gold");
        clients.stream().forEach(System.out::println);
    }

    /**
     * Returns the result of the retrieval operation for the clients in a readable form.
     */
    private void printAllClients() {
        Set<Client> clients = ctrl.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    /**
     * Allows the interaction specific to adding more clients to take place.
     */
    private void addClients() {
        while (true) {
            Client client = readClient();
            if (client == null || client.getId() < 0) {
                break;
            }
            try {
                ctrl.addClient(client);
            } catch (ValidatorException  e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Allows a client to be requested an its info to be given as input.
     *
     * @return Client - the resulted created client.
     *
     */
    private Client readClient() {
        System.out.println("Read client {id,name,age,membership}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String name = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());
            String membership = bufferRead.readLine();

            Client client = new Client(name, age, membership);
            client.setId(id);

            return client;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a given client using the ID
     */
    private void deleteClient()
    {
        System.out.println("Delete client with the ID: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            ctrl.deleteClient(id);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Updates a client by its ID
     *
     */
    public void updateClient(){

        System.out.println("Update client with ID: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            System.out.println("Enter new characteristics: ");
            System.out.println("Name: ");
            String newName = bufferRead.readLine();
            System.out.println("Age: ");
            int newAge = Integer.parseInt(bufferRead.readLine());
            System.out.println("Membership type: ");
            String newMembershipType = bufferRead.readLine();

            Client newClient = new Client(newName, newAge, newMembershipType);
            newClient.setId(id);
            this.ctrl.updateClient(newClient);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Finds the most popular designers.
     */
    private void getMostPopularDesigners(){
        System.out.println(this.ctrl.getMostPopularDesigner().getKey() + " " + this.ctrl.getMostPopularDesigner().getValue().toString() + " items");
    }

    /**
     * Allows the interaction specific to adding more transactions to take place.
     */
    private void addTransaction()throws IOException{
        System.out.println("New client? ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String opt = bufferRead.readLine();
        Client c = new Client();
        if(opt.equals("yes")){
            c = this.readClient();
            this.ctrl.addClient(c);
        }
        else {
            this.printAllClients();
            int clientOption = Integer.parseInt(bufferRead.readLine());
            c = (Client) this.ctrl.getAllClients().toArray()[clientOption - 1];
        }
        System.out.println("Enter the date(dd/mm/yyyy): ");
        String date = bufferRead.readLine();
        Transaction t = new Transaction(c, date);

        System.out.println("Choose an item: ");
        this.printAllItems();
        String sOption = bufferRead.readLine();

        while(!(sOption.equals("stop"))) {

            int option = 0;

            option = Integer.parseInt(sOption);
            ClothingItem item = (ClothingItem) this.ctrl.getAllItems().toArray()[option - 1];
            t.addItemToCart(item);

            this.printAllItems();
            System.out.println("Enter another option: ");
            sOption = bufferRead.readLine();
        }
        System.out.println("Enter a transaction id: ");
        Long id = Long.parseLong(bufferRead.readLine());
        t.setId(id);
        this.ctrl.addTransaction(t);
    }

    /**
     * Returns the result of the retrieval operation for the transactions in a readable form.
     */
    private void printAllTransactions(){
        Set<Transaction> transactions = ctrl.getAllTransactions();
        transactions.stream().forEach(System.out::println);
    }

    /**
     * Deletes a given transaction using the ID
     */
    private void deleteTransaction()
    {
        System.out.println("Delete transaction with the ID: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            ctrl.deleteTransaction(id);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Updates a transaction by its ID
     *
     */
    public void updateTransaction(){

        System.out.println("Update transaction with ID: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            Transaction newTransaction = this.ctrl.getTransactions().findOne(id).get();
            System.out.println("Choose an action: \n 1.Change the client details. \n" +
                    "2.Add a new item to the same transaction. \n 3.Update the date.");

            String option = bufferRead.readLine();

            switch (option) {
                case "1":
                    System.out.println("New client? ");
                    String opt = bufferRead.readLine();
                    Client c = new Client();
                    if(opt.equals("yes")){
                        c = this.readClient();
                        this.ctrl.addClient(c);
                    }
                    else {
                        this.printAllClients();
                        int clientOption = Integer.parseInt(bufferRead.readLine());
                        Long clientId  = ((Client) this.ctrl.getAllClients().toArray()[clientOption - 1]).getId();
                        System.out.println("Enter new characteristics: ");
                        System.out.println("Name: ");
                        String newName = bufferRead.readLine();
                        System.out.println("Age: ");
                        int newAge = Integer.parseInt(bufferRead.readLine());
                        System.out.println("Membership type: ");
                        String newMembershipType = bufferRead.readLine();

                        c = new Client(newName, newAge, newMembershipType);
                        c.setId(clientId);
                        ctrl.updateClient(c);
                    }
                    newTransaction.setClient(c);
                    ctrl.updateTransaction(newTransaction);
                    break;
                case "2":
                    System.out.println("Choose the item you would like to add: ");
                    this.printAllItems();
                    String sOption = bufferRead.readLine();

                    while(!(sOption.equals("stop"))) {

                        int itemOption = 0;

                        itemOption = Integer.parseInt(sOption);
                        ClothingItem item = (ClothingItem) this.ctrl.getAllItems().toArray()[itemOption - 1];
                        newTransaction.addItemToCart(item);

                        this.printAllItems();
                        System.out.println("Choose another item(or stop if you have finished adding): ");
                        sOption = bufferRead.readLine();
                    }
                    ctrl.updateTransaction(newTransaction);
                    break;
                case "3":
                    System.out.println("Enter a new date(dd/mm/yyyy): ");
                    String newDate = bufferRead.readLine();
                    newTransaction.setDate(newDate);
                    ctrl.updateTransaction(newTransaction);
                    break;
                default:
                    break;

            }



            this.ctrl.updateTransaction(newTransaction);
            }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Sorts the clothing item accordingly:
     * every item with a price higher than a given one, sorted ascending by designer
     * and every item with a price lower than the given one, sorted descending by designer
     * @throws IOException
     */
    public void coolOrder() throws IOException{
        System.out.println("Enter price: ");

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        int price = Integer.parseInt(bufferReader.readLine());

        List<ClothingItem> items = ctrl.coolOrder(price);
        for (ClothingItem item : items) {
            System.out.println(item.toString());
        }

    }
}
