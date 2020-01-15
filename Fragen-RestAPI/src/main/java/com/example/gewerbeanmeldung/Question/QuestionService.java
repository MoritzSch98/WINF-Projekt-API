package com.example.gewerbeanmeldung.Question;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Answers.AnswersService;
import com.example.gewerbeanmeldung.Choices.Choices;
import com.example.gewerbeanmeldung.Choices.ChoicesService;
import com.example.gewerbeanmeldung.QuestionCategory.QuestionCategory;
import com.example.gewerbeanmeldung.QuestionCategory.QuestionCategoryService;
import com.example.gewerbeanmeldung.form.Form;
import com.example.gewerbeanmeldung.form.FormService;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepo;

	@Autowired
	private ChoicesService choiceService;

	@Autowired
	private QuestionCategoryService questionCategoryService;
	
	@Autowired
	private FormService formService;
	@Autowired
	private AnswersService aService;

	// Lists all questions
	public List<Question> getAllQuestions() {
		List<Question> frageList = new ArrayList<>();
		questionRepo.findAll().forEach(frageList::add);
		return frageList;
	}

	// Lists all questions of a specific FormType
	public List<Question> getByFormType(Integer form_id) {
		List<Question> frageList = new ArrayList<>();
		String formType = formService.getFormById(form_id).getFormname();
		System.out.println(formType);
		questionRepo.findByFormType(formType).forEach(frageList::add);
		;
		return frageList;
	}

	// Saves a Question
	public String saveQuestion(Question question) {
		try {
			for(int i = 0; i < question.getQuestionCategories().size(); i++) {
				if(questionCategoryService.existsByCategoryName(question.getQuestionCategories().get(i).getCategory())) {
					question.getQuestionCategories().set(i, questionCategoryService.getByCategoryName(question.getQuestionCategories().get(i).getCategory())); 
				}
			}
			//Save question
			questionRepo.save(question);
			//Add the formtype to form entity, if not existing
			formService.addWhenNotExisting(question.getFormType());
				
		}catch(Exception e) { 
			if(question.getQuestion() == null) {
				return "Your Question needs to have a Question Name";
			}
			if(question.getQuestionCategories() == null) {
				return "Your Question needs to have a Category!";
			}
			if(question.getFormType() == null) {
				return "Your Question needs to have a form-type";
			}
			if(question.getQuestionCategories()!= null) {
				for(int i = 0; i < question.getQuestionCategories().size(); i++) {
					if(question.getQuestionCategories().get(i).getCategory() == null) {
						return "You need to add a category name!";
					}
				}
			}
			if(question.getQuestionType() == null) {
				return "You have to define a QuestionType";
			}
			if(question.getQuestionType().getType() == null) {
				return "You have to define a QuestionType and the Typename especially";
			}
			if(question.getQuestionType().getChoices() != null) {
				for(int i = 0; i < question.getQuestionType().getChoices().size(); i++) {
					if(question.getQuestionType().getChoices().get(i).getChoice() == null) {
						return "If you add any Choices, the Choicename can't be null";
					}
				}
			}
	
		}
		return "This question with Questionname: "+ 
		question.getQuestion() + " was added successfully";
	}

	// Gets a specific Question by it's id
	public Question getQuestionById(Integer id) {
		return questionRepo.findById(id).orElse(null);
	}

	// Edits a specific Question
	public Question editQuestion(Question question, Integer id) {

		question.setId(id);
		question.getQuestionType().setId(id);
		
		// Gets all old Data of the Question, which is going to be updated
		Question q = getQuestionById(id);

		//Check if we need to edit the form and formtype
		if(!(question.getFormType().equals(q.getFormType()))){
			formService.editForm(question.getFormType(), q.getFormType());
		}
		
		//Check if Lookback exists in already saved data, if yes, we need to add it
		if(question.getLookbackId() == 0 && q.getLookbackId() != 0) {
			question.setLookbackId(q.getLookbackId());
		}
		
		//If no new default way was chosen, use the old existing one
		if(question.getQuestionType().getDefaultWay() == 0) {
			question.getQuestionType().setDefaultWay(q.getQuestionType().getDefaultWay());;
		}
		
		// Sets all Ids of the old Choices from the Question to the probably
		// new choices of new question. So we don't create new Choices, but update
		// existing
		List<Choices> c = q.getQuestionType().getChoices();
		Integer size = c.size();
		List<Choices> delete_choices_candidates = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (question.getQuestionType().getChoices() != null && question.getQuestionType().getChoices().size() > i) {
				question.getQuestionType().getChoices().get(i).setId(c.get(i).getId());
				if(question.getQuestionType().getChoices().get(i).getNextQuestionId() == 0)
				question.getQuestionType().getChoices().get(i).setNextQuestionId(c.get(i).getNextQuestionId());
			} else {
				delete_choices_candidates.add(c.get(i));
			}
		}

		// Get the QuestionCategory IDs from the old instance of the Question and
		// overtakes these for the new QuestionCategory. So we will update this instance
		// and not creating a new one
		List<QuestionCategory> qc = q.getQuestionCategories();
		Integer size2 = qc.size();
		List<QuestionCategory> delete_question_category_candidates = new ArrayList<>();
		for (int i = 0; i < size2; i++) {
			if (question.getQuestionCategories() != null && question.getQuestionCategories().size() > i) {
				question.getQuestionCategories().get(i).setId(qc.get(i).getId());
			} else {
				delete_question_category_candidates.add(qc.get(i));
			}
		}
		question = questionRepo.save(question);
		choiceService.deleteAllChoices(delete_choices_candidates);
		questionCategoryService.deleteAllCategories(delete_question_category_candidates);

		return question;
	}

	// Deletes a question by it's ID
	public String deleteQuestionById(Integer id) {
		try{
			//Delete also the answers which belong to this QuestionId
			aService.deleteAllAnswersOfQuestionId(id);
			//Gets list of all empty categories after this question is going to be removed
			//We have to get List first, cause of cascading we can't directly delete category or question
			//with category directly
			List<QuestionCategory> qcList = getQuestionById(id).getQuestionCategories();
			qcList = questionCategoryService.deleteWhenNoMoreExisting(qcList);	
			
			//Deleting the question now, this is also deleting the row in relation table between question 
			//and category
			questionRepo.deleteById(id);
			
			//Deleting all categories, which we found as empty
			questionCategoryService.deleteAllCategories(qcList);
			return "Success in deleting this Question";
		}catch(Exception e) {
			return "Failed to delete this Question. Reason: "+e;
		}
		
	}

	// Gets all Questions of a specific FormType and which belong to a specific
	// category
	public List<Question> getAllQuestionsOfFormTypeWithinCategory(Integer form_id, Integer category_id) {
		List<Question> formTypeQuestions = getByFormType(form_id);
		String category = questionCategoryService.getCategoryById(category_id).getCategory();
		List<Question> output = new ArrayList<>();
		for (int i = 0; i < formTypeQuestions.size(); i++) {
			List<QuestionCategory> qc = formTypeQuestions.get(i).getQuestionCategories();
			int j = 0;
			while (j < qc.size()) {
				if (qc.get(j).getCategory().equals(category)) {
					output.add(formTypeQuestions.get(i));
					break;
				}
				j++;
			}
		}
		return output;
	}

	// Updates the Question by putting the number for the next question on the right
	// position
	public String addFollowingQuestion(Integer id, Question question) {
		
		//Check if the input is allowed or we face an invalid input
		if (question.getQuestionType().getChoices() != null && question.getQuestionType().getChoices().size() > 1) {
			for (int i = 0; i < question.getQuestionType().getChoices().size(); i++) {
				if (!(question.getQuestionType().getChoices().get(i).getNextQuestionId() != 0)) {
					return "You need to add a Following Question for every Choice!";
				}
			}
			//If every existing choice has a NextId and choices exist, we wanna use the choices answer as 
			//path for following question
			question.getQuestionType().setUseDefault(false);
		}
		
		//Set useDefaultWay to true, if default way exists and no choices 
		if(question.getQuestionType().getDefaultWay() != 0 && question.getQuestionType().getChoices() == null) {
			question.getQuestionType().setUseDefault(true);
		}
		
		this.editQuestion(question, id);
		return "You added the following questions for this question sucessfully. Please note: if you have "
				+ "any choices, the NextQuestionId of the QuestionType was set to -1";
	}
	
	//Returning if we have to choose default way or the way of a choice as next way after the answer
	public boolean checkWhichWay(Question question) {
		return question.getQuestionType().isUseDefault();
	}
	
	
	public boolean checkIfQuestionExists(Integer question_id) {
		return questionRepo.existsById(question_id);
	}
	
	public boolean existsQuestionByFormType(String formtype) {
		return questionRepo.existsQuestionByFormType(formtype);
	}
}


