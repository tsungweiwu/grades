/*
 * Written by Tsung Wei Wu
 */

// import the scanner and io packages
import java.util.*;

public class GradeCalculator {
	
	static Scanner key;

	public static void main(String[] args) {
		Database data = new Database();
		key = new Scanner(System.in);
		System.out.println("Welcome to Edison's Grade Calculator");

		try {
			while(true) {
				printOptions();
				int options = key.nextInt();
				key.nextLine();

				data.readCourses();

				switch(options) {
					// add course
					case 1: {
						data.addCourse(add());
						break;
					}

					// remove course
					case 2: {
						System.out.println("Enter course name to remove");
						String title = key.nextLine();
						data.removeCourse(title);
						break;
					}

					// print courses
					case 3: {
						data.printCourse();
						break;
					}

					// print grades
					case 4: {
						System.out.println("Please enter course");
						String course = key.nextLine();
						data.readFile(course);
						break;
					}

					// create new grade
					case 5: {
							System.out.println("Enter course name to create file");
							String title = key.nextLine();
							
							System.out.println("Enter amount of headings to add");
							int num = key.nextInt();
							key.nextLine();

							String[] headings = new String[num];
							double[] weights = new double[num];

						
							for(int i = 0; i < num; i++) {
								System.out.println("Enter grade heading, ex: Homework, Test, Exam");
								headings[i] = key.nextLine();
								
								System.out.println("Enter heading's grade weight in decimals, ex: 0.25 is 25%");
								weights[i] = key.nextDouble();
								
								key.nextLine();
							}
							data.writeGrade(title, headings, weights);
						
						break;

					}

					case 6: {
						manual();
						break;
					}

					case 9: {
						System.out.println("Goodbye!");
						System.exit(0);
						break;
					}

					default: {
						System.out.print("Invalid Option");
					}

				} // end switch
			} // end while

		} // end try

		catch(Exception e) {
			System.out.println(e);
		}

	} // end main


	public static void printOptions() {
		System.out.println("\nEnter:\n1. Add a Course\n2. Remove a Course by title" +
			"\n3. Print Courses\n4. Print Grades\n5. Create Grade file\n6. For User Manual\n9. Quit");
	} // end printOptions

	public static Course add() {
		System.out.println("Please enter the course name");
		String course = key.nextLine();
		System.out.println("Please enter the course file name");
		String file = key.nextLine();

		return new Course(course, file);
	} // end add

	public static void manual() {
		System.out.println("Option 1 is to add a course along with the file name of the course where you store your grades" +
			"\nOption 2 is to remove a course by searching for its course name that you have inputted before" + 
			"\nOption 3 is to print all the courses that are available in your courses.txt file as record" +
			"\nOption 4 is to calculate and print the grades that are available in your file name of the course, but you MUST create a grade file first!!" +
			"\nOption 5 is to create the grade file, Please follow the instructions for optimal format with course headings and weightings, then you will add the grades individually in the text files");
	}

}