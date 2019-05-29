sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

# change the java file name for every project
run:
	java GradeCalculator 

clean :
	rm -f *.class

%.class : %.java
	javac $<

