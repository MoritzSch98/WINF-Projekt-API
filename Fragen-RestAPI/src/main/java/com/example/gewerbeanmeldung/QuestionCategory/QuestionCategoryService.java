package com.example.gewerbeanmeldung.QuestionCategory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gewerbeanmeldung.Question.Question;
//The logic behind the question Category requests is defined here
@Service
public class QuestionCategoryService {

	@Autowired
	private QuestionCategoryRepository questionCategoryRepo;

	//delete all categories in a list
	public void deleteAllCategories(List<QuestionCategory> qc) {
		questionCategoryRepo.deleteAll(qc);
	}

	//get question from category
	public List<Question> getQuestionByCategory(String category) {
		QuestionCategory qc = questionCategoryRepo.findByCategory(category);
		return qc.getQuestions();
	}
	//find category by name
	public QuestionCategory getByCategoryName(String category) {
		return questionCategoryRepo.findByCategory(category);	
	}
	
	//list of questions through category id
	public List<Question> getQuestionByCategoryId(Integer id) {
		QuestionCategory qc = getCategoryById(id);
		return qc.getQuestions();
	}

	//list of all categories
	public List<QuestionCategory> getAllCategories() {
		List<QuestionCategory> qcs = new ArrayList<>();
		questionCategoryRepo.findAll().forEach(qcs::add);
		
		return qcs;
	}
	//find category by id
	public QuestionCategory getCategoryById(Integer id) {
		return questionCategoryRepo.findById(id).orElse(null);
	}
	//delete a category by id
	public String deleteQuestionCategoryById(Integer id){
		questionCategoryRepo.deleteById(id);
		return "deleted category";
	}
	
	//check if category exists by name
	public boolean existsByCategoryName(String category) {
		QuestionCategory qc = questionCategoryRepo.findByCategory(category);
		if(qc != null){
			return true;
		}
		return false;
	}
	//delete a category, if there is just one question, which wants to be deleted soon, exists. So we 
	//completely delete the category to save some space
	public List<QuestionCategory> deleteWhenNoMoreExisting(List<QuestionCategory> qcList) {
		List<QuestionCategory> deleteQcs = new ArrayList<QuestionCategory>();
		for(int i = 0; i < qcList.size(); i++) {
		Integer id = qcList.get(i).getId();
		QuestionCategory qc = getCategoryById(id);
		if(qc.getQuestions().size() == 1) {
			questionCategoryRepo.deleteById(id);
			deleteQcs.add(qc);
			}
		}
		return deleteQcs;
	}
}
