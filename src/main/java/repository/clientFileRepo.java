package repository;

import domain.BaseEntity;
import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class clientFileRepo extends inMemoryRepository<Long, Client> {

    private String filePath;

    public clientFileRepo(Validator<Client> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
            writer.print("");
            writer.append(entity.toStringFile());
            writer.append('\n');
            writer.flush();
            writer.close();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        Optional<Client> opt = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        logToFile();
        return opt;

    }

    @Override
    public Optional<Client> delete(Long id)
    {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<Client> opt = Optional.ofNullable(entities.remove(id));

        logToFile();
        return opt;
    }

    public void logToFile(){

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));


            for (Client item: this.entities.values()) {
                writer.write(item.toStringFile());
                writer.append('\n');
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<Client> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
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
            Integer id = Integer.parseInt(members[0]);
            Long idd = (Long) java.lang.Long.valueOf(id.longValue());
            String name = members[1];
            int age = Integer.parseInt(members[2]);
            String membership = members[3];
            Client c = new Client(name, age, membership);
            c.setId((java.lang.Long)idd);
            this.entities.put(idd, c);

        }
    }
}
