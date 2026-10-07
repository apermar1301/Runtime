package com.github.apermar1301;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListDirectories {

    public void init(){
        toOutputString(listDirectories());
    }

    public Process listDirectories(){
        try {
            return Runtime.getRuntime().exec(commandOS());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void toOutputString(Process process){
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))){

            String line;
            while((line = bufferedReader.readLine()) != null){
                System.out.println(line);
            }

            process.waitFor();

        } catch (IOException e){
            System.err.printf("I/O Exception: %s%n", e.getMessage());
        } catch (InterruptedException e) {
            System.err.printf("Interrupted Exception: %s%n", e.getMessage());
        }
    }

    public String[] commandOS() throws IllegalArgumentException {
        return switch(System.getProperty("os.name").toLowerCase()){
            case String os when os.contains("win") -> new String[]{"cmd.exe", "/c", "dir", "/a"};
            case String os when os.contains("linux") || os.contains("mac") -> new String[]{"ls", "-la"};
            default -> throw new IllegalArgumentException("Unsupported operating system");
        };
    }

}
