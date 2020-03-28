package com.example.gewerbeanmeldung.Answers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswers;
import com.example.gewerbeanmeldung.AnswerOfAnswers.AnswerOfAnswersService;
import com.example.gewerbeanmeldung.FormFilled.FormFilled;
import com.example.gewerbeanmeldung.FormFilled.FormFilledService;
import com.example.gewerbeanmeldung.Question.Question;
import com.example.gewerbeanmeldung.Question.QuestionService;
import com.example.gewerbeanmeldung.dbfile.DatabaseFileService;
import com.example.gewerbeanmeldung.generator.PDFService;

@Service
public class AnswersService {

	@Autowired
	private AnswersRepository answerRepo;
	@Autowired 
	private FormFilledService ffService;
	@Autowired
	private AnswerOfAnswersService aoaService;
	@Autowired
	private QuestionService qService;
	@Autowired
	private DatabaseFileService dbService;
	@Autowired
	private PDFService pdfService;


//--------------------- The Get Functions ----------------------------------	
	
	//Method getting a Answers Entity by its Id, if nothing found, we return nullpointer exception
	public Answers getAnswer(Integer answerId) {
		Answers a = answerRepo.findById(answerId).orElse(null);
		if(a == null) {
			throw new NullPointerException("The answer you try to get through the PathVariable is not available"); 
		}
		
		return a;
	}
	
	//Method returning a List of Answers which all belong to one FilledForm Entity
	public List<Answers> getAllAnswers(Integer formfilled_id){
		FormFilled ff = ffService.getFilledForm(formfilled_id);
		return answerRepo.findAllByFormId(ff);		
	}

//--------------------- The Add Functions ----------------------------------	
	
	//Method adding an Answer to the Database, through the answer, formfilled_id(FormFilled id) and questionId it is
	//a unique answer for a specific FormFilled to one specific question
	public String addAnswer(Answers answer, Integer formfilled_id, Integer question_id) {
		FormFilled ff = new FormFilled();

		//Check if the input form exists, if so, we gonna set this filledForm to the answers Entity,
		//if not, we return that we couldnt perfom the adAnswer method
		if(ffService.checkFormExisting(formfilled_id)) {
			 ff = ffService.getFilledForm(formfilled_id);
			 answer.setFormFilled(ff);
		}else {
			return "The form you try to add an answer to, is not existing, please add the form first";
		}

		//We are checking if there alreay is an answer existing, if so, we return the String shown there
		if(checkAnswerForQuestionIdExisting(question_id, ff)) {
			return "this answer is alreay existing for this form. You can't add it twice, but you can edit";
		}
		//We check, if the Question itself exists. If Yes, we set the QuestionId if this answer, and we perform 
		//findAnswerType method, which is finding the answer type. If not, we return that there wasn't
		//a question to this answer
		if(qService.checkIfQuestionExists(question_id)) {
			answer.setQuestion_id(question_id);
			answer = findAnswerType(answer, question_id);
		}else {
			return "This question you try to add an answer to, is not existing!";
		}
		/*
		 * In this part we want to get all AnswerOfAnswers to this specific answer and add it. This 
		 * part is needed, cause of database relations. We first add the AnswerOfAnswers and only afterwards
		 * it is possibile to add the answer and than map the AnswerOfAnswers back to the Answers Entity which they
		 * belong to
		 */
		List<AnswerOfAnswers> aoaList = answer.getAoa();
		if(aoaList != null) {
			for(int i = 0; i < aoaList.size(); i++) {
				aoaList.get(i).setAnswers(answer);
				aoaService.addAnswerOfAnswers(aoaList.get(i));		
			}
		}
		//Trying to save answers Entity, if it goes wrong, we have the catch part with an error message	
		try {
			answerRepo.save(answer);
		}catch(Exception e) {
			return e.getMessage();
		}
		//In this part we check, if the answer to this question influences the need to display one of the following questions or not.
		//The changeQuestionFlow method is checking if needed and now returning the questionid for the next question.
		Integer nextQuestion = changesQuestionFlow(answer, question_id);
		return "You saved the answer successful, the next Question is the question with the Id: "+ nextQuestion;
	}
	
	//Adding all answers to a specific formtype, which basically is always needed, when a person fills a whole form
	//and sending it to our server.
	public String addAllAnswers(List<Answers> answers, Integer formfilled_id) {
		int i = 0;
		while(i < answers.size()) {
			String returnMessage;
			if(answers.get(i).getQuestion_id() == null) {
				returnMessage = "not savable";
			}else {
				returnMessage = addAnswer(answers.get(i), formfilled_id, answers.get(i).getQuestion_id());	
			}
			if(!(returnMessage.contains("You saved the answer successful"))) {
				undoAllSaved(answers,formfilled_id, 0, i);
				return "You cannot save this form, cause you didn't fill everything correct. Check your Inputs.";
			}
			i++;
		}
		//If we had a successfully try of adding all answers, we are generating a pdf of it. It is also being send as email to the 
		//personal email address of the input person.
		try {
			pdfService.generatePDF(formfilled_id);
		} catch (IOException e) {
			return "We saved your inputs, but there was a problem with sending the pdf of it to your mail";
		}
		return "successfully saved all inputs and we generated a pdf. It's being send to the email: gethackingyourlife@gmail.com";
	}
	
//--------------------- The Delete Functions ----------------------------------		
	
	//Deleting an Answer of a Formfilled. It is unique identified by question id and formId, which is actually 
	//formfilled_id, it will be Admin command
	public String deleteAnswerOfFormFilledByFormAndQuestion(Integer formfilled_id, Integer question_id) {
		
		//Finding the answer
		Answers a = findAnswerByQuestionIdAndFilledFormId(question_id, formfilled_id);
		//if we have a fileupload as an answer to this question, 
		//we also need to delete the file to save database space
		if(a.getAnswerType().equals("fileanswer")){
			dbService.deleteFileByAnswerId(a);
		}
		//actual delete of the Answer
		deleteAnswerOfFormFilled(a);	
		return "answer deleted";
	}
	
	//Delete all Answers of a QuestionId by looping through all answers of a questionId, this
	//is important, if we decide to delete a specific question of a form
	public String deleteAllAnswersOfQuestionId(Integer question_id) {
		
		//find all answers of a question ID
		List<Answers> aList = answerRepo.findAllByQuestionId(question_id);
		for(int i = 0; i < aList.size(); i++) {
			if(aList.get(i).getAnswerType().equals("fileanswer")){
				//if we have a fileupload as an answer to this question, 
				//we also need to delete the file to save database space
				dbService.deleteFileByAnswerId(aList.get(i));
			}
			//actual delete of the Answer
			deleteAnswerOfFormFilled(aList.get(i));
		}
		return "You deleted all Answers which have been belonging to the questionId: " + question_id;
	}
	
	//Deleting an Answer of a Formfilled is the actual delete command through the answer entity as input
	public String deleteAnswerOfFormFilled(Answers answer) {
	
		//deleting all answerOfAnswers to this answer. There exists at least one, if we are not having a 
		//file or dateanswer. Also if there is just a single answer to a question its displayed in AOA to
		//be able to also choose multiple answers
		//we need to delete aoa seperately, cause of database relations
		for(int i = 0; i < answer.getAoa().size(); i++) {
			aoaService.deleteAnswerOfAnswers(answer.getAoa().get(i));
		}
		//if we have a fileupload as an answer to this question, 
		//we also need to delete the file to save database space
		//some bugs make it necessary, that here we also need the file deleting
		if(answer.getAnswerType().equals("fileanswer")){
			dbService.deleteFileByAnswerId(answer);
		}
		//finally deleting the answer
		answerRepo.delete(answer);
		
		return "answer deleted";
	}
	
	//Delete all Answers just loops through deleting answer process
	public String deleteAllAnswersOfFormFilled(Integer formfilled_id) {
		List<Answers> a = getAllAnswers(formfilled_id);
		for(int i = 0; i < a.size(); i++) {
			deleteAnswerOfFormFilled(a.get(i));
		}
		return "deleted all";
	}
	
	//This method is used, if we couldn't save all the answers out of some reason. We then want all already 
	//made inserts to be deleted. We can also delete all Answers of a form with this function
	private void undoAllSaved(List<Answers> answers, int formfilled_id, int start, int end) {
		for(int i = start; i < end; i++) {
			answers.get(i).setFormFilled(ffService.getFilledForm(formfilled_id));
			deleteAnswerOfFormFilled(answers.get(i));
		}
	} 
	
//--------------------- The Edit Functions ----------------------------------	
	
	//Edit all answers of a specific filled form. When a user wants to change his inputs
	public String editAllAnswers(List<Answers> answers, Integer formfilled_id) {
		int i = 0;
		while(i < answers.size()) {
			String returnMessage;
			//If there is a question id in it, which is not existing, we want to return error. Usually it
			//can't be possible but we had this issue in checking once, so we made this part of code. 
			if(answers.get(i).getQuestion_id() == null) {
				returnMessage = "not editable";
			
			// getting the formfilled, getting the answers entity and starting 
			//the updateanswer for each answers entity
			}else {
				FormFilled ff = ffService.getFilledForm(formfilled_id);
				Answers a = answerRepo.findByQuestionId(answers.get(i).getQuestion_id(), ff);
				returnMessage = updateAnswer(answers.get(i), formfilled_id, answers.get(i).getQuestion_id(), a.getId());	
			}
			//we edit everything, sometimes an answer is missing or is optional 
			//and now empty, then we have to undo all old ones
			if(!(returnMessage.contains("You edited the answer successful"))) {
				undoAllSaved(answers,formfilled_id, 0, i);
				return returnMessage;
			}
			
			i++;
		}
		
		return "successfully edited all inputs";
	}

	
	//Method updating a single answer
	public String updateAnswer(Answers answer, Integer formfilled_id, Integer question_id, Integer answer_id) {
		FormFilled ff = new FormFilled();

		//checking if formfilled exists
		if(ffService.checkFormExisting(formfilled_id)) {
			 ff = ffService.getFilledForm(formfilled_id);
			 answer.setFormFilled(ff);
		}else {
			return "The form you try to add an answer to, is not existing, please add the form first";
		}
		//check, if question exists
		if(qService.checkIfQuestionExists(question_id)) {
			answer.setQuestion_id(question_id);
			answer = findAnswerType(answer, question_id);
		}else {
			return "This question you try to add an answer to, is not existing!";
		}
		//getting all aoa's that we can update them as well. We need to do this due to database constraint violations
		List<AnswerOfAnswers> aoaList = updateAnswerOfAnswersFromAnswer(answer, answer_id);
		//setting answerId, that we can safe afterwards at the right place into the database and not as
		//new entity
		answer.setId(answer_id);
		try {
			answerRepo.save(answer);
		}catch(Exception e) {
			return e.getMessage();
		}
		//Sets the answerOfAnswers
		answer.setAoa(aoaList);
		
		//In this part we check, if the answer to this question influences the need to display one of the following questions or not.
		//The changeQuestionFlow method is checking if needed and now returning the questionid for the next question.
		Integer nextQuestion = changesQuestionFlow(answer, question_id);
		return "You edited the answer successful, the next Question is the question with the Id: "+ nextQuestion;
		
	}
	
	//update AnswerOfAnswers of a answer 
	public List<AnswerOfAnswers> updateAnswerOfAnswersFromAnswer(Answers answer, Integer answer_id) {
		//saving new answerOfAnswers
		List<AnswerOfAnswers> aoa_new = answer.getAoa();
		//saving old answerOfAnswers
		List<AnswerOfAnswers> aoa_old = aoaService.getAllAnswerOfAnswersByAnswerId(answer_id);

		//Checks if the amount of aoa changed after edit and then adjusting it corrctly by removing old,
		//or update old if necessary. 
		int newSize = aoa_new.size();
		int oldSize = aoa_old.size();
		int removeCount = 0;
		int i = 0;
		while(i < newSize-removeCount) {
			int j = 0;
			while(j < oldSize-removeCount) {
				if(aoa_new.get(i).getAnswer().equals(aoa_old.get(j).getAnswer())){
					aoa_old.remove(j);
					aoa_new.remove(i);			
				}
				j++;
			}
			i++;
		}	
		//If sizes are equivalent, than we dont need to go into if or else.
		while(aoa_new.size() != 0 || aoa_old.size() != 0) {
			aoa_old.get(0).setAnswer(aoa_new.get(0).getAnswer());
			aoaService.editAnswerOfAnswers(aoa_old.get(0));
			aoa_old.remove(0);
			aoa_new.remove(0);
		}
		//if we have more aoa_new than old ones
		if (aoa_new.size() != 0) {
			for(int x = 0; x < aoa_new.size(); x++) {
				aoaService.addAnswerOfAnswers(aoa_new.get(x));
			}
		//if we have more aoa_old than new ones	
		}else if(aoa_old.size() != 0){
			for(int x = 0; x < aoa_old.size(); x++) {
				aoaService.deleteAnswerOfAnswers(aoa_old.get(x));
			}
		}
		return aoaService.getAllAnswerOfAnswersByAnswerId(answer_id);
		
	}

	
//--------------------- The Helping Functions ----------------------------------	
	
	//This method is going to set the correct answertype of the question and if some other answers are in it as well,
	//it is removing them
	public Answers findAnswerType(Answers answer, Integer question_id) {
		
		String answerType = "normal";
		
		//Gets the Type of the Question to look which answer type we have to set
		qService.getQuestionById(question_id).getId();
		String type = qService.getQuestionById(question_id).getQuestionType().getType();
		
		//Searches which answertype is right and sets it
		if(type.equals("Datum")) {
			answerType = "dateanswer";
		}else if(type.equals("Datei-Upload")) {
			answerType = "fileanswer";
		}
		answer.setAnswerType(answerType);
		removeIrrelevantAnswertypes(answer);
		
		return answer;
	}
	
	
	//Removes not needed answers for not needed answertype
	public Answers removeIrrelevantAnswertypes(Answers a) {
		String answerType = a.getAnswerType();
		if(answerType.equals("fileanswer")) {
			if(a.getAoa() != null) {
				a.setAoa(null);
			}
			if(a.getDateanswer() != null) {
				a.setDateanswer(null);
			}
		}else if(answerType.equals("dateanswer")) {
			if(a.getAoa() != null) {
				a.setAoa(null);
			}
			if(a.getFileanswer() != null) {
				a.setFileanswer(null);
			}
		}else {
			a.setFileanswer(null);
			a.setDateanswer(null);
		}
		
		return a;
	}
	
	//Checks if answer exists for a question id and formfilled combination
	public boolean checkAnswerForQuestionIdExisting(Integer question_id, FormFilled ff) {
		Answers a = answerRepo.findByQuestionId(question_id, ff);
		if(a != null && a.getFormFilled().equals(ff)){
			return true;
		}else {
			return false;
		}
	}
	
	
	//Method returns the answer by question id and ff id
	public Answers findAnswerByQuestionIdAndFilledFormId(Integer question_id, Integer formfilled_id) {
		FormFilled ff = ffService.getFilledForm(formfilled_id);
		return answerRepo.findByQuestionId(question_id, ff);
	}
	
	//Looks if the question flow is changed and returns the next questionid, 
	//which needs to be displayed
	public Integer changesQuestionFlow(Answers a, Integer question_id) {
		
		Integer nextQuestion = null;
		Question q = qService.getQuestionById(question_id);
		
		//Checks if we need to look for the NextQuestionId within the chosen choice
		//Indirectly we now know that this question is a question, where we can chose only one
		//out of many options. Multi select or Date or File upload is not allowed
		//so we just need to look at the very first element of aoa within the answer to get the made input
		//also its not a free text input. Has to be Dropdown or RadioButton
		if(!(q.getQuestionType().isUseDefault())) {
			String input = a.getAoa().get(0).getAnswer();
			int i = 0;
			
			//Try to find the matching choice for the answer and saves the nextQuestion of it, 
			//to be returned soon
			while(i < q.getQuestionType().getChoices().size()) {
				if(q.getQuestionType().getChoices().get(i).getChoice().equals(input)) {
					nextQuestion = q.getQuestionType().getChoices().get(i).getNextQuestionId();
				}
				i++;
			}
		}else {
			nextQuestion = q.getQuestionType().getDefaultWay();
		}
		
		return nextQuestion;
	}

}