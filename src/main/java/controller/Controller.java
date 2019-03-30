package controller;

import domain.ClothingItem;
import domain.Client;
import domain.Transaction;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import repository.Repository;


import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import java.util.*;
import java.util.stream.Collector;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 * Service for managing clients and clothing items.
 * @author catad
 *
 */
public class Controller {

    private Repository<Long, ClothingItem> repository;

    private Repository<Long, Client> clientRepository;

    private Repository<Long, Transaction> transactions;

    /**
     * Controller constructor.
     *
     * @param repo
     * @param clientRepo
     *
     */
    public Controller(Repository<Long, ClothingItem> repo, Repository<Long, Client> clientRepo, Repository<Long, Transaction> transactionRepo)
    {
        this.repository = repo;
        this.clientRepository = clientRepo;
        this.transactions = transactionRepo;
    }

    /**
     * Adds a ClothingItem entity to the specific repository.
     *
     * @param item
     * @throws ValidatorException
     *                          if validation conditions are not met.
     */
    public void addItem(ClothingItem item) throws ValidatorException
    {
        repository.save(item);
    }

    /**
     * Returns all the clothing items in the specific repository.
     *
     * @return a Set of ClothingItem elements
     *
     */
    public Set<ClothingItem> getAllItems()
    {
        Iterable<ClothingItem> items = repository.findAll();
        return StreamSupport.stream(items.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Deletes a given item using its id
     *
     * @param id
     */
    public void deleteItem(Long id)
    {
        repository.delete(id);
    }

    /**
     * Updates a given item (the id will remain the same, the other characteristics change)
     *
     * @param item
     */
    public void updateItem(ClothingItem item){
        repository.update(item);
    }

    /**
     * Adds a Client entity to the specific repository.
     *
     * @param client
     * @throws ValidatorException
     *                          if validation conditions are not met.
     *
     */
    public void addClient(Client client) throws ValidatorException
    {
        clientRepository.save(client);
    }

    /**
     * Returns all the clients in the specific repository.
     *
     * @return a Set of Client elements.
     *
     */
    public Set<Client> getAllClients()
    {
        Iterable<Client> clients = clientRepository.findAll();
        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Deletes a given client using its id
     *
     * @param id
     */
    public void deleteClient(Long id)
    {
        clientRepository.delete(id);
    }

    /**
     * Updates a given client (the id will remain the same, the other characteristics change)
     *
     * @param client
     */

    public void updateClient(Client client){
        clientRepository.update(client);
    }

    /**
     * Adds a Transaction entity to the specific repository.
     *
     * @param transaction
     * @throws ValidatorException
     *                          if validation conditions are not met.
     *
     */

    public void addTransaction(Transaction transaction) throws ValidatorException {
        transactions.save(transaction);
    }
    /**
     * Returns all the transactions in the specific repository.
     *
     * @return a Set of Transaction elements.
     *
     */

    public Set<Transaction> getAllTransactions(){
        Iterable<Transaction> t = transactions.findAll();
        return StreamSupport.stream(t.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Deletes a given transaction using its id
     *
     * @param id
     */
    public void deleteTransaction(Long id)
    {
        transactions.delete(id);
    }

    /**
     * Updates a given transaction (the id will remain the same, the other characteristics change)
     *
     * @param t
     */

    public void updateTransaction(Transaction t){
        transactions.update(t);
    }

    /**
     * Returns all items whose name contain the given string.
     *
     * @param s (a String)
     * @return a Set of ClothingItem elements that satisfy the given requirement.
     *
     */
    public Set<ClothingItem> filterItemsByName(String s) {
        Iterable<ClothingItem> items = repository.findAll();

        Set<ClothingItem> filteredItems = StreamSupport.stream(items.spliterator(), false)
                .filter(item -> item.getName().contains(s)).collect(Collectors.toSet());

        return filteredItems;
    }

    /**
     * Returns all clients whose membership type is the given string.
     *
     * @param s (a String)
     * @return a Set of Client elements that satisfy the given requirement.
     *
     */

    public Set<Client> filterClientsByMembershipType(String s){
        Iterable<Client> clients = clientRepository.findAll();

        Set<Client> filteredClients = StreamSupport.stream(clients.spliterator(), false).
                filter(item -> item.getMembershipType().equals(s)).collect(Collectors.toSet());

        return filteredClients;

    }

    /**
     * Returns all clients sorted alphabetically
     *
     * @return a Set of Client elements that satisfy the given requirement.
     *
     */
    public Set<Client> sortClientsAlphabetically()
    {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).sorted(Comparator.comparing(Client::getName)).collect(Collectors.toSet());
    }


    /**
     * Returns the most popular designer
     *
     * @return the name of the designer and the number of his pieces that were bought
     */
    public Map.Entry<String, Long> getMostPopularDesigner()
    {
       //Map<String, Long> itemsByDesigner = StreamSupport.stream(this.repository.findAll().spliterator(), false).collect(groupingBy(ClothingItem::getDesigner, Collectors.counting()));
       return StreamSupport.stream(StreamSupport.stream(this.repository.findAll().spliterator(), false).collect(groupingBy(ClothingItem::getDesigner, Collectors.counting())).entrySet().spliterator(), false).max(Comparator.comparingLong(Map.Entry::getValue)).get();
        /*
        HashMap<String, Integer> designers = new HashMap<String, Integer>();
        for (ClothingItem i : this.repository.findAll()) {
            int v;
            //int v = designers.get(i.getDesigner());
            if (designers.containsKey(i.getDesigner())) {
                v = designers.get(i.getDesigner());
                designers.put(i.getDesigner(), ++v);
            }
            else
                designers.put(i.getDesigner(), 1);
        }

        int d = StreamSupport.stream(designers.values().spliterator(), false).max(Comparator.comparing(Integer::valueOf)).get();
        List<String> popularDesigners = new ArrayList<String>();
        for (String k: designers.keySet()) {
            if(designers.get(k) == d)
                popularDesigners.add(k);
        }
        return popularDesigners;
        */
    }

    /**
     * Returns all the transactions in the specific repository.
     *
     * @return a repo of Transaction elements.
     *
     */
    public Repository<Long, Transaction> getTransactions(){
        return this.transactions;
    }

    /**
     * Sorts the clothing items in a specific order:
     * every item with a price higher than a given one, sorted ascending by designer
     * and every item with a price lower than the given one, sorted descending by designer
     * @param price
     * @return
     */
    public List<ClothingItem> coolOrder(int price){
        //List<ClothingItem> listOne = StreamSupport.stream(this.repository.findAll().spliterator(), false).filter(object -> object.getPrice() >= price).sorted(Comparator.comparing(item -> item.getDesigner())).collect(Collectors.toList());
        //List<ClothingItem> listTwo = StreamSupport.stream(this.repository.findAll().spliterator(), false).filter(object -> object.getPrice() < price).sorted(Comparator.comparing(item -> item.getDesigner(), reverseOrder())).collect(Collectors.toList());

       return Stream.concat(StreamSupport.stream(this.repository.findAll().spliterator(), false).
               filter(object -> object.getPrice() >= price).
               sorted(Comparator.comparing(item -> item.getDesigner())).collect(Collectors.toList()).stream(),
               StreamSupport.stream(this.repository.findAll().spliterator(), false).
                       filter(object -> object.getPrice() < price).
                       sorted(Comparator.comparing(item -> item.getDesigner(), reverseOrder())).collect(Collectors.toList()).stream())
                .collect(Collectors.toList()); }

}
