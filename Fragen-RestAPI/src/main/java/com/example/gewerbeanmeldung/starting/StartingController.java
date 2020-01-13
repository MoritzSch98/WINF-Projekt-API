package com.example.gewerbeanmeldung.starting;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(path = "")
public class StartingController {

	@Autowired
	private StartingService sService;
	// Lists all Questions
		@RequestMapping(path = "/form/{form_id}/category/{category_id}/starting/add")
		public String addStarting(@RequestBody Starting starting, @PathVariable Integer form_id, @PathVariable Integer category_id) {
			return sService.addStarting(starting, form_id, category_id);
		}
}
