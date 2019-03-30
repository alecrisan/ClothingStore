import controller.Controller;
import domain.ClothingItem;
import domain.Client;
import domain.Transaction;
import domain.validators.ClothingItemValidator;
import domain.validators.ClientValidator;
import domain.validators.TransactionValidator;
import domain.validators.Validator;
import repository.*;
import ui.Console;

import java.io.IOException;

/**
 * Main class
 */
public class Main {

    /**
     * Main function - creates the service and starts the console action.
     *
     * @param args
     *
     */

    public static void main(String[] args) {

        Validator<ClothingItem> itemValidator = new ClothingItemValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<Transaction> transactionValidator = new TransactionValidator();

        //in-memory

        //Repository<Long, ClothingItem> itemsRepository = new inMemoryRepository<>(itemValidator);
        //Repository<Long, Client> clientRepository = new inMemoryRepository<>(clientValidator);
        //Repository<Long, Transaction> transactions = new inMemoryRepository<>(transactionValidator);

        //file repo

        //Repository<Long, ClothingItem> items = new itemFileRepo<>(itemValidator, "/Users/Ale/Documents/Facultate/An2/Semestrul2/MPP/Lab2-4/lab2-4final/src/main/java/ui/items.txt");
        //Repository<Long, Client> clients = new clientFileRepo<>(clientValidator, "/Users/Ale/Documents/Facultate/An2/Semestrul2/MPP/Lab2-4/lab2-4final/src/main/java/ui/clients.txt");
        //Repository<Long, Transaction> transactions = new transactionFileRepo<>(transactionValidator, "/Users/Ale/Documents/Facultate/An2/Semestrul2/MPP/Lab2-4/lab2-4final/src/main/java/ui/transactions.txt");

//        try {
//           ((itemFileRepo<Long, ClothingItem>) items).readFromFile();
//           ((clientFileRepo<Long, Client>) clients).readFromFile();
//           ((transactionFileRepo<Long, Transaction>) transactions).readFromFile();
//       }
//       catch(IOException e){
//           e.printStackTrace();
//       }

        //xml repo

        //Repository<Long, ClothingItem> itemsXML = new itemXmlRepo(itemValidator, "itemsX.xml");
        //Repository<Long, Client> clientsXML = new clientXmlRepo(clientValidator, "clientsX.xml");
        //Repository<Long, Transaction> transactionsXML = new transactionXmlRepo(transactionValidator, "transactionsX.xml");

//        try {
//            ((itemXmlRepo) itemsXML).readFromFile();
//            ((clientXmlRepo) clientsXML).readFromFile();
//            ((transactionXmlRepo) transactionsXML).readFromFile();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }

        //db repo

        Repository<Long, Client> dbRepoClients = new clientDBRepo(clientValidator);
        Repository<Long, ClothingItem> dbRepoItems = new itemDBRepo(itemValidator);
        Repository<Long, Transaction> dbRepoTrans = new transactionDBRepo(transactionValidator);



        Controller ctrl = new Controller(dbRepoItems, dbRepoClients, dbRepoTrans);
        Console console = new Console(ctrl);

        try {
            console.runConsole();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}