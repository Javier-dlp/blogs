package de.kaufda.controllers;


import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import de.kaufda.common.Utils;
import de.kaufda.dao.EntryDao;
import de.kaufda.dao.UserDao;
import de.kaufda.model.Entry;
import de.kaufda.model.User;

/**
 * Controller for all the actions related to entries in the blog. Here we specify the behavior for
 * navigate "/entry/{entryId}", create "/create/{userId}", save "/save" of an entry.
 *
 * @author Javier
 */
@Controller
@RequestMapping("/entry")
public class EntryController {

	/**
	 * Common DAO for entry objects
	 */
	private final EntryDao entryDao = new EntryDao();
	/**
	 * Common Logger initialization
	 */
	private static final Logger logger = Logger.getLogger(EntryController.class.getName());

	/**
	 * Saves the received entry when the file (if any). It sets the current date when saving.
	 * TODO: create a custom @InitBinder method to retrieve the image altogether with the entry
	 * @param entry Entry to be saved/updated
	 * @param image New image to add to the entry
	 * @return entry page when it fails with an object error, blog page when is a success
	 */
	@RequestMapping("/save")
	public ModelAndView save(final @ModelAttribute("entry")Entry entry, @RequestParam("file") MultipartFile image) {
		
		ModelAndView result;
		// If not entry is provided or an entry without title, will go back to the page with an error message
		if (entry == null || entry.getEntryTitle() == null) {
			result = new ModelAndView("/entry", "entry", entry);
			result.addObject("error", "Entry title is mandatory");
		// If is administrator or the entry belongs to this user, it will proceed
		} else if (hasPermission(entry)) {
			// Sets the currentDate
			entry.setEntryDate(new Date());
			// If it has an image, but fails loading it, will go back to the entry with an error message.
			try {
				if (0L != image.getSize()) {
					entry.setEntryImage(image.getBytes());
				}
				entryDao.saveOrUpdate(entry);
				result = new MainController().myBlog(entry.getUser().getUserId());
			} catch (IOException e) {
				result = new ModelAndView("/entry", "entry", entry);
				result.addObject("error", "failed loading image");
				logger.log(Level.WARNING, "failed loading image", e);
			}
		} else {
			result = new ModelAndView("/entry", "entry", new Entry());
		}
		return result;
	}

	/**
	 * Method to create an entry for the specified user
	 * @param userId Owner of the entry
	 * @return main page when the userId does not exist or trying to create for a User over whom we have no permission
	 */
	@RequestMapping("/create/{userId}")
	public ModelAndView newEntry(final @PathVariable("userId") Integer userId) {

		final ModelAndView result;
		final User user = new UserDao().find(userId);
		// Checks that either we are the owner of the entry or we are administrator
		if (userId != null && (Utils.isAdmin() || userId.equals(Utils.getMyUserId()))) {
			final Entry entry = new Entry();
			entry.setUser(user);
			result = new ModelAndView("/entry", "entry", entry);
			result.addObject("creation", true);
		} else {
			result = new MainController().main();
		}
		return result;
	}

	/**
	 * Method to delete an entry by Id
	 * @param entryId Idenifier of the entry to delete
	 * @return mainpage if the entry does not exist, blog page otherwise
	 */
	@RequestMapping("/delete/{entryId}")
	public ModelAndView delete(final @ModelAttribute("entryId") Integer entryId) {
	
		final Entry entry = entryDao.find(entryId);
		final ModelAndView result;
		// If is administrator or the entry belongs to this user, it will proceed
		if (entry != null) {
			if (hasPermission(entry)) {
				entryDao.delete(entry);
			}
			result = new MainController().myBlog(entry.getUser().getUserId());
		// If there is no entry for the given Id we return to the the main page
		} else {
			result = new MainController().main();
		}
		return result;
	}

	/**
	 * Navigates to the entry specified
	 * @param entryId Identificator of the entry we want to see.
	 * @return entry page with edition=true when we are owner or administrator
	 */
	@RequestMapping("/{entryId}")
	public ModelAndView entry(final @PathVariable("entryId") Integer entryId) {

		final Entry entry = entryDao.find(entryId);
		final ModelAndView result = new ModelAndView("/entry", "entry", entry);
		// If it has an image we send it in a base64 encode to the javascript
		if (entry != null && entry.getEntryImage() != null) {
			result.addObject("photo", Base64.getEncoder().encodeToString(entry.getEntryImage()).toString());
		}
		// If we have rights, we inject the value true for edition
		if (hasPermission(entry)) {
			result.addObject("edition", true);
		}
		return result;
	}

	/**
	 * Method that checks if the user from security context has permissions for a given entry
	 * @param entry Entry over that we check the permission
	 * @return true when we are logged in and we are administrator or the owner of the entr; false otherwise
	 */
	private boolean hasPermission(final Entry entry) {

		// This if checks conditions programatically to prevent NullPointerException
		final boolean result;
		if (Utils.isAdmin()) {
			result = true;
		} else if (null != entry) {
			final Integer myUserId = Utils.getMyUserId();
			// Checks we are actually logged in
			if (myUserId == null) {
				result = false;
			// If the given entry has no Id, is a new entry, and we do have permissions
			} else if (entry.getEntryId() == null) {
				result = true;
			// If the entry is not new, it must has an userId, checks it in the DB
			} else if (entry.getUser() == null) {
				final Entry auxEntry = entryDao.find(entry.getEntryId());
				entry.setUser(auxEntry.getUser());
				if (myUserId.equals(entry.getUser().getUserId())) {
					result = true;
				} else {
					result = false;
				}
			// If none of the above, checks the ownership of the entry
			} else {
				result = myUserId.equals(entry.getUser().getUserId());
			}
		} else {
			result = false;
		}
		return result;
	}
}
