package repository;

import domain.BaseEntity;
import domain.Client;
import domain.Transaction;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class transactionFileRepo extends inMemoryRepository<Long, Transaction> {

    private String filePath;

    public transactionFileRepo(Validator<Transaction> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            writer.print("");
            writer.append(entity.toStringFile());
            writer.flush();
            writer.close();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Optional<Transaction> opt = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        logToFile();
        return opt;

    }

    @Override
    public Optional<Transaction> delete(Long id)
    {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Transaction> opt = Optional.ofNullable(entities.remove(id));

        logToFile();
        return opt;
    }

    public void logToFile(){

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));


            for (Transaction item: this.entities.values()) {
                writer.write(item.toStringFile());
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Transaction> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        logToFile();
        return opt;
    }

    public void readFromFile()throws FileNotFoundException {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);
        String line;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            String[] members = line.split(",");

            //Client
            Integer id = Integer.parseInt(members[0]);
            Long iddC = (Long) java.lang.Long.valueOf(id.longValue());
            String name = members[1];
            int age = Integer.parseInt(members[2]);
            String membership = members[3];
            Client c = new Client(name, age, membership);
            c.setId((java.lang.Long)iddC);

            Integer idT = Integer.parseInt(members[4]);
            Long iddT = (Long) java.lang.Long.valueOf(id.longValue());
            String date = members[5];
            Transaction t = new Transaction(c, date);
            t.setId((java.lang.Long)iddT);
            this.entities.put(iddT, t);

        }
    }
}
