/* 
    Assignment 5: Question 3
    CPS 610 – Database Systems II - Winter 2021
    Wednesday Section – Group 13
    Member #1: Andy Lee (500163559)
    Member #2: Sohrab Soltani (500801172)
*/

db.Professors.insert(
  {
    ProfFirstName: "Ilkka",
    ProfLastName: "Kokkarinen",
    ProfPhone: "416-888-8881",
    Faculty: "Science",
    CanTeach: [
      {
        CourseNo: "CPS 109",
        Term: "Fall 2021",
        Preference: "CPS 209"
      },
      {
        CourseNo: "CPS 209",
        Term: "Fall 2021",
        Preference: "CPS 721"
      },
    ],
    Teaches: [
      {
        CourseNo: "CPS 721",
        Term: "Winter 2021",
      },
      {
        CourseNo: "CPS 616",
        Term: "Winter 2021",
      },
    ],
});


db.Professors.insert(
  {
    ProfFirstName: "Alex",
    ProfLastName: "Ufkes",
    ProfPhone: "416-888-8882",
    Faculty: "Science",
    CanTeach: [
      {
        CourseNo: "CPS 213",
        Term: "Fall 2021",
        Preference: "CPS 310"
      },
      {
        CourseNo: "CPS 310",
        Term: "Fall 2021",
        Preference: "CPS 616"
      },
    ],
    Teaches: [
      {
        CourseNo: "CPS 616",
        Term: "Winter 2021",
      },
      {
        CourseNo: "CPS 506",
        Term: "Winter 2021",
      },
    ],
});


db.Professors.insert(
  {
    ProfFirstName: "Kunquan",
    ProfLastName: "Lan",
    ProfPhone: "416-888-8883",
    Faculty: "Mathematics",
    CanTeach: [
      {
        CourseNo: "MTH 110",
        Term: "Fall 2021",
        Preference: "MTH 217"
      },
    ],
    Teaches: [
      {
        CourseNo: "MTH 110",
        Term: "Winter 2021",
      },
      {
        CourseNo: "MTH 217",
        Term: "Winter 2021",
      },
    ],
});




db.Professors.insert(
  {
    ProfFirstName: "Sophie",
    ProfLastName: "Quigley",
    ProfPhone: "416-888-8884",
    Faculty: "Science",
    CanTeach: [
      {
        CourseNo: "CPS 420",
        Term: "Fall 2021",
        Preference: "CPS 721"
      },
    ],
    Teaches: [
      {
        CourseNo: "MTH 420",
        Term: "Winter 2021",
      }
    ],
});



db.Professors.insert(
  {
    ProfFirstName: "Abdolreza",
    ProfLastName: "Abhari",
    ProfPhone: "416-888-8885",
    Faculty: "Science",
    CanTeach: [
      {
        CourseNo: "CPS 510",
        Term: "Fall 2021",
        Preference: "CPS 610"
      },
      {
        CourseNo: "CPS 610",
        Term: "Winter 2021",
        Preference: "CPS 610"
      },
    ],
    Teaches: [
      {
        CourseNo: "CPS 610",
        Term: "Winter 2021",
      }
    ],
});