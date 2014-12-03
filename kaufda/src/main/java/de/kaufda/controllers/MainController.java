package de.kaufda.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.kaufda.common.Utils;
import de.kaufda.dao.EntryDao;
import de.kaufda.dao.UserDao;
import de.kaufda.model.Entry;
import de.kaufda.model.User;

/**
 * Principal Controller for the index action "/" and the Blogs navigation to my blog "/blog"
 * and others blog "/blog/{userId}".
 *
 * @author Javier
 */
@Controller
public class MainController {

	/**
	 * Main Controller that loads the entries by date (desc)
	 * @return index page with all of the entries
	 */
	@RequestMapping("/")
	public ModelAndView main() {

		return new ModelAndView("/index", "entries", getEntries(null));
	}

	/**
	 * Loads the blog of the Security Loaded User
	 * @return myBlog(MyUserId)
	 */
	@RequestMapping("/blog")
	public ModelAndView myBlog() {
		
		return myBlog(Utils.getMyUserId());
	}

	/**
	 * Loads the blog of the given User
	 * @param userId Id of the User we want to load the blog of
	 * @return main page when incorrect user, blog of the user with edition=true when we are owner or administrator
	 * otherwise
	 */
	@RequestMapping("/blog/{userId}")
	public ModelAndView myBlog(final @PathVariable("userId") Integer userId) {

		final ModelAndView result;
		final User user = new UserDao().find(userId);
		// If the userId does not exist, reeturn to the main page
		if (null == user) {
			result = new MainController().main();
		} else {
			result = new ModelAndView("/blog", "entries", getEntries(userId));
			result.addObject("blogger", user);
			// If we have rights, we inject the value true for edition
			if (Utils.isAdmin() || user.getUserId().equals(Utils.getMyUserId())) {
				result.addObject("edition", true);
			}
		}
		return result;
	}

	/**
	 * Method that retrieves the entries in reverse chronological Order for a given User, all of them if not specified
	 * @param userId owner of the entries, all of them when no user id specified
	 * @return the list of entries belonging to the user.
	 */
	private List<Entry> getEntries(final Integer userId) {

		final EntryDao entryDao = new EntryDao();
		final List<Entry> entries;
		if (null != userId) {
			entries = entryDao.getAllEntries(userId);
		} else {
			entries = entryDao.getAllEntries();
		}
		return entries;
	}
}
