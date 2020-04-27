package view;

import database.mysql.*;

/**
  *Deze launcher is om methodes te testen
 */

public class TestLauncher {

    public static void main(String[] args) {


        java.util.Date dt = new java.util.Date();

        DBAccess dBaccess = new DBAccess("Quizmaster", "userQuizmaster", "tznQuizmaster");
        GroupDAO groupDAO = new GroupDAO(dBaccess);
        RoleDAO roleDAO = new RoleDAO(dBaccess);
        UserDAO userDAO = new UserDAO(dBaccess);
        CourseDAO courseDAO = new CourseDAO(dBaccess);
        QuizDAO quizDAO = new QuizDAO(dBaccess);
        QuestionDAO questionDAO = new QuestionDAO(dBaccess);
        QuizResultDAO quizresultDAO = new QuizResultDAO(dBaccess);
        AnswerDAO answerDAO = new AnswerDAO(dBaccess);
        dBaccess.openConnection();

    }
}
