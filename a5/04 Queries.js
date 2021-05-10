/* 
    Assignment 5: Question 5 - 10
    CPS 610 – Database Systems II - Winter 2021
    Wednesday Section – Group 13
    Member #1: Andy Lee (500163559)
    Member #2: Sohrab Soltani (500801172)
*/

/* Snippents */
/*

  https://docs.mongodb.com/manual/reference/mongo-shell/
  show dbs;
  use CPS610-A5;
  db.Students.remove({});
  db.Professors.remove({});
  db.Courses.remove({});

  https://docs.mongodb.com/manual/tutorial/query-arrays/
  db.Students.findOne({ Enrolled: { $elemMatch: {Term: "Fall 1998"} } });

 */

/* Q5 */
db.Students.find();
db.Professors.find();
db.Courses.find();

/* Q6 */
db.Students.findOne({ StudentLastName: "Lee" });
db.Students.findOne({ StudentLastName: "Soltani" });

db.Professors.findOne({ ProfLastName: "Kokkarinen" });
db.Professors.findOne({ ProfLastName: "Abhari" });

/* Q7 */
db.Students.aggregate({ $sort: { "Enrolled.Term": -1 } }, { $limit: 5 });

/* Q8 */
db.Students.update(
  { _id: db.Students.findOne({ StudentLastName: "Lee" })._id },
  {
    $set: {
      StudentNo: "500163559",
    },
  }
);

db.Students.update(
  { _id: db.Students.findOne({ StudentLastName: "Soltani" })._id },
  {
    $set: {
      StudentNo: "500801172",
    },
  }
);

/* Q9 */
db.Students.update(
  { Faculty: "Science" },
  {
    $set: {
      Faculty: "Arts",
    },
  },
  { multi: true }
);


/* Q10 */
db.Courses.find({ CourseDescription: /advanced/i });