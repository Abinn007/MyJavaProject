package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizResult;
import view.Main;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Optional;

/**
 * In deze klasse kan de student een quiz maken.
 * Daarbij wordt bijgehouden hoeveel vragen er correct en slaat het deze op in QuizResult
 *
 * @author Wesley Wong
 */

public class FillOutQuizController {

    public static final String BEEINDIGEN = "Je hebt het einde van deze quiz bereikt, wil je het beeindigen?";

    private AnswerDAO answerDAO;
    private QuizResultDAO quizresultDAO;
    private QuestionDAO questionDAO;
    private Quiz currentQuiz;
    private StringBuilder sb = new StringBuilder();

    private ArrayList<Question> allQuestions = new ArrayList<>(); // slaat alle vragen op

    private ArrayList<Boolean> registratieAntwoorden = new ArrayList<>(); // slaat op of de antwoord goed was

    private int vraagNummer = 0; // houdt de vraag waarop de gebruiker zit bij

    private int aantalGoedeAntwoorden = 0;

    private Answer antwoordEen;
    private Answer antwoordTwee;
    private Answer antwoordDrie;
    private Answer antwoordVier;

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;

    /**
     * De setup maakt connectie met de DAOs en haalt de antwoorden van de huidige quiz op
     *
     * @param quiz geslecteerde quiz uit "SelectQuizForStudentController"
     */

    public void setup(Quiz quiz) {
        openDBAccess();

        currentQuiz = quiz;

        // haal alle vragen op van quiz
        allQuestions = questionDAO.getAllFromQuizId(currentQuiz.getIdQuiz());

        // initialiseerd eerste vraag
        getQuestionsAndAnswers(vraagNummer);

        // initaliseerd correcteAntwoorden, dat het aantal correcte antwoorden bijhoudt
        registratieAntwoorden.add(vraagNummer, false);
    }

    /**
     * Haalt vraag op en bijbehordende antwoorden en vult daarna het scherm met de vraag en antwoorden.
     *
     * @param vraagNummer       teller dat bijhoudt in welke vraag de gebruiker zit
     */
    private void getQuestionsAndAnswers(int vraagNummer) {

        // check of we al bij het einde zijn
        if (vraagNummer < allQuestions.size()) {

            // voeg een index to aan correcteAntwoorden als deze nog niet bestaat om de antwoord in op te slaan
            increaseSizeCorrecteAntwoorden(vraagNummer);

            // set Title met vraagnummer & reset veld
            titleLabel.setText(String.format("Vraag %d", vraagNummer + 1));
            sb.setLength(0);

            // haal volgende vraag op en zet in StringBuilder
            Question currentQuestion = allQuestions.get(vraagNummer);
            sb.append(String.format("%s\n", currentQuestion.getTextQuestion()));

            // haal antwoorden van de vraag op & shuffled deze
            ArrayList<Answer> allAnswers = answerDAO.getAllByQuestionid(currentQuestion.getIdQuestion());
            Collections.shuffle(allAnswers);

            // zet de antwoorde op het scherm en ken ze toe aan de knoppen
            setAndShowAnswers(allAnswers);

        } else {
            // als we bij het einde zijn, bereken resultaat en sla op in database en ga naar het volgende scherm
            berekenResultaatEnSlaOp(registratieAntwoorden);
            Main.getSceneManager().showStudentFeedback(currentQuiz);
        }


    }

    /**
     * Voegt een index to aan correcteAntwoorden als deze nog niet bestaat om de antwoord in op te slaan
     *
     * @param vraagNummer       vraagNummer waarbij eventueel een index aangemaakt moet worden
     */
    private void increaseSizeCorrecteAntwoorden(int vraagNummer) {
        // dit voorkomt dat er niet meer indexen in correctAntwoorden kan zitten dan vragen in de quiz
        if (registratieAntwoorden.size() == vraagNummer) {
            {
                registratieAntwoorden.add(vraagNummer, null);
            }
        }
    }

    /**
     * Laat de antwoorden zien en wijst ze toe aan de knoppen
     *
     * @param allAnswers        alle antwoorden die horen bij de vraag
     */
    private void setAndShowAnswers(ArrayList<Answer> allAnswers) {
        // zet alle antwoorden op het scherm
        for (int i = 0; i < allAnswers.size(); i++) {
            char ch = (char) ('A' + i);
            sb.append(String.format("\n%s: %s", ch, allAnswers.get(i)));
        }
        questionArea.setText(sb.toString());

        antwoordEen = allAnswers.get(0);
        antwoordTwee = allAnswers.get(1);
        antwoordDrie = allAnswers.get(2);
        antwoordVier = allAnswers.get(3);
    }

    /**
     * Deze functie telt alle juiste antwoorden op en vergelijkt het met de cesuur.
     * Als de gebruiker geslaagd is wordt dit opgeslagen
     */
    private void berekenResultaatEnSlaOp(ArrayList<Boolean> correcteAntwoorden) {
        // tel alle juiste antwoorden op
        for (Boolean b : correcteAntwoorden) {
            if (b) {
                aantalGoedeAntwoorden++;
            }
        }
        // vergelijk aantal goede antwoorden met cesuur en sla op met de uitslag
        if (currentQuiz.getSuccesdefinitie() <= aantalGoedeAntwoorden) {
            QuizResult qr = quizresultDAO.getLatestResult(currentQuiz.getIdQuiz(), SessionController.currentUser.getIdUser());
            qr.setPassed(true);
            quizresultDAO.updateQuizResult(qr);
        }
    }

    /**
     * Deze functie roept de volgende vraag aan en wordt gebruikt de antwoordknoppen en "volgende knop"
     * Hij houdt ook bij of alle vragen al geweest zijn.
     */
    private void volgendeVraag() {
        // telt vraagNummer door
        vraagNummer = vraagNummer + 1;

        // check of je bij de laatste vraag bent
        if (vraagNummer != allQuestions.size()) {
            // zo nee, volgende vraag
            getQuestionsAndAnswers(vraagNummer);
        } else {
            confirmationAlert();
        }
    }

    /**
     * Vraagt bevestiging of je klaar bent met de quiz als je alle vragen ingevuld hebt
     */
    private void confirmationAlert() {
        // maak alert aan
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Einde quiz");
        alert.setHeaderText("Bevestiging");
        alert.setContentText(BEEINDIGEN);

        Optional<ButtonType> result = alert.showAndWait();
        // als je op okay klikt wordt vraagNummer verhoogd, wat het einde triggered in getQuestionsAndAnswers
        if (result.orElse(null) == ButtonType.OK) {
            vraagNummer = vraagNummer + 1;
            getQuestionsAndAnswers(vraagNummer);
        } else {
            vraagNummer = vraagNummer - 1;

        }
    }

    /**
     * Alle buttons om antwoord te geven
     */
    public void doRegisterA() {
        // check of het correct is
        if (antwoordEen.isCorrect()) {
            // hier zet hij een true in de correcteAntwoorden ArrayList op de plek van de vraag
            registratieAntwoorden.set(vraagNummer, true);
        } else {
            registratieAntwoorden.set(vraagNummer, false);
        }
        volgendeVraag();
        System.out.println(registratieAntwoorden);
    }

    public void doRegisterB() {
        if (antwoordTwee.isCorrect()) {
            registratieAntwoorden.set(vraagNummer, true);
        } else {
            registratieAntwoorden.set(vraagNummer, false);
        }
        volgendeVraag();
        System.out.println(registratieAntwoorden);
    }

    public void doRegisterC() {
        if (antwoordDrie.isCorrect()) {
            registratieAntwoorden.set(vraagNummer, true);
        } else {
            registratieAntwoorden.set(vraagNummer, false);
        }
        volgendeVraag();
        System.out.println(registratieAntwoorden);
    }

    public void doRegisterD() {
        if (antwoordVier.isCorrect()) {
            registratieAntwoorden.set(vraagNummer, true);
        } else {
            registratieAntwoorden.set(vraagNummer, false);
        }
        volgendeVraag();
        System.out.println(registratieAntwoorden);
    }

    /**
     * Overige buttons
     */
    public void doNextQuestion() {
        vraagNummer = vraagNummer + 1;
        getQuestionsAndAnswers(vraagNummer);
    }

    public void doPreviousQuestion() {
        if (vraagNummer > 0) {
            vraagNummer = vraagNummer - 1;
            getQuestionsAndAnswers(vraagNummer);
        }
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene();
    }

    /**
     * Opent DBAccess voor relevante DAOs
     */
    private void openDBAccess() {
        DBAccess dbAccess = Main.getDBaccess();
        this.questionDAO = new QuestionDAO(dbAccess);
        this.answerDAO = new AnswerDAO(dbAccess);
        this.quizresultDAO = new QuizResultDAO(dbAccess);
        dbAccess.openConnection();
    }

}
