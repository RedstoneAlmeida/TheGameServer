package network.properties;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private Map<String, Object> all = new HashMap<>();

    private File file;
    private boolean prop = false;

    public Config(String file){
        this(new File(file));
    }

    public Config(File file){
        this.file = file;
        if(file.isDirectory()) try {
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        read();
        save();
    }

    public Config(File file, boolean prop){
        this.file = file;
        if(file.isDirectory()) try {
            throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.prop = prop;

        read();
        save();
    }

    private void read(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String linha = br.readLine();
                if(!prop) {
                    if (linha.contains(": ")) {
                        String[] split = linha.split(": ", 2);
                        all.put(split[0], split[1]);
                    }
                } else if(prop && linha.contains("=")){
                    if(linha.contains("generator-settings")) continue;
                    if(linha.contains("level-seed")) continue;
                    String[] ss = linha.split("=", 2);
                    if(ss[1] == null) continue;
                    all.put(ss[0], ss[1]);
                }
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setAll(Map<String, Object> all) {
        this.all = all;
    }

    public Map<String, Object> getAll() {
        return all;
    }

    public void replace(String key, Object object){
        if(all.containsKey(key)){
            all.replace(key, object);
        }
    }

    public void set(String key, Object object){
        if(all.containsKey(key))
            return;
        all.put(key, object);
    }

    public void set(String key, Object object, boolean force){
        if(all.containsKey(key) && !force)
            return;
        all.put(key, object);
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(file);
            PrintWriter print = new PrintWriter(writer);

            for(String key : all.keySet()){
                Object obj = all.get(key);
                if(prop){
                    print.printf("%s%s%s%n", key, "=", obj);
                } else {
                    print.printf("%s%s%s%n", key, ": ", obj);
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key){
        return all.get(key);
    }

    public boolean exists(String key){
        return all.containsKey(key);
    }

    public boolean getBoolean(String key){
        return (boolean) all.get(key);
    }

    public String getString(String key){
        return (String) all.get(key);
    }

    public int getInt(String key){
        return (int) all.get(key);
    }

    public double getDouble(String key){
        return (double) all.get(key);
    }

    public long getLong(String key){
        return (long) all.get(key);
    }

    public float getFloat(String key){
        return (float) all.get(key);
    }

}

