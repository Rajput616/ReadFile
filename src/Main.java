// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Main {
    public static void main(String[] args) {

        List<Staff> staffList = new ArrayList<>();
        try{
            File file = new File("src/test.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("staff");
//            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int staffId = Integer.parseInt(eElement.getAttribute("id"));
                    String firstName = eElement.getElementsByTagName("firstname")
                            .item(0).getTextContent();
                    String lastName = eElement.getElementsByTagName("lastname")
                            .item(0).getTextContent();
                    String nickName = eElement.getElementsByTagName("nickname")
                            .item(0).getTextContent();
                    long salary = Long.parseLong(eElement.getElementsByTagName("salary")
                            .item(0).getTextContent());
                    int age = Integer.parseInt(eElement.getElementsByTagName("age")
                            .item(0).getTextContent());
                    int exp = Integer.parseInt(eElement.getElementsByTagName("exp")
                            .item(0).getTextContent());

                    Staff staff = new Staff(staffId, firstName, lastName, nickName, salary, age, exp);
                    staffList.add(staff);
                }
            }

            System.out.println("Max Salary - " + maxSalary(staffList));
            System.out.println("Names in sorted order - " + namesInSortedOrder(staffList));
            System.out.println("Employee with max experience - " + employeeWithMaxExp(staffList));
            System.out.println("Max age diff - " + maxAgeDiff(staffList));



        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
    private static long maxSalary(List<Staff> staffList){
        Optional<Long> sal = staffList.stream().map(Staff::getSalary).max(Comparator.naturalOrder());
        if(sal.isPresent()){
            Long val = sal.get();
            return (long)val;
        }
        return 0;
    }

    private static List<String> namesInSortedOrder(List<Staff> staffList){
        List<String> names = staffList.stream().map(str -> str.firstName + str.lastName).sorted().toList();
        return names;
    }

    private static String employeeWithMaxExp(List<Staff> staffList){
        Staff staff = new Staff();
        int maxExp = 0;
        for(Staff s : staffList){
            int currExp = s.getExp();
            if(currExp > maxExp){
                maxExp = currExp;
                staff = s;
            }
        }
        return staff.getFirstName() + " " + staff.getLastName();
    }

    private static int maxAgeDiff(List<Staff> staffList){
        Optional<Integer> max = staffList.stream().map(s -> s.age).max(Comparator.naturalOrder());
        Optional<Integer> min = staffList.stream().map(s -> s.age).min(Comparator.naturalOrder());
        return max.map(integer -> integer - min.get()).orElse(0);
    }












}