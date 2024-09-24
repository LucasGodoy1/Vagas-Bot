package LucasGodoy1.com.github.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conversor {


    public static List<String> converteArquivoEmLista(String arquivo) throws IOException {
        List<String> sites = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                sites.add(linha);
            }
        } catch (FileNotFoundException e) {
            System.out.println("O ARQUIVO NÃO FOI ENCONTRADO! " + e);
        }
        return sites;
    }

}
