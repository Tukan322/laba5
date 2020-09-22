package tukan;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class invoker {
    public void invoke() throws IOException {
        ArrayList<Organization> orgs = new ArrayList<>();
        Scanner reader = new Scanner(System.in);
        String checker;
        int id = 0;
        int vId;
        String fileName;
        try {
            fileName = System.getenv("fileName");
            System.out.println(fileName);
            int N = Commands.countFile(fileName);
            String[] input = Commands.execute(fileName, N);
            String[] vInput = new String[13];
            for(int i = 0; i < N; i++) {
                if (!(i % 13 == 12)) {
                    vInput[i % 13] = input[i];
                } else {
                    orgs.add(new Organization(id, orgs, vInput));
                    id += 1;
                }
            }
        }
        catch (IndexOutOfBoundsException|NullPointerException ex){
            System.out.println("ошибка автоматического заполнения из указанного файла");
        }
        String type;
        while (true){
            System.out.println("Введите комманду. help для справки");
            checker = reader.next();
            switch (checker){
                case("help"): Commands.help();
                    break;
                case("info"): Commands.info(orgs);
                    break;
                case("show"): for(Organization org : orgs) {
                    Commands.show(org);
                }
                    break;
                case("add"): orgs.add(new Organization(id, orgs));
                    id += 1;
                    break;
                case("update"):
                    try{
                        vId = Integer.parseInt(reader.next());
                        orgs.remove(vId);
                        orgs.add(new Organization(vId, orgs));
                    }
                    catch (IndexOutOfBoundsException|NumberFormatException ex){
                        System.out.println("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                    }
                    break;
                case("remove_by_id"):
                    try{
                        boolean k = true;
                        vId = Integer.parseInt(reader.next());
                        Organization wrong = new Organization();
                        for(Organization org:orgs)
                            if(vId == org.getId()) {
                                wrong = org;
                                k=false;
                            }
                        orgs.remove(wrong);
                        if(k) System.out.println("Элемент с таким id отсутствует");
                    }
                    catch (IndexOutOfBoundsException|NumberFormatException ex){
                        System.out.println("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                    }
                    break;
                case("clear"):orgs.clear();
                    break;
                case("save"): {
                    FileWriter fileWriter = new FileWriter("Collection.csv");
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    for (Organization org : orgs)
                        Commands.save(org, printWriter);
                    printWriter.close();
                }
                break;
                case("execute_script"):
                    fileName = reader.next();
                    orgs = Commands.executeScript(orgs, id, fileName);
                    orgs.sort(Comparator.comparing(Organization::getId));
                    for(Organization org:orgs)
                        id = org.getId();
                    id +=1;
                    break;
                case("exit"): System.exit(1);
                    break;
                case("remove_first"): try{
                    orgs.remove(0);
                }
                catch(IndexOutOfBoundsException ex){
                    System.out.println("Произошла ошибка. Невозможно удалить первый элемент в пустой коллекции");
                }
                    break;
                case("reorder"): Collections.reverse(orgs);
                    break;
                case("sort"): orgs.sort(Comparator.comparing(Organization::getId));
                    break;
                case("remove_all_by_type"):
                    type = reader.next();
                    ArrayList<Organization> copy = new ArrayList<>();
                    for (Organization org:orgs) {
                        if (!org.getStringType().equals(type)) {
                            copy.add(org);
                        }
                        orgs = copy;
                    }
                    break;
                case("filter_less_than_annual_turnover"):int anTurn = reader.nextInt();

                    for (Organization org : orgs) {
                        try {
                            if (org.getAnnualTurnover() < anTurn)
                                Commands.show(org);
                        }
                        catch (NullPointerException ex) {
                            //
                        }
                    }

                    break;
                case("filter_greater_than_employees_count"):int emCount = reader.nextInt();
                    for(Organization org:orgs){
                        if (org.getEmployeesCount() > emCount)
                            Commands.show(org);
                    }
                    break;
                case("ShowId"):System.out.println(id);
                case("StrTypes"):for(Organization org:orgs) System.out.println(org.getStringType());
                default: System.out.println("Ошибка ввода.");

            }
        }
    }
}