package repository;

import domain.BaseEntity;
import domain.ClothingItem;
import domain.validators.ClothingItemValidator;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import javax.swing.text.html.Option;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.StreamSupport;
import java.lang.Long;

import static java.lang.Long.getLong;
import static java.lang.Long.parseLong;

public class itemFileRepo extends inMemoryRepository<Long, ClothingItem> {

    private String filePath;

    public itemFileRepo(Validator<ClothingItem> v, String file){
        super(v);
        this.filePath = file;
    }

    @Override
    public Optional<ClothingItem> save(ClothingItem entity) throws ValidatorException
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
        Optional<ClothingItem> opt = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        return opt;

    }

    @Override
    public Optional<ClothingItem> delete(Long id)
    {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        Optional<ClothingItem> opt = Optional.ofNullable(entities.remove(id));

        logToFile();
        return opt;
    }

    public void logToFile(){

        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, false)));


            for (ClothingItem item: this.entities.values()) {
                writer.write(item.toStringFile());
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<ClothingItem> update(ClothingItem entity) throws ValidatorException
    {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        Optional<ClothingItem> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
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
            String designer = members[2];
            int price = Integer.parseInt(members[3]);
            ClothingItem item = new ClothingItem(name, designer, price);
            item.setId((java.lang.Long)idd);
            this.entities.put(idd, item);

        }
    }
}
