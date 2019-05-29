
/*
 * Written by Tsung Wei Wu
 */

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Database {
	public static final String delim = "\t";
	public static final int MAX = 100;
	public static final String dir = "classes/";

	// course
	private Course[] courses;
	private Course[] tempCourse;
	private int course_count = 0;

	// grade
	private String[] headers;
	private double[] sections;
	private int[] grades;
	private double[] weighing;

	public Database() {
		courses = new Course[MAX];
		tempCourse = new Course[MAX];
	}

	public Course[] getCourses() {
		return courses;
	}

	public void initialize(Course aCourse) {
		for (int i = 0; i < courses.length; i++) {
			if (courses[i] == null) {
				courses[i] = aCourse;
				tempCourse[i] = aCourse;
				course_count = courses.length;
				return;
			}
		}
	}

	// adds a course into the array
	public void addCourse(Course aCourse) {
		for (int i = 0; i < tempCourse.length; i++) {
			if (tempCourse[i] == null) {
				tempCourse[i] = aCourse;
				System.out.println("ADDITION SUCCESSFUL");
				course_count++;
				this.writeCourses();
				break;
			}
		}
	}

	public void removeCourse(String title) {
		this.readCourses();

		// int temp = 0;
		// String[] array = new String[tempCourse.length];
		// for(int i = 0; i < tempCourse.length; i++) {
		// if(tempCourse[i] != null)
		// array[i] = tempCourse[i].getCourse().toLowerCase();
		// }

		// // array list and hashmap
		// List<String> asList = Arrays.asList(array);
		// Set<String> mySet = new HashSet<String>(asList);
		// for(String s: mySet){
		// if(s != null && s.equalsIgnoreCase(title)) {
		// temp = Collections.frequency(asList,s);
		// }
		// }

		for (int i = 0; i < tempCourse.length; i++) {
			if (tempCourse[i] != null && tempCourse[i].getCourse().equals(title)) {

				List<Course> tempList = new ArrayList<Course>(Arrays.asList(tempCourse));
				tempList.remove(i);

				tempCourse = tempList.toArray(new Course[0]);

				course_count--;
				this.writeCourses();
				break;
			}
		}
	}

	public void writeCourses() {
		try {
			PrintWriter fileWriter = new PrintWriter(new FileOutputStream("courses.txt"));

			fileWriter.println(course_count);

			for (Course course : tempCourse) {
				if (course == null) {
					break;
				}
				fileWriter.println(course.getCourse() + delim + course.getFile());
			}
			fileWriter.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void readCourses() {
		try {
			// file input
			Scanner fileScanner = new Scanner(new File("courses.txt"));
			// checks if the file has anything in it

			courses = new Course[Integer.parseInt(fileScanner.nextLine())];

			while (fileScanner.hasNextLine()) {
				String fileLine = fileScanner.nextLine();
				String[] splitLines = fileLine.split(delim);
				String course = splitLines[0];
				String file = splitLines[1];
				Course crse = new Course(course, file);

				this.initialize(crse);
			}
			fileScanner.close();
		}

		catch (Exception e) {
			System.out.println(e);
		}
	}

	public void printCourse() {
		System.out.println("\nCourses: \n");
		for (int i = 0; i < tempCourse.length; i++) {
			if (tempCourse[i] != null) {
				System.out.println(tempCourse[i].getCourse() + delim + tempCourse[i].getFile());
			} else {
				break;
			}

		}
	}

	public void readFile(String course) {
		System.out.println();
		try {
			for (int k = 0; k < courses.length; k++) {
				if (courses[k].getCourse().equalsIgnoreCase(course)) {
					// opens the file to read
					Scanner fileScanner = new Scanner(new File(dir + courses[k].getFile()));

					int header_size = Integer.parseInt(fileScanner.nextLine());

					// create an array of each section with grades within it
					headers = new String[header_size];
					sections = new double[header_size];
					grades = new int[header_size];

					String fileLine = fileScanner.nextLine();
					String[] splitLine = fileLine.split(delim);

					weighing = new double[header_size];
					String[] splitWeight = fileScanner.nextLine().split(delim);

					for (int i = 0; i < splitLine.length; i++) {
						headers[i] = splitLine[i];
						weighing[i] = Double.parseDouble(splitWeight[i]);
					}

					int sectionIndex = 0;

					fileScanner.nextLine();

					// runs while the file isn't empty
					while (fileScanner.hasNextLine()) {
						// assigns each line to a string
						fileLine = fileScanner.nextLine();
						// adds according to the section

						if (Arrays.stream(headers).anyMatch(fileLine::equals)) {
							sectionIndex++;
						} else {

							sections[sectionIndex] += Double.parseDouble(fileLine);
							grades[sectionIndex]++;
						}

					} // end-while

					if (courses[k].getCourse().equals("phys211")) {
						for (int i = 0; i < sections.length; i++) {
							if (i != 2) {
								sections[i] /= grades[i];
							} else {
								sections[i] /= grades[i] - 1;
							}

							if (i != 1) {
								System.out.println("Your " + headers[i] + " = " + sections[i]);
							}
						} // end-for

						System.out.println("Your " + headers[1] + " before = " + sections[1]);

						if (sections[1] > 70) {
							sections[1] = 100;
							System.out.println("Your " + headers[1] + " after = " + sections[1]);
						}
					} else {
						for (int i = 0; i < sections.length; i++) {
							sections[i] /= grades[i];
							System.out.println("Your " + headers[i] + " = " + sections[i]);
						} // end-for
					}

					double sum = 0;

					for (int i = 0; i < sections.length; i++) {
						sum += (sections[i] * weighing[i]);
					}
					// adds up the grade according to weighing
					double final_grade = Math.ceil(sum); // final

					// prints out the raw total rounded to nearest whole numbers
					System.out.println("Your raw total is " + final_grade);
					// prints out letter grade
					letterGrade(final_grade);
					// closes scanner
					fileScanner.close();
				}

			}
		}

		catch (Exception e) {
			System.out.println(e);
		}

	}

	// checks for the letter corresponding to the grade
	public static void letterGrade(double final_grade) {
		if (final_grade >= 90)
			System.out.println("Your final grade is A");
		else if (final_grade >= 85)
			System.out.println("Your final grade is B+");
		else if (final_grade >= 80)
			System.out.println("Your final grade is B");
		else if (final_grade >= 75)
			System.out.println("Your final grade is C+");
		else if (final_grade >= 70)
			System.out.println("Your final grade is C");
		else if (final_grade >= 65)
			System.out.println("Your final grade is D+");
		else if (final_grade >= 60)
			System.out.println("Your final grade is D");
		else {
			System.out.println("your final grade is F");
		}
	} // end letterGrade

	public void writeGrade(String title, String[] headers, double[] weights) {
		try {
			this.readCourses();
			boolean enter_equals = false;

			File tmpDir = new File("classes");
			boolean exists = tmpDir.exists();
			boolean success = false;

			if (!exists) {
				success = tmpDir.mkdir();
			}

			if (success) {
				System.out.println("\nDirectory Successfully Created");
			}

			for (int i = 0; i < tempCourse.length; i++) {

				if (tempCourse[i] != null) {
					if (tempCourse[i].getCourse().equals(title)) {
						enter_equals = true;
						PrintWriter fileWriter = new PrintWriter(new FileOutputStream(dir + tempCourse[i].getFile()));
						int header_size = headers.length;
						fileWriter.println(header_size);

						for (int j = 0; j < headers.length; j++) {
							if (headers[j] == null) {
								break;
							}
							fileWriter.print(headers[j] + delim);
						}
						fileWriter.println();

						for (int j = 0; j < weights.length; j++) {
							if (headers[j] == null) {
								break;
							}
							fileWriter.print(weights[j] + delim);
						}
						fileWriter.println();

						for (int j = 0; j < headers.length; j++) {
							if (headers[j] == null) {
								break;
							}
							fileWriter.println(headers[j]);
							fileWriter.println("\"Enter Grade Here\"");
						}

						fileWriter.close();
						break;
					}
				} else {
					// recursion that occurs once
					Exception e = new Exception();
					e.fillInStackTrace();
					if (e.getStackTrace().length <= 2 && !enter_equals) {
						System.out.println("\nCourse doesn't exist and will be added");
						String course = title;
						String file = title + ".txt";

						this.addCourse(new Course(course, file));
						this.readCourses();
						this.writeGrade(course, headers, weights); // recursion
					}
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}