package view;
import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.util.ArrayList;

public class SceneManager {
    private Stage primaryStage;
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    // Laadt een scene
    public FXMLLoader getScene(String fxml) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }
    public void setWindowTool() {
        FXMLLoader loader = getScene("/view/fxml/windowTool.fxml");
        if (loader != null) {
            WindowToolController controller = loader.getController();
            controller.populateScreenMenu();
        } else {
            System.out.println("set windowTool: Loader is not initialized");
            System.out.flush();
        }
    }
    public void showLoginScene() {
        getScene("/view/fxml/login.fxml");
    }
    public void showWelcomeScene() {
        FXMLLoader loader = getScene("/view/fxml/welcomeScene.fxml");
        WelcomeController controller = loader.getController();
        controller.setup();
    }
    public void showManageUserScene() {
        FXMLLoader loader = getScene("/view/fxml/manageUsers.fxml");
        ManageUsersController controller = loader.getController();
        controller.setup();
    }
    public void showCreateUpdateUserScene(User user) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateUser.fxml");
        CreateUpdateUserController controller = loader.getController();
        controller.setup(user);
    }
    public void showManageCoursesScene() {
        FXMLLoader loader = getScene("/view/fxml/manageCourses.fxml");
        ManageCoursesController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateCourseScene(Course course) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateCourse.fxml");
        CreateUpdateCourseController controller = loader.getController();
        controller.setup(course);
    }

    public void showStudentProgressScene() {
        FXMLLoader loader = getScene("/view/fxml/studentProgress.fxml");
        StudentProgressController controller = loader.getController();
        controller.setup();
    }
    public void showManageGroupsScene() {
        FXMLLoader loader = getScene("/view/fxml/manageGroups.fxml");
        ManageGroupsController controller = loader.getController();
        controller.setup();
    }
    public void showCreateUpdateGroupScene(Group group) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateGroup.fxml");
        CreateUpdateGroupController controller = loader.getController();
        controller.setup(group);
    }
    public void showManageQuizScene() {
        FXMLLoader loader = getScene("/view/fxml/manageQuizzes.fxml");
        ManageQuizzesController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateQuizScene(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateQuiz.fxml");
        CreateUpdateQuizController controller = loader.getController();
        controller.setup(quiz);

    }
    public void showManageQuestionsScene() {
        FXMLLoader loader = getScene("/view/fxml/manageQuestions.fxml");
        ManageQuestionsController controller = loader.getController();
        controller.setup();
    }
    public void showCreateUpdateQuestionScene(Question question) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateQuestion.fxml");
        CreateUpdateQuestionController controller = loader.getController();
        controller.setup(question);
    }
    public void showStudentSignInOutScene() {
        FXMLLoader loader = getScene("/view/fxml/studentSignInOut.fxml");
        StudentSignInOutController controller = loader.getController();
        controller.setup();
    }
    public void showSelectQuizForStudent() {
        FXMLLoader loader = getScene("/view/fxml/selectQuizForStudent.fxml");
        SelectQuizForStudentController controller = loader.getController();
        controller.setup();
    }

    public void showFillOutQuiz(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/fillOutQuiz.fxml");
        FillOutQuizController controller = loader.getController();
        controller.setup(quiz);
    }
    public void showStudentFeedback(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/studentFeedback.fxml");
        StudentFeedbackController controller = loader.getController();
        controller.setup(quiz);
    }
    public void showCoordinatorDashboard() {
        FXMLLoader loader = getScene("/view/fxml/coordinatorDashboard.fxml");
        CoordinatorDashboardController controller = loader.getController();
        controller.setup();
    }

    /**
     * Opties binnen de WelcomeController menu
     */
    public ArrayList<String> getTaken(int idRol) {
        ArrayList<String> result = new ArrayList<>();
        switch (idRol) {
            case 1:
                //
                result.add("In- en uitschrijven voor een cursus");
                result.add("Maak een quiz");
                break;
            case 2:
                // coordinator
                result.add("Coordinator Dashboard");
                result.add("Vraagbeheer");
                result.add("Quizbeheer");
                result.add("Voortgang Studenten");
                break;
            case 3:
                //  administrator
                result.add("Cursusbeheer");
                result.add("Groepenbeheer");
                break;
            case 4:
                // teschnisch beheerder
                result.add("Gebruikersbeheer");
                break;
            case 5:
                // docent
                result.add("Voortgang Studenten");
                break;
            default:
                break;
        }
        return result;
    }

    public void showSceneBijTaak(String str) {
        switch (str) {
            case "In- en uitschrijven voor een cursus":
                Main.getSceneManager().showStudentSignInOutScene();
                break;
            case "Maak een quiz":
                Main.getSceneManager().showSelectQuizForStudent();
                break;
            case "Coordinator Dashboard":
                Main.getSceneManager().showCoordinatorDashboard();
                break;
            case "Vraagbeheer":
                Main.getSceneManager().showManageQuestionsScene();
                break;
            case "Quizbeheer":
                Main.getSceneManager().showManageQuizScene();
                break;
            case "Cursusbeheer":
                Main.getSceneManager().showManageCoursesScene();
                break;
            case "Groepenbeheer":
                Main.getSceneManager().showManageGroupsScene();
                break;
            case "Voortgang Studenten":
                Main.getSceneManager().showStudentProgressScene();
                break;
                case "Gebruikersbeheer":
                Main.getSceneManager().showManageUserScene();
            default:
                break;
        }
    }
}