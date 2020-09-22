package tukan;
import java.io.*;
import java.util.*;

import static java.lang.System.*;

public class Commands {
    public static void help(){
        out.println("help - вывод доступных команд");
        out.println("info - вывод информации о коллекции");
        out.println("show - вывод всех элементов коллекции");
        out.println("add - добавить новый элемент в коллекцию");
        out.println("update id - обновить элемент по id");
        out.println("remove_by_id id - удалить элемент с заданным id");
        out.println("clear - очистить коллекцию ");
        out.println("save- сохранить коллекцию в файл");
        out.println("execute_script file_name - считать скрипт из заданного файла");
        out.println("exit - выход из программы");
        out.println("remove_first - удаляет первый элемент в коллекции");
        out.println("reorder - отсортировать коллекцию в обратном порядке");
        out.println("sort - отсортировать коллекцию");
        out.println("remove_all_by_type type - удалить все элементы с типом type");
        out.println("filter_less_than_annual_turnover annualTurnover - вывести все элементы со значением годового оборота меньше заданного");
        out.println("filter_greater_than_employees_count employeesCount - вывести все элементы со значением кол-ва сотрудников выше заданного");

    }
    public static void info(ArrayList orgs){
        out.println("Тип");
        out.println("ArrayList");
        out.println("Дата инициализации");
        out.println(Organization.getCreationDate());
        out.println("Размер коллекции");
        out.println(orgs.size());
    }
    public static void show(Organization org) {
        out.println("id организации");
        out.println(org.getId() + "\n" );
        out.println("Имя организации");
        out.println(org.getName());
        out.println();
        out.println("Координаты организации");
        out.print(org.getCoordinatesX());
        out.print(" ");
        out.println(org.getCoordinatesY());
        out.println();
        out.println("Годовой оборот");
        if(org.getAnnualTurnover() == null)
            out.println("unknown unit");
        else
            out.println(org.getAnnualTurnover() + " unit");
        out.println();
        out.println("Полное название");
        out.println(org.getFullName());
        out.println();
        out.println("Количество работяг");
        if(org.getEmployeesCount() == null)
            out.println("unknown");
        else
            out.println(org.getEmployeesCount());
        out.println();
        out.println("Тип организации");
        out.println(org.getType());
        out.println();
        out.println("Местоположение организации");
        if(org.getStreet() == null)
            out.println("Street: unknown");
        else
            out.println("Street: " + org.getStreet());
        if (org.getTown().isEmpty())
            out.println("Town: unknown");
        else {
            out.println("Town: " + org.getTown());
            out.println("x: " + org.getX());
            out.println("y: " + org.getY());
            out.println("z: " + org.getZ());
        }
        out.println("__________________________________");
    }
    public static void save(Organization org, PrintWriter printWriter){
        printWriter.println(org.getName());
        printWriter.println(org.getCoordinatesX());
        printWriter.println(org.getCoordinatesY());
        if (org.getAnnualTurnover() == null)
            printWriter.println("");
        else
            printWriter.println(org.getAnnualTurnover());
        printWriter.println(org.getFullName());
        if (org.getEmployeesCount() == null)
            printWriter.println("");
        else
            printWriter.println(org.getEmployeesCount());
        printWriter.println(org.getType());
        if (org.getStreet() == null)
            printWriter.println("");
        else
            printWriter.println(org.getStreet());
        printWriter.println(org.getTown());
        if(!org.getTown().isEmpty()) {
            printWriter.println(org.getX());
            printWriter.println(org.getY());
            printWriter.println(org.getZ());
        }
        else{
            printWriter.println("");
            printWriter.println("");
            printWriter.println("");
        }
        printWriter.println();
    }
    public static int countFile(String fileName) {
        int count = 0;
        File file = new File(fileName);
        Scanner sc;
        try {
            sc = new Scanner(file);

            while (true) {
                try {
                    sc.nextLine();
                    count+=1;
                } catch (NoSuchElementException ex) {
                    break;
                }
            }
        }
        catch (FileNotFoundException ex){
        }
        return count;
    }
    public static String execute(String fileName){
        File file = new File(fileName);
        String script ="";
        try {
            Scanner sc = new Scanner(file);
            script = sc.nextLine();
        }
        catch (FileNotFoundException ex){
            out.println("Не удается найти указанный файл");
        }
        return script;
    }
    public static String[] execute(String fileName, int N){
        File file = new File(fileName);
        String[] script = new String[N];
        try {
            Scanner sc = new Scanner(file);
        for(int i = 0; i < N; i++) {
            script[i] = sc.nextLine();
        }
        } catch (FileNotFoundException ex) {
            out.println("Не удается найти указанный файл");
        }
        return script;
    }

    public static ArrayList<Organization> executeScript(ArrayList<Organization> orgs, int id, String fileName){
        String type;
        int vId;
        Scanner reader = new Scanner(in);
        try {
            int N = Commands.countFile(fileName);
            int i = 0;
            if (i == N) return orgs;
            String[] commands = Commands.execute(fileName, N);
            while (i < N) {
                commands[i] = commands[i].trim() + " ";
                int space = commands[i].indexOf(' ');
                String sCom = commands[i].substring(0, space);
                commands[i] = commands[i].substring(space).trim();
                out.println(N);
                System.out.println(sCom + commands[i]);
                i++;
                switch (sCom) {
                    case ("help"):
                        Commands.help();
                        break;
                    case ("info"):
                        Commands.info(orgs);
                        break;
                    case ("show"):
                        for (Organization org : orgs) {
                            Commands.show(org);
                        }
                        break;
                    case ("add"):try
                    {
                        String[] vInput = new String[12];
                        for (int j = 0; j < 12; j++) {
                            vInput[j] = commands[i];
                            System.out.println(i + " " + vInput[j]);
                            i++;
                        }
                        System.out.println();
                        orgs.add(new Organization(id, orgs, vInput));
                        id += 1;
                    }
                    catch (IndexOutOfBoundsException ex){
                        System.out.println("Ошибка ввода. Проверьте корректность данных. Попробуйте использовать команду add, и проверить правильность порядка заполнения файла.");
                    }


                        break;
                    case ("update"):
                        try {
                            vId = Integer.parseInt(commands[i-1]);
                            out.println(vId);
                            orgs.remove(vId);
                            String[] vInput = new String[12];
                            for(int j = 0; j < 12; j++){
                                vInput[j] = commands[i];
                                out.println(commands[i]);
                                i++;
                            }
                            orgs.add(new Organization(vId, orgs, vInput));
                        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                            System.out.println("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                            ex.printStackTrace();
                        }
                        break;
                    case ("remove_by_id"):
                        try {
                            boolean k = true;
                            Organization wrong = new Organization();
                            vId = Integer.parseInt(commands[i]);
                            for(Organization org:orgs){
                                if (org.getId() == vId){
                                    wrong = org;
                                    k = false;
                                }
                            }
                            orgs.remove(wrong);
                            if (k) System.out.println("Элемент с таким id отсутсвует");
                        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                            System.out.println("Неверный ввод id или элемент с таким id отсутствует. Попробуйте снова.");
                        }
                        break;
                    case ("clear"):
                        orgs.clear();
                        break;
                    case ("save"): {
                        FileWriter fileWriter = new FileWriter("Collection.txt");
                        PrintWriter printWriter = new PrintWriter(fileWriter);
                        for (Organization org : orgs)
                            Commands.save(org, printWriter);
                    }
                    break;
                    case ("execute_script"):
                        String oldFileName = fileName;
                        fileName = commands[i-1];
                        if (!oldFileName.equals(fileName))
                        orgs = Commands.executeScript(orgs, id, fileName);
                        for(Organization org:orgs)
                            id = org.getId();
                        id +=1;
                        break;
                    case ("exit"):
                        System.exit(1);
                        break;
                    case ("remove_first"):
                        orgs.remove(0);
                        break;
                    case ("reorder"):
                        Collections.reverse(orgs);
                        break;
                    case ("sort"):
                        orgs.sort(Comparator.comparing(Organization::getId));
                        break;
                    case ("remove_all_by_type"):
                        type = reader.next();
                        ArrayList<Organization> copy = new ArrayList<>();
                        for (Organization org : orgs) {
                            if (!org.getStringType().equals(type)) {
                                copy.add(org);
                            }
                            orgs = copy;
                        }
                        break;
                    case ("filter_less_than_annual_turnover"):
                        int anTurn = Integer.parseInt(commands[i]);
                        for (Organization org : orgs) {
                            if (org.getAnnualTurnover() > anTurn)
                                Commands.show(org);
                        }
                        break;
                    case ("filter_greater_than_employees_count"):
                        int emCount = Integer.parseInt(commands[i]);
                        for (Organization org : orgs) {
                            if (org.getEmployeesCount() > emCount)
                                Commands.show(org);
                        }
                        break;
                    default:
                        System.out.println("Ошибка ввода.");
                }
            }
        }
        catch (IndexOutOfBoundsException | IOException ex){
            System.out.println("Ошибка выполнения скрипта из указанного файла");
            ex.printStackTrace();
        }
        return orgs;
    }
}
