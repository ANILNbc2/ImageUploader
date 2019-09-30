package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;


    @Autowired
    private CommentService commentService;

    //This method will be called from images/image.html with imageId and title as path Variables along with the comment and httpSession.
    //It will be used to add comments to the image.
    @RequestMapping(value = "/image/{id}/{title}/comments", method = RequestMethod.POST)
    public String commentImageSubmit(@PathVariable("id") Integer imageId, @PathVariable("title") String title, Comment comment, HttpSession session) throws IOException {

        //Using ImageId, fetch the Image using ImageService.
        Image image = imageService.getImage(imageId);

        //Fetch the current logged On User from Session.
        User user = (User) session.getAttribute("loggeduser");

        //Set image in Comment entity.
        comment.setImage(image);

        //Set the User in Comment Entity
        comment.setUser(user);

        //set date
        comment.setDate(new Date());

        //Add comment to image.
        image.getComments().add(comment);

        //Use commentService and add comment to DB
        commentService.addComment(comment);

        //Once done, redirect with updated Image details.
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
