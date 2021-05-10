/* 
    Assignment 5: Question 4
    CPS 610 – Database Systems II - Winter 2021
    Wednesday Section – Group 13
    Member #1: Andy Lee (500163559)
    Member #2: Sohrab Soltani (500801172)
*/


db.Courses.insert({
  CourseNo: 109,
  CourseName: "Computer Science I",
  CourseDescription:
    "An introductory programming course designed to introduce fundamental " +
    "Computer Science concepts such as abstraction, modelling and algorithm design. " +
    "Emphasis is placed on producing correct software.",
  Faculty: "Science",
});

db.Courses.insert({
  CourseNo: 310,
  CourseName: "Computer Organizartion II",
  CourseDescription:
    "A continuation of CPS 213. Memory; CPU architecture and instruction set; " +
    "the instruction processing sequence; generic assembler level programming " +
    "illustrated for specific CPUs; I/O essentials including interrupts and DMA; " +
    "characteristics of major peripherals interfaces; RISC and CISC architectures compared; " +
    "parallel processing. The laboratory requires using a specific assembler/editor for the " +
    "creation of programs illustrating some of the principles discussed in lectures.",
  Faculty: "Science",
});

db.Courses.insert({
  CourseNo: 616,
  CourseName: "Algorithms",
  CourseDescription:
    "Complexity analysis and order notations, recurrence equations, brute force, " +
    "advanced divide-and-conquer techniques and the master theorem, transform-and-conquer and " +
    "problem reduction, greedy method, dynamic programming, the knapsack and travelling " +
    "salesman problems, graph algorithms, text processing and pattern matching techniques, P, NP, and NP-complete classes.",
  Faculty: "Science",
});


db.Courses.insert({
    CourseNo: 510,
    CourseName: "Database Systems I",
    CourseDescription:
      "Advanced file management techniques involving fundamentals of database organization, " + 
      "design and management. Emphasis is given to Relational Database Management Systems " + 
      "including relational algebra, normal Forms, physical Database Structures and their " + 
      "implementation, and Relational Database Languages. Other types of Database Managers " + 
      "are also discussed such as Hierarchical, Network and Inverted Files.",
    Faculty: "Science",
  });

  db.Courses.insert({
    CourseNo: 610,
    CourseName: "Database Systems II",
    CourseDescription:
      "This course is a continuation of CPS510. Topics include: embedded DB languages " +
      "(e.g. JDBC class libraries) and Embedded SQL, Transaction management, Distributed " +
      "Databases, Transaction Concurrency Control, Concurrency Control through " +
      "Locking/protocol and time stamps, Object-Oriented and Object-Relational " +
      "Database Systems, non-structured and NOSQL databases (e.g. Mongo DB). " + 
      "Introduction to big data management, Map-Reduce and Hadoop.",
    Faculty: "Science",
  });