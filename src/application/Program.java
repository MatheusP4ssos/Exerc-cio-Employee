package application;

import entities.Employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        //Solicita localização do arquivo txt com as informações para o problema
        System.out.println("Enter the patch file: ");
        String patchFile = sc.nextLine();

        //entrada do valor de salário que seráusado para filtrar e-mail de funcionários que ganham acima da entrada
        System.out.println("Enter salary: ");
        double value = sc.nextDouble();

        //Inicia um arquivo
        File file = new File(patchFile);


        //Criação de exceção casop arquivo não seja encontrado
        if (!file.exists()) {
            System.out.println("Patch file does not exist");
        } else {
            System.out.println("Patch file found, trying to read...");
        }

        //Inicia lista de empregados
        List<Employee> employees = new ArrayList<>();

        //Tenta ler o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(patchFile))) {
            String line = br.readLine();

            //Enquanto houverem linhas a ser lida cria repartição de campos separados por ","
            while (line != null) {
                String[] fields = line.split(",");

                String name = fields[0];
                String email = fields[1];
                double salary = Double.parseDouble(fields[2]);
                employees.add(new Employee(name, email, salary));

                line = br.readLine();
            }

            // Comparator para ordenar os e-mails em ordem decrescente
            Comparator<String> comp = Comparator.naturalOrder();


            // Filtra os salários maiores que o valor e ordena os e-mails
            List<String> emails = employees.stream()
                    .filter(p -> p.getSalary() > value)
                    .map(Employee::getEmail)
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            System.out.println("Email of peolple whose salary is more than 2000.00");
            emails.forEach(System.out::println);



//soma o sálario daqueles que começam com "m"
            double totalSalary = employees.stream()
                    .filter(emp -> emp.getName().toUpperCase().startsWith("M"))
                    .mapToDouble(Employee::getSalary).sum();


System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", totalSalary) );

        } catch (IOException e) {
            System.out.println("Error reading patch file: " + e.getMessage());
        }

        sc.close();
    }
}
