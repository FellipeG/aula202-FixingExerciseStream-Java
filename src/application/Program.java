package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Employee;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the full path of the file: ");
		String path = sc.nextLine();
		System.out.print("Enter salary value: ");
		double salary = sc.nextDouble();
		List<Employee> employees = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String line = br.readLine();
			while(line != null) {
				String[] vect = line.split(",");
				employees.add(new Employee(vect[0], vect[1], Double.parseDouble(vect[2])));
				line = br.readLine();
			}
			
			List<String> emails = employees.stream()
					.filter(e -> e.getSalary() > salary)
					.map(e -> e.getEmail())
					.sorted((x, y) -> x.toUpperCase().compareTo(y.toUpperCase()))
					.collect(Collectors.toList());
			
			double salarySum = employees.stream()
					.filter(e -> e.getName().charAt(0) == 'M')
					.map(e -> e.getSalary())
					.reduce(0.0, (x, y) -> x + y);
					
			System.out.println("Email of people whose salary is more than " + salary + ":");
			emails.forEach(System.out::println);
			System.out.println();
			System.out.println("Sum of the salary of people whose name starts with 'M': " + String.format("%.2f", salarySum));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}

	}

}
