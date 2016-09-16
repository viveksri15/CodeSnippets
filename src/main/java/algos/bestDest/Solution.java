package algos.bestDest;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int _count;
        _count = Integer.parseInt(in.nextLine());

        OutputCommonManager(_count, in);
    }


    private static class Node {
        private final String name;
        private Set<String> reportees = new HashSet<>();
        boolean hasEmployee1 = false, hasEmployee2 = false;

        Node(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        Set<String> getReportees() {
            return reportees;
        }

        boolean hasReportee(String employee) {
            return reportees.contains(employee);
        }

        void addReportees(Node employee) {
            reportees.add(employee.getName());
        }
    }

    private static class Result {
        String manager;
        int depth = -1;

        String getManager() {
            return manager;
        }

        void setManager(String manager, int depth) {
            if (depth > this.depth) {
                this.depth = depth;
                this.manager = manager;
            }
        }
    }

    private static void OutputCommonManager(int count, Scanner in) {
        String employee1 = in.nextLine();
        String employee2 = in.nextLine();
        Map<String, Node> nameNodeMap = new HashMap<>();
        String head = null;

        for (int i = 0; i < count; ) {
            String employeeRelation = in.nextLine();
            String[] employees = employeeRelation.split("\\s");
            String employeeName1 = employees[0];
            String employeeName2 = employees[1];
            if (head == null)
                head = employeeName1;
            Node employeeNode1 = getEmployeeNode(nameNodeMap, employeeName1);
            Node employeeNode2 = getEmployeeNode(nameNodeMap, employeeName2);

            if (employeeName1.equals(employee1))
                employeeNode1.hasEmployee1 = true;
            if (employeeName1.equals(employee2))
                employeeNode1.hasEmployee2 = true;

            if (employeeName2.equals(employee2))
                employeeNode2.hasEmployee2 = true;
            if (employeeName2.equals(employee2))
                employeeNode2.hasEmployee2 = true;

            employeeNode1.addReportees(employeeNode2);
            i = nameNodeMap.size();
        }

        Node employeeNode1 = getEmployeeNode(nameNodeMap, employee1);
        Node employeeNode2 = getEmployeeNode(nameNodeMap, employee2);

        if (employeeNode1.hasReportee(employee2))
            sendResult(employee1);
        else if (employeeNode2.hasReportee(employee1))
            sendResult(employee2);
        else {
            Result result = new Result();
            result.setManager(head, 0);
            findReportee(head, employee1, nameNodeMap, 1);
            findReportee(head, employee2, nameNodeMap, 2);
            findResult(result, head, nameNodeMap, 0);
            sendResult(result.getManager());
        }
    }

    private static void findResult(Result result, String head, Map<String, Node> nameNodeMap, int depth) {
        Node headNode = getEmployeeNode(nameNodeMap, head);
        if (headNode.hasEmployee1 && headNode.hasEmployee2) {
            result.setManager(head, depth);
        }

        for (String reportee : headNode.getReportees()) {
            findResult(result, reportee, nameNodeMap, depth + 1);
        }
    }

    private static void sendResult(String employee1) {
        System.out.println(employee1);
    }

    private static boolean findReportee(String head, String employee, Map<String, Node> nameNodeMap, int number) {


        Node headNode = getEmployeeNode(nameNodeMap, head);

        if (headNode.hasReportee(employee)) {
            if (number == 1) {
                headNode.hasEmployee1 = true;
            } else
                headNode.hasEmployee2 = true;

            return true;
        }

        boolean found = false;

        for (String reportee : headNode.getReportees()) {
            Node reporteeNode = getEmployeeNode(nameNodeMap, reportee);
            if (findReportee(reportee, employee, nameNodeMap, number)) {
                if (number == 1) {
                    reporteeNode.hasEmployee1 = true;
                } else
                    reporteeNode.hasEmployee2 = true;
                found = true;
                break;
            }
        }

        if (found) {
            if (number == 1) {
                headNode.hasEmployee1 = true;
            } else
                headNode.hasEmployee2 = true;
        }

        return found;
    }

    private static Node getEmployeeNode(Map<String, Node> nameNodeMap, String employeeName) {
        Node node1 = nameNodeMap.get(employeeName);
        if (node1 == null) {
            node1 = new Node(employeeName);
            nameNodeMap.put(employeeName, node1);
        }
        return node1;
    }
}
