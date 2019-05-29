/*
 * Written by Tsung Wei Wu
 */

public class Course {
	private String course;
	private String fileName;

	public Course() {
		course = "none";
		fileName = "none";
	}

	public Course(String crse, String file) {
		setCourse(crse);
		setFile(file);
	}

	public String getCourse() {
		return course;
	}

	public String getFile() {
		return fileName;
	}

	public void setCourse(String crse) {
		course = crse;
	}

	public void setFile(String file) {
		fileName = file;
	}

	public String toString() {
		return course + " " + fileName;
	}

	public boolean equals(Course aCourse) {
		return aCourse != null &&
		course.equals(aCourse.getCourse()) &&
		fileName.equals(aCourse.getFile());
	}
}